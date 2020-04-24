package com.rabbitholes;

import com.rabbitholes.config.ManualConfig;
import com.rabbitholes.domain.entity.Article;

public class Main {
	public static void main(String[] args) {
		// Setup
		var config = new ManualConfig();
		var createArticle = config.createArticle();
		var findArticle = config.findArticle();
		var article = Article.builder()
			.title("john.doe@gmail.com")
			.topic("doe")
			.link("john")
			.build();

		// Create a article
		var actualCreateArticle = createArticle.create(article);
		System.out.println("Article created with id " + actualCreateArticle.getId());

		// Find a article by id
		var actualFindArticle = findArticle.findById(actualCreateArticle.getId());
		System.out.println("Found article with id " + actualFindArticle.get().getId());

		// List all articles
		var articles = findArticle.findAllArticles();
		System.out.println("List all articles: " + articles);
	}
}
