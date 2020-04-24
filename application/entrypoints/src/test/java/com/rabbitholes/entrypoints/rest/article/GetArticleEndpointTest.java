package com.rabbitholes.entrypoints.rest.article;

import com.rabbitholes.core.entity.Article;
import com.rabbitholes.core.usecase.article.getarticle.ArticleNotFoundException;
import com.rabbitholes.core.usecase.article.getarticle.GetArticleUseCase;
import com.rabbitholes.entrypoints.rest.exception.NotFoundException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetArticleEndpointTest {

	private static final String TITLE = "articleTitle1";
    private static final String TOPIC = "topicNumber1";
    private static final String LINK = "http://wikipedia.org";

    GetArticleUseCase getArticleUseCase = mock(GetArticleUseCase.class);

    GetArticleEndpoint getArticleEndpoint = new GetArticleEndpoint(getArticleUseCase);

    @Test
    public void returnsTheArticle() throws Exception {
        givenAnArticleExists();

        ArticleDto articleDto = getArticleEndpoint.getArticle(TITLE);

        assertThat(articleDto.getTitle()).isEqualTo(TITLE);
        assertThat(articleDto.getTopic()).isEqualTo(TOPIC);
        assertThat(articleDto.getLink()).isEqualTo(LINK);
    }

    @Test
    public void errorWhenDeviceIsNotFound() throws Exception {
        givenAnArticleDoesNotExist();

        assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> getArticleEndpoint.getArticle(TITLE));
    }

    private void givenAnArticleExists() {
        Article article = new Article(TITLE, TOPIC, LINK);
        when(getArticleUseCase.getArticle(TITLE)).thenReturn(article);
    }

    private void givenAnArticleDoesNotExist() {
        when(getArticleUseCase.getArticle(TITLE)).thenThrow(new ArticleNotFoundException());
	}
	
}