package com.server.autodevlog.gpt.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PromptValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NullPrompt {
    String message() default "Prompt Cannot Be NULL";
    Class<?>[] groups() default {};
    Class<?extends Payload>[] payload() default {};
}
