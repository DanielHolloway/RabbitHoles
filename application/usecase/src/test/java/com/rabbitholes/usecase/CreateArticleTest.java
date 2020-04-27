package com.rabbitholes.usecase;

import com.rabbitholes.domain.entity.Article;
import com.rabbitholes.usecase.exception.ArticleAlreadyExistsException;
import com.rabbitholes.usecase.port.IdGenerator;
import com.rabbitholes.usecase.port.ArticleRepository;
import com.rabbitholes.usecase.validator.ArticleValidator;
import java.util.Optional;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CreateArticleTest {

    ArticleRepository repository = mock(ArticleRepository.class);
    IdGenerator idGenerator = mock(IdGenerator.class);

    CreateArticle createArticle = new CreateArticle(repository, idGenerator);

    @Test
    public void createsArticle() throws Exception {
        Article expectedArticle = givenAnArticleIsCreated();

        Article testArticle = Article.builder()
                .id("article1")
                .title("Article 1")
                .topic("Test")
                .link("test.com")
                .build();

        var actualArticle = createArticle.create(testArticle);
        System.out.println(expectedArticle.toString());
        System.out.println(actualArticle.toString());
        assertThat(actualArticle).isEqualTo(expectedArticle);
    }

    private Article givenAnArticleIsCreated() {
        Article expectedArticle = Article.builder()
                .id("article1")
                .title("Article 1")
                .topic("Test")
                .link("test.com")
                .build();

        when(repository.findByTitle(expectedArticle.getTitle())).thenReturn(Optional.empty());
        when(idGenerator.generate()).thenReturn("article1");
        when(repository.create(any(Article.class))).thenReturn(expectedArticle);
        return expectedArticle;
    }

}
