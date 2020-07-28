package com.sellics.keywordranker.controller;

import com.sellics.keywordranker.model.*;
import com.sellics.keywordranker.service.KeywordService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
public class KeywordController {

	private final KeywordService keywordService;

	@GetMapping("estimate")
	public EstimateResponse getEstimate(@RequestParam("keyword") String keyword,
																			@RequestParam(value = "matchType", required = false) MatchType matchType) {
		log.info("Get estimate for '{}'", keyword);
		int score = keywordService.getScoreEstimate(keyword, matchType);

		return EstimateResponse.builder().keyword(keyword).score(score).build();
	}

}
