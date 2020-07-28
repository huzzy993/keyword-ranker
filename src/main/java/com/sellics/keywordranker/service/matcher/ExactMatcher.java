package com.sellics.keywordranker.service.matcher;

import com.sellics.keywordranker.model.*;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.sellics.keywordranker.model.MatchType.EXACT;
import static com.sellics.keywordranker.model.Suggestion.suggestion;

@Component
public class ExactMatcher implements KeywordMatcher {

	/**
	 * ExactMatcher uses a Set#contains to determine if the keyword is present in the list.
	 *
	 * @param suggestions Set of suggestions from Amazon API
	 * @param keyword     searched keyword
	 * @return a {@link MatcherProcessResult} with keywordScore equal 1 (when word is in suggestions list) or 0 (when word is not in suggestions list) and maxPossibleScore equal to 0 or 1
	 */
	@Override
	public MatcherProcessResult process(Set<Suggestion> suggestions, String keyword) {
		int count = suggestions.contains(suggestion(keyword)) ? 1 : 0;
		return new MatcherProcessResult(count, count);
	}

	@Override
	public MatchType matchType() {
		return EXACT;
	}

}
