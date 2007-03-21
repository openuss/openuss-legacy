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

	public static final String FACULTY_PAGE = "faculty";
	public static final String FACULTY_PERIOD_PAGE = "faculty_period";
	public static final String FACULTY_PERIODS_PAGE = "faculty_periods";
	public static final String FACULTY_PERIOD_REMOVE_PAGE = "faculty_period_remove";
	public static final String FACULTY_SUBJECTS_PAGE = "faculty_subjects";
	public static final String FACULTY_ENROLLMENT_REMOVE_PAGE = "faculty_enrollment_remove";
	public static final String FACULTY_SUBJECT_REMOVE_PAGE = "faculty_subject_remove";
	public static final String FACULTY_MEMBERS_PAGE = "faculty_members";
	public static final String FACULTY_NEWS_PAGE = "faculty_news";
	public static final String FACULTY_NEWS_EDIT_PAGE = "faculty_news_edit";

	public static final String FACULTY_REGISTRATION_START_PAGE = "faculty_registration_start";
	public static final String FACULTY_REGISTRATION_STEP1_PAGE = "faculty_registration_step1";
	public static final String FACULTY_REGISTRATION_STEP2_PAGE = "faculty_registration_step2";

	public static final String USER_PROFILE_PAGE = "user_profile";
	public static final String USER_PROFILE_VIEW_PAGE = "user_profile_view";

	public static final String SHOW_USER = "showuser";

	// session constants of the last view - used for back buttons
	public static final String LAST_VIEW = "last_view";

	public static final String SECURE_PASSWORD_CHANGE = "secure_password_change";

	// enrollment pages
	public static final String ENROLLMENT_PAGE = "enrollment_main";
	public static final String ENROLLMENT_OPTIONS_PAGE = "enrollment_options";

	public static final String OUTCOME_BACKWARD = "view:backward";

	public static final String SUBJECT_ENROLLMENT_SELECTION_PAGE = "subject_enrollment_selection";

	public static final String DOCUMENTS_NEW_FOLDER_PAGE = "document_new_folder";

	public static final String SELECTED_FOLDER = "selected_folder";

	public static final String DOCUMENTS_EDIT_FOLDER_PAGE = "document_edit_folder";

	public static final String SELECTED_FILEENTRY = "selected_fileentry";

	public static final String DOCUMENTS_EDIT_FILEENTRY_PAGE = "document_edit_fileentry";

}
