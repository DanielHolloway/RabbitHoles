package com.rabbitholes.spring.controller;

import com.rabbitholes.controller.ArticleController;
import com.rabbitholes.controller.model.ArticleWeb;

import com.rabbitholes.spring.controller.exception.SpringArticleAlreadyExistsException;
import com.rabbitholes.spring.controller.exception.SpringArticleValidationException;
import com.rabbitholes.usecase.CreateArticle;
import com.rabbitholes.usecase.exception.ArticleAlreadyExistsException;
import com.rabbitholes.usecase.exception.ArticleValidationException;

import org.springframework.http.ResponseEntity;

import org.junit.Assert;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

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

    @Test
    public void errorWhenPostingInvalidArticle() throws Exception {

        givenAnArticleIsInvalid();

        ArticleWeb postedArticleWeb = new ArticleWeb();
        postedArticleWeb.setTopic(TOPIC);
        postedArticleWeb.setLink(LINK);

        assertThatExceptionOfType(SpringArticleValidationException.class)
                .isThrownBy(() -> springArticleController.createArticle(postedArticleWeb));
    }

    private void givenAnArticleIsCreated() {

        when(articleController.createArticle(any(ArticleWeb.class)))
                .thenAnswer( invocation -> {
                    return invocation.getArgument(0);
                });
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

}
