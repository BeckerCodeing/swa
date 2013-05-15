package de.shop.bestellverwaltung.service;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.util.Locale;
import java.util.Set;

import static de.shop.util.Constants.MIN_ID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.jboss.logging.Logger;

import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.kundenverwaltung.domain.Kunde;
import de.shop.kundenverwaltung.service.KundeService;
import de.shop.util.IdGroup;
import de.shop.util.Log;
import de.shop.util.Mock;
import de.shop.util.ValidatorProvider;

@Log
public class BestellungService implements Serializable {
	private static final long serialVersionUID = 3188789767052580247L;
	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());
	
	@Inject
	private KundeService ks;
	
	@Inject
	@NeueBestellung
	private transient Event<Bestellung> event;
	
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

	public Bestellung findBestellungById(Long id, Locale locale) {
		validateBestellungId(id, locale);
		// TODO Datenbanzugriffsschicht statt Mock
		final Bestellung bestellung = Mock.findBestellungById(id);
		return bestellung;
	}
	
	private void validateBestellungId(Long bestellungId, Locale locale) {
		final Validator validator = validatorProvider.getValidator(locale);
		final Set<ConstraintViolation<Bestellung>> violations = validator.validateValue(Bestellung.class,
				                                                                           "id",
				                                                                           bestellungId,
				                                                                           IdGroup.class);
		if (!violations.isEmpty())
			throw new InvalidBestellungIdException(bestellungId, violations);
	}
	
	public Bestellung createBestellung(Bestellung bestellung, Long kundeId, Locale locale) {
		if (bestellung == null) {
			return bestellung;
		}
		
		final Kunde kunde = ks.findKundeById(kundeId, locale);
		
		return createBestellung(bestellung, kunde, locale);
		
	}

	private Bestellung createBestellung(Bestellung bestellung, Kunde kunde,
			Locale locale) {
		if (bestellung == null) {
			return null;
		}
		
				
		kunde.addBestellung(bestellung);
		bestellung.setKunde(kunde);
		
//		zum Testen
//		bestellung.setKunde(null);
		
		bestellung.setId(MIN_ID);
//		for (Position pos : bestellung.getPositionen()) {
//			pos.setId(KEINE_ID);
//			LOGGER.tracef("Bestellposition: %s", pos);				
//		}
		
		validateBestellung(bestellung, locale, Default.class);
		
		bestellung = Mock.createBestellung(bestellung, kunde);
		event.fire(bestellung);
		
		return bestellung;
	}

	private void validateBestellung(Bestellung bestellung, Locale locale, Class<?>... groups) {
		// Werden alle Constraints beim Einfuegen gewahrt?
		final Validator validator = validatorProvider.getValidator(locale);
		
		final Set<ConstraintViolation<Bestellung>> violations = validator.validate(bestellung);
		if (violations != null && !violations.isEmpty()) {
			LOGGER.debugf("createBestellung: violations=%s", violations);
			throw new InvalidBestellungException(bestellung, violations);
		}
	}

}
