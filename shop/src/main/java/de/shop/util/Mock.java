 package de.shop.util;


import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jboss.logging.Logger;

import de.shop.artikelverwaltung.domain.Artikel;
import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.bestellverwaltung.domain.Rechnung;
import de.shop.bestellverwaltung.domain.Position;
import de.shop.kundenverwaltung.domain.Adresse;
import de.shop.kundenverwaltung.domain.Kunde;


//Emulation des Anwendungskerns
public final class Mock {
	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());

	private static final int MAX_ID = 99;
	
	private static final int MAX_ARTIKEL = 100;

	private static final int MAX_BESTELLUNGEN = 20;
	
	private static final int MAX_POSITIONEN = 5;
	
	private static final int MAX_KUNDEN = 99;
	
	private static final long ADDRESSID = 5;
	
	private static final String POSTLEITZAHL = "12345";
	
	private static final int HAUSNUMMER = 123;
	
	private static final int FUCKCHECKSTYLE = 6;
	
	//private static final int KATEGORIE_ID = 1;
	

	
//	kleiner Zufallsgenerator für den Preis
	private static BigDecimal getRandomPreis() {
		final Random zufall = new Random();
		final BigDecimal ergebnis = new BigDecimal(zufall.nextInt());
		return ergebnis;
	
	}
	///Artikel nach ID suchen
	public static Artikel findArtikelById(Long id) {
		if (id > MAX_ID)
			return null;
		
		final Artikel artikel = new Artikel();
		artikel.setId(id);
		artikel.setBezeichnung("Bezeichnung");
		artikel.setPreis(Mock.getRandomPreis());
		
		///Kategorie immer "BAD" und ID = 1
		//final Kategorie kategorie = new Kategorie();
		//kategorie.setId(KATEGORIE_ID);
		
		///String ausgeben anhand Kategorie ID ==> ordinal von KategorieType
		//kategorie.setBezeichnung(kategorie.getId());
			
		
		//artikel.setKategorie(kategorie);
		
		return artikel;
	}
	
	///Alle Artikel nach Bezeichnung suchen
	public static List<Artikel> findArtikelByBezeichnung(String bezeichnung) {
		final int anzahl = bezeichnung.length();
		final List<Artikel> gesuchteArtikel = new ArrayList<>(anzahl);
		for (int i = 1; i <= anzahl; ++i) {
			final Artikel artikel = findArtikelById(Long.valueOf(i));
			artikel.setBezeichnung(bezeichnung);
			gesuchteArtikel.add(artikel);
		}
		return gesuchteArtikel;
	}
	public static Artikel findArtikelByBez(String bezeichnung) {
				
		final Artikel artikel = Mock.findArtikelById(ADDRESSID);
		return artikel;

		
	}
	
	///Artikel erstellen
	public static Artikel createArtikel(Artikel artikel) {
		
		final String bezeichnung = artikel.getBezeichnung();
		artikel.setId(Long.valueOf(bezeichnung.length()));
		artikel.setPreis(getRandomPreis());
		
		//final KategorieType kategorie = artikel.getKategorie();
		//kategorie.setId(1);
		
		//kategorie.setBezeichnung(kategorie.getId());
		
		//artikel.setKategorie(kategorie);
				
		LOGGER.infof("Neuer Artikel: %s", artikel);
		return artikel;
		
	}
	
	//Rechnung nach ID suchen
		public static Rechnung findRechnungById(Long id) {
			if (id > MAX_ID)
			return null;
			
			final Bestellung bestellung = findBestellungById(id + 1);
		
			final Rechnung rechnung = new Rechnung();
			rechnung.setId(id);
			rechnung.setBestellung(bestellung);
		
			return rechnung;
		}

		
	public static Rechnung createRechnung(Rechnung rechnung, Kunde kunde) {
		LOGGER.infof("Neue Rechnung: %s fuer Kunde: %s", rechnung, kunde);
		return rechnung;
	}
	
	public static void updateRechnung(Rechnung rechnung) {
		LOGGER.infof("Aktualisierte Rechnung: %s", rechnung);	
	}

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
		
		//Erstmal nur zum Testen ob anhand der Kategorie-ID auch der passende Enum in der Bezeichnung gespeichert wird
		//final KategorieType kategorie = artikel.getKategorie();
		//kategorie.setBezeichnung(kategorie.getId());
		//artikel.setKategorie(kategorie);
		LOGGER.infof("Aktualisierter Artikel: %s", artikel);
	}


	//TODO Umbauen, ohne WarenkorbBestellung nach ID suchen 
	public static Bestellung findBestellungById(Long id) {
		if (id > MAX_ID) {
			return null;
		}
		
		
		//Kunde suchen anhand ID
		final Kunde kunde = findKundeById(id);
		//Positionen ermitteln
		final List<Position> positionen = new ArrayList<>();
				
		final Bestellung bestellung = new Bestellung();
		bestellung.setId(id);
		bestellung.setAusgeliefert(false);
		bestellung.setKunde(kunde);
		
		//+1 um zu vermeiden, dass 0 Positionen zugefügt werden.
//		for (int i = 1; i <= (MAX_POSITIONEN + id) % FUCKCHECKSTYLE + 1; i++) {
//			positionen.add(createPosition(findArtikelById(Long.valueOf(i)), Long.valueOf(i)));
//		}
		
		
		//Liste der Positionen zur Bestellung zufügen
		
		bestellung.setPositionen(positionen);
		//Preis berechnen
		bestellung.setGesamtpreis(bestellung.calcPreis());
			
		return bestellung;
	}
	
	//Bestellung nach KundenID suchen
	public static Collection<Bestellung> findBestellungenByKundeId(Long kundeId) {
		final Kunde kunde = findKundeById(kundeId);
			
		// Beziehungsgeflecht zwischen Kunde und Bestellungen aufbauen
		final int anzahl = kundeId.intValue() % MAX_BESTELLUNGEN + 1;  // 1, 2, 3 oder 4 Bestellungen
		
		final List<Bestellung> bestellungen = new ArrayList<>(anzahl);
		for (int i = 1; i <= anzahl; i++) {
			//Bestellung suchen anhand KundenID
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
		kunde.setNachname("Nachname");
		kunde.setVorname("Vorname");
		kunde.setEmail("" + id + "@hska.de");
			
		final Adresse adresse = new Adresse();
		adresse.setId(ADDRESSID);
		adresse.setStrasse("Musterstrasse");
		adresse.setHausnummer(HAUSNUMMER);
		adresse.setPlz(POSTLEITZAHL);
		adresse.setBezeichnung("Testort");
		adresse.setKunde(kunde);
		
		kunde.setAdresse(adresse);
		
			
		return kunde;
	}
	
	public static Kunde findKundeByEmail(String email) {
		if (email.startsWith("x")) {
			return null;
		}
		final Long id = Long.valueOf(email.length());
		final Kunde kunde = new Kunde();
		kunde.setId(id);
		kunde.setNachname("Nachname");
		kunde.setVorname("Vorname");
		kunde.setEmail(email);
			
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
			
		LOGGER.infof("Neuer Kunde: %s", kunde);
		return kunde;
	}
	
	//Kundendaten ändern
	public static void updateKunde(Kunde kunde) {
		LOGGER.infof("Aktualisierter Kunde: %s", kunde);
	}

//	//Position erstellen
//	public static Position createPosition(Artikel artikel, Long id) {
//		final Position position = new Position();
//		position.setArtikel(artikel);
//		position.setId(id);
//		//Nur Modulo schlecht, da Menge = 0 unerwünscht..
//		//position.setMenge(artikel.hashCode() % FUCKCHECKSTYLE + 1);
//		position.setPreis(position.calcPreis());
//		
//		return position;
//	}
	
	
	
//	//nur zum Vereinfachten Testen der REST-Methoden Warenkorb
//	public static Position findPositionById(Long id) {
//		final Artikel artikel = findArtikelById(id);
//		final Position position = createPosition(artikel, id);
//			
//		return position;
//	}
	//Warenkorb des Kunden anhand KundenID leeren, entspricht später "Refresh",
	//also alle Positionen entfernen und Warenkorb neu anlegen
	public static void resetWarenkorb(Long kundeId) {
		LOGGER.infof("Warenkorb von Kunde " + kundeId + " erfolgreich geleert!");
		
	}
	public static void deleteWarenkorbPosition(Kunde kunde, Position position) {
		LOGGER.infof("Position " + position.getId() + " aus Warenkorb von Kunde " 
				+ kunde.getId() + " " + kunde.getVorname() + " " + kunde.getNachname() + " gelöscht!");
		
	}
	//CreateBestellung
	public static Bestellung createBestellung(Bestellung bestellung, Kunde kunde) {
		
		LOGGER.infof("Neue Bestellung: %s fuer Kunde: %s", bestellung, kunde);
		
		return bestellung;
	}
	
	
	
	private Mock() { /**/ }

}
	

