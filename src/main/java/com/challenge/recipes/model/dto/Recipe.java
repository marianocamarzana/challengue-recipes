package com.challenge.recipes.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    private Long id;
    private Integer readyInMinutes;
    private String sourceUrl;
    private String image;
    private Integer servings;
    private String title;
    private Integer score;
}
