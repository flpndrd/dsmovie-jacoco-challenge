package com.devsuperior.dsmoviejacoco.movie.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsmoviejacoco.movie.dto.MovieDTO;
import com.devsuperior.dsmoviejacoco.movie.entity.Movie;
import com.devsuperior.dsmoviejacoco.movie.exception.MovieInUseException;
import com.devsuperior.dsmoviejacoco.movie.exception.MovieNotFoundException;
import com.devsuperior.dsmoviejacoco.movie.repository.MovieRepository;

@Service
public class MovieService {

	private final MovieRepository movieRepository;

	public MovieService(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	@Transactional(readOnly = true)
	public Page<MovieDTO> list(String title, Pageable pageable) {
		return movieRepository.listByTitle(title, pageable);
	}

	@Transactional(readOnly = true)
	public MovieDTO find(Long movieId) {
		Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException());
		return MovieDTO.from(movie);
	}

	@Transactional
	public MovieDTO create(MovieDTO movieDTO) {
		Movie movie = new Movie(movieDTO.title(), movieDTO.score(), movieDTO.count(), movieDTO.image());
		Movie savedMovie = movieRepository.save(movie);
		return MovieDTO.from(savedMovie);
	}

	@Transactional
	public MovieDTO update(Long movieId, MovieDTO movieDTO) {
		Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException());
		movie.setTitle(movieDTO.title());
		movie.setScore(movieDTO.score());
		movie.setCount(movieDTO.count());
		movie.setImage(movieDTO.image());
		return MovieDTO.from(movie);
	}

	@Transactional
	public void delete(Long movieId) {
		if (!movieRepository.existsById(movieId)) {
			throw new MovieNotFoundException();
		}

		if (movieRepository.existsWithScores(movieId)) {
			throw new MovieInUseException();
		}
		movieRepository.deleteById(movieId);
	}

}
