package com.rabbitholes.db;

import com.rabbitholes.domain.entity.Article;
import com.rabbitholes.usecase.port.ArticleRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryArticleRepository implements ArticleRepository {

	private final Map<String, Article> inMemoryDb = new HashMap<>();

	@Override
	public Article create(final Article article) {
		inMemoryDb.put(article.getId(), article);
		return article;
	}

	@Override
	public Optional<Article> findById(final String id) {
		return Optional.ofNullable(inMemoryDb.get(id));
	}

	@Override
	public Optional<Article> findByTitle(final String title) {
		return inMemoryDb.values().stream()
			.filter(article -> article.getTitle().equals(title))
			.findAny();
	}

	@Override
	public List<Article> findAllArticles() {
		return new ArrayList<>(inMemoryDb.values());
	}
}
