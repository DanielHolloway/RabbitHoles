package com.rabbitholes.usecase;

import com.rabbitholes.domain.entity.Article;
import com.rabbitholes.usecase.port.ArticleRepository;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FindArticleTest {

    ArticleRepository repository = mock(ArticleRepository.class);

    FindArticle findArticle = new FindArticle(repository);

    @Test
    public void returnsArticle() throws Exception {
        var expectedArticle = givenAnArticleIsFound();

        var actualArticle = findArticle.findById("article1");

        assertThat(actualArticle).isEqualTo(expectedArticle);
    }

    private Optional<Article> givenAnArticleIsFound() {
        Article expectedArticle = Article.builder()
                .id("article1")
                .title("Article 1")
                .topic("Test")
                .link("test.com")
                .build();
        when(repository.findById("article1")).thenReturn(Optional.of(expectedArticle));
        return Optional.of(expectedArticle);
    }

}
