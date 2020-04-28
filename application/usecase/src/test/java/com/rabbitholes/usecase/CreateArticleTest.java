package com.rabbitholes.usecase;

import com.rabbitholes.domain.entity.Article;
import com.rabbitholes.usecase.exception.ArticleAlreadyExistsException;
import com.rabbitholes.usecase.exception.ArticleValidationException;
import com.rabbitholes.usecase.port.IdGenerator;
import com.rabbitholes.usecase.port.ArticleRepository;
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
        assertThat(actualArticle).isEqualTo(expectedArticle);
    }

    @Test
    public void errorWhenCreatingAnArticleThatExists() throws Exception {
        givenAnArticleAlreadyExists();

        Article testArticle = Article.builder()
                .id("article1")
                .title("Article 1")
                .topic("Test")
                .link("test.com")
                .build();

        assertThatExceptionOfType(ArticleAlreadyExistsException.class)
                .isThrownBy(() -> createArticle.create(testArticle));
    }

    @Test
    public void errorWhenCreatingAnArticleWithoutArticle() throws Exception {
        assertThatExceptionOfType(ArticleValidationException.class)
                .isThrownBy(() -> createArticle.create(null));
    }

    @Test
    public void errorWhenCreatingAnArticleWithoutTitle() throws Exception {
        Article testArticle = Article.builder()
                .id("article1")
                .title("")
                .topic("Test")
                .link("test.com")
                .build();

        assertThatExceptionOfType(ArticleValidationException.class)
                .isThrownBy(() -> createArticle.create(testArticle));
    }

    @Test
    public void errorWhenCreatingAnArticleWithoutTopic() throws Exception {
        Article testArticle = Article.builder()
                .id("article1")
                .title("Article 1")
                .topic("")
                .link("test.com")
                .build();

        assertThatExceptionOfType(ArticleValidationException.class)
                .isThrownBy(() -> createArticle.create(testArticle));
    }

    @Test
    public void errorWhenCreatingAnArticleWithoutLink() throws Exception {
        Article testArticle = Article.builder()
                .id("article1")
                .title("Article 1")
                .topic("Test")
                .link("")
                .build();

        assertThatExceptionOfType(ArticleValidationException.class)
                .isThrownBy(() -> createArticle.create(testArticle));
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
        when(repository.create(any(Article.class)))
                .thenAnswer( invocation -> {
                    Article paramObj = invocation.getArgumentAt(0, Article.class);
                    if (paramObj.getId().equals(expectedArticle.getId()) &&
                            paramObj.getTitle().equals(expectedArticle.getTitle()) &&
                            paramObj.getTopic().equals(expectedArticle.getTopic()) &&
                            paramObj.getLink().equals(expectedArticle.getLink())) {
                        return expectedArticle;
                    }
                    else{
                        return any(Article.class);
                    }
                });
        return expectedArticle;
    }

    private void givenAnArticleAlreadyExists() {
        Article existingArticle = Article.builder()
                .id("article1")
                .title("Article 1")
                .topic("Test")
                .link("test.com")
                .build();

        when(repository.findByTitle(existingArticle.getTitle())).thenReturn(Optional.of(existingArticle));
    }

}
