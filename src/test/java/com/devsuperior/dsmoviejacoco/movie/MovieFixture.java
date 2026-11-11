package com.devsuperior.dsmoviejacoco.movie;

import org.springframework.test.util.ReflectionTestUtils;

import com.devsuperior.dsmoviejacoco.movie.entity.Movie;

public final class MovieFixture {

	public static final Long MOVIE_ID = 1L;

	public static final Long NONEXISTENT_MOVIE_ID = 999L;

	public static final String MOVIE_TITLE = "Test Movie";

	private MovieFixture() {
	}

	public static Movie anUnscoredMovie() {
		String witcherImageUrl = "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/jBJWaqoSCiARWtfV0GlqHrcdidd.jpg";
		Movie movie = new Movie(MOVIE_TITLE, 0.0, 0, witcherImageUrl);
		ReflectionTestUtils.setField(movie, "movieId", MOVIE_ID);
		return movie;
	}

}
