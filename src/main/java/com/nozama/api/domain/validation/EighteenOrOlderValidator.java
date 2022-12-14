package com.nozama.api.domain.validation;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EighteenOrOlderValidator implements ConstraintValidator<EighteenOrOlder, LocalDate> {

  @Override
  public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
    if(value == null) return false;

    LocalDate eighteenYearsAgoFromToday = LocalDate.now().minusYears(18);
    
    return value.compareTo(eighteenYearsAgoFromToday) <= 0;
  }
  
}
