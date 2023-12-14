package com.challenge.recipes.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recipe_summary")
public class RecipeSummary {

    @Id
    private Long id;
    @Column
    private Integer readyInMinutes;
    @Column
    private String sourceUrl;
    @Column
    private String image;
    @Column
    private Integer servings;
    @Column
    private String title;
    @Column
    private Integer score;
}
