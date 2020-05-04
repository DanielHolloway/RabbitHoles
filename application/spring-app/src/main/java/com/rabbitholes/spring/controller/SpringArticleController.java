package com.rabbitholes.spring.controller;

import com.rabbitholes.controller.ArticleController;
import com.rabbitholes.controller.model.ArticleWeb;

import com.rabbitholes.spring.controller.exception.*;

import com.rabbitholes.usecase.exception.ArticleAlreadyExistsException;
import com.rabbitholes.usecase.exception.ArticleValidationException;

import com.rabbitholes.wikipedia.exception.WikipediaBadSearchParameterException;
import com.rabbitholes.wikipedia.exception.WikipediaFutureException;
import com.rabbitholes.wikipedia.exception.WikipediaApiFailedException;
import com.rabbitholes.wikipedia.exception.WikipediaJSONParsingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity createArticle(@RequestBody final ArticleWeb articleWeb) {
		try{
			return ResponseEntity.ok(controller.createArticle(articleWeb));
		} catch(ArticleAlreadyExistsException e) {
			throw new SpringArticleAlreadyExistsException();
		} catch(ArticleValidationException e) {
			throw new SpringArticleValidationException();
		} catch(RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@RequestMapping(value = "/articles/{articleId}", method = RequestMethod.GET)
	public ResponseEntity getArticle(@PathVariable("articleId") final String articleId) {
		try{
			return ResponseEntity.ok(controller.getArticle(articleId));
		} catch(RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@RequestMapping(value = "/articles", method = RequestMethod.GET)
	public ResponseEntity allArticles() {
		try{
			return ResponseEntity.ok(controller.allArticles());
		} catch(RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@RequestMapping(value = "/articles/search/{articleTitle}", method = RequestMethod.GET)
	public ResponseEntity searchArticle(@PathVariable("articleTitle") final String articleTitle) {
		try{
			return ResponseEntity.ok(controller.searchArticle(articleTitle));
		} catch(WikipediaBadSearchParameterException e) {
			throw new SpringWikipediaBadSearchParameterException();
		} catch(WikipediaFutureException e) {
			throw new SpringWikipediaFutureException();
		} catch(WikipediaApiFailedException | WikipediaJSONParsingException e) {
			throw new SpringWikipediaBadApiException();
		} catch(RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}
