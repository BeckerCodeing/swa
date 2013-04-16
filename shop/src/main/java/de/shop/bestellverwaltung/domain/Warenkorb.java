package de.shop.bestellverwaltung.domain;

import java.util.List;

import de.shop.kundenverwaltung.domain.Kunde;

public class Warenkorb {

	private Kunde kunde;
	private List<Position> positionen;
	private double gesamtpreis;
	
	public double getGesamtpreis() {
		return gesamtpreis;
	}
	public void setGesamtpreis(double gesamtpreis) {
		this.gesamtpreis = gesamtpreis;
	}
	public List<Position> getPositionen() {
		return positionen;
	}
	public void setPositionen(List<Position> positionen) {
		this.positionen = positionen;
	}
	public Kunde getKunde() {
		return kunde;
	}
	public void setKunde(Kunde kunde) {
		this.kunde = kunde;
	}
}
