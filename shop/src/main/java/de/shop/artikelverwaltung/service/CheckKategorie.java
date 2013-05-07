package de.shop.artikelverwaltung.service;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
///Eigene Annotation CheckKategorie für die Validierung der Kategorie Bezeichnung
@Target({ METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = CheckKategorieValidator.class)
@Documented
public @interface CheckKategorie {
	
	String message() default "{artikelverwaltung.artikel.kategorie.bezeichnung}";
	
	String[] value() default {};
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};

}
