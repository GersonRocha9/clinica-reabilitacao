
package br.edu.iff.projetoClinicaReab.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailValidation, String> {

    @Override
    public boolean isValid(String t, ConstraintValidatorContext cvc) {
        if (t == null) return false;
        if (t.contains(" ")) return false;
        return t.matches("[\\w\\s]+[@]+[\\w\\s]+[.]+[\\w\\s]+");
    }
}
