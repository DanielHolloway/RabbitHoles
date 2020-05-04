package com.rabbitholes.spring.controller;

import com.rabbitholes.controller.ArticleController;
import com.rabbitholes.controller.model.ArticleWeb;

import com.rabbitholes.spring.controller.exception.*;
import com.rabbitholes.usecase.exception.ArticleAlreadyExistsException;
import com.rabbitholes.usecase.exception.ArticleValidationException;

import com.rabbitholes.wikipedia.exception.WikipediaApiFailedException;
import com.rabbitholes.wikipedia.exception.WikipediaBadSearchParameterException;
import com.rabbitholes.wikipedia.exception.WikipediaFutureException;
import com.rabbitholes.wikipedia.exception.WikipediaJSONParsingException;
import org.springframework.http.ResponseEntity;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;

public class SpringArticleControllerTest {

    private static final String ID = "article1";
    private static final String TITLE = "Article 1";
    private static final String TOPIC = "Test";
    private static final String LINK = "test.com";

    ArticleController articleController = mock(ArticleController.class);

    SpringArticleController springArticleController = new SpringArticleController(articleController);

    @Test
    public void postsAnArticle() throws Exception {

        givenAnArticleIsCreated();

        ArticleWeb postedArticleWeb = new ArticleWeb();
        postedArticleWeb.setTitle(TITLE);
        postedArticleWeb.setTopic(TOPIC);
        postedArticleWeb.setLink(LINK);

        ResponseEntity postResponse = springArticleController.createArticle(postedArticleWeb);

        assertThat(postResponse.getStatusCodeValue()).isEqualTo(200);
    }

    private void givenAnArticleIsCreated() {

        when(articleController.createArticle(any(ArticleWeb.class)))
                .thenAnswer( invocation -> {
                    return invocation.getArgument(0);
                });
    }

    @Test
    public void errorWhenPostingRepeatedArticle() throws Exception {

        givenAnArticleAlreadyExists();

        ArticleWeb postedArticleWeb = new ArticleWeb();
        postedArticleWeb.setTitle(TITLE);
        postedArticleWeb.setTopic(TOPIC);
        postedArticleWeb.setLink(LINK);

        assertThatExceptionOfType(SpringArticleAlreadyExistsException.class)
                .isThrownBy(() -> springArticleController.createArticle(postedArticleWeb));
    }

    private void givenAnArticleAlreadyExists() {
        when(articleController.createArticle(any(ArticleWeb.class)))
                .thenAnswer( invocation -> {
                    ArticleWeb paramObj = invocation.getArgument(0);
                    if(paramObj.getTitle().equals(TITLE)){
                        throw new ArticleAlreadyExistsException(TITLE);
                    }
                    else{
                        return paramObj;
                    }
                });
    }

    @Test
    public void errorWhenPostingInvalidArticle() throws Exception {

        givenAnArticleIsInvalid();

        ArticleWeb postedArticleWeb = new ArticleWeb();
        postedArticleWeb.setTopic(TOPIC);
        postedArticleWeb.setLink(LINK);

        assertThatExceptionOfType(SpringArticleValidationException.class)
                .isThrownBy(() -> springArticleController.createArticle(postedArticleWeb));
    }

    private void givenAnArticleIsInvalid() {
        when(articleController.createArticle(any(ArticleWeb.class)))
                .thenAnswer( invocation -> {
                    ArticleWeb paramObj = invocation.getArgument(0);
                    if(paramObj.getTitle() == null){
                        throw new ArticleValidationException("Article's title must not be null");
                    }
                    else{
                        return paramObj;
                    }
                });
    }

    @Test
    public void searchesAnArticleWeb() throws Exception {
        givenAnArticleSearchesSuccessfully();

        ResponseEntity postResponse = springArticleController.searchArticle(TITLE);

        assertThat(postResponse.getStatusCodeValue()).isEqualTo(200);

    }

    private void givenAnArticleSearchesSuccessfully() {
        List<ArticleWeb> expectedArticlesWeb = new ArrayList<>();
        ArticleWeb expectedArticleWeb = new ArticleWeb();
        expectedArticleWeb.setTitle(TITLE);
        expectedArticleWeb.setTopic(TOPIC);
        expectedArticleWeb.setLink(LINK);
        expectedArticlesWeb.add(expectedArticleWeb);

        when(articleController.searchArticle(TITLE)).thenReturn(expectedArticlesWeb);
    }

    @Test
    public void searchesWithBadParameter() throws Exception {
        givenBadSearchParameter();

        assertThatExceptionOfType(SpringWikipediaBadSearchParameterException.class)
                .isThrownBy(() -> springArticleController.searchArticle(TITLE));

    }

    private void givenBadSearchParameter() {
        when(articleController.searchArticle(TITLE))
                .thenThrow(new WikipediaBadSearchParameterException("Bad search parameter"));
    }

    @Test
    public void searchFailsWithHttpFuture() throws Exception {
        givenHttpFutureFails();

        assertThatExceptionOfType(SpringWikipediaFutureException.class)
                .isThrownBy(() -> springArticleController.searchArticle(TITLE));
    }

    private void givenHttpFutureFails() {
        when(articleController.searchArticle(TITLE))
                .thenThrow(new WikipediaFutureException("HTTP future failed during execution"));
    }

    @Test
    public void searchFailsWithWikipediaApi() throws Exception {
        givenWikipediaApiFails();

        assertThatExceptionOfType(SpringWikipediaBadApiException.class)
                .isThrownBy(() -> springArticleController.searchArticle(TITLE));
    }

    private void givenWikipediaApiFails() {
        when(articleController.searchArticle(TITLE))
                .thenThrow(new WikipediaApiFailedException("Wikipedia API returned error"));
    }

    @Test
    public void searchFailsWhenParsingJSON() throws Exception {
        givenJSONParsingFails();

        assertThatExceptionOfType(SpringWikipediaBadApiException.class)
                .isThrownBy(() -> springArticleController.searchArticle(TITLE));
    }

    private void givenJSONParsingFails() {
        when(articleController.searchArticle(TITLE))
                .thenThrow(new WikipediaJSONParsingException("Failed when parsing JSON"));
    }

}
