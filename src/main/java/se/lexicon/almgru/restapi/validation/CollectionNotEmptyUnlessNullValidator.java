package se.lexicon.almgru.restapi.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;

public class CollectionNotEmptyUnlessNullValidator implements ConstraintValidator<NotEmptyUnlessNull, Collection<?>> {
    @Override
    public boolean isValid(Collection<?> value, ConstraintValidatorContext context) {
        return value == null || value.size() > 0;
    }
}
