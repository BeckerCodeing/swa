package de.shop.util;

import de.shop.artikelverwaltung.domain.Artikel;

//Emulation des Anwendungskerns
public class Mock {

	private static final int MAX_ID = 99;
	
	
	public static Artikel findArtikelById(int id){
		if(id > MAX_ID){
			return null;
		}
	final Artikel artikel = new Artikel();
	artikel.setId(id);
	artikel.setBezeichnung("Bezeichnung" + id);
	artikel.setPreis(2.45);
	
	
	return artikel;
	}
	public static Artikel createArtikel(Artikel artikel){
		
		final String bezeichnung = artikel.getBezeichnung();
		@SuppressWarnings("unused")
		final double preis = artikel.getPreis();
		artikel.setId(bezeichnung.hashCode());
		
		return artikel;
		
		
	}
	
	private Mock(){/**/}
}
