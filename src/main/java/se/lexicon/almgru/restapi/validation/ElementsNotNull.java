package se.lexicon.almgru.restapi.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = ElementNotNullValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ElementsNotNull {
    String message() default "Element cannot be null";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
