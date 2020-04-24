package com.rabbitholes.vertx;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rabbitholes.config.VertxConfig;
import com.rabbitholes.controller.ArticleController;
import com.rabbitholes.vertx.controller.VertxArticleController;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class RestVertxApplication extends AbstractVerticle {

	private final VertxConfig vertxConfig = new VertxConfig();
	private final ArticleController articleController = new ArticleController(vertxConfig.createArticle(), vertxConfig.findArticle());
	private final VertxArticleController controller = new VertxArticleController(articleController);

	@Override
	public void start() {
		Json.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		var router = Router.router(vertx);
		router.route().handler(BodyHandler.create());
		router.post("/articles").handler(controller::createArticle);
		router.get("/articles/:articleId").handler(controller::findArticle);
		router.get("/articles").handler(controller::findAllArticle);

		vertx.createHttpServer().requestHandler(router::accept).listen(8080);
	}

	public static void main(final String[] args) {
		Launcher.executeCommand("run", RestVertxApplication.class.getName());
	}
}
