package com.rabbitholes.controller;

import com.rabbitholes.controller.model.ArticleWeb;
import com.rabbitholes.usecase.CreateArticle;
import com.rabbitholes.usecase.FindArticle;
import com.rabbitholes.usecase.SearchArticle;

import java.util.List;
import java.util.stream.Collectors;

public class ArticleController {

	private final CreateArticle createArticle;
	private final FindArticle findArticle;
	private final SearchArticle searchArticle;

	public ArticleController(final CreateArticle createArticle, final FindArticle findArticle, final SearchArticle searchArticle) {
		this.createArticle = createArticle;
		this.findArticle = findArticle;
		this.searchArticle = searchArticle;
	}

	public ArticleWeb createArticle(final ArticleWeb articleWeb) {
		var article = articleWeb.toArticle();
		return ArticleWeb.toArticleWeb(createArticle.create(article));
	}

	public ArticleWeb getArticle(final String articleId) {
		return ArticleWeb.toArticleWeb(findArticle.findById(articleId)
				.orElseThrow(() -> new RuntimeException("Article not found")));
	}

	public List<ArticleWeb> allArticles() {
		return findArticle.findAllArticles()
			.stream()
			.map(ArticleWeb::toArticleWeb)
			.collect(Collectors.toList());
	}

	public List<ArticleWeb> searchArticle(final String articleTitle) {
		return searchArticle.searchByTitle(articleTitle)
				.stream()
				.map(ArticleWeb::toArticleWeb)
				.collect(Collectors.toList());
	}
}
