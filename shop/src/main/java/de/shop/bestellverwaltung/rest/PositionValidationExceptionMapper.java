package de.shop.bestellverwaltung.rest;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.Response.Status.CONFLICT;

import java.util.Collection;

import javax.validation.ConstraintViolation;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import de.shop.bestellverwaltung.domain.Position;
import de.shop.bestellverwaltung.service.AbstractPositionValidationException;

public class PositionValidationExceptionMapper implements ExceptionMapper<AbstractPositionValidationException> {
	private static final String NEWLINE = System.getProperty("line.seperator");
	
	@Override
	public Response toResponse(AbstractPositionValidationException e) {
		final Collection<ConstraintViolation<Position>> violations = e.getViolations();
		final StringBuilder sb = new StringBuilder();
		for (ConstraintViolation<Position> v : violations) {
			sb.append(v.getMessage());
			sb.append(NEWLINE);
		}
		final String responseStr = sb.toString();
		final Response response = Response.status(CONFLICT)
										  .type(TEXT_PLAIN)
										  .entity(responseStr)
										  .build();
		return response;
	}

}
