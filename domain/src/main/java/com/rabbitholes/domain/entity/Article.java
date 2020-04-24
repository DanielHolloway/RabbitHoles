package com.rabbitholes.domain.entity;

public class Article {

	private String id;
	private String title;
	private String topic;
	private String link;

	private Article(final String id, final String title, final String topic, final String link) {
		this.id = id;
		this.title = title;
		this.topic = topic;
		this.link = link;
	}

	public static ArticleBuilder builder() {
		return new ArticleBuilder();
	}

	public static class ArticleBuilder {
		private String id;
		private String title;
		private String topic;
		private String link;

		ArticleBuilder() {
		}

		public ArticleBuilder id(final String id) {
			this.id = id;
			return this;
		}

		public ArticleBuilder title(final String title) {
			this.title = title;
			return this;
		}

		public ArticleBuilder topic(final String topic) {
			this.topic = topic;
			return this;
		}

		public ArticleBuilder link(final String link) {
			this.topic = link;
			return this;
		}

		public Article build() {
			return new Article(id, title, topic, link);
		}
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getTopic() {
		return topic;
	}

	public String getLink() {
		return link;
	}

	@Override
	public String toString() {
		return "Article{" +
			"id='" + id + '\'' +
			", title='" + title + '\'' +
			", topic='" + topic + '\'' +
			", link='" + link + '\'' +
			'}';
	}
}
