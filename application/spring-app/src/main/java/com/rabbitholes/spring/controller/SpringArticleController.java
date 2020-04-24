package com.rabbitholes.spring.controller;

import com.rabbitholes.controller.ArticleController;
import com.rabbitholes.controller.model.ArticleWeb;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringArticleController {

	private final ArticleController controller;

	@Autowired
	public SpringArticleController(final ArticleController controller) {
		this.controller = controller;
	}

	@RequestMapping(value = "/articles", method = RequestMethod.POST)
	public ArticleWeb createArticle(@RequestBody final ArticleWeb articleWeb) {
		return controller.createArticle(articleWeb);
	}

	@RequestMapping(value = "/articles/{articleId}", method = RequestMethod.GET)
	public ArticleWeb getArticle(@PathVariable("articleId") final String articleId) {
		return controller.getArticle(articleId);
	}

	@RequestMapping(value = "/articles", method = RequestMethod.GET)
	public List<ArticleWeb> allArticles() {
		return controller.allArticles();
	}
}
