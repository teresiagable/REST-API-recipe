package se.lexicon.almgru.restapi.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StringNotEmptyUnlessNullValidator implements ConstraintValidator<NotEmptyUnlessNull, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || value.trim().length() > 0;
    }
}
