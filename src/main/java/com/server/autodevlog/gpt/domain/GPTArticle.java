package com.server.autodevlog.gpt.domain;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "GPT-article")
public class GPTArticle {
    @Id
    private String id;
    private String content;

    @Builder
    public GPTArticle(String content){
        this.content = content;
    }
}
