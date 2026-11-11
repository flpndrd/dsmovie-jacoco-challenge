package com.devsuperior.dsmoviejacoco.score.exception;

import java.io.Serial;

import com.devsuperior.dsmoviejacoco.exception.InvalidRequestException;

public class InvalidScoreMovieException extends InvalidRequestException {

	@Serial
	private static final long serialVersionUID = 1L;

	private static final String MESSAGE = "Score has a movie that does not exist.";

	public InvalidScoreMovieException() {
		super(MESSAGE);
	}

}
