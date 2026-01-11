/*
 * Personal License Agreement
 * Copyright Notice
 *
 * © 2025 Voltaire Bazurto Blacio. All rights reserved.
 * License Terms
 *
 *     Ownership: All code contained in this portfolio is the sole property of Voltaire Bazurto Blacio and is hereby copyrighted by me.
 *
 *     Permitted Use: Others are welcome to view and study the code for personal, educational, or non-commercial purposes. You may share insights or information about the code, but you cannot use it for any commercial products, either as-is or in a derivative form.
 *
 *     Restrictions: The code may not be used, reproduced, or distributed for commercial purposes under any circumstances without my explicit written permission.
 *
 *     Rights Reserved: I reserve the right to use the code, or any future versions thereof, for my own purposes in any way I choose, including but not limited to the development of future commercial derivative works under my name or personal brand.
 *
 *     Disclaimer: The code is provided "as is" without warranty of any kind, either express or implied. I am not responsible for any damages resulting from the use of this code.
 *
 * By accessing this portfolio, you agree to abide by these terms.
 */

package org.vbazurtob.HRRecruitApp.lib.validators;

import java.util.Date;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class FormFieldsDatesValidator
    implements ConstraintValidator<FormFieldsDatesConstraint, Object> {

  private String fromDate;
  private String toDate;

  @Override
  public void initialize(FormFieldsDatesConstraint constraint) {
    fromDate = constraint.from();
    toDate = constraint.to();
    // inProgress = constraint.inProgress();
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext ctx) {

    Date fromDateField = (Date) new BeanWrapperImpl(value).getPropertyValue(fromDate);
    Date toDateField = (Date) new BeanWrapperImpl(value).getPropertyValue(toDate);

    if (toDateField != null && fromDateField != null) {
      return fromDateField.before(toDateField);
    } else {
      return true; // we don't care about the rest. FromDate is validated with NotNull
    }
  }
}
