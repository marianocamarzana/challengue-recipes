package com.challenge.recipes.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Data
public class UpdateScoreRequest {

    @NotNull(message = "score field should not be null")
    @Min(value = 1, message = "The score should be grather than 0")
    @Max(value = 5, message = "The score should be less than 6")
    private Integer score;

}
