package modu.menu.core.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnumListValidator implements ConstraintValidator<EnumValidation, List<String>> {

    private Set<String> enumNames;

    @Override
    public void initialize(EnumValidation constraintAnnotation) {
        enumNames = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {
        if (values == null) {
            return true;
        }

        return values.stream().map(String::toUpperCase).allMatch(enumNames::contains);
    }
}
