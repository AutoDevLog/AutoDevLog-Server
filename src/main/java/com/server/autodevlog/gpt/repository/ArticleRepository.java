package com.server.autodevlog.gpt.repository;

import com.server.autodevlog.gpt.domain.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article,String> {
}
