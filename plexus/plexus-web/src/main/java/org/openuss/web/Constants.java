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
	public static final String ENROLLMENT_INFO = "enrollment_info";

	public static final String PERIOD = "period";
	public static final String PERIODS = "periods";
	
	public static final String USER = "user";
	
	public static final String NEWS_PUBLISHER = "news_publisher";
	public static final String NEWS_SELECTED_NEWSITEM = "news_selected_newsitem";
	public static final String NEWS_NEWSDETAIL_PAGE = "news_newsdetail_page";

	public static final String UPLOADED_FILE = "LAST_UPLOADED_FILE";
	
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

	public static final String SHOW_USER_PROFILE = "showuser";

	// session constants of the last view - used for back buttons
	public static final String LAST_VIEW = "last_view";

	public static final String SECURE_PASSWORD_CHANGE = "secure_password_change";

	// enrollment pages
	public static final String ENROLLMENT_PAGE = "enrollment_main";

	public static final String ENROLLMENT_OPTIONS_PAGE = "enrollment_options";

	public static final String OUTCOME_BACKWARD = "view:backward";

	public static final String SUBJECT_ENROLLMENT_SELECTION_PAGE = "subject_enrollment_selection";

	
	public static final String DOCUMENTS_CURRENT_FOLDER = "documents_current_folder";
	public static final String DOCUMENTS_SELECTED_FOLDER = "documents_selected_folder";
	public static final String DOCUMENTS_SELECTED_FILEENTRY = "documents_selected_file";
	public static final String DOCUMENTS_SELECTED_FILEENTRIES = "documents_selected_files";
	public static final String DOCUMENTS_SELECTED_FOLDERENTRIES = "documents_selected_folderentries";

	public static final String DOCUMENTS_MAIN_PAGE = "documents_main";
	public static final String DOCUMENTS_ADD_ZIP_PAGE = "documents_add_zip";
	public static final String DOCUMENTS_EDIT_FOLDER_PAGE = "documents_edit_folder";
	public static final String DOCUMENTS_EDIT_FILEENTRY_PAGE = "documents_edit_file";
	public static final String DOCUMENTS_REMOVE_FOLDERENTRY_PAGE = "documents_remove_folderentries";
	public static final String DOCUMENTS_FOLDERENTRY_SELECTION = "documents_entry_selection";

	//brain contest
	public static final String BRAINCONTEST_NEWCONTEST = "braincontest_new";
	public static final String BRAINCONTEST_MAIN = "braincontest_main";
	public static final String BRAINCONTEST_TOP = "braincontest_top";
	public static final String BRAINCONTEST_SOLVE = "braincontest_solve";
	public static final String BRAINCONTEST_SOLVED = "braincontest_solved";
	public static final String BRAINCONTEST_WRONG = "braincontest_wrong";
	public static final String BRAINCONTENT_CONTEST = "braincontest_contest";

	public static final String DISCUSSION_DISCUSSIONENTRY = "discussion_discussionentry";
	public static final String DISCUSSION_NEW = "discussion_new";
	public static final String DISCUSSION_TOPIC = "discussion_topic";
	public static final String DISCUSSION_MAIN = "discussion_main";
	public static final String DISCUSSION_THREAD = "discussion_thread";
	public static final String DISCUSSION_THREADLENGTH = "discussion_threadlength";
	
	public static final String ZIP_DOWNLOAD_URL = "/zips/documents.zip";
	
	public static final String USER_IMAGE_NAME = "useravartar";
	
	public static final String UPLOAD_FILE_MANAGER = "uploadFileManager";

	public static final String BRAINCONTEST_ATTACHMENTS = "braincontest_attachments";

	public static final String ENROLLMENT_NEWS_EDIT_PAGE = "enrollment_news_edit";

	public static final String ENROLLMENT_NEWS_PAGE = "enrollment_news";

	public static final String REGISTRATION_DATA = "registrationData";

	public static final String USER_SESSION_KEY = "user";
	
	public static final String MAILINGLIST_MAILINGLIST = "mailinglist_mailinglist";
	public static final String MAILINGLIST_MAIL = "mailinglist_mail";
	public static final String MAILINGLIST_NEWMAIL = "mailinglist_newmail";
	public static final String MAILINGLIST_SUBSCRIBERS = "mailinglist_subscribers";
	public static final String MAILINGLIST_MAIN = "enrollment_mailinglist";
	public static final String MAILINGLIST_SHOWMAIL = "mailinglist_showmail";

}