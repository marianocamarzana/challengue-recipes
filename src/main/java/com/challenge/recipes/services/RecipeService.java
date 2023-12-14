package com.challenge.recipes.services;

import com.challenge.recipes.model.dto.Recipe;
import com.challenge.recipes.model.dto.RecipeResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface RecipeService {

    public RecipeResponse loadRecipesFromClient(String query);

    public List<Recipe> getRecipesSummaryByIds(List<Long> ids);

    public Recipe updateScoreRecipe(Long id, Integer newScore);

    public Recipe updateDetailRecipe(Long id);

}
