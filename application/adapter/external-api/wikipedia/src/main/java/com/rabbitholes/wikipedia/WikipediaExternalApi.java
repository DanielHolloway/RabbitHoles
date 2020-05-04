package com.rabbitholes.wikipedia;

import com.google.gson.Gson;
import com.rabbitholes.domain.entity.Article;
import com.rabbitholes.usecase.port.ExternalApi;
import com.rabbitholes.wikipedia.exception.WikipediaApiFailedException;
import com.rabbitholes.wikipedia.exception.WikipediaBadSearchParameterException;
import com.rabbitholes.wikipedia.exception.WikipediaFutureException;
import com.rabbitholes.wikipedia.exception.WikipediaJSONParsingException;
import com.rabbitholes.wikipedia.model.WikipediaArticle;
import com.rabbitholes.wikipedia.model.WikipediaQuery;
import com.rabbitholes.wikipedia.model.WikipediaSearch;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.io.UnsupportedEncodingException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class WikipediaExternalApi implements ExternalApi {

    @Override
    public List<Article> searchByTitle(String title) {
        String uri = "";
        try{
            uri = "https://en.wikipedia.org/w/api.php?action=query&list=search&srlimit=5&srsearch=" +
                    URLEncoder.encode(title, StandardCharsets.UTF_8.toString()) + "&format=json";
        } catch(UnsupportedEncodingException e) {
            throw new WikipediaBadSearchParameterException("Bad search parameter could not be encoded for Wikipedia");
        }

        CompletableFuture<List<Article>> completableFuture = getArticlesFromWikipediaSearch(uri);

        try{
            return completableFuture.get();
        } catch(CancellationException | ExecutionException | InterruptedException e){
            throw new WikipediaFutureException("Future failed during Wikipedia search API call");
        } catch(RuntimeException e){
            throw new WikipediaApiFailedException("Wikipedia search API returned invalid response");
        }
    }

    private CompletableFuture<List<Article>> getArticlesFromWikipediaSearch(String uri) {

        HttpRequest request = HttpRequest.newBuilder(URI.create(uri))
                .header("Accept", "application/json")
                .build();

        return HttpClient.newHttpClient()
                .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(WikipediaExternalApi::parseStringToArticles);

    }

    private static List<Article> parseStringToArticles(String content) {

        Gson gson = new Gson();

        WikipediaSearch wikipediaSearch;
        WikipediaQuery wikipediaQuery;
        List<WikipediaArticle> wikipediaArticles;

        try {
            wikipediaSearch = gson.fromJson(content, WikipediaSearch.class);
        } catch(RuntimeException e) {
            throw new WikipediaJSONParsingException("Parsing Wikipedia's returned JSON resulted in a failure");
        }

        try{
            wikipediaQuery = wikipediaSearch.getWikipediaQuery();
        } catch(RuntimeException e){
            throw new WikipediaJSONParsingException("Failed to get query property from Wikipedia response.");
        }

        try{
            wikipediaArticles = wikipediaQuery.getWikipediaArticles();
        } catch(RuntimeException e){
            throw new WikipediaJSONParsingException("Failed to get articles property from Wikipedia response.");
        }

        return convertWikipediaArticlesToArticles(wikipediaArticles);

    }

    private static List<Article> convertWikipediaArticlesToArticles(List<WikipediaArticle> wikipediaArticles) {
        List<Article> returnedArticles = new ArrayList<Article>();

        for(WikipediaArticle wikipediaArticle : wikipediaArticles){
            String pageUrl = "https://en.wikipedia.org/w/api.php?action=query&prop=info&pageids=" +
                                wikipediaArticle.getPageid() + "&inprop=url";
            Article returnedArticle = Article.builder()
                    .id(wikipediaArticle.getPageid())
                    .title(wikipediaArticle.getTitle())
                    .topic("Test")
                    .link(pageUrl)
                    .build();
            returnedArticles.add(returnedArticle);
        }

        return returnedArticles;
    }

}



