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

package org.vbazurtob.HRRecruitApp.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.vbazurtob.HRRecruitApp.model.ApplicantBaseClass;
import org.vbazurtob.HRRecruitApp.model.ApplicantWithPassword;
import org.vbazurtob.HRRecruitApp.model.repository.ApplicantRepository;

@Service
public class ApplicantService {

  @Autowired private ApplicantRepository applicantRepository;

  @Autowired protected BCryptPasswordEncoder bcryptEncoder;

  public void updateApplicantProfile(ApplicantBaseClass applicantFormData) {

    ApplicantWithPassword applicantDB =
        applicantRepository.findOneByUsername(applicantFormData.getUsername());

    // Update only selected fields. Password is not updated

    applicantDB.setNames(applicantFormData.getNames());
    applicantDB.setLastname(applicantFormData.getLastname());
    applicantDB.setAddress1(applicantFormData.getAddress1());
    applicantDB.setAddress2(applicantFormData.getAddress2());

    applicantDB.setCountry(applicantFormData.getCountry());
    applicantDB.setState(applicantFormData.getState());
    applicantDB.setZipcode(applicantFormData.getZipcode());
    applicantDB.setEmail(applicantFormData.getEmail());

    applicantRepository.save(applicantDB);
  }

  public void createNewApplicantInDB(String username, String password, String email) {

    ApplicantWithPassword newApplicantToSave = new ApplicantWithPassword();
    newApplicantToSave.setUsername(username);
    newApplicantToSave.setPassword(bcryptEncoder.encode(password));
    newApplicantToSave.setNames(username);
    newApplicantToSave.setLastname(".");
    newApplicantToSave.setAddress1(".");
    newApplicantToSave.setAddress2(".");
    newApplicantToSave.setCountry(".");
    newApplicantToSave.setState(".");
    newApplicantToSave.setEmail(email);
    newApplicantToSave.setZipcode(".");

    applicantRepository.save(newApplicantToSave);
  }

  public boolean updatePassword(
      String applicantUsername, String password, String passwordConfirmation) {

    if (!password.equals(passwordConfirmation)
        || password.trim().isEmpty()
        || passwordConfirmation.trim().isEmpty()) {
      return false;
    } else {

      // Encrypt the password
      ApplicantWithPassword applicantDB = applicantRepository.findOneByUsername(applicantUsername);

      applicantDB.setPassword(bcryptEncoder.encode(password));
      applicantRepository.save(applicantDB);

      return true;
    }
  }

  public boolean currentApplicantPasswordMatch(String username, String rawPassword) {

    ApplicantWithPassword applicantWithPassword = applicantRepository.findOneByUsername(username);
    return bcryptEncoder.matches(rawPassword, applicantWithPassword.getPassword());
  }

  public boolean usernameExists(String username) {
    return applicantRepository.countByUsername(username) > 0;
  }
}
