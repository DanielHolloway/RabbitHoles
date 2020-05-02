package com.rabbitholes.usecase;

import com.rabbitholes.domain.entity.Article;
import com.rabbitholes.usecase.port.ExternalApi;
import java.util.List;

public class SearchArticle {

    private final ExternalApi externalApi;

    public SearchArticle(final ExternalApi externalApi) { this.externalApi = externalApi; }

    public List<Article> searchByTitle(String title) {
        return externalApi.searchByTitle(title);
    }

}
