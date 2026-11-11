package com.devsuperior.dsmoviejacoco.movie.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.dsmoviejacoco.movie.dto.MovieDTO;
import com.devsuperior.dsmoviejacoco.movie.service.MovieService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/movies")
public class MovieController {

	private final MovieService movieService;

	public MovieController(MovieService movieService) {
		this.movieService = movieService;
	}

	@GetMapping("/{movieId}")
	ResponseEntity<MovieDTO> find(@PathVariable Long movieId) {
		MovieDTO movieDTO = movieService.find(movieId);
		return ResponseEntity.ok(movieDTO);
	}

	@GetMapping
	ResponseEntity<Page<MovieDTO>> list(@RequestParam(defaultValue = "") String title, Pageable pageable) {
		Page<MovieDTO> movies = movieService.list(title, pageable);
		return ResponseEntity.ok(movies);
	}

	@PostMapping
	ResponseEntity<MovieDTO> create(@Valid @RequestBody MovieDTO movieDTO) {
		MovieDTO createdMovieDTO = movieService.create(movieDTO);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{movieId}")
				.buildAndExpand(createdMovieDTO.movieId())
				.toUri();

		return ResponseEntity.created(uri).body(createdMovieDTO);
	}

	@PutMapping("/{movieId}")
	ResponseEntity<MovieDTO> update(@PathVariable Long movieId, @Valid @RequestBody MovieDTO movieDTO) {
		MovieDTO updatedMovieDTO = movieService.update(movieId, movieDTO);
		return ResponseEntity.ok(updatedMovieDTO);
	}

	@DeleteMapping("/{movieId}")
	ResponseEntity<Void> delete(@PathVariable Long movieId) {
		movieService.delete(movieId);
		return ResponseEntity.noContent().build();
	}

}
