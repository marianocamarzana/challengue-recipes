package com.challenge.recipes.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Data
public class RecipeRequest {

    @NotNull
    private String query;

}
