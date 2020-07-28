package com.sellics.keywordranker.service.matcher;

import com.sellics.keywordranker.model.*;

import java.util.Set;

public interface KeywordMatcher {

	MatcherProcessResult process(Set<Suggestion> suggestions, String keyword);

	MatchType matchType();
}

