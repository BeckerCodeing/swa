package de.shop.kundenverwaltung.domain;

public class Adresse {
	
	private Long id;
	private Ort ort;
	private String strasse;
	private int hausnummer;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Ort getOrt() {
		return ort;
	}
	public void setOrt(Ort ort) {
		this.ort = ort;
	}
	public String getStrasse() {
		return strasse;
	}
	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}
	public int getHausnummer() {
		return hausnummer;
	}
	public void setHausnummer(int hausnummer) {
		this.hausnummer = hausnummer;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hausnummer;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ort == null) ? 0 : ort.hashCode());
		result = prime * result + ((strasse == null) ? 0 : strasse.hashCode());
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
		final Adresse other = (Adresse) obj;
		if (hausnummer != other.hausnummer)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} 
		else if (!id.equals(other.id))
			return false;
		if (ort == null) {
			if (other.ort != null)
				return false;
		} 
		else if (!ort.equals(other.ort))
			return false;
		if (strasse == null) {
			if (other.strasse != null)
				return false;
		} 
		else if (!strasse.equals(other.strasse))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Adresse [id=" + id + ", ort=" + ort + ", strasse=" + strasse
				+ ", hausnummer=" + hausnummer + "]";
	}

}
