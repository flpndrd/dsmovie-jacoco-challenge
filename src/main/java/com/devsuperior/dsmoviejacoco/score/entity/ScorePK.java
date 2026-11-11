package com.devsuperior.dsmoviejacoco.score.entity;

import java.util.Objects;

import com.devsuperior.dsmoviejacoco.movie.entity.Movie;
import com.devsuperior.dsmoviejacoco.user.entity.User;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class ScorePK {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movie_id")
	private Movie movie;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	protected ScorePK() {
	}

	ScorePK(Movie movie, User user) {
		this.movie = movie;
		this.user = user;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ScorePK other)) {
			return false;
		}
		return Objects.equals(movie, other.movie) && Objects.equals(user, other.user);
	}

	@Override
	public int hashCode() {
		return Objects.hash(movie, user);
	}

}
