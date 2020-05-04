package com.rabbitholes.wikipedia;

import com.rabbitholes.domain.entity.Article;
import com.rabbitholes.usecase.port.ExternalApi;

import org.junit.Assert;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WikipediaExternalApiTest {

    WikipediaExternalApi wikipediaExternalApi = mock(WikipediaExternalApi.class);

    @Test
    public void findsAWikipediaArticle() throws Exception {

        List<Article> expectedArticles = givenArticlesAreFound();

        List<Article> actualArticles = wikipediaExternalApi.searchByTitle("Article 1");

        Assert.assertArrayEquals(actualArticles.toArray(),expectedArticles.toArray());

    }

    @Test
    public void doesNotFindAWikipediaArticle() throws Exception {

        givenNoArticleIsFound();

        List<Article> actualArticles = wikipediaExternalApi.searchByTitle("Article 1");

        assertThat(actualArticles.size() == 0);
    }

    private List<Article> givenArticlesAreFound() {
        List<Article> expectedArticles = new ArrayList<Article>();

        Article expectedArticle = Article.builder()
                .id("article1")
                .title("Article 1")
                .topic("Test 1")
                .link("test.com/1")
                .build();
        expectedArticles.add(expectedArticle);

        when(wikipediaExternalApi.searchByTitle("Article 1")).thenReturn(expectedArticles);

        return expectedArticles;
    }

    private void givenNoArticleIsFound() {
        when(wikipediaExternalApi.searchByTitle("Article 1")).thenReturn(Collections.<Article>emptyList());
    }

}
