package org.vbazurtob.HRRecruitApp.lib;

import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

public class FormFieldsDatesValidator  implements ConstraintValidator<FormFieldsDatesConstraint , Object>  {

	private String fromDate;
	private String toDate;
	
	@Override
	public void initialize(FormFieldsDatesConstraint constraint) {
		fromDate = constraint.from();
		toDate = constraint.to();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext ctx) {
		
		Date fromDateField = (Date) new BeanWrapperImpl(value).getPropertyValue(fromDate);
		Date toDateField   = (Date) new BeanWrapperImpl(value).getPropertyValue(toDate);
		
		
		System.out.println( fromDateField + " < " + toDateField );
		if(toDateField != null && fromDateField != null) {
<<<<<<< HEAD
			
			// System.out.println( "if date before?" + fromDateField.before(toDateField)  );
			
			
=======
>>>>>>> b5256d02c41f6f6ae9c92ffeb8fec7c47c4e487b
			return fromDateField.before(toDateField);
		}else {
			return true;//we don't care about the rest. FromDate is validated with NotNull
		}
		
	}

}
