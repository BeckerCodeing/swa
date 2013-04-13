package de.shop.util;


import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import de.shop.artikelverwaltung.domain.Artikel;

import de.shop.artikelverwaltung.domain.Kategorie;
import de.shop.artikelverwaltung.domain.KategorieType;

import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.kundenverwaltung.domain.Adresse;
import de.shop.kundenverwaltung.domain.Kunde;
import de.shop.kundenverwaltung.domain.Ort;


//Emulation des Anwendungskerns
public final class Mock {

	private static final int MAX_ID = 99;

	private static final int MAX_Zufall = 250;

	private static final int MAX_BESTELLUNGEN = 4;

	
//	kleiner Zufallsgenerator f�r den Preis
	private static int Zufallsgenerator() {
		final Random zufall = new Random();
		
		return zufall.nextInt(MAX_Zufall);
	
	}
	///Artikel nach ID suchen
	public static Artikel findArtikelById(Long id) {
		if (id > MAX_ID)
			return null;
		
		final Artikel artikel = new Artikel();
		artikel.setId(id);
		artikel.setBezeichnung("Bezeichnung" + id);
		artikel.setPreis(Mock.Zufallsgenerator() + 0.5);
		
		///Kategorie immer "BAD" und ID = 1
		final Kategorie kategorie = new Kategorie();
		kategorie.setId(1);
		
		///Enum auf Typ: BAD setzen und anschlie�end Kategorie Artikel zuweisen
		final Set<KategorieType> bezeichnung = new HashSet<>();
		bezeichnung.add(KategorieType.BAD);
		kategorie.setBezeichnung(bezeichnung);
		
		artikel.setKategorie(kategorie);
		
		return artikel;
	}
	///Artikel erstellen
	public static Artikel createArtikel(Artikel artikel) {
		
		final String bezeichnung = artikel.getBezeichnung();
		artikel.setId(Long.valueOf(bezeichnung.length()));
		artikel.setPreis(23.4);
		
		final Kategorie kategorie = artikel.getKategorie();
		kategorie.setId(1);
		
		final Set<KategorieType> katBezeichnung = new HashSet<>();
		katBezeichnung.add(KategorieType.BAD);
		
		artikel.setKategorie(kategorie);
				
		System.out.println("Neuer Artikel: " + artikel);
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
		
		//Kunden nach der ID Suchen
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
			adresse.setId(id + 4);
			final Ort ort = new Ort();
			ort.setPlz(12345);
			ort.setBezeichnung("Testort");
			ort.setStrasse("Musterstrasse");
			ort.setHausnummer(123);
			adresse.setOrt(ort);
			kunde.setAdresse(adresse);
			
			return kunde;
		}
	
	

	private Mock() { /**/ };
	
}
	

