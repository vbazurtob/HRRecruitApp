package org.vbazurtob.HRRecruitApp.lib.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

public class FormFieldsMatchValidator implements ConstraintValidator<FormFieldsMatchConstraint, Object> {

	private String field;
	
	private String fieldMatch;
	
	@Override
	public void initialize(FormFieldsMatchConstraint constraint) {
		this.field = constraint.field();
		this.fieldMatch = constraint.fieldMatch();
	}


	@Override
	public boolean isValid(Object value, ConstraintValidatorContext ctx) {
		
		Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
		Object fieldValueMatch = new BeanWrapperImpl(value).getPropertyValue(fieldMatch);
		
		if(fieldValue != null) {
			return fieldValue.equals(fieldValueMatch);
		}else {
			return fieldValue == null;
		}
		
	}
	
	
}
