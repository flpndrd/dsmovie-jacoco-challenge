package com.devsuperior.dsmoviejacoco.movie.exception;

import java.io.Serial;

import com.devsuperior.dsmoviejacoco.exception.ResourceNotFoundException;

public class MovieNotFoundException extends ResourceNotFoundException {

	@Serial
	private static final long serialVersionUID = 1L;

	private static final String MESSAGE = "Movie not found.";

	public MovieNotFoundException() {
		super(MESSAGE);
	}

}
