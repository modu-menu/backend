package modu.menu.core.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnumValidator implements ConstraintValidator<EnumValidation, Enum> {

    private Set<String> enumNames;

    @Override
    public void initialize(EnumValidation constraintAnnotation) {
        enumNames = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(Enum value, ConstraintValidatorContext context) {
        return enumNames.contains(value);
    }
}
