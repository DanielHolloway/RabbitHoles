package com.rabbitholes.config;

import com.rabbitholes.db.InMemoryArticleRepository;
import com.rabbitholes.jug.JugIdGenerator;
import com.rabbitholes.usecase.CreateArticle;
import com.rabbitholes.usecase.FindArticle;
import com.rabbitholes.usecase.port.IdGenerator;
import com.rabbitholes.usecase.port.ArticleRepository;

public class VertxConfig {

	private final ArticleRepository articleRepository = new InMemoryArticleRepository();
	private final IdGenerator idGenerator = new JugIdGenerator();
	private final CreateArticle createArticle = new CreateArticle(articleRepository, idGenerator);
	private final FindArticle findArticle = new FindArticle(articleRepository);

	public CreateArticle createArticle() {
		return createArticle;
	}

	public FindArticle findArticle() {
		return findArticle;
	}
}
