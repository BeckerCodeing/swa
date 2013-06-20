package de.shop.bestellverwaltung.service;

import static de.shop.util.Constants.KEINE_ID;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;




import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.jboss.logging.Logger;

import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.bestellverwaltung.domain.Position;
import de.shop.kundenverwaltung.domain.Kunde;
import de.shop.kundenverwaltung.service.KundeService;
import de.shop.util.Log;
import de.shop.util.ValidatorProvider;

@Log
public class BestellungService implements Serializable {
	private static final long serialVersionUID = 3188789767052580247L;
	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());
	
	@PersistenceContext
	private transient EntityManager em;
	
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

	public Bestellung findBestellungById(Long id) {
				
		final Bestellung bestellung = em.find(Bestellung.class, id);
		return bestellung;
	}
	
	public Bestellung createBestellung(Bestellung bestellung, Long kundeId, Locale locale) {
		if (bestellung == null) {
			return bestellung;
		}

				
	// Den persistenten Kunden mit der transienten Bestellung verknuepfen
	final Kunde kunde = ks.findKundeById(kundeId, locale);
	return createBestellung(bestellung, kunde, locale);
	}

	private Bestellung createBestellung(Bestellung bestellung, Kunde kunde,
			Locale locale) {
		if (bestellung == null) {
			return null;
		}
		
		// Den persistenten Kunden mit der transienten Bestellung verknuepfen	
		if (!em.contains(kunde)) {
			kunde = ks.findKundeById(kunde.getId(), locale);
		}
		kunde.addBestellung(bestellung);
		bestellung.setKunde(kunde);
		
		// IDs zuruecksetzen
		bestellung.setId(KEINE_ID);
		for (Position pos : bestellung.getPositionen()) {
			pos.setId(KEINE_ID);
			LOGGER.tracef("Bestellposition: %s", pos);				
		}
		
		validateBestellung(bestellung, locale, Default.class);
		em.persist(bestellung);
		event.fire(bestellung);
		
		
		return bestellung;
	}

	private void validateBestellung(Bestellung bestellung, Locale locale, Class<?>... groups) {
		
		final Validator validator = validatorProvider.getValidator(locale);
		
		final Set<ConstraintViolation<Bestellung>> violations = validator.validate(bestellung);
		if (violations != null && !violations.isEmpty()) {
			LOGGER.debugf("createBestellung: violations=%s", violations);
			throw new InvalidBestellungException(bestellung, violations);
		}
	}

	
	public List<Bestellung> findBestellungenByKunde(Kunde kunde) {
		if (kunde == null) {
			return Collections.emptyList();
		}
		final List<Bestellung> bestellungen = em.createNamedQuery(Bestellung.FIND_BESTELLUNGEN_BY_KUNDE,
																	Bestellung.class)
											   .setParameter(Bestellung.PARAM_KUNDE, kunde)
											   .getResultList();
		return bestellungen;
	}


	
	

}
