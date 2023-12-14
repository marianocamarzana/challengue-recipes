package com.challenge.recipes.services;

import com.challenge.recipes.model.dto.RecipeResponse;

public interface ExternalApiService {

	RecipeResponse fetchData(String query);

}
