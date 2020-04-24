package com.rabbitholes.configuration;

import com.rabbitholes.core.usecase.article.getarticle.GetArticleUseCase;
import com.rabbitholes.core.usecase.article.getarticle.GetArticle;

public class UseCaseConfiguration {

    public GetArticleUseCase getArticleUseCase(GetArticle getArticle) {
        return new GetArticleUseCase(getArticle);
    }

}