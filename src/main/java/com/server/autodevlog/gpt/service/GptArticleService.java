package com.server.autodevlog.gpt.service;

import com.server.autodevlog.gpt.dto.GptArticleUploadRequestDto;
import com.server.autodevlog.global.exception.CustomException;
import com.server.autodevlog.global.exception.ErrorCode;
import com.server.autodevlog.gpt.domain.GptArticle;
import com.server.autodevlog.gpt.repository.GptArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GptArticleService {
    private final GptArticleRepository gptArticleRepository;

    public String saveArticle(GptArticle GPTArticle){
        gptArticleRepository.save(GPTArticle);
        return GPTArticle.getId();
    }

    public GptArticle findArticle(String key){
        return gptArticleRepository.findById(key).orElseThrow(()-> new CustomException(ErrorCode.GPT_ARTICLE_ERROR));
    }

    public String findArticleContent(String key){
        return gptArticleRepository.findById(key).orElseThrow(()-> new CustomException(ErrorCode.GPT_ARTICLE_ERROR)).getContent();
    }

    public String findArticleContent(GptArticleUploadRequestDto dto){
        GptArticle gptArticle = gptArticleRepository.findById(dto.getHashCode()).orElseThrow(()-> new CustomException(ErrorCode.GPT_ARTICLE_ERROR));
        return gptArticle.getContent();
    }

}
