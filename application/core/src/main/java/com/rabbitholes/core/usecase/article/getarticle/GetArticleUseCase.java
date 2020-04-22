package com.rabbitholes.core.usecase.article.getarticle;

import com.rabbitholes.core.entity.Article;

public class GetArticleUseCase {

    private final GetArticle getArticle;

    public GetArticleUseCase(GetArticle getArticle) {
        this.getArticle = getArticle;
    }

    public Article getArticle(String title) {
        Article article = getArticle.getArticle(title);

        if(article == null) {
            throw new ArticleNotFoundException();
        }

        return article;
    }

}