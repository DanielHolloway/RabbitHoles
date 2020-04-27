package com.rabbitholes.config;

import com.rabbitholes.db.InMemoryArticleRepository;
import com.rabbitholes.usecase.port.IdGenerator;
import com.rabbitholes.usecase.port.ArticleRepository;
import com.rabbitholes.jug.JugIdGenerator;
import com.rabbitholes.usecase.CreateArticle;
import com.rabbitholes.usecase.FindArticle;

public class ManualConfig {
	private final ArticleRepository articleRepository = new InMemoryArticleRepository();
	private final IdGenerator idGenerator = new JugIdGenerator();

	public CreateArticle createArticle() {
		return new CreateArticle(articleRepository, idGenerator);
	}

	public FindArticle findArticle() {
		return new FindArticle(articleRepository);
	}
}
