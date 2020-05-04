package com.rabbitholes.controller;

import com.rabbitholes.controller.model.ArticleWeb;
import com.rabbitholes.domain.entity.Article;
import com.rabbitholes.usecase.CreateArticle;
import com.rabbitholes.usecase.FindArticle;

import com.rabbitholes.usecase.SearchArticle;
import com.rabbitholes.usecase.exception.ArticleAlreadyExistsException;
import com.rabbitholes.usecase.exception.ArticleValidationException;
import com.rabbitholes.wikipedia.exception.WikipediaBadSearchParameterException;
import com.rabbitholes.wikipedia.exception.WikipediaFutureException;
import com.rabbitholes.wikipedia.exception.WikipediaApiFailedException;
import com.rabbitholes.wikipedia.exception.WikipediaJSONParsingException;

import org.junit.Assert;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.util.ArrayList;
import java.util.List;

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

    private void givenAnArticleWebIsInvalid() {
        when(createArticle.create(any(Article.class)))
                .thenThrow(new ArticleValidationException("Article already exists"));
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

    private void givenAnArticleWebAlreadyExists() {
        when(createArticle.create(any(Article.class)))
                .thenAnswer( invocation -> {
                    Article paramObj = invocation.getArgument(0);
                    throw new ArticleAlreadyExistsException(paramObj.getTitle());
                });
    }

    @Test
    public void searchesAnArticleWeb() throws Exception {
        List<ArticleWeb> expectedArticlesWeb = givenArticleWebIsFoundFromExternalApi();

        List<ArticleWeb> actualArticlesWeb = articleController.searchArticle(TITLE);

        Assert.assertTrue(new ReflectionEquals(expectedArticlesWeb.get(0)).matches(actualArticlesWeb.get(0)));

    }

    private List<ArticleWeb> givenArticleWebIsFoundFromExternalApi() {
        List<ArticleWeb> expectedArticlesWeb = new ArrayList<ArticleWeb>();
        ArticleWeb expectedArticleWeb = new ArticleWeb();
        expectedArticleWeb.setTitle(TITLE);
        expectedArticleWeb.setTopic(TOPIC);
        expectedArticleWeb.setLink(LINK);
        expectedArticlesWeb.add(expectedArticleWeb);

        List<Article> actualArticles = new ArrayList<Article>();
        Article actualArticle = Article.builder().title(TITLE).topic(TOPIC).link(LINK).build();
        actualArticles.add(actualArticle);


        when(searchArticle.searchByTitle(TITLE)).thenReturn(actualArticles);

        return expectedArticlesWeb;
    }

    @Test
    public void searchesAnArticleWebWithNoResults() throws Exception {
        givenArticleWebIsNotFoundFromExternalApi();

        List<ArticleWeb> actualArticlesWeb = articleController.searchArticle(TITLE);

        assertThat(actualArticlesWeb.isEmpty());

    }

    private void givenArticleWebIsNotFoundFromExternalApi() {
        when(searchArticle.searchByTitle(TITLE)).thenReturn(new ArrayList<Article>());
    }

    @Test
    public void searchesWithBadParameter() throws Exception {
        givenBadSearchParameter();

        assertThatExceptionOfType(WikipediaBadSearchParameterException.class)
                .isThrownBy(() -> articleController.searchArticle(TITLE));

    }

    private void givenBadSearchParameter() {
        when(searchArticle.searchByTitle(TITLE))
                .thenThrow(new WikipediaBadSearchParameterException("Bad search parameter"));
    }

    @Test
    public void searchFailsWithHttpFuture() throws Exception {
        givenHttpFutureFails();

        assertThatExceptionOfType(WikipediaFutureException.class)
                .isThrownBy(() -> articleController.searchArticle(TITLE));
    }

    private void givenHttpFutureFails() {
        when(searchArticle.searchByTitle(TITLE))
                .thenThrow(new WikipediaFutureException("HTTP future failed during execution"));
    }

    @Test
    public void searchFailsWithWikipediaApi() throws Exception {
        givenWikipediaApiFails();

        assertThatExceptionOfType(WikipediaApiFailedException.class)
                .isThrownBy(() -> articleController.searchArticle(TITLE));
    }

    private void givenWikipediaApiFails() {
        when(searchArticle.searchByTitle(TITLE))
                .thenThrow(new WikipediaApiFailedException("Wikipedia API returned error"));
    }

    @Test
    public void searchFailsWhenParsingJSON() throws Exception {
        givenJSONParsingFails();

        assertThatExceptionOfType(WikipediaJSONParsingException.class)
                .isThrownBy(() -> articleController.searchArticle(TITLE));
    }

    private void givenJSONParsingFails() {
        when(searchArticle.searchByTitle(TITLE))
                .thenThrow(new WikipediaJSONParsingException("Failed when parsing JSON"));
    }

}
