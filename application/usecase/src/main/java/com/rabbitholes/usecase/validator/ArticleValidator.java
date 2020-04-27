package com.rabbitholes.usecase.validator;

import com.rabbitholes.domain.entity.Article;
import com.rabbitholes.usecase.exception.ArticleValidationException;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class ArticleValidator {

	public static void validateCreateArticle(final Article article) {
		if (article == null) throw new ArticleValidationException("Article should not be null");
		if (isBlank(article.getTitle())) throw new ArticleValidationException("Title should not be null");
		if (isBlank(article.getLink())) throw new ArticleValidationException("Link should not be null");
		if (isBlank(article.getTopic())) throw new ArticleValidationException("Topic should not be null");
	}

	private ArticleValidator() {

	}
}
