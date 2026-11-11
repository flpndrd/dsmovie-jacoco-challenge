package com.devsuperior.dsmoviejacoco.score.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devsuperior.dsmoviejacoco.movie.MovieFixture;
import com.devsuperior.dsmoviejacoco.movie.dto.MovieDTO;
import com.devsuperior.dsmoviejacoco.movie.entity.Movie;
import com.devsuperior.dsmoviejacoco.movie.repository.MovieRepository;
import com.devsuperior.dsmoviejacoco.score.dto.ScoreDTO;
import com.devsuperior.dsmoviejacoco.score.entity.Score;
import com.devsuperior.dsmoviejacoco.score.exception.InvalidScoreMovieException;
import com.devsuperior.dsmoviejacoco.score.repository.ScoreRepository;
import com.devsuperior.dsmoviejacoco.user.UserFixture;
import com.devsuperior.dsmoviejacoco.user.entity.User;
import com.devsuperior.dsmoviejacoco.user.service.UserService;

@ExtendWith(MockitoExtension.class)
class ScoreServiceTest {

	private static final Double SCORE_VALUE = 4.5;

	@Mock
	private UserService userService;

	@Mock
	private MovieRepository movieRepository;

	@Mock
	private ScoreRepository scoreRepository;

	@InjectMocks
	private ScoreService scoreService;

	@Test
	void saveScoreShouldReturnMovieDTO() {
		User user = UserFixture.aClientUser();
		given(userService.findCurrentUser()).willReturn(user);
		given(movieRepository.findById(MovieFixture.MOVIE_ID)).willReturn(Optional.of(aMovieScoredBy(user)));

		MovieDTO movieDTO = scoreService.save(new ScoreDTO(MovieFixture.MOVIE_ID, SCORE_VALUE));

		assertThat(movieDTO.movieId()).isEqualTo(MovieFixture.MOVIE_ID);
		assertThat(movieDTO.score()).isEqualTo(SCORE_VALUE);
		assertThat(movieDTO.count()).isEqualTo(1);
		then(scoreRepository).should().saveAndFlush(any(Score.class));
	}

	@Test
	void saveScoreShouldThrowInvalidScoreMovieExceptionWhenNonExistingMovieId() {
		ScoreDTO scoreDTO = new ScoreDTO(MovieFixture.NONEXISTENT_MOVIE_ID, SCORE_VALUE);
		given(userService.findCurrentUser()).willReturn(UserFixture.aClientUser());
		given(movieRepository.findById(MovieFixture.NONEXISTENT_MOVIE_ID)).willReturn(Optional.empty());

		assertThatThrownBy(() -> scoreService.save(scoreDTO)).isInstanceOf(InvalidScoreMovieException.class);
		then(scoreRepository).shouldHaveNoInteractions();
	}

	private Movie aMovieScoredBy(User user) {
		Movie movie = MovieFixture.anUnscoredMovie();
		movie.getScores().add(new Score(movie, user, SCORE_VALUE));
		return movie;
	}

}
