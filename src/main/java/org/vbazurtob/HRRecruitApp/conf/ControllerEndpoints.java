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

package org.vbazurtob.HRRecruitApp.conf;

public interface ControllerEndpoints {

  // Controllers
  String PUBLIC_CNTROLLER = "/public";
  String JOBS_CNTROLLER = "/jobs";
  String JOBS_ADS_MANAGEMENT_CNTROLLER = "/job-vacancies";
  String APPLICANT_CV_CNTROLLER = "/cv";

  String CSS_FOLDER = "/css/";
  String JS_FOLDER = "/js/";
  String IMG_FOLDER = "/img/";

  // Pages in public
  String APPLICANT_LOGIN_PAGE = "/login-applicant";
  String HR_MEMBER_LOGIN_PAGE = "/login-hr";
  String CREATE_NEW_APPLICANT_PAGE = "/signup";
  String POST_CREATE_NEW_APPLICANT_PAGE = "/register-new-applicant";
  String HOME_PAGE = "/home";
  String ROOT_PAGE = "/";
  String INDEX_PAGE = "/index";
  String NOT_AUTHORIZED_PAGE = "/403";

  // Pages for Job Ads Management
  String HR_MEMBER_SUMMARY_PAGE = "/summary-hr";
  String HR_MEMBER_JOBS_MANAGEMENT_PAGE = "/manage/";
  String HR_MEMBER_FILTER_JOB_LIST = "/filter-jobs/";
  String HR_MEMBER_FILTER_JOB_CLEAR = "/clear-filter-jobs/";
  String HR_MEMBER_APPLICANTS_JOB = "/view-applicants/";
  String HR_MEMBER_VIEW_APPLICANT_CV = "/view-applicant-cv/";

  // Pages for Applicant CV profile
  String APPLICANT_SUMMARY_PAGE = "/summary";
  String APPLICANT_PROFILE_PAGE = "/profile/";
  String APPLICANT_WORKEXP_PAGE = "/workexp/";
  String APPLICANT_SKILLS_PAGE = "/skills/";
  String APPLICANT_ACADEMICS_PAGE = "/academics/";
  String VIEW_CV = "/view/";

  // Pages for Jobs search
  String APPLICANT_JOB_SEARCH_PAGE = "/search/";
  String APPLICANT_JOB_POST_FILTER_JOB_LIST = "/filter-search/";
  String APPLICANT_JOB_POST_FILTER_JOB_CLEAR = "/clear-filter-jobs/";
  String APPLICANT_JOB_VIEW_JOB_DETAIL = "/view/";
  String APPLICANT_JOB_APPLICANTS_JOBS_APPLIED = "/my-applications/";

  // Logout pages

  String APPLICANT_LOGOUT = "/logout/";
  String HR_MEMBER_LOGOUT = "/logout-hr/";
}
