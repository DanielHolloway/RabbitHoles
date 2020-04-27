package com.rabbitholes.controller;

import com.rabbitholes.controller.model.ArticleWeb;
import com.rabbitholes.usecase.CreateArticle;
import com.rabbitholes.usecase.FindArticle;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleController {

	private final CreateArticle createArticle;
	private final FindArticle findArticle;

	public ArticleController(final CreateArticle createArticle, final FindArticle findArticle) {
		this.createArticle = createArticle;
		this.findArticle = findArticle;
	}

	public ArticleWeb createArticle(final ArticleWeb articleWeb) {
		var article = articleWeb.toArticle();
		return ArticleWeb.toArticleWeb(createArticle.create(article));
	}

	public ArticleWeb getArticle(final String articleId) {
		return ArticleWeb.toArticleWeb(findArticle.findById(articleId).orElseThrow(() -> new RuntimeException("article not found")));
	}

	public List<ArticleWeb> allArticles() {
		return findArticle.findAllArticles()
			.stream()
			.map(ArticleWeb::toArticleWeb)
			.collect(Collectors.toList());
	}
}
