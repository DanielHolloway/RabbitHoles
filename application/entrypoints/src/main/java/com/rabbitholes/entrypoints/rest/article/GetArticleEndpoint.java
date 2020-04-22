package com.rabbitholes.entrypoints.rest.article;

import com.rabbitholes.core.entity.Article;
import com.rabbitholes.core.usecase.article.getarticle.ArticleNotFoundException;
import com.rabbitholes.core.usecase.article.getarticle.GetArticleUseCase;
import com.rabbitholes.entrypoints.rest.exception.NotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class GetArticleEndpoint {

	public static final String API_PATH = "/";

	// To-do: integrate with logger

    // private static final Logger LOGGER = LoggerFactory.getLogger(GetBroadbandAccessDeviceEndpoint.class);

    private GetArticleUseCase getArticleUseCase;

    public GetArticleEndpoint(GetArticleUseCase getArticleUseCase) {
        this.getArticleUseCase = getArticleUseCase;
    }

	@RequestMapping(value = API_PATH, method = GET)
	public ArticleDto getArticle(@PathVariable String title) {
        // LOGGER.info("Retrieving details of article: {}", title);
        try {
            Article article = getArticleUseCase.getArticle(title);
            return toDto(article);
        } catch (ArticleNotFoundException e) {
            // LOGGER.info("Article not found: {}", title);
            throw new NotFoundException();
        }
	}

    private ArticleDto toDto(Article article) {
        return new ArticleDto(
                article.getTitle(),
                article.getTopic(),
                article.getLink());
    }

}
