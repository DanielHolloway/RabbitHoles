package com.rabbitholes.controller.model;

import com.rabbitholes.domain.entity.Article;

public class ArticleWeb {
	private String id;
	private String title;
	private String topic;
	private String link;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Article toArticle() {
		return Article.builder()
			.title(title)
			.topic(topic)
			.link(link)
			.build();
	}

	public static ArticleWeb toArticleWeb(final Article article) {
		var articleWeb = new ArticleWeb();
		articleWeb.setId(article.getId());
		articleWeb.setTitle(article.getTitle());
		articleWeb.setTopic(article.getTopic());
		articleWeb.setLink(article.getLink());
		return articleWeb;
	}
}
