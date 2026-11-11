package com.devsuperior.dsmoviejacoco.movie.dto;

import org.hibernate.validator.constraints.URL;

import com.devsuperior.dsmoviejacoco.movie.entity.Movie;
import com.devsuperior.dsmoviejacoco.score.dto.ScoreScale;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record MovieDTO(
		@JsonProperty("id")
		Long movieId,
		@NotBlank(message = "Field is required")
		@Size(min = 5, max = 80, message = "Title must be between 5 and 80 characters")
		String title,
		@PositiveOrZero(message = "Score must be greater than or equal to zero")
		Double score,
		@PositiveOrZero(message = "Count must be greater than or equal to zero")
		Integer count,
		@NotBlank(message = "Field is required")
		@URL(message = "Field must be a valid URL")
		String image) {

	public MovieDTO {
		score = ScoreScale.round(score);
	}

	public static MovieDTO from(Movie movie) {
		return new MovieDTO(movie.getMovieId(), movie.getTitle(), movie.getScore(), movie.getCount(), movie.getImage());
	}

}
