package com.devsuperior.dsmoviejacoco.score.entity;

import com.devsuperior.dsmoviejacoco.movie.entity.Movie;
import com.devsuperior.dsmoviejacoco.user.entity.User;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_score")
public class Score {

	@EmbeddedId
	private ScorePK scoreId;

	private Double scoreValue;

	protected Score() {
	}

	public Score(Movie movie, User user, Double scoreValue) {
		this.scoreId = new ScorePK(movie, user);
		this.scoreValue = scoreValue;
	}

	public Double getScoreValue() {
		return scoreValue;
	}

}
