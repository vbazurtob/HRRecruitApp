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

package org.vbazurtob.HRRecruitApp.model;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


@Generated(value="EclipseLink-2.7.1.v20171221-rNA", date="2018-06-23T01:22:23")
@StaticMetamodel(Job.class)
public class Job_ { 

    public static volatile ListAttribute<Job, JobApplicant> jobApplicants;
    public static volatile SingularAttribute<Job, String> description;
    public static volatile SingularAttribute<Job, Long> id;
    public static volatile SingularAttribute<Job, Integer> salary;
    public static volatile SingularAttribute<Job, String> title;
    public static volatile SingularAttribute<Job, JobType> jobType;
    public static volatile SingularAttribute<Job, String> status;
    public static volatile SingularAttribute<Job, Date> datePosted;
}