package org.practice.basicmangodb.controller;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.context.request.WebRequest;

public class HttpHeaderrequestValidator implements ConstraintValidator<MyValidator, WebRequest> {

    @Override
    public boolean isValid(WebRequest request, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("smurf2");
        return false;
    }
}
