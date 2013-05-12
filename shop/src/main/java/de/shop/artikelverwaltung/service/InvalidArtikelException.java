package de.shop.artikelverwaltung.service;

import java.util.Collection;

import javax.validation.ConstraintViolation;

import de.shop.artikelverwaltung.domain.Artikel;

/** 
 * Exception, die ausgegeben wird, wenn die Attributwerte eines Artikel nicht korrekt sind
 */
public class InvalidArtikelException extends AbstractArtikelValidationException {
	
	private static final long serialVersionUID = 3600155661087187266L;
	private final Artikel artikel;
	
	public InvalidArtikelException(Artikel artikel, Collection<ConstraintViolation<Artikel>> violations) {
		
		super(violations);
		this.artikel = artikel;
	}
	public Artikel getArtikel() {
		return artikel;
	}
}
