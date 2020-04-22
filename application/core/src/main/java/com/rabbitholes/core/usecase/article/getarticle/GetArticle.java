package com.rabbitholes.core.usecase.article.getarticle;

import com.rabbitholes.core.entity.Article;

public interface GetArticle {

    Article getArticle(String title);

}