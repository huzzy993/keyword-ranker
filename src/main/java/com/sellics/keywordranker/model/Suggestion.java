package com.sellics.keywordranker.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Suggestion {
	private String value;

	public static Suggestion suggestion(String value) {
		return new Suggestion(value);
	}

	@Override
	public String toString() {
		return value;
	}
}
