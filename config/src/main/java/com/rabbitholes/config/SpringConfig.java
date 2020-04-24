package com.rabbitholes.config;

import com.rabbitholes.db.hazelcast.HazelcastArticleRepository;
import com.rabbitholes.usecase.port.ArticleRepository;
import com.rabbitholes.usecase.CreateArticle;
import com.rabbitholes.usecase.FindArticle;
import com.rabbitholes.uuid.UuidGenerator;

public class SpringConfig {

	private final ArticleRepository articleRepository = new HazelcastArticleRepository();

	public CreateArticle createArticle() {
		return new CreateArticle(articleRepository, new UuidGenerator());
	}

	public FindArticle findArticle() {
		return new FindArticle(articleRepository);
	}
}
