package com.sellics.keywordranker.service.matcher;

import com.sellics.keywordranker.model.*;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.regex.Pattern;

import static com.sellics.keywordranker.model.MatchType.PHRASE;

@Component
public class PhraseMatcher implements KeywordMatcher {

	/**
	 * PhraseMatcher uses a word boundary regex to determine if the keyword is present in the list.
	 *
	 * @param suggestions Set of suggestions from Amazon API
	 * @param keyword     searched keyword
	 * @return a {@link MatcherProcessResult} with keywordScore equal to how many times a keyword appears in the suggestions list and maxPossibleScore equal to the size of the suggestions list
	 */
	@Override
	public MatcherProcessResult process(Set<Suggestion> suggestions, String keyword) {
		Pattern pattern = Pattern.compile("\\b" + keyword + "\\b");
		int score = (int) suggestions.stream()
						.filter(p -> pattern.matcher(p.getValue()).find())
						.count();
		return new MatcherProcessResult(score, suggestions.size());
	}

	@Override
	public MatchType matchType() {
		return PHRASE;
	}

}
