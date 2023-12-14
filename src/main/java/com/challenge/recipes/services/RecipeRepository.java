package com.challenge.recipes.services;

import com.challenge.recipes.model.RecipeSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("recipeRepository")
public interface RecipeRepository extends JpaRepository<RecipeSummary, Long> {

    @Query("SELECT recipe.id FROM RecipeSummary recipe where recipe.id in :ids")
    List<Long> getPersistedRecipesIds(@Param("ids") List<Long> ids);

}
