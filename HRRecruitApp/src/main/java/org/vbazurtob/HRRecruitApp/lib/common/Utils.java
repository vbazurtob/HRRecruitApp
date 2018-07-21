package org.vbazurtob.HRRecruitApp.lib.common;

import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class Utils {

	public Utils() {
	}
	
	public static void printFormErrors( BindingResult results ) {
		
//		 DEBUG form Validations
				System.out.println("Errors? " + results.hasErrors() );
				
				List<ObjectError> oes = results.getAllErrors();
				for( ObjectError oe: oes ) {
					System.out.println(oe.toString());
				}
		
	}

}
