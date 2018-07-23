package org.vbazurtob.HRRecruitApp.lib.validators;

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
		//inProgress = constraint.inProgress();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext ctx) {
		
		Date fromDateField = (Date) new BeanWrapperImpl(value).getPropertyValue(fromDate);
		Date toDateField   = (Date) new BeanWrapperImpl(value).getPropertyValue(toDate);
		
		
		if(toDateField != null && fromDateField != null) {
			return fromDateField.before(toDateField);
		}else {
			return true;//we don't care about the rest. FromDate is validated with NotNull
		}
		
	}

}
