package com.sellics.keywordranker.service;

import com.sellics.keywordranker.model.AutoCompleteResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Service
public class AmazonService {

	private static final String SUGGESTIONS_URL_TEMPLATE = "https://completion.amazon.com/api/2017/suggestions?prefix={prefix}&mid=ATVPDKIKX0DER&alias=aps";

	private final RestTemplate restTemplate;

	public AutoCompleteResponse fetchSuggestionsForPrefix(String prefix) {
		return restTemplate.getForEntity(SUGGESTIONS_URL_TEMPLATE, AutoCompleteResponse.class, prefix).getBody();
	}

}
