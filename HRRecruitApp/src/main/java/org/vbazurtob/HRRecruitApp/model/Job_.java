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