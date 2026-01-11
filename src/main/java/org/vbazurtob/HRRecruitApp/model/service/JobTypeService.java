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

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vbazurtob.HRRecruitApp.model.JobType;
import org.vbazurtob.HRRecruitApp.model.repository.JobTypeRepository;

@Service
public class JobTypeService {

  @PersistenceContext private EntityManager entityManager;

  @Autowired private JobTypeRepository jobTypeRepository;

  public List<JobType> getListJobTypeUI() {

    ArrayList<JobType> jobTypeList =
        (ArrayList<JobType>) jobTypeRepository.findAllByOrderByDescriptionAsc();

    JobType jtAll = new JobType();
    jtAll.setId(0);
    jtAll.setDescription("All");

    ArrayList<JobType> newTmpLst = new ArrayList<>();
    newTmpLst.add(jtAll);
    newTmpLst.addAll(jobTypeList);

    return newTmpLst;
  }
}
