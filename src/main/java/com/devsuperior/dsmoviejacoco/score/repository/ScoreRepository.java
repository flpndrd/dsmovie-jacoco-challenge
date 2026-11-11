package com.devsuperior.dsmoviejacoco.score.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmoviejacoco.score.entity.Score;
import com.devsuperior.dsmoviejacoco.score.entity.ScorePK;

public interface ScoreRepository extends JpaRepository<Score, ScorePK> {

}
