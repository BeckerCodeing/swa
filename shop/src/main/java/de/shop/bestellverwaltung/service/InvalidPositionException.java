package de.shop.bestellverwaltung.service;

import java.util.Collection;

import javax.validation.ConstraintViolation;

import de.shop.bestellverwaltung.domain.Position;

public class InvalidPositionException extends
		AbstractPositionValidationException {
	private static final long serialVersionUID = -5813437208193892977L;
	private final Position position;
	
	public InvalidPositionException(Position position,
			Collection<ConstraintViolation<Position>> violations) {
		super(violations);
		this.position = position;
	}
	public Position getPosition() {
		return position;
	}
}
