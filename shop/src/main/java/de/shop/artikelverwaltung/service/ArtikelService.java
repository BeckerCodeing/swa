package de.shop.artikelverwaltung.service;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;


import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;



import org.jboss.logging.Logger;

import de.shop.artikelverwaltung.domain.Artikel;
import de.shop.util.IdGroup;
import de.shop.util.Log;
import de.shop.util.Mock;
import de.shop.util.ValidatorProvider;


@Log
public class ArtikelService implements Serializable {
	private static final long serialVersionUID = 2929027248430844352L;
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
	
	public Artikel findArtikelById(Long id, Locale locale) {
		validateArtikelId(id, locale);
		//TODO Datenbankzugriffsschicht statt Mock
		final Artikel artikel = Mock.findArtikelById(id);
		return artikel;
	}
	
	public void validateArtikelId(Long artikelId, Locale locale) {
		final Validator validator = validatorProvider.getValidator(locale);
		final Set<ConstraintViolation<Artikel>> violations = validator.validateValue(Artikel.class,
																					 "id",
																					 artikelId,
																					 IdGroup.class);
		
		if (!violations.isEmpty())
			throw new InvalidArtikelIdException(artikelId, violations);
																						
	}
	
	public List<Artikel> findAllArtikel() {
		//TODO Datenbankzugriffsschicht statt Mock
		final List<Artikel> artikel = Mock.findAllArtikel();
		return artikel;
	}
	public List<Artikel> findArtikelByBezeichnung(String bezeichnung, Locale locale) {
		validateBezeichnug(bezeichnung, locale);
		//TODO Datenbankzugriffsschicht statt Mock
		List<Artikel> artikel = Mock.findArtikelByBezeichnung(bezeichnung);
		return artikel;
	}

	private void validateBezeichnug(String bezeichnung, Locale locale) {
		final Validator validator = validatorProvider.getValidator(locale);
		final Set<ConstraintViolation<Artikel>> violations = validator.validateValue(Artikel.class,
																					 "bezeichnung",
																					 bezeichnung,
																					 Default.class);
		if(!violations.isEmpty())
			throw new InvalidBezeichnungException(bezeichnung, violations);
		
	}



	

}
