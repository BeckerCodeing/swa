package de.shop.bestellverwaltung.service;

import java.util.Collection;

import javax.validation.ConstraintViolation;

import de.shop.bestellverwaltung.domain.Position;

public class InvalidPositionIdException extends AbstractPositionValidationException {
	private static final long serialVersionUID = 7921343422690540548L;
	
	private final Long positionId;
	
	public InvalidPositionIdException(Long positionId, Collection<ConstraintViolation<Position>> violations) {
		super(violations);
		this.positionId = positionId;
	}
	
	public Long getPositionId() {
		return positionId;
	}
	
	
}
