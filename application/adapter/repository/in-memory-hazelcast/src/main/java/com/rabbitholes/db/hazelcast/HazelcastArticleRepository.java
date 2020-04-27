package com.rabbitholes.db.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.rabbitholes.db.hazelcast.model.ArticleDb;
import com.rabbitholes.domain.entity.Article;
import com.rabbitholes.usecase.port.ArticleRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HazelcastArticleRepository implements ArticleRepository {

	private static final HazelcastInstance HAZELCAST = Hazelcast.getInstance();
	private static final String MAP_NAME = "article";

	@Override
	public Article create(final Article article) {
		var articleDb = ArticleDb.from(article);
		var map = HAZELCAST.getMap(MAP_NAME);
		map.put(articleDb.getId(), articleDb);
		return article;
	}

	@Override
	public Optional<Article> findById(final String id) {
		var map = HAZELCAST.<String, ArticleDb>getMap(MAP_NAME);
		if (map.containsKey(id)) {
			var articleDb = map.get(id);
			return Optional.of(articleDb.toArticle());
		}
		return Optional.empty();
	}

	@Override
	public Optional<Article> findByTitle(final String title) {
		return HAZELCAST.<String, ArticleDb>getMap(MAP_NAME)
			.values().stream()
			.filter(articleDb -> articleDb.toArticle().getTitle().equals(title))
			.map(ArticleDb::toArticle)
			.findAny();
	}

	@Override
	public List<Article> findAllArticles() {
		return HAZELCAST.<String, ArticleDb>getMap(MAP_NAME)
			.values()
			.stream()
			.map(ArticleDb::toArticle)
			.collect(Collectors.toList());
	}
}
