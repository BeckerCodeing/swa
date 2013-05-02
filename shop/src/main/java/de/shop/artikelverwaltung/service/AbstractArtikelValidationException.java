package de.shop.artikelverwaltung.service;

import java.util.Collection;

import javax.validation.ConstraintViolation;

import de.shop.artikelverwaltung.domain.Artikel;



public abstract class AbstractArtikelValidationException extends AbstractArtikelServiceException {
	private static final long serialVersionUID = -7579041616155661778L;
	
	private final Collection<ConstraintViolation<Artikel>> violations;
	
	public AbstractArtikelValidationException(Collection<ConstraintViolation<Artikel>> violations) {
		super("Violations: " + violations);
		this.violations = violations;
	}
	
	public Collection<ConstraintViolation<Artikel>> getViolations() {
		return violations;
	}

}
