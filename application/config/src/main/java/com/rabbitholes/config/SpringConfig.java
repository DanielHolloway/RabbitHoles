package com.rabbitholes.config;

import com.rabbitholes.uuid.UuidGenerator;
import com.rabbitholes.db.hazelcast.HazelcastArticleRepository;
import com.rabbitholes.wikipedia.WikipediaExternalApi;
import com.rabbitholes.usecase.port.ArticleRepository;
import com.rabbitholes.usecase.port.ExternalApi;
import com.rabbitholes.usecase.CreateArticle;
import com.rabbitholes.usecase.FindArticle;
import com.rabbitholes.usecase.SearchArticle;

public class SpringConfig {

	private final ArticleRepository articleRepository = new HazelcastArticleRepository();

	private final ExternalApi externalApi = new WikipediaExternalApi();

	public CreateArticle createArticle() {
		return new CreateArticle(articleRepository, new UuidGenerator());
	}

	public FindArticle findArticle() {
		return new FindArticle(articleRepository);
	}

	public SearchArticle searchArticle() { return new SearchArticle(externalApi); }
}
