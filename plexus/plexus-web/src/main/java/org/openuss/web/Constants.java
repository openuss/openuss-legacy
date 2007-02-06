package org.openuss.web;

/**
 * Contains any constant that is used in conjunction with scopes. 
 * 
 * @author Ingo Dueppe
 */
public class Constants {
	// request parameters 
	public static final String REPOSITORY_FILE_ID = "fileid";
	
	// Session Entity Beans
	
	public static final String FACULTY = "faculty";
	public static final String SUBJECT = "subject";
	public static final String ENROLLMENT = "enrollment";

	public static final String PERIOD = "period";
	public static final String PERIODS = "periods";
	
	public static final String USER = "user";
	
	public static final String NEWSITEM = "newsItem";
	public static final String NEWSBEAN = "newsBean";		

	public static final String UPLOADED_FILE = "uploadedFile";
	
	// service beans
	public static final String LECTURE_SERVICE = "lectureService";
	
	// Session View Controller
	public static final String FACULTY_REGISTRATION_CONTROLLER = "facultyRegistration";


	// outcomes 
	public static final String HOME = "home";
	public static final String FAILURE = "failure";
	public static final String SUCCESS = "success";
	public static final String DESKTOP = "desktop";
	public static final String ENROLLMENT_OPTIONS = "enrollment_options";

	public static final String FACULTY_MAIN = "show_faculty";
	public static final String FACULTY_PERIOD = "faculty_period";
	public static final String FACULTY_PERIODS = "faculty_periods";
	public static final String FACULTY_PERIOD_REMOVE = "faculty_period_remove";
	public static final String FACULTY_SUBJECTS = "faculty_subjects";
	public static final String FACULTY_ENROLLMENT_REMOVE = "faculty_enrollment_remove";
	public static final String FACULTY_SUBJECT_REMOVE = "faculty_subject_remove";
	public static final String FACULTY_MEMBERS = "faculty_members";
	public static final String FACULTY_NEWS = "faculty_news";
	public static final String FACULTY_NEWS_EDIT = "faculty_news_edit";

	public static final String FACULTY_REGISTRATION_START = "faculty_registration_start";
	public static final String FACULTY_REGISTRATION_STEP1 = "faculty_registration_step1";
	public static final String FACULTY_REGISTRATION_STEP2 = "faculty_registration_step2";

	public static final String USER_PROFILE = "user_profile";
	public static final String USER_PROFILE_VIEW = "user_profile_view";

	public static final String SHOW_USER = "showuser";

	// session constants of the last view - used for back buttons
	public static final String LAST_VIEW = "last_view";

	public static final String SECURE_PASSWORD_CHANGE = "secure_password_change";

	public static final String ENROLLMENT_MAIN = "enrollment_main";

}
