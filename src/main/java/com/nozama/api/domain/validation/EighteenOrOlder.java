package com.nozama.api.domain.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = EighteenOrOlderValidator.class)
@Documented
public @interface EighteenOrOlder {

  String message() default "{com.nozama.api.domain.validation.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
