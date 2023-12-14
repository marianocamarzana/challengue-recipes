package com.challenge.recipes.configuration;

import com.challenge.recipes.model.dto.Recipe;
import com.challenge.recipes.model.dto.RecipeResponse;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {


	private TypeResolver typeResolver;

	@Autowired
	public SwaggerConfiguration(TypeResolver typeResolver) {
        this.typeResolver = typeResolver;
    }

    private ApiInfo apiInfo() {
		return new ApiInfo("Challengue Recipes REST API", "Api developes for code chyallengue using spoonacular API", "API TOS", "MIT License",
				new Contact("General UserName", "www.baeldung.com", "user-name@gmail.com"),
				"License of API", "API license URL", Collections.emptyList());
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
				.additionalModels(
						typeResolver.resolve(RecipeResponse.class),
						typeResolver.resolve(Recipe.class)
				)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.challenge.recipes.controller"))
				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
				.build();
	}

}