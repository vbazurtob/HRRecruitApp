package org.vbazurtob.HRRecruitApp.conf;

public interface ControllerEndpoints {

	// Controllers
	final static String PUBLIC_CNTROLLER = "/public";
	final static String JOBS_CNTROLLER = "/jobs";
	final static String JOBS_ADS_MANAGEMENT_CNTROLLER = "/job-vacancies";
	final static String APPLICANT_CV_CNTROLLER = "/cv";
	
	// Pages in public
	final static  String APPLICANT_LOGIN_PAGE = "/login-applicant";
	final static  String HR_MEMBER_LOGIN_PAGE = "/login-hr";
	final static  String CREATE_NEW_APPLICANT_PAGE = "/signup";
	final static  String POST_CREATE_NEW_APPLICANT_PAGE = "/register-new-applicant";
	final static  String HOME_PAGE = "/home";
	final static  String ROOT_PAGE = "/";
	final static  String INDEX_PAGE = "/index";
	final static  String NOT_AUTHORIZED_PAGE = "/403";
	
	// Pages for Job Ads Management
	final static  String HR_MEMBER_SUMMARY_PAGE = "/summary-hr";
	
	// Pages for Applicant CV profile
	final static  String APPLICANT_SUMMARY_PAGE = "/summary";
	
	
}
