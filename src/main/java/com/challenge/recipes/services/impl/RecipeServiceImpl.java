package com.challenge.recipes.services.impl;

import com.challenge.recipes.exception.model.RecipeNotFoundException;
import com.challenge.recipes.model.RecipeSummary;
import com.challenge.recipes.model.dto.Recipe;
import com.challenge.recipes.model.dto.RecipeResponse;
import com.challenge.recipes.services.ExternalApiService;
import com.challenge.recipes.services.RecipeRepository;
import com.challenge.recipes.services.RecipeService;
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

    @Override
    public RecipeResponse loadRecipesFromClient(String query) {
        RecipeResponse result = externalApiService.fetchData(query);
        if (result != null) {
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            List<RecipeSummary> entities = result.getResults().stream().map(externalRecipe -> {
                return modelMapper.map(externalRecipe, RecipeSummary.class);
            }).toList();
            entities.forEach(this.recipeRepository::save);
        } else{
            throw new RecipeNotFoundException();
        }
        return result;
    }

    @Override
    public List<Recipe> getRecipesSummaryByIds(List<Long> ids) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        if (!ids.isEmpty()) {
            return recipeRepository.findAllById(ids)
                    .stream().map(entity -> modelMapper.map(entity, Recipe.class))
                    .collect(Collectors.toList());
        } else {
            return recipeRepository.findAll()
                    .stream().map(entity -> modelMapper.map(entity, Recipe.class))
                    .collect(Collectors.toList());
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
}

