package de.shop.artikelverwaltung.domain;

import java.io.Serializable;






import javax.validation.constraints.Max;
import javax.validation.constraints.Min;








import de.shop.util.IdGroup;
import static de.shop.util.Constants.MAX_KATEGORIE_ID;
import static de.shop.util.Constants.MIN_KATEGORIE_ID;


public class Kategorie implements Serializable {

	private static final long serialVersionUID = -1065751720590017636L;
	
	@Min(value = MIN_KATEGORIE_ID, message = "{artikelverwaltung.artikel.kategorie.id.min}", groups = IdGroup.class)
	@Max(value = MAX_KATEGORIE_ID, message = "{artikelverwaltung.artikel.kategorie.id.max}", groups = IdGroup.class)
	private int id;

	//Benötigt keine Validierung, da Bezeichnung durch ID vergeben wird
	//Bezeichnung = enum.ordinal()
	private String bezeichnung;
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getBezeichnung() {
		return bezeichnung;
	}
	
	public void setBezeichnung(int id) {
		this.bezeichnung = KategorieType.values()[id].toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bezeichnung == null) ? 0 : bezeichnung.hashCode());
		result = prime * result + id;
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
		final Kategorie other = (Kategorie) obj;
		if (bezeichnung == null) {
			if (other.bezeichnung != null)
				return false;
		} 
		else if (!bezeichnung.equals(other.bezeichnung))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Kategorie [id=" + id + ", bezeichnung=" + bezeichnung + "]";
	}
	
}
