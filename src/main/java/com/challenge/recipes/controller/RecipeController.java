package com.challenge.recipes.controller;

import com.challenge.recipes.model.dto.RecipeRequest;
import com.challenge.recipes.model.dto.UpdateScoreRequest;
import com.challenge.recipes.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/challengue")
@Slf4j
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(
            @Qualifier("recipeService") RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping(value = "/recipe", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> loadRecipes(@RequestBody RecipeRequest recipeRequest) {
        return new ResponseEntity<>(recipeService.loadRecipesFromClient(recipeRequest.getQuery()), HttpStatus.CREATED);
    }

    @GetMapping(value = "/recipe")
    @ResponseBody
    public ResponseEntity<Object> getRecipeSummary(@RequestParam(value = "ids") List<Long> ids) {
        return new ResponseEntity<>(this.recipeService.getRecipesSummaryByIds(ids), HttpStatus.OK);
    }

    @PatchMapping(value = "/recipe/{id}/score")
    @ResponseBody
    public ResponseEntity<Object> updateScoreRecipe(@PathVariable("id") Long id, @Valid @RequestBody UpdateScoreRequest req) {
        return new ResponseEntity<>(this.recipeService.updateScoreRecipe(id, req.getScore()), HttpStatus.OK);
    }

    @PatchMapping(value = "/recipe/{id}/detail")
    @ResponseBody
    public ResponseEntity<Object> updateDetail(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.recipeService.updateDetailRecipe(id), HttpStatus.OK);

    }

}
