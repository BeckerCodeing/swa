package de.shop.util;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import de.shop.artikelverwaltung.domain.Artikel;
import de.shop.artikelverwaltung.domain.Kategorie;
import de.shop.artikelverwaltung.domain.KategorieType;

//Emulation des Anwendungskerns
public final class Mock {

	private static final int MAX_ID = 99;
	private static final int MAX_Zufall = 250;
	
//	kleiner Zufallsgenerator f¸r den Preis
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
		
		///Enum auf Typ: BAD setzen und anschlieﬂend Kategorie Artikel zuweisen
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
	
	private Mock() { /**/ }
}
