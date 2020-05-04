package com.rabbitholes.wikipedia.model;

public class WikipediaArticle {
    private String ns;
    private String title;
    private String pageid;
    private String size;
    private String wordcount;
    private String snippet;
    private String timestamp;

    public String getNs() { return ns; }
    public String getTitle() { return title; }
    public String getPageid() { return pageid; }
    public String getSize() { return size; }
    public String getWordcount() { return wordcount; }
    public String getSnippet() { return snippet; }
    public String getTimestamp() { return timestamp; }
}
