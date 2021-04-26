package se.lexicon.almgru.restapi.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;
import java.util.Objects;

public class ElementNotEmptyStringValidator implements ConstraintValidator<ElementsNotEmpty, Collection<String>> {
    @Override
    public boolean isValid(Collection<String> value, ConstraintValidatorContext context) {
        return value == null || value.stream().filter(Objects::nonNull).noneMatch(String::isEmpty);
    }
}
