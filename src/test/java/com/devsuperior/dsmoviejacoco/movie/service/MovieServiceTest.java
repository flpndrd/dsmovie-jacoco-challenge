package com.devsuperior.dsmoviejacoco.movie.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.devsuperior.dsmoviejacoco.movie.MovieFixture;
import com.devsuperior.dsmoviejacoco.movie.dto.MovieDTO;
import com.devsuperior.dsmoviejacoco.movie.entity.Movie;
import com.devsuperior.dsmoviejacoco.movie.exception.MovieInUseException;
import com.devsuperior.dsmoviejacoco.movie.exception.MovieNotFoundException;
import com.devsuperior.dsmoviejacoco.movie.repository.MovieRepository;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

	private static final String UPDATED_TITLE = "Updated Movie";

	@Mock
	private MovieRepository movieRepository;

	@InjectMocks
	private MovieService movieService;

	@Test
	void findAllShouldReturnPagedMovieDTO() {
		Pageable pageable = PageRequest.of(0, 12);
		MovieDTO movieDTO = MovieDTO.from(MovieFixture.anUnscoredMovie());
		given(movieRepository.listByTitle("Test", pageable)).willReturn(new PageImpl<>(List.of(movieDTO)));

		Page<MovieDTO> movies = movieService.list("Test", pageable);

		assertThat(movies.getNumberOfElements()).isEqualTo(1);
		assertThat(movies.getContent().getFirst().title()).isEqualTo(MovieFixture.MOVIE_TITLE);
	}

	@Test
	void findByIdShouldReturnMovieDTOWhenIdExists() {
		given(movieRepository.findById(MovieFixture.MOVIE_ID)).willReturn(Optional.of(MovieFixture.anUnscoredMovie()));

		MovieDTO movieDTO = movieService.find(MovieFixture.MOVIE_ID);

		assertThat(movieDTO.movieId()).isEqualTo(MovieFixture.MOVIE_ID);
		assertThat(movieDTO.title()).isEqualTo(MovieFixture.MOVIE_TITLE);
	}

	@Test
	void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		given(movieRepository.findById(MovieFixture.NONEXISTENT_MOVIE_ID)).willReturn(Optional.empty());

		assertThatThrownBy(() -> movieService.find(MovieFixture.NONEXISTENT_MOVIE_ID))
				.isInstanceOf(MovieNotFoundException.class);
	}

	@Test
	void insertShouldReturnMovieDTO() {
		MovieDTO newMovieDTO = MovieDTO.from(MovieFixture.anUnscoredMovie());
		given(movieRepository.save(any(Movie.class))).willReturn(MovieFixture.anUnscoredMovie());

		MovieDTO createdMovieDTO = movieService.create(newMovieDTO);

		assertThat(createdMovieDTO.movieId()).isEqualTo(MovieFixture.MOVIE_ID);
		assertThat(createdMovieDTO.title()).isEqualTo(MovieFixture.MOVIE_TITLE);
	}

	@Test
	void updateShouldReturnMovieDTOWhenIdExists() {
		given(movieRepository.findById(MovieFixture.MOVIE_ID)).willReturn(Optional.of(MovieFixture.anUnscoredMovie()));

		MovieDTO updatedMovieDTO = movieService.update(MovieFixture.MOVIE_ID, anUpdatedMovieDTO());

		assertThat(updatedMovieDTO.movieId()).isEqualTo(MovieFixture.MOVIE_ID);
		assertThat(updatedMovieDTO.title()).isEqualTo(UPDATED_TITLE);
	}

	@Test
	void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		given(movieRepository.findById(MovieFixture.NONEXISTENT_MOVIE_ID)).willReturn(Optional.empty());

		assertThatThrownBy(() -> movieService.update(MovieFixture.NONEXISTENT_MOVIE_ID, anUpdatedMovieDTO()))
				.isInstanceOf(MovieNotFoundException.class);
	}

	@Test
	void deleteShouldDoNothingWhenIdExists() {
		given(movieRepository.existsById(MovieFixture.MOVIE_ID)).willReturn(true);
		given(movieRepository.existsWithScores(MovieFixture.MOVIE_ID)).willReturn(false);

		movieService.delete(MovieFixture.MOVIE_ID);

		then(movieRepository).should().deleteById(MovieFixture.MOVIE_ID);
	}

	@Test
	void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		given(movieRepository.existsById(MovieFixture.NONEXISTENT_MOVIE_ID)).willReturn(false);

		assertThatThrownBy(() -> movieService.delete(MovieFixture.NONEXISTENT_MOVIE_ID))
				.isInstanceOf(MovieNotFoundException.class);
		then(movieRepository).should(never()).deleteById(MovieFixture.NONEXISTENT_MOVIE_ID);
	}

	@Test
	void deleteShouldThrowMovieInUseExceptionWhenDependentId() {
		given(movieRepository.existsById(MovieFixture.MOVIE_ID)).willReturn(true);
		given(movieRepository.existsWithScores(MovieFixture.MOVIE_ID)).willReturn(true);

		assertThatThrownBy(() -> movieService.delete(MovieFixture.MOVIE_ID)).isInstanceOf(MovieInUseException.class);
		then(movieRepository).should(never()).deleteById(MovieFixture.MOVIE_ID);
	}

	private MovieDTO anUpdatedMovieDTO() {
		String updatedImageUrl = "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/vIgyYkXkg6NC2whRbYjBD7eb3Er.jpg";
		return new MovieDTO(null, UPDATED_TITLE, 4.5, 2, updatedImageUrl);
	}

}
