package com.rabbitholes.usecase;

import com.rabbitholes.domain.entity.Article;
import com.rabbitholes.usecase.port.ExternalApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SearchArticleTest {

    ExternalApi externalApi = mock(ExternalApi.class);

    SearchArticle searchArticle = new SearchArticle(externalApi);

    @Test
    public void returnsArticles() throws Exception {
        List<Article> expectedArticles = givenArticlesAreReturned();

        List<Article> actualArticles = searchArticle.searchByTitle("Article");

        Assert.assertArrayEquals(actualArticles.toArray(),expectedArticles.toArray());

    }

    @Test
    public void findsNoArticles() throws Exception {
        givenNoArticlesAreReturned();

        List<Article> actualArticles = searchArticle.searchByTitle("Article");

        assertThat(actualArticles.size() == 0);
    }

    private List<Article> givenArticlesAreReturned() {
        List<Article> expectedArticles = new ArrayList<Article>();

        for(int i = 0; i < 3; i++){
            Article expectedArticle = Article.builder()
                    .id("article" + i)
                    .title("Article " + i)
                    .topic("Test " + i)
                    .link("test.com/" + i)
                    .build();
            expectedArticles.add(expectedArticle);
        }

        when(externalApi.searchByTitle("Article")).thenReturn(expectedArticles);

        return expectedArticles;
    }

    private void givenNoArticlesAreReturned() {
        when(externalApi.searchByTitle("Article")).thenReturn(Collections.<Article>emptyList());
    }
}
