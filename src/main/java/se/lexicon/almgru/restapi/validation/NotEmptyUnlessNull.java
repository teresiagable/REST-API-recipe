package se.lexicon.almgru.restapi.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = { StringNotEmptyUnlessNullValidator.class, CollectionNotEmptyUnlessNullValidator.class })
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEmptyUnlessNull {
    String message() default "Cannot be empty";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
