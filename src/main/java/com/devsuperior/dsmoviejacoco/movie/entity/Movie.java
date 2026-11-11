package com.devsuperior.dsmoviejacoco.movie.entity;

import java.util.HashSet;
import java.util.Set;

import com.devsuperior.dsmoviejacoco.score.entity.Score;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_movie")
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long movieId;

	private String title;

	private Double score;

	private Integer count;

	private String image;

	@OneToMany(mappedBy = "scoreId.movie")
	private Set<Score> scores = new HashSet<>();

	protected Movie() {
	}

	public Movie(String title, Double score, Integer count, String image) {
		this.title = title;
		this.score = score;
		this.count = count;
		this.image = image;
	}

	public Long getMovieId() {
		return movieId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Set<Score> getScores() {
		return scores;
	}

}
