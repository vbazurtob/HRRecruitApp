package org.vbazurtob.HRRecruitApp.lib.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Constraint(validatedBy = FormFieldsMatchValidator.class )
@Target( { ElementType.METHOD, ElementType.FIELD } )
@Retention(RetentionPolicy.RUNTIME)
public @interface FormFieldsMatchConstraint {

	String message() default "Fields don't match";
	
	String field();
	
	
	String fieldMatch();

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
	
	
    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
    	FormFieldsMatchConstraint[] value();
    }
	
}
