package com.rabbitholes.controller;

import com.rabbitholes.controller.model.ArticleWeb;
import com.rabbitholes.usecase.CreateArticle;
import com.rabbitholes.usecase.FindArticle;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ArticleControllerTest {

    CreateArticle createArticle = mock(CreateArticle.class);
    FindArticle findArticle = mock(FindArticle.class);

    ArticleController articleController = new ArticleController(createArticle, findArticle);

}
