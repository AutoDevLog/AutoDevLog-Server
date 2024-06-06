package com.server.autodevlog.gpt.service;

import com.server.autodevlog.gpt.dto.GPTArticleUploadRequestDto;
import com.server.autodevlog.global.exception.CustomException;
import com.server.autodevlog.global.exception.ErrorCode;
import com.server.autodevlog.gpt.domain.GPTArticle;
import com.server.autodevlog.gpt.repository.GPTArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GPTArticleService {
    private final GPTArticleRepository gptArticleRepository;

    public String saveArticle(GPTArticle GPTArticle){
        gptArticleRepository.save(GPTArticle);
        return GPTArticle.getId();
    }

    public GPTArticle findArticle(String key){
        return gptArticleRepository.findById(key).orElseThrow(()-> new CustomException(ErrorCode.GPT_ARTICLE_ERROR));
    }

    public String findArticleContent(String key){
        return gptArticleRepository.findById(key).orElseThrow(()-> new CustomException(ErrorCode.GPT_ARTICLE_ERROR)).getContent();
    }

    public String findArticleContent(GPTArticleUploadRequestDto dto){
        GPTArticle gptArticle = gptArticleRepository.findById(dto.getHashCode()).orElseThrow(()-> new CustomException(ErrorCode.GPT_ARTICLE_ERROR));
        return gptArticle.getContent();
    }

}
