package de.shop.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.shop.artikelverwaltung.domain.Artikel;
import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.kundenverwaltung.domain.Adresse;
import de.shop.kundenverwaltung.domain.Kunde;
import de.shop.kundenverwaltung.domain.Ort;

//Emulation des Anwendungskerns
public final class Mock {

	private static final int MAX_ID = 99;
	private static final int MAX_BESTELLUNGEN = 4;
	
	
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
	
	public static Bestellung findBestellungById(Long id) {
		if (id > MAX_ID) {
			return null;
		}

		final Kunde kunde = findKundeById(id + 1);  // andere ID fuer den Kunden

		final Bestellung bestellung = new Bestellung();
		bestellung.setId(id);
		bestellung.setAusgeliefert(false);
		bestellung.setKunde(kunde);
		
		return bestellung;
	}
	
	public static Collection<Bestellung> findBestellungenByKundeId(Long kundeId) {
		final Kunde kunde = findKundeById(kundeId);
		
		// Beziehungsgeflecht zwischen Kunde und Bestellungen aufbauen
		final int anzahl = kundeId.intValue() % MAX_BESTELLUNGEN + 1;  // 1, 2, 3 oder 4 Bestellungen
		final List<Bestellung> bestellungen = new ArrayList<>(anzahl);
		for (int i = 1; i <= anzahl; i++) {
			final Bestellung bestellung = findBestellungById(Long.valueOf(i));
			bestellung.setKunde(kunde);
			bestellungen.add(bestellung);			
		}
		kunde.setBestellungen(bestellungen);
		
		return bestellungen;
	}
	
	public static Kunde findKundeById(Long id) {
		if (id > MAX_ID) {
			return null;
		}
		
		final Kunde kunde = new Kunde();
		kunde.setId(id);
		kunde.setNachname("Nachname" + id);
		kunde.setVorname("Vorname" + id);
		kunde.setEmail("" + id + "@hska.de");
		
		final Adresse adresse = new Adresse();
		adresse.setId(id + 1);        // andere ID fuer die Adresse
		final Ort ort = new Ort();
		ort.setPlz(12345);
		ort.setBezeichnung("Testort");
		ort.setStrasse("Musterstrasse");
		ort.setHausnummer(123);
		adresse.setOrt(ort);
		kunde.setAdresse(adresse);
		
		return kunde;
	}
	
	private Mock(){/**/}
}
