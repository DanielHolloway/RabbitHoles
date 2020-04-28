package com.rabbitholes.usecase;

import com.rabbitholes.domain.entity.Article;
import com.rabbitholes.usecase.exception.ArticleAlreadyExistsException;
import com.rabbitholes.usecase.port.IdGenerator;
import com.rabbitholes.usecase.port.ArticleRepository;
import com.rabbitholes.usecase.validator.ArticleValidator;

public final class CreateArticle {

	private final ArticleRepository repository;
	private final IdGenerator idGenerator;

	public CreateArticle(final ArticleRepository repository, final IdGenerator idGenerator) {
		this.repository = repository;
		this.idGenerator = idGenerator;
	}

	public Article create(final Article article) {
		ArticleValidator.validateCreateArticle(article);
		if (repository.findByTitle(article.getTitle()).isPresent()) {
			throw new ArticleAlreadyExistsException(article.getTitle());
		}
		var articleToSave = Article.builder()
			.id(idGenerator.generate())
			.title(article.getTitle())
			.topic(article.getTopic())
			.link(article.getLink())
			.build();
		return repository.create(articleToSave);
	}
}
