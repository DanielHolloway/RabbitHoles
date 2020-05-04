package com.rabbitholes.spring.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitholes.config.SpringConfig;
import com.rabbitholes.controller.ArticleController;
import com.rabbitholes.usecase.CreateArticle;
import com.rabbitholes.usecase.FindArticle;
import com.rabbitholes.usecase.SearchArticle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

	private final SpringConfig config = new SpringConfig();

	@Bean
	public ObjectMapper objectMapper() {
		var objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return objectMapper;
	}

	@Bean
	public CreateArticle createArticle() {
		return config.createArticle();
	}

	@Bean
	public FindArticle findArticle() {
		return config.findArticle();
	}

	@Bean
	public SearchArticle searchArticle() {
		return config.searchArticle();
	}

	@Bean
	public ArticleController articleController() {
		return new ArticleController(createArticle(), findArticle(), searchArticle());
	}
}
