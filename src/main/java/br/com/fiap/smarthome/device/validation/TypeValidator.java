package br.com.fiap.smarthome.device.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TypeValidator implements ConstraintValidator<Type, String>  {
    
    @Override
    public boolean isValid(String type, ConstraintValidatorContext arg1) {
        return type.equals("MANHÃ") || type.equals("TARDE") || type.equals("NOITE") || type.equals("DIA TODO") || type.equals("MORNING") || type.equals("AFTERNOON") || type.equals("NIGHT") || type.equals("ALL DAY");
    }
}
