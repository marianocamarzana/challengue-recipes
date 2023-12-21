package com.challenge.recipes.services.impl;

import com.challenge.recipes.exception.RestTemplateResponseErrorHandler;
import com.challenge.recipes.exception.model.RecipeNotFoundException;
import com.challenge.recipes.model.dto.RecipeResponse;
import com.challenge.recipes.services.ExternalApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("externalApiService")
public class ExternalApiServiceImpl implements ExternalApiService {

    @Value("${external.api.url.search.recipes}")
    private String externalApiUrlSearch;

    @Value("${external.api.url.recipe}")
    private String externalApiUrlRecipe;

    private final RestTemplate restTemplate;

    @Autowired
    public ExternalApiServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
    }

    @Override
    public RecipeResponse fetchData(String query) {
        return restTemplate.getForObject(externalApiUrlSearch.replace("{search_term}", query), RecipeResponse.class);
    }

    @Override
    public String getSourceUrl(Long id) throws JsonProcessingException {
        ResponseEntity<String> response = restTemplate.getForEntity(externalApiUrlRecipe.replace("{recipe_id}", id.toString()), String.class);
        String json = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);
        return jsonNode.get("sourceUrl").asText();
    }

    @Override
    public String getDetailRecipeFromSourceUrl(String sourceUrl) throws JsonProcessingException {
        ResponseEntity<String> response = restTemplate.exchange(sourceUrl, HttpMethod.GET, null, String.class);
        HttpStatus status = response.getStatusCode();
        if (status.is2xxSuccessful()) {
            return response.getBody();
        } else if (status.is3xxRedirection()) {
            String redirectedUrl = response.getHeaders().getLocation().toString();
            ResponseEntity<String> redirectedResponse = restTemplate.exchange(redirectedUrl, HttpMethod.GET, null, String.class);
           return redirectedResponse.getBody();
        }
        throw new RecipeNotFoundException();
    }

}
