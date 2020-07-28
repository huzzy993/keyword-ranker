package com.sellics.keywordranker.model;

import lombok.*;

import java.util.*;

@Getter
@Setter
@EqualsAndHashCode
public class AutoCompleteResponse {
	private Set<Suggestion> suggestions = new HashSet<>();

	@Override
	public String toString() {
		return suggestions.toString();
	}
}
