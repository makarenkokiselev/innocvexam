package com.pablo.innocvexam.model.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator<DateConstraint, String> {
	
	private String format;
	
	@Override
	public void initialize(DateConstraint constraintAnnotation) {
		this.format = constraintAnnotation.format();
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		try {
		    formatter.parse(value, LocalDate::from);
		} catch (DateTimeParseException e) {
			return false;
		}
		return true;
	}

}
