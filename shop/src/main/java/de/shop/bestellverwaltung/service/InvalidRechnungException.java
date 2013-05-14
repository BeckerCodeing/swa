package de.shop.bestellverwaltung.service;

import java.util.Collection;

import javax.validation.ConstraintViolation;

import de.shop.bestellverwaltung.domain.Rechnung;

public class InvalidRechnungException extends AbstractRechnungValidationException {
	private static final long serialVersionUID = 4255133082483647701L;
	private final Rechnung rechnung;

	public InvalidRechnungException(Rechnung rechnung,
			                     Collection<ConstraintViolation<Rechnung>> violations) {
		super(violations);
		this.rechnung = rechnung;
	}

	public Rechnung getRechnung() {
		return rechnung;
	}
}
