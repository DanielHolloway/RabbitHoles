package com.rabbitholes.db;

import com.rabbitholes.db.exception.InvalidArticleException;
import com.rabbitholes.domain.entity.Article;
import com.rabbitholes.usecase.port.ArticleRepository;

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

import java.util.List;
import java.util.Optional;

public class InMemoryArticleRepositoryTest {

    InMemoryArticleRepository inMemoryArticleRepository = new InMemoryArticleRepository();

    @Test
    public void addsAnArticle() throws Exception {

        Article expectedArticle = Article.builder()
                .id("article1")
                .title("Article 1")
                .topic("Test")
                .link("test.com")
                .build();

        Article actualArticle = inMemoryArticleRepository.create(expectedArticle);

        Assert.assertTrue(new ReflectionEquals(expectedArticle).matches(actualArticle));
    }

    @Test
    public void errorWhenAddingAnInvalidArticle() throws Exception {

        Article invalidArticle = Article.builder()
                .title("Article 1")
                .topic("Test")
                .link("test.com")
                .build();

        assertThatExceptionOfType(InvalidArticleException.class)
                .isThrownBy(() -> inMemoryArticleRepository.create(invalidArticle));
    }

    @Test
    public void findsArticleWithId() throws Exception {

        Article expectedArticle = Article.builder()
                .id("article1")
                .title("Article 1")
                .topic("Test")
                .link("test.com")
                .build();

        inMemoryArticleRepository.create(expectedArticle);

        Optional<Article> actualArticle = inMemoryArticleRepository.findById(expectedArticle.getId());

        assertThat(actualArticle).isEqualTo(Optional.of(expectedArticle));
    }

    @Test
    public void doesNotFindArticleWithId() throws Exception {

        Optional<Article> actualArticle = inMemoryArticleRepository.findById("article1");

        assertThat(actualArticle).isEqualTo(Optional.empty());
    }

    @Test
    public void findsArticleWithTitle() throws Exception {

        Article expectedArticle = Article.builder()
                .id("article1")
                .title("Article 1")
                .topic("Test")
                .link("test.com")
                .build();

        inMemoryArticleRepository.create(expectedArticle);

        Optional<Article> actualArticle = inMemoryArticleRepository.findByTitle(expectedArticle.getTitle());

        assertThat(actualArticle).isEqualTo(Optional.of(expectedArticle));
    }

    @Test
    public void doesNotFindArticleWithTitle() throws Exception {

        Optional<Article> actualArticle = inMemoryArticleRepository.findByTitle("Article 1");

        assertThat(actualArticle).isEqualTo(Optional.empty());
    }

    @Test
    public void findsAllArticles() throws Exception {

        Article expectedArticle = Article.builder()
                .id("article1")
                .title("Article 1")
                .topic("Test")
                .link("test.com")
                .build();

        inMemoryArticleRepository.create(expectedArticle);

        List<Article> actualArticles = inMemoryArticleRepository.findAllArticles();

        assertThat(actualArticles.get(0)).isEqualTo(expectedArticle);
    }

    @Test
    public void doesNotFindAnyArticles() throws Exception {

        List<Article> actualArticles = inMemoryArticleRepository.findAllArticles();

        assertThat(actualArticles).isEmpty();
    }

}
