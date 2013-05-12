package de.shop.bestellverwaltung.service;

import de.shop.util.AbstractShopException;

public abstract class AbstractPositionServiceException extends AbstractShopException {
	private static final long serialVersionUID = 7641294699771891948L;
	
	public AbstractPositionServiceException(String msg) {
		super(msg);
	}
	
	public AbstractPositionServiceException(String msg, Throwable t) {
		super(msg, t);
	}

}
