package com.sellics.keywordranker.model;

import lombok.*;

@Data
@AllArgsConstructor
public class MatcherProcessResult {
	int keywordScore;
	int maxPossibleScore;
}
