package org.vbazurtob.HRRecruitApp.lib;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Date;

import javax.validation.Constraint;
import javax.validation.Payload;



@Constraint(validatedBy = FormFieldsDatesValidator.class)
@Retention(RUNTIME)
@Target({ ElementType.TYPE })
public @interface FormFieldsDatesConstraint {

	String from();
	String to();
	String message() default "Dates contain errors. Check validation";
	
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
	
}
