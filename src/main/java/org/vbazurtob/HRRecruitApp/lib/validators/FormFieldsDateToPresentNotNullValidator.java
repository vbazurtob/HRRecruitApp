package org.vbazurtob.HRRecruitApp.lib.validators;

import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

public class FormFieldsDateToPresentNotNullValidator  implements ConstraintValidator<FormFieldsDateToPresentNotNullConstraint, Object>  {

	private String inProgress;
	private String toDate;
	
	@Override
	public void initialize(FormFieldsDateToPresentNotNullConstraint constraint) {
		inProgress = constraint.inProgress();
		toDate = constraint.toDate();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext ctx) {
		
		String inProgressField = (String) new BeanWrapperImpl(value).getPropertyValue(inProgress);
		Date toDateField   = (Date) new BeanWrapperImpl(value).getPropertyValue(toDate);
		// System.out.println( "inProgress: " + inProgressField + " toDate: " + toDateField );
		
		if( (inProgressField == null || inProgressField.equals("N") ) && toDateField == null ) {
			return false;
		}else {
			return true;//we don't care about the rest
		}
		
	}

}
