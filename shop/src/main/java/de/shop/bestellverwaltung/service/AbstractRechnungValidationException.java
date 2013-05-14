package de.shop.bestellverwaltung.service;

import java.util.Collection;

import javax.validation.ConstraintViolation;

import de.shop.bestellverwaltung.domain.Rechnung;

public class AbstractRechnungValidationException extends AbstractRechnungServiceException {
	private static final long serialVersionUID = -6924234959157503601L;
	private final Collection<ConstraintViolation<Rechnung>> violations;
	
	public AbstractRechnungValidationException(Collection<ConstraintViolation<Rechnung>> violations) {
		super("Violations: " + violations);
		this.violations = violations;
	}
	
	public Collection<ConstraintViolation<Rechnung>> getViolations() {
		return violations;
	}
}
