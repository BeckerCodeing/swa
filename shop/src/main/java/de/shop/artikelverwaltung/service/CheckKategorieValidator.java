package de.shop.artikelverwaltung.service;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
///Validierung der Kategorie-Bezeichnung
public class CheckKategorieValidator implements ConstraintValidator<CheckKategorie, String> {
	Set<String> allowedValues;
	
	@Override
	public void initialize(CheckKategorie annotation) {
		allowedValues = new HashSet<>();
		for (String value : annotation.value()) {
				allowedValues.add(value);
		}
	}
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return allowedValues.contains(value);
	}

}
