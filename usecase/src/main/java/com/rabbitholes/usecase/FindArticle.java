package com.rabbitholes.usecase;

import com.rabbitholes.domain.entity.Article;
import com.rabbitholes.usecase.port.ArticleRepository;
import java.util.List;
import java.util.Optional;

public final class FindArticle {

	private final ArticleRepository repository;

	public FindArticle(final ArticleRepository repository) {
		this.repository = repository;
	}

	public Optional<Article> findById(final String id) {
		return repository.findById(id);
	}

	public List<Article> findAllArticles() {
		return repository.findAllArticles();
	}
}
