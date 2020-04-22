package com.rabbitholes.entrypoints.rest.article;

public class ArticleDto {
    private final String title;
    private final String topic;
    private final String link;

    public ArticleDto(String title, String topic, String link) {
        this.title = title;
        this.topic = topic;
        this.link = link;
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
}
