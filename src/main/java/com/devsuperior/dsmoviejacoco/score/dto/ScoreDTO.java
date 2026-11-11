package com.devsuperior.dsmoviejacoco.score.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ScoreDTO(
		@NotNull(message = "Field is required")
		Long movieId,
		@NotNull(message = "Field is required")
		@PositiveOrZero(message = "Score must be greater than or equal to zero")
		@Max(value = 5, message = "Score must be less than or equal to 5")
		Double score) {

	public ScoreDTO {
		score = ScoreScale.round(score);
	}

}
