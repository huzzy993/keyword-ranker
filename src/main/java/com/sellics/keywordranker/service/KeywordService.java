package com.sellics.keywordranker.service;

import com.sellics.keywordranker.model.*;
import com.sellics.keywordranker.service.matcher.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@Service
@AllArgsConstructor
@Slf4j
public class KeywordService {

	private final AmazonService amazonService;

	private final ExecutorService executorService;

	private final List<KeywordMatcher> matchers;

	private final PhraseMatcher defaultMatcher;

	@SuppressWarnings("rawtypes")
	public int getScoreEstimate(String keyword, MatchType matchType) {
		String trimmedKeyword = StringUtils.trimWhitespace(keyword);
		KeywordMatcher matcher = getMatcher(matchType);

		AtomicInteger keywordScore = new AtomicInteger();
		AtomicInteger maxPossibleScore = new AtomicInteger();

		CompletableFuture[] futures = new CompletableFuture[trimmedKeyword.length()];
		int i = 0;

		StringBuilder prefix = new StringBuilder();
		for (char ch : trimmedKeyword.toCharArray()) {
			String prefixStr = prefix.append(ch).toString();
			int weight = trimmedKeyword.length() - prefixStr.length() + 1;

			CompletableFuture<Void> completableFuture = supplyAsync(() -> {
				AutoCompleteResponse autoCompleteResponse = amazonService.fetchSuggestionsForPrefix(prefixStr);
				MatcherProcessResult processResult = matcher.process(autoCompleteResponse.getSuggestions(), trimmedKeyword);

				keywordScore.getAndAdd(weight * processResult.getKeywordScore());
				maxPossibleScore.getAndAdd(weight * processResult.getMaxPossibleScore());

				return null;
			}, executorService);
			futures[i] = completableFuture;
			i++;
		}

		try {
			CompletableFuture.allOf(futures).get(8L, TimeUnit.SECONDS);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			log.error("interrupted future", e);
		}

		int maxObtainable = maxPossibleScore.get();
		log.info("total score for '{}' is '{}' while max obtainable is '{}'", trimmedKeyword, keywordScore, maxObtainable);
		return maxObtainable == 0 ? 0 : (keywordScore.get() * 100) / maxObtainable;
	}

	private KeywordMatcher getMatcher(MatchType matchType) {
		return matchers.stream().filter(s -> s.matchType().equals(matchType)).findFirst().orElse(defaultMatcher);
	}

}
