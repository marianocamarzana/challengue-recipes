package com.challenge.recipes.services;

import com.challenge.recipes.model.dto.RecipeResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ExternalApiService {

	RecipeResponse fetchData(String query);

	String getSourceUrl(Long id) throws JsonProcessingException;

	String getDetailRecipeFromSourceUrl(String sourceUrl) throws JsonProcessingException;

}
