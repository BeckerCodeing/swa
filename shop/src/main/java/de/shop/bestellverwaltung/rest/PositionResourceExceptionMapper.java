package de.shop.bestellverwaltung.rest;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.Response.Status.CONFLICT;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import de.shop.bestellverwaltung.service.AbstractPositionServiceException;

public class PositionResourceExceptionMapper implements ExceptionMapper<AbstractPositionServiceException> {
	@Override
	public Response toResponse(AbstractPositionServiceException e) {
		final String msg = e.getMessage();
		final Response response = Response.status(CONFLICT)
										  .type(TEXT_PLAIN)
										  .entity(msg)
										  .build();
		return response;
	}
}
