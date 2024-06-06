package com.server.autodevlog.gpt.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "article")
public class Article {
    @Id
    private String id;
    private String content;

    @Builder
    public Article(String content){
        this.content = content;
    }
}
