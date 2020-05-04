package com.rabbitholes.wikipedia.model;

import java.util.List;

public class WikipediaQuery {
    public List<WikipediaArticle> search;

    public List<WikipediaArticle> getWikipediaArticles() {
        return search;
    }
}
