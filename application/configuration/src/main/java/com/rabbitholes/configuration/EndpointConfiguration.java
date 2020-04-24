package com.rabbitholes.configuration;

import com.rabbitholes.core.usecase.article.getarticle.GetArticleUseCase;
import com.rabbitholes.entrypoints.rest.article.GetArticleEndpoint;

public class EndpointConfiguration {

    public GetArticleEndpoint GetArticleEndpoint(GetArticleUseCase getArticleUseCase) {
        return new GetArticleEndpoint(getArticleUseCase);
    }

}
