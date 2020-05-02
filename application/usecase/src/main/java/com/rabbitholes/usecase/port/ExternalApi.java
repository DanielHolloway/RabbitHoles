package com.rabbitholes.usecase.port;

import com.rabbitholes.domain.entity.Article;
import java.util.List;

public interface ExternalApi {

    List<Article> searchByTitle(String title);
}
