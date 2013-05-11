package de.shop.bestellverwaltung.service;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.util.Locale;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.jboss.logging.Logger;

import de.shop.bestellverwaltung.domain.Position;
import de.shop.util.IdGroup;
import de.shop.util.Log;
import de.shop.util.Mock;
import de.shop.util.ValidatorProvider;

@Log
public class PositionService implements Serializable {
	private static final long serialVersionUID = -7322125733055682131L;
	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());
	
	@Inject
	private ValidatorProvider validatorProvider;
	
	@PostConstruct
	private void postConstruct() {
		LOGGER.debugf("CDI fähiges Bean %s wurde erzeugt", this);
	}
	
	@PreDestroy
	private void preDestroy() {
		LOGGER.debugf("CDI fähiges Bean %s wird gelöscht", this);
	}

	public Position findPositionById(Long id, Locale locale) {
		validatePositionId(id, locale);
		// TODO Datenbankzugriffsschicht statt Mock
		final Position position = Mock.findPositionById(id);
		return position;
	}

	private void validatePositionId(Long positionId, Locale locale) {
		final Validator validator = validatorProvider.getValidator(locale);
		final Set<ConstraintViolation<Position>> violations = validator.validateValue(Position.class,
																					  "id",
																					  positionId,
																					  IdGroup.class);
		if (!violations.isEmpty())
			throw new InvalidPositionIdException(positionId, violations);
		
	}
	
	

}
