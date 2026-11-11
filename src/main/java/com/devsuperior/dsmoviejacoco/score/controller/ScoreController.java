package com.devsuperior.dsmoviejacoco.score.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dsmoviejacoco.movie.dto.MovieDTO;
import com.devsuperior.dsmoviejacoco.score.dto.ScoreDTO;
import com.devsuperior.dsmoviejacoco.score.service.ScoreService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/scores")
public class ScoreController {

	private final ScoreService scoreService;

	public ScoreController(ScoreService scoreService) {
		this.scoreService = scoreService;
	}

	@PutMapping
	ResponseEntity<MovieDTO> save(@Valid @RequestBody ScoreDTO scoreDTO) {
		MovieDTO movieDTO = scoreService.save(scoreDTO);
		return ResponseEntity.ok(movieDTO);
	}

}
