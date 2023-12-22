package com.challenge.recipes.controller;

import com.challenge.recipes.model.dto.Recipe;
import com.challenge.recipes.model.dto.RecipeRequest;
import com.challenge.recipes.model.dto.RecipeResponse;
import com.challenge.recipes.model.dto.UpdateScoreRequest;
import com.challenge.recipes.services.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Retrieve the recipes according to the QUERY filter through the spoonacular API and then persisting into H2 database.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success",
            content = @Content(schema = @Schema(implementation = RecipeResponse.class)))
    })
    public ResponseEntity<Object> loadRecipes(@Valid @RequestBody RecipeRequest recipeRequest) {
        return new ResponseEntity<>(recipeService.loadRecipesFromClient(recipeRequest.getQuery()), HttpStatus.CREATED);
    }

    @GetMapping(value = "/recipe")
    @Operation(summary = "Retrieves the list of recipes from the H2 Database filtering by the list of ids. If ids are not sent, all recipes are recovered.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success",
            content = @Content(schema = @Schema(implementation = Recipe.class))),
    })
    public ResponseEntity<Object> getRecipeSummary(@RequestParam(value = "ids") List<Long> ids) {
        return new ResponseEntity<>(this.recipeService.getRecipesSummaryByIds(ids), HttpStatus.OK);
    }

    @PatchMapping(value = "/recipe/{id}/score")
    @Operation(summary = "Update a specific recipe with the indicated score.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success",
            content = @Content(schema = @Schema(implementation = Recipe.class))),
    })
    public ResponseEntity<Object> updateScoreRecipe(@PathVariable("id") Long id, @Valid @RequestBody UpdateScoreRequest req) {
        return new ResponseEntity<>(this.recipeService.updateScoreRecipe(id, req.getScore()), HttpStatus.OK);
    }

    @PatchMapping(value = "/recipe/{id}/detail")
    @Operation(summary = "Update a specific recipe with the sourceUrl obtained from spoonacular API from detail recipe")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success",
            content = @Content(schema = @Schema(implementation = Recipe.class))),
    })
    public ResponseEntity<Object> updateDetail(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.recipeService.updateDetailRecipe(id), HttpStatus.OK);

    }

}
