package org.vbazurtob.HRRecruitApp.lib.validators;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RUNTIME)
@Target({ ElementType.TYPE })
@Constraint(validatedBy=FormFieldsDateToPresentNotNullValidator.class)
public @interface FormFieldsDateToPresentNotNullConstraint {
	
	String inProgress();
	String toDate();
	String message() default "toDate cannot be null because inProgress is either null or N. Check validation";
	
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

}
