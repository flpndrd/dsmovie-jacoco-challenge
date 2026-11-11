package com.devsuperior.dsmoviejacoco.movie.exception;

import java.io.Serial;

import com.devsuperior.dsmoviejacoco.exception.BusinessRuleException;

public class MovieInUseException extends BusinessRuleException {

	@Serial
	private static final long serialVersionUID = 1L;

	private static final String MESSAGE = "Movie cannot be deleted because it has scores.";

	public MovieInUseException() {
		super(MESSAGE);
	}

}
