package de.shop.kundenverwaltung.domain;

import java.io.Serializable;

public class Ort implements Serializable{
	
	private static final long serialVersionUID = 6131379693351641182L;
	private int plz;
	private String bezeichnung;
	
	public int getPlz() {
		return plz;
	}
	public void setPlz(int plz) {
		this.plz = plz;
	}
	public String getBezeichnung() {
		return bezeichnung;
	}
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bezeichnung == null) ? 0 : bezeichnung.hashCode());
		result = prime * result + plz;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Ort other = (Ort) obj;
		if (bezeichnung == null) {
			if (other.bezeichnung != null)
				return false;
		} 
		else if (!bezeichnung.equals(other.bezeichnung))
			return false;
		if (plz != other.plz)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Ort [plz=" + plz + ", bezeichnung=" + bezeichnung + "]";
	}
	
	
	

}
