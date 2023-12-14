package com.challenge.recipes.services.impl;

import com.challenge.recipes.model.dto.RecipeResponse;
import com.challenge.recipes.services.ExternalApiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("externalApiService")
public class ExternalApiServiceImpl implements ExternalApiService {

    @Value("${external.api.url.search.recipes}")
    private String externalApiUrl;

    private final RestTemplate restTemplate;

    public ExternalApiServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public RecipeResponse fetchData(String query) {
        return restTemplate.getForObject(externalApiUrl.replace("{search_term}", query), RecipeResponse.class);
    }

}
