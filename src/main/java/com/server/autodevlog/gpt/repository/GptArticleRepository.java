package com.server.autodevlog.gpt.repository;

import com.server.autodevlog.gpt.domain.GptArticle;
import org.springframework.data.repository.CrudRepository;

public interface GptArticleRepository extends CrudRepository<GptArticle,String> {
}
