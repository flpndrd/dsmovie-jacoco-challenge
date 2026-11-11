package com.devsuperior.dsmoviejacoco.score.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class ScoreScale {

	private static final int DECIMAL_PLACES = 2;

	private ScoreScale() {
	}

	public static Double round(Double score) {
		if (score == null) {
			return null;
		}
		return BigDecimal.valueOf(score).setScale(DECIMAL_PLACES, RoundingMode.HALF_EVEN).doubleValue();
	}

}
