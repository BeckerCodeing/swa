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

import de.shop.bestellverwaltung.domain.Rechnung;

import de.shop.bestellverwaltung.domain.Position;
import de.shop.bestellverwaltung.domain.Warenkorb;

import de.shop.kundenverwaltung.domain.Adresse;
import de.shop.kundenverwaltung.domain.Kunde;


//Emulation des Anwendungskerns
public final class Mock {

	private static final int MAX_ID = 99;
	
	private static final int MAX_ARTIKEL = 100;

	private static final int MAX_ZUFALL = 250;

	private static final int MAX_BESTELLUNGEN = 20;
	
	private static final int MAX_POSITIONEN = 5;
	
	private static final int MAX_KUNDEN = 99;
	
	private static final double PREIS = 0.5;
	
	private static final long ADDRESSID = 5;
	
	private static final String POSTLEITZAHL = "12345";
	
	private static final int HAUSNUMMER = 123;
	
	private static final int FUCKCHECKSTYLE = 6;
	
	

	
//	kleiner Zufallsgenerator für den Preis
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
		
		///Enum auf Typ: BAD setzen und anschließend Kategorie Artikel zuweisen
		final Set<KategorieType> bezeichnung = new HashSet<>();
		bezeichnung.add(KategorieType.BAD);
		kategorie.setBezeichnung(bezeichnung);
		
		artikel.setKategorie(kategorie);
		
		return artikel;
	}
	
	///Artikel nach Name suchen
	public static List<Artikel> findArtikelByBezeichnung(String bezeichnung) {
		final int anzahl = bezeichnung.length();
		final List<Artikel> gesuchteArtikel = new ArrayList<>(anzahl);
		for (int i = 1; i <= anzahl; i++) {
			final Artikel artikel = findArtikelById(Long.valueOf(i));
			artikel.setBezeichnung(bezeichnung);
			gesuchteArtikel.add(artikel);
		}
		return gesuchteArtikel;
	}
	
	///Artikel erstellen
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

	//TODO Machen!
	public static Rechnung findRechnungById(Long id) {
		return null;
	}
	//TODO findRechnungByKundeId
//	public static Collection<Rechnung> findRechnungByBestellungId(
//			Long bestellungId) {
//		System.out.println("gefundene Rechnung: " + bestellungId);
//		return null;
//	}
	
	public static Rechnung createRechnung(Rechnung rechnung) {
		System.out.println("Rechnung wurde erstellt " + rechnung);
		return null;
	}
	public static void updateRechnung(Rechnung rechnung) {
		System.out.println("Rechnung aktualisiert: " + rechnung);
		
	};

	///Alle Artikel ausgeben
	public static List<Artikel> findAllArtikel() {
		final int anzahl = MAX_ARTIKEL;
		final List<Artikel> alleArtikel = new ArrayList<>(anzahl);
		for (int i = 1; i <= anzahl; i++) {
			final Artikel artikel = findArtikelById(Long.valueOf(i));
			alleArtikel.add(artikel);
		}
		return alleArtikel;
	}
	//Artikel ändern
	public static void updateArtikel(Artikel artikel) {
		System.out.println("Aktualisierter Artikel: " + artikel);
	}


	//Bestellung nach ID suchen
	public static Bestellung findBestellungById(Long id) {
		if (id > MAX_ID) {
			return null;
		}

		final Kunde kunde = findKundeById(id + 1);  // andere ID fuer den Kunden
		final Warenkorb warenkorb = getWarenKorbById(id + FUCKCHECKSTYLE);
		final Bestellung bestellung = new Bestellung();
		bestellung.setId(id);
		bestellung.setAusgeliefert(false);
		bestellung.setKunde(kunde);
		bestellung.setPositionen(warenkorb.getPositionen());
			
		return bestellung;
	}
	
	//Bestellung nach KundenID suchen
	public static Collection<Bestellung> findBestellungenByKundeId(Long kundeId) {
		final Kunde kunde = findKundeById(kundeId);
			
		// Beziehungsgeflecht zwischen Kunde und Bestellungen aufbauen
		final int anzahl = kundeId.intValue() % MAX_BESTELLUNGEN + 1;  // 1, 2, 3 oder 4 Bestellungen
		final Warenkorb warenkorb = getWarenKorbById(kundeId);
		final List<Bestellung> bestellungen = new ArrayList<>(anzahl);
		for (int i = 1; i <= anzahl; i++) {
			final Bestellung bestellung = findBestellungById(Long.valueOf(i));
			bestellung.setKunde(kunde);
			bestellungen.add(bestellung);
			bestellung.setPositionen(warenkorb.getPositionen());
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
		adresse.setStrasse("Musterstrasse");
		adresse.setHausnummer(HAUSNUMMER);
		adresse.setPlz(POSTLEITZAHL);
		adresse.setBezeichnung("Testort");
		kunde.setAdresse(adresse);
			
		return kunde;
	}
	
	//alle Kunden finden
	public static Collection<Kunde> findAllKunden() {
		final int anzahl = MAX_KUNDEN;
		final Collection<Kunde> kunden = new ArrayList<>(anzahl);
		for (int i = 1; i <= anzahl; i++) {
			final Kunde kunde = findKundeById(Long.valueOf(i));
			kunden.add(kunde);			
		}
		return kunden;
	}
	
	//Kunden nach Nachnamen finden
	public static Collection<Kunde> findKundenByNachname(String nachname) {
		final int anzahl = nachname.length();
		final Collection<Kunde> kunden = new ArrayList<>(anzahl);
		for (int i = 1; i <= anzahl; i++) {
			final Kunde kunde = findKundeById(Long.valueOf(i));
			kunde.setNachname(nachname);
			kunden.add(kunde);			
		}
		return kunden;
	}
		
	//Kunde anlegen
	public static Kunde createKunde(Kunde kunde) {
		final String nachname = kunde.getNachname();
		kunde.setId(Long.valueOf(nachname.length()));
		final Adresse adresse = kunde.getAdresse();
		adresse.setId((Long.valueOf(nachname.length())) + 1);
		kunde.setBestellungen(null);
			
		System.out.println("Neuer Kunde: " + kunde);
		return kunde;
	}
	
	//Kundendaten ändern
	public static void updateKunde(Kunde kunde) {
		System.out.println("Aktualisierter Kunde: " + kunde);
	}
	
	//Warenkorb anhand KundenID ausgeben
	public static Warenkorb getWarenKorbById(Long id) {
		if (id > MAX_ID) {
			return null;
		}
		final Kunde kunde = findKundeById(id + 1); // andere ID für den Kunden
		final Warenkorb warenkorb = new Warenkorb();
		warenkorb.setKunde(kunde);
		final List<Position> positionen = new ArrayList<>(MAX_POSITIONEN);
		for (int i = 1; i <= (MAX_POSITIONEN + id) % FUCKCHECKSTYLE; i++) {
			positionen.add(createPosition(findArtikelById(Long.valueOf(i)), Long.valueOf(i)));
		}
		warenkorb.setPositionen(positionen);
		warenkorb.setGesamtpreis(warenkorb.calcPreis());		
		
		return warenkorb;
	}
	//Position erstellen
	public static Position createPosition(Artikel artikel, Long id) {
		final Position position = new Position();
		position.setArtikel(artikel);
		position.setId(id);
		position.setMenge(artikel.hashCode() % FUCKCHECKSTYLE);
		position.setGesamtpreis(position.calcPreis());
		
		return position;
	}
	
	
	public static void updateWarenkorbPosition(Position position) {
		System.out.println("Anzahl der Artikel in Position " + position.getId() 
							+ " geändert auf " + position.getMenge() + " Stück.");
		
	}
	
	//nur zum Vereinfachten Testen der REST-Methoden Warenkorb
	public static Position findPositionById(Long id) {
		final Artikel artikel = findArtikelById(id);
		final Position position = createPosition(artikel, id);
			
		return position;
	}
	//Warenkorb des Kunden anhand KundenID leeren, entspricht später "Refresh",
	//also alle Positionen entfernen und Warenkorb neu anlegen
	public static void resetWarenkorb(Long kundeId) {
		System.out.println("Warenkorb von Kunde " + kundeId + " erfolgreich geleert!");
		
	}
	public static void deleteWarenkorbPosition(Kunde kunde, Position position) {
		System.out.println("Position " + position.getId() + " aus Warenkorb von Kunde " 
				+ kunde.getId() + " " + kunde.getVorname() + " " + kunde.getNachname() + " gelöscht!");
		
	}
	//CreateBestellung
	public static Bestellung createBestellung(Bestellung bestellung) {
		bestellung.setId(Long.valueOf(FUCKCHECKSTYLE));
		final Kunde kunde = findKundeById(bestellung.getId() + FUCKCHECKSTYLE);
		bestellung.setKunde(kunde);
		final Warenkorb warenkorb = getWarenKorbById(bestellung.getKunde().getId());
		bestellung.setPositionen(warenkorb.getPositionen());
		
		System.out.println("Neue Bestellung mit der Id: " + bestellung.getId() + " erstellt.");
		return bestellung;
	}
	
	private Mock() { /**/ }
}
	

