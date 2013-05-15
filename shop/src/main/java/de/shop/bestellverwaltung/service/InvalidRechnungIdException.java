package de.shop.bestellverwaltung.service;

import java.util.Collection;

import javax.validation.ConstraintViolation;

import de.shop.bestellverwaltung.domain.Rechnung;

public class InvalidRechnungIdException extends AbstractRechnungValidationException {
	private static final long serialVersionUID = -6924234959157503601L;
	private final Long rechnungId;
	
	public InvalidRechnungIdException(Long rechnungId, Collection<ConstraintViolation<Rechnung>> violations) {
		super(violations);
		this.rechnungId = rechnungId;
	}

	public Long getRechnungId() {
		return rechnungId;
	}
}
