package de.shop.artikelverwaltung.service;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;













import org.jboss.logging.Logger;

import de.shop.artikelverwaltung.domain.Artikel;
import de.shop.util.IdGroup;
import de.shop.util.Log;
import de.shop.util.ValidatorProvider;


@Log
public class ArtikelService implements Serializable {
	private static final long serialVersionUID = 2929027248430844352L;
	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());
	
	@PersistenceContext
	private transient EntityManager em;
	
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
	
	//Artikel anhand ID ausgeben, wenn Artikel nicht gefunden null zurück
	public Artikel findArtikelById(Long id, Locale locale) {
		validateArtikelId(id, locale);
		
		Artikel artikel = null;
		
		try{
			artikel = em.find(Artikel.class, id);
		}
		catch (NoResultException e) {
			return null;
		}
		
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
	//TODO Hier muss man im Bezug auf Bestellung vermutlich noch was machen :)
	public List<Artikel> findArtikelByIds(List<Long> ids, Locale locale) {
			
		if (ids == null || ids.isEmpty()) {
			return Collections.emptyList();
		}
		final List<Artikel> gefundeneArtikel = new ArrayList<>();
		
		for (Long artikelId : ids) {
			gefundeneArtikel.add(findArtikelById(artikelId, locale));
		}
		
		return gefundeneArtikel;
		
		
		
	}
	// Alle Artikel ausgeben
	public List<Artikel> findAllArtikel() {
			List<Artikel> artikel;
			artikel = em.createNamedQuery(Artikel.FIND_ARTIKEL_ORDER_BY_ID, Artikel.class)
						.getResultList();
		return artikel;
	}
	// Artikel anhand Bezeichnung suchen, wobei Bezeichnung mit Wildcards
	public List<Artikel> findArtikelByBezeichnungWildcard(String bezeichnung, Locale locale) {
		validateBezeichnung(bezeichnung, locale);
		
		List<Artikel> artikel;
		artikel = em.createNamedQuery(Artikel.FIND_ARTIKEL_BY_BEZ, Artikel.class)
					.setParameter(Artikel.PARAM_BEZEICHNUNG, "%" + bezeichnung + "%")
					.getResultList();
		return artikel;
	}
	// Artikel exakt nach einer Bezeichnung suchen, Ergebnis 1 oder 0 Artikel
	public Artikel findArtikelByBezeichnung(String bezeichnung, Locale locale) {
		validateBezeichnung(bezeichnung, locale);
		try {
			final Artikel artikel = em.createNamedQuery(Artikel.FIND_ARTIKEL_BY_BEZ, Artikel.class)
									  .setParameter(Artikel.PARAM_BEZEICHNUNG, bezeichnung)
									  .getSingleResult();
			return artikel;
		}
		catch (NoResultException e) {
			return null;
		}
	}
	private void validateBezeichnung(String bezeichnung, Locale locale) {
		final Validator validator = validatorProvider.getValidator(locale);
		final Set<ConstraintViolation<Artikel>> violations = validator.validateValue(Artikel.class,
																					 "bezeichnung",
																					 bezeichnung,
																					 Default.class);
		if (!violations.isEmpty())
			throw new InvalidBezeichnungException(bezeichnung, violations);
		
	}


	public Artikel createArtikel(Artikel artikel, Locale locale) {
		if (artikel == null) {
			return artikel;
		}
		//Werden Constraints gewahrt?
		validateArtikel(artikel, locale, Default.class);
		
		//Prüfung ob Bezeichnung schon existiert
		try{
			em.createNamedQuery(Artikel.FIND_ARTIKEL_BY_BEZ, Artikel.class)
			  .setParameter(Artikel.PARAM_BEZEICHNUNG, artikel.getBezeichnung())
			  .getSingleResult();
			throw new BezeichnungExistsException(artikel.getBezeichnung());
		}
		catch (NoResultException e) {
			//Noch kein Artikel mit dieser Bezeichnung
			LOGGER.trace("Artikel existiert noch nicht");
		}	
		em.persist(artikel);
		return artikel;
	}

	private void validateArtikel(Artikel artikel, Locale locale, Class<?>... groups) {
		
		//Werden alle Constrainst beim Einfügen gewahrt?
		final Validator validator = validatorProvider.getValidator(locale);
		
		final Set<ConstraintViolation<Artikel>> violations = validator.validate(artikel, groups);
		if (!violations.isEmpty()) {
			throw new InvalidArtikelException(artikel, violations);
		}
		
		
	}

	public Artikel updateArtikel(Artikel artikel, Locale locale) {
		if (artikel == null) {
			return null;
		}
		
		//Werden Constraints gewahrt?
		validateArtikel(artikel, locale, Default.class, IdGroup.class);
		
		// Artikel von Entity Manager trennen, weil z.B. nach Bezeichnung gesucht wird
		em.detach(artikel);
		
		// Gibt es anderes Objekt mit gleicher Bezeichnung?
		final Artikel vorhandenerArtikel = findArtikelByBezeichnung(artikel.getBezeichnung(), locale);
		if (vorhandenerArtikel != null) {
			em.detach(vorhandenerArtikel);
			if (vorhandenerArtikel.getId().longValue() != artikel.getId().longValue()) {
				throw new BezeichnungExistsException(artikel.getBezeichnung());
			}
		}
		em.merge(artikel);
		return artikel;
			
	}

}
