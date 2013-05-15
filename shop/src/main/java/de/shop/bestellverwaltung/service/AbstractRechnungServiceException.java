package de.shop.bestellverwaltung.service;

import de.shop.util.AbstractShopException;

public abstract class AbstractRechnungServiceException extends AbstractShopException {
		private static final long serialVersionUID = -2849585609393128387L;

		public AbstractRechnungServiceException(String msg) {
			super(msg);
		}
		
		public AbstractRechnungServiceException(String msg, Throwable t) {
			super(msg, t);
		}
}

