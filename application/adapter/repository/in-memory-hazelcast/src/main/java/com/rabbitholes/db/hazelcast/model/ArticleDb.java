package com.rabbitholes.db.hazelcast.model;

import com.rabbitholes.domain.entity.Article;
import java.io.Serializable;

public class ArticleDb implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String title;
	private String topic;
	private String link;
	private String role;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(final String topic) {
		this.topic = topic;
	}

	public String getLink() {
		return link;
	}

	public void setLink(final String link) {
		this.link = link;
	}

	public String getRole() {
		return role;
	}

	public void setRole(final String role) {
		this.role = role;
	}

	public Article toArticle() {
		return Article.builder()
			.id(id)
			.title(title)
			.topic(topic)
			.link(link)
			.build();
	}

	public static ArticleDb from(final Article article) {
		final ArticleDb articleDb = new ArticleDb();
		articleDb.setId(article.getId());
		articleDb.setTitle(article.getTitle());
		articleDb.setLink(article.getLink());
		articleDb.setTopic(article.getTopic());
		return articleDb;
	}
}
