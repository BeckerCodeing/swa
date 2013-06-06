package de.shop.artikelverwaltung.domain;

import java.io.Serializable;











import static de.shop.util.Constants.KEINE_ID;






import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

















import de.shop.util.IdGroup;
import static de.shop.util.Constants.MAX_KATEGORIE_ID;
import static de.shop.util.Constants.MIN_KATEGORIE_ID;

@Entity
@Table(name = "kategorie")
public class Kategorie implements Serializable {

	private static final long serialVersionUID = -1065751720590017636L;
	
	@Id
	@GeneratedValue
	@Column(nullable = false, updatable = false)
	@Min(value = MIN_KATEGORIE_ID, message = "{artikelverwaltung.artikel.kategorie.id.min}", groups = IdGroup.class)
	@Max(value = MAX_KATEGORIE_ID, message = "{artikelverwaltung.artikel.kategorie.id.max}", groups = IdGroup.class)
	private Long id = KEINE_ID;

	@Enumerated(EnumType.STRING)
	private KategorieType bezeichnung;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public KategorieType getBezeichnung() {
		return bezeichnung;
	}
	public void setBezeichnung(KategorieType bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bezeichnung == null) ? 0 : bezeichnung.hashCode());
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
		Kategorie other = (Kategorie) obj;
		if (bezeichnung != other.bezeichnung)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Kategorie [id=" + id + ", bezeichnung=" + bezeichnung + "]";
	}
	
}
