package com.rabbitholes.vertx.controller;

import com.rabbitholes.controller.ArticleController;
import com.rabbitholes.controller.model.ArticleWeb;
import com.rabbitholes.vertx.utils.JsonCollectors;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class VertxArticleController {

	private final ArticleController controller;

	public VertxArticleController(final ArticleController controller) {
		this.controller = controller;
	}

	public void createArticle(final RoutingContext routingContext) {
		var response = routingContext.response();
		var body = routingContext.getBody();
		if (isNull(body)) {
			sendError(400, response);
		} else {
			var articleWeb = body.toJsonObject().mapTo(ArticleWeb.class);
			var article = controller.createArticle(articleWeb);
			var result = JsonObject.mapFrom(article);
			sendSuccess(result, response);
		}
	}

	public void findArticle(final RoutingContext routingContext) {
		var response = routingContext.response();
		var articleId = routingContext.request().getParam("articleId");
		if (articleId == null) {
			sendError(400, response);
		} else {
			try {
				var article = controller.getArticle(articleId);
				var result = JsonObject.mapFrom(article);
				sendSuccess(result, response);
			} catch (RuntimeException e) {
				sendError(404, response);
			}
		}
	}

	public void findAllArticle(final RoutingContext routingContext) {
		var response = routingContext.response();
		var articles = controller.allArticles();
		var result = articles.stream()
			.map(JsonObject::mapFrom)
			.collect(JsonCollectors.toJsonArray());
		response
			.putHeader("content-type", "application/json")
			.end(result.encodePrettily());
	}


	private boolean isNull(final Buffer buffer) {
		return buffer == null || "".equals(buffer.toString());
	}

	private void sendError(int statusCode, HttpServerResponse response) {
		response
			.putHeader("content-type", "application/json")
			.setStatusCode(statusCode)
			.end();
	}

	private void sendSuccess(JsonObject body, HttpServerResponse response) {
		response
			.putHeader("content-type", "application/json")
			.end(body.encodePrettily());
	}
}
