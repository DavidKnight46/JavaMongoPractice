package org.practice.basicmangodb.controller;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = HttpHeaderrequestValidator.class)
@Documented
public @interface isAdmin {

    String message() default "{IpAddress.invalid}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
