package com.server.autodevlog.gpt.repository;

import com.server.autodevlog.gpt.domain.GPTArticle;
import org.springframework.data.repository.CrudRepository;

public interface GPTArticleRepository extends CrudRepository<GPTArticle,String> {
}
