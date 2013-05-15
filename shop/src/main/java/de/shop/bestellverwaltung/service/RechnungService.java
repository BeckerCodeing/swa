package de.shop.bestellverwaltung.service;

import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.Locale;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.jboss.logging.Logger;

import de.shop.bestellverwaltung.domain.Rechnung;
import de.shop.util.IdGroup;
import de.shop.util.Mock;
import de.shop.util.ValidatorProvider;

public class RechnungService {

private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());
	
	@Inject
	private ValidatorProvider validatorProvider;
	
	@PostConstruct
	private void postConstruct() {
		LOGGER.debugf("CDI-faehiges Bean %s wurde erzeugt", this);
	}
	
	@PreDestroy
	private void preDestroy() {
		LOGGER.debugf("CDI-faehiges Bean %s wird geloescht", this);
	}

	public Rechnung findRechnungById(Long id, Locale locale) {
		validateRechnungId(id, locale);
		// TODO Datenbanzugriffsschicht statt Mock
		final Rechnung rechnung = Mock.findRechnungById(id);
		return rechnung;
	}
	
	private void validateRechnungId(Long rechnungId, Locale locale) {
		final Validator validator = validatorProvider.getValidator(locale);
		final Set<ConstraintViolation<Rechnung>> violations = validator.validateValue(Rechnung.class,
				                                                                           "id",
				                                                                           rechnungId,
				                                                                           IdGroup.class);
		if (!violations.isEmpty())
			throw new InvalidRechnungIdException(rechnungId, violations);
	}
	
	
	
	public Rechnung createRechnung(Rechnung rechnung, Locale locale) {
		if (rechnung == null) {
			return rechnung;
		}

		// Werden alle Constraints beim Einfuegen gewahrt?
		validateRechnung(rechnung, locale, Default.class);

		rechnung = Mock.createRechnung(rechnung, null);

		return rechnung;
	}

	private void validateRechnung(Rechnung rechnung, Locale locale, Class<?>... groups) {
		// Werden alle Constraints beim Einfuegen gewahrt?
		final Validator validator = validatorProvider.getValidator(locale);
		
		final Set<ConstraintViolation<Rechnung>> violations = validator.validate(rechnung, groups);
		if (!violations.isEmpty()) {
			throw new InvalidRechnungException(rechnung, violations);
		}
	}

	public Rechnung updateRechnung(Rechnung rechnung, Locale locale) {
		if (rechnung == null) {
			return null;
		}

		// Werden alle Constraints beim Modifizieren gewahrt?
		validateRechnung(rechnung, locale, Default.class, IdGroup.class);
		
		// TODO Datenbanzugriffsschicht statt Mock
		Mock.updateRechnung(rechnung);
		
		return rechnung;
	}

	public Collection<Rechnung> findRechnungByKundeId(Long kundeId) {
		// TODO Auto-generated method stub
		return null;
	}
}
