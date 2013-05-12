package de.shop.bestellverwaltung.service;

import java.util.Collection;

import javax.validation.ConstraintViolation;

import de.shop.bestellverwaltung.domain.Position;

public abstract class AbstractPositionValidationException extends AbstractPositionServiceException {
	private static final long serialVersionUID = -4237790986527174683L;
	
	private final Collection<ConstraintViolation<Position>> violations;
	
	public AbstractPositionValidationException(Collection<ConstraintViolation<Position>> violations) {
		super("Violations: " + violations);
		this.violations = violations;

	}
	public Collection<ConstraintViolation<Position>> getViolations() {
		return violations;
	}

}
