package com.server.autodevlog.gpt.validation;

import com.server.autodevlog.global.exception.CustomException;
import com.server.autodevlog.global.exception.ErrorCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class PromptValidator implements ConstraintValidator<NullPrompt,String> {
    @Override
    public void initialize(NullPrompt constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        if(s.isBlank()){
            throw new CustomException(ErrorCode.PROMPT_NULL_ERROR);
        }
        return true;
    }
}
