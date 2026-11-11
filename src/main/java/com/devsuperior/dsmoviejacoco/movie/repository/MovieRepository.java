package com.devsuperior.dsmoviejacoco.movie.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmoviejacoco.movie.dto.MovieDTO;
import com.devsuperior.dsmoviejacoco.movie.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

	@Query("""
			SELECT new com.devsuperior.dsmoviejacoco.movie.dto.MovieDTO(
				movie.movieId,
				movie.title,
				movie.score,
				movie.count,
				movie.image)
			FROM Movie movie
			WHERE UPPER(movie.title) LIKE UPPER(CONCAT('%', :title, '%'))
			ORDER BY movie.movieId
			""")
	Page<MovieDTO> listByTitle(String title, Pageable pageable);

	@Query("""
			SELECT COUNT(score) > 0
			FROM Score score
			WHERE score.scoreId.movie.movieId = :movieId
			""")
	boolean existsWithScores(Long movieId);

}
