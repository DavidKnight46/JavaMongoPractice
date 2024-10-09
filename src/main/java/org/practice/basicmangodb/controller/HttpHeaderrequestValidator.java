package org.practice.basicmangodb.controller;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.practice.basicmangodb.exceptions.InsufficientPrivilagesException;
import org.springframework.web.context.request.WebRequest;

public class HttpHeaderrequestValidator implements ConstraintValidator<isAdmin, WebRequest> {

    @Override
    public boolean isValid(WebRequest request, ConstraintValidatorContext constraintValidatorContext) {
        if(request.getHeader("isAdmin").contentEquals("false")){
            throw new InsufficientPrivilagesException("User not admin");
        } else {
            return true;
        }
    }
}
