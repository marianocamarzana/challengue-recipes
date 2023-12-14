package com.challenge.recipes.exception.model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeNotFoundException extends RuntimeException {

    private Long id;

}