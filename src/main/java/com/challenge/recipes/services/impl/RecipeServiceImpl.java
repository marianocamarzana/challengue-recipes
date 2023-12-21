package com.challenge.recipes.services.impl;

import com.challenge.recipes.exception.model.RecipeNotFoundException;
import com.challenge.recipes.model.RecipeSummary;
import com.challenge.recipes.model.dto.Recipe;
import com.challenge.recipes.model.dto.RecipeResponse;
import com.challenge.recipes.services.ExternalApiService;
import com.challenge.recipes.services.RecipeRepository;
import com.challenge.recipes.services.RecipeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("recipeService")
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final ExternalApiService externalApiService;
    private final ModelMapper modelMapper;

    public RecipeServiceImpl(@Qualifier("recipeRepository") RecipeRepository recipeRepository,
                             @Qualifier("externalApiService") ExternalApiService externalApiService, ModelMapper modelMapper) {
        this.recipeRepository = recipeRepository;
        this.externalApiService = externalApiService;
        this.modelMapper = modelMapper;
    }


    private List<Recipe> filterPersistedRecipes(List<Recipe> recipesFromClient) {
        List<Long> ids = recipesFromClient.stream().map(Recipe::getId)
                .collect(Collectors.toList());
        List<Long> persistedRecipes = recipeRepository.getPersistedRecipesIds(ids);
        return recipesFromClient.stream().filter(recipe -> !persistedRecipes.contains(recipe.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public RecipeResponse loadRecipesFromClient(String query) {
        RecipeResponse result = externalApiService.fetchData(query);
        if (result != null) {
            List<Recipe> recipes = filterPersistedRecipes(result.getResults());
            if (!recipes.isEmpty()) {
                modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
                List<RecipeSummary> entities = recipes.stream().map(externalRecipe -> {
                    return modelMapper.map(externalRecipe, RecipeSummary.class);
                }).collect(Collectors.toList());
                entities.forEach(recipeRepository::save);
            }
        } else {
            throw new RecipeNotFoundException();
        }
        return result;
    }

    @Override
    public List<Recipe> getRecipesSummaryByIds(List<Long> ids) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<Recipe> result = null;
        if (!ids.isEmpty()) {
            result = recipeRepository.findAllById(ids)
                    .stream().map(entity -> modelMapper.map(entity, Recipe.class))
                    .collect(Collectors.toList());
        } else {
            result = recipeRepository.findAll()
                    .stream().map(entity -> modelMapper.map(entity, Recipe.class))
                    .collect(Collectors.toList());
        }
        if (!result.isEmpty()) {
            return result;
        } else {
            throw new RecipeNotFoundException();
        }
    }

    @Override
    public Recipe updateScoreRecipe(Long id, Integer newScore) {
        RecipeSummary recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));
        recipe.setScore(newScore);
        recipeRepository.save(recipe);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(recipe, Recipe.class);
    }

    @Override
    public Recipe updateDetailRecipe(Long id) {
        String sourceUrl = null;
        String recipeDetail = null;
        try {
            sourceUrl = externalApiService.getSourceUrl(id);
            recipeDetail = externalApiService.getDetailRecipeFromSourceUrl(sourceUrl);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        RecipeSummary recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));
            recipe.setSourceUrlBlob(recipeDetail.getBytes());
            recipeRepository.save(recipe);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(recipe, Recipe.class);
    }
}

