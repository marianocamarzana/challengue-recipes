package com.challenge.recipes.services;

import com.challenge.recipes.model.RecipeSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("recipeRepository")
public interface RecipeRepository extends JpaRepository<RecipeSummary, Long> {

}
