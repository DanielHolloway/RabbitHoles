package com.rabbitholes.core.entity;

public class Article {

    private String title;
    private String topic;
    private String link;

    public Article(String title, String topic, String link) {
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
