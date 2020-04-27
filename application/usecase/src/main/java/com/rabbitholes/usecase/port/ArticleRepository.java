package com.rabbitholes.usecase.port;

import com.rabbitholes.domain.entity.Article;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

	Article create(Article article);

	Optional<Article> findById(String id);

	Optional<Article> findByTitle(String title);

	List<Article> findAllArticles();
}
