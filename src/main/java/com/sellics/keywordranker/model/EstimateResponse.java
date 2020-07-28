package com.sellics.keywordranker.model;

import lombok.*;

@Data
@Builder
public class EstimateResponse {
	private String keyword;
	private int score;
}
