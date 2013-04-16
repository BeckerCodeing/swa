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
	
	private static final int MAX_ARTIKEL = 100;

	private static final int MAX_ZUFALL = 250;

	private static final int MAX_BESTELLUNGEN = 4;
	
	private static final int MAX_KUNDEN = 99;
	
	private static final double PREIS = 0.5;
	
	private static final long ADDRESSID = 5;
	
	private static final int POSTLEITZAHL = 12345;
	
	private static final int HAUSNUMMER = 123;
	
	

	
//	kleiner Zufallsgenerator f¸r den Preis
	private static double getRandomPreis() {
		final Random zufall = new Random();
		
		return zufall.nextInt(MAX_ZUFALL) + PREIS;
	
	}
	///Artikel nach ID suchen
	public static Artikel findArtikelById(Long id) {
		if (id > MAX_ID)
			return null;
		
		final Artikel artikel = new Artikel();
		artikel.setId(id);
		artikel.setBezeichnung("Bezeichnung" + id);
		artikel.setPreis(Mock.getRandomPreis());
		
		///Kategorie immer "BAD" und ID = 1
		final Kategorie kategorie = new Kategorie();
		kategorie.setId(1);
		
		///Enum auf Typ: BAD setzen und anschlieﬂend Kategorie Artikel zuweisen
		final Set<KategorieType> bezeichnung = new HashSet<>();
		bezeichnung.add(KategorieType.BAD);
		kategorie.setBezeichnung(bezeichnung);
		
		artikel.setKategorie(kategorie);
		
		return artikel;
	}
	///Artikel nach Name suchen
	///Artikel erstellen
	public static Collection<Artikel> findArtikelByBezeichnung(String bezeichnung) {
		final int anzahl = bezeichnung.length();
		final Collection<Artikel> gesuchteArtikel = new ArrayList<>(anzahl);
		for (int i = 1; i <= anzahl; i++) {
			final Artikel artikel = findArtikelById(Long.valueOf(i));
			artikel.setBezeichnung(bezeichnung);
			gesuchteArtikel.add(artikel);
		}
		return gesuchteArtikel;
	}
	public static Artikel createArtikel(Artikel artikel) {
		
		final String bezeichnung = artikel.getBezeichnung();
		artikel.setId(Long.valueOf(bezeichnung.length()));
		artikel.setPreis(getRandomPreis());
		
		final Kategorie kategorie = artikel.getKategorie();
		kategorie.setId(1);
		
		final Set<KategorieType> katBezeichnung = new HashSet<>();
		katBezeichnung.add(KategorieType.BAD);
		
		artikel.setKategorie(kategorie);
				
		System.out.println("Neuer Artikel: " + artikel);
		return artikel;
		
	}

	///Alle Artikel ausgeben
	public static Collection<Artikel> findAllArtikel() {
		final int anzahl = MAX_ARTIKEL;
		final Collection<Artikel> alleArtikel = new ArrayList<>(anzahl);
		for (int i = 1; i <= anzahl; i++) {
			final Artikel artikel = findArtikelById(Long.valueOf(i));
			alleArtikel.add(artikel);
		}
		return alleArtikel;
	}


		//Bestellung nach ID suchen

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
		//Bestellung nach KundenID suchen
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
			adresse.setId(ADDRESSID);
			final Ort ort = new Ort();
			ort.setPlz(POSTLEITZAHL);
			ort.setBezeichnung("Testort");
			adresse.setStrasse("Musterstrasse");
			adresse.setHausnummer(HAUSNUMMER);
			adresse.setOrt(ort);
			kunde.setAdresse(adresse);
			
			return kunde;
		}
	
		//TODO: findAllKunden hat noch fehler!
	public static Collection<Kunde> findAllKunden() {
		final int anzahl = MAX_KUNDEN;
		final Collection<Kunde> kunden = new ArrayList<>(anzahl);
		for (int i = 1; i <= anzahl; i++) {
			final Kunde kunde = findKundeById(Long.valueOf(i));
			kunden.add(kunde);
		}
		
		return kunden;
	}
	
	//TODO: findKundenByNachname hat noch fehler
	public static Collection<Kunde> findKundenByNachname(String nachname) {
		final int anzahl = nachname.length();
		final Collection<Kunde> kunden = new ArrayList<>(anzahl);
		for (int i = 1; i <= anzahl; i++) {
			final Kunde kunde = findKundeById(Long.valueOf(i));
			kunde.setNachname(nachname);
			kunden.add(kunde);
		}
		return kunden;
	};
	
	private Mock() { /**/ }
}
	

