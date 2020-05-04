package com.rabbitholes.controller;

import com.rabbitholes.controller.model.ArticleWeb;
import com.rabbitholes.domain.entity.Article;
import com.rabbitholes.usecase.CreateArticle;
import com.rabbitholes.usecase.FindArticle;

import com.rabbitholes.usecase.SearchArticle;
import com.rabbitholes.usecase.exception.ArticleAlreadyExistsException;
import com.rabbitholes.usecase.exception.ArticleValidationException;
import org.junit.Assert;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

public class ArticleControllerTest {

    private static final String ID = "article1";
    private static final String TITLE = "Article 1";
    private static final String TOPIC = "Test";
    private static final String LINK = "test.com";

    CreateArticle createArticle = mock(CreateArticle.class);
    FindArticle findArticle = mock(FindArticle.class);
    SearchArticle searchArticle = mock(SearchArticle.class);

    ArticleController articleController = new ArticleController(createArticle, findArticle, searchArticle);

    @Test
    public void createsAnArticleWeb() throws Exception {
        givenAnArticleWebIsCreated();

        ArticleWeb testArticleWeb = new ArticleWeb();
        testArticleWeb.setTitle(TITLE);
        testArticleWeb.setTopic(TOPIC);
        testArticleWeb.setLink(LINK);

        ArticleWeb articleWeb = articleController.createArticle(testArticleWeb);

        Assert.assertTrue(new ReflectionEquals(testArticleWeb).matches(articleWeb));
    }

    @Test
    public void errorWhenCreatingAnInvalidArticleWeb() throws Exception {
        givenAnArticleWebIsInvalid();

        ArticleWeb testArticleWeb = new ArticleWeb();
        testArticleWeb.setTitle(TITLE);
        testArticleWeb.setTopic(TOPIC);
        testArticleWeb.setLink(LINK);

        assertThatExceptionOfType(ArticleValidationException.class)
                .isThrownBy(() -> articleController.createArticle(testArticleWeb));
    }

    @Test
    public void errorWhenCreatingAnArticleWebThatAlreadyExists() throws Exception {
        givenAnArticleWebAlreadyExists();

        ArticleWeb testArticleWeb = new ArticleWeb();
        testArticleWeb.setTitle(TITLE);
        testArticleWeb.setTopic(TOPIC);
        testArticleWeb.setLink(LINK);

        assertThatExceptionOfType(ArticleAlreadyExistsException.class)
                .isThrownBy(() -> articleController.createArticle(testArticleWeb));
    }

    private void givenAnArticleWebIsCreated() {
        when(createArticle.create(any(Article.class)))
                .thenAnswer( invocation -> {
                    Article paramObj = invocation.getArgument(0);
                    if (paramObj.getTitle().equals(TITLE) &&
                            paramObj.getTopic().equals(TOPIC) &&
                            paramObj.getLink().equals(LINK)) {
                        return paramObj;
                    }
                    else{
                        return any(Article.class);
                    }
                });
    }

    private void givenAnArticleWebIsInvalid() {
        when(createArticle.create(any(Article.class)))
                .thenThrow(new ArticleValidationException("Article already exists"));
    }

    private void givenAnArticleWebAlreadyExists() {
        when(createArticle.create(any(Article.class)))
                .thenAnswer( invocation -> {
                    Article paramObj = invocation.getArgument(0);
                    throw new ArticleAlreadyExistsException(paramObj.getTitle());
                });
    }

}
