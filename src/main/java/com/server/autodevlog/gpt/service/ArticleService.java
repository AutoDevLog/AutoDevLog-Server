package com.server.autodevlog.gpt.service;

import com.server.autodevlog.gpt.domain.Article;
import com.server.autodevlog.gpt.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public String saveArticle(Article article){
        articleRepository.save(article);
        return article.getId();
    }

    public Article findArticle(String key){
        return articleRepository.findById(key).orElseThrow(()-> new IllegalArgumentException("조회 실패"));
    }
}
