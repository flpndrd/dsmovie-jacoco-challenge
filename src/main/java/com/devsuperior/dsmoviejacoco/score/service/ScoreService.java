package com.devsuperior.dsmoviejacoco.score.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsmoviejacoco.movie.dto.MovieDTO;
import com.devsuperior.dsmoviejacoco.movie.entity.Movie;
import com.devsuperior.dsmoviejacoco.movie.repository.MovieRepository;
import com.devsuperior.dsmoviejacoco.score.dto.ScoreDTO;
import com.devsuperior.dsmoviejacoco.score.entity.Score;
import com.devsuperior.dsmoviejacoco.score.exception.InvalidScoreMovieException;
import com.devsuperior.dsmoviejacoco.score.repository.ScoreRepository;
import com.devsuperior.dsmoviejacoco.user.entity.User;
import com.devsuperior.dsmoviejacoco.user.service.UserService;

@Service
public class ScoreService {

	private final UserService userService;

	private final MovieRepository movieRepository;

	private final ScoreRepository scoreRepository;

	public ScoreService(UserService userService, MovieRepository movieRepository, ScoreRepository scoreRepository) {
		this.userService = userService;
		this.movieRepository = movieRepository;
		this.scoreRepository = scoreRepository;
	}

	@Transactional
	public MovieDTO save(ScoreDTO scoreDTO) {
		User user = userService.findCurrentUser();
		Movie movie = movieRepository.findById(scoreDTO.movieId()).orElseThrow(() -> new InvalidScoreMovieException());
		scoreRepository.saveAndFlush(new Score(movie, user, scoreDTO.score()));
		double averageScore = movie
				.getScores()
				.stream()
				.mapToDouble(score -> score.getScoreValue())
				.average()
				.orElseThrow();

		movie.setScore(averageScore);
		movie.setCount(movie.getScores().size());
		return MovieDTO.from(movie);
	}

}
