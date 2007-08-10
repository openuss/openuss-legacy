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
	
	public static final String INSTITUTE = "institute";
	public static final String COURSE_TYPE = "courseType";
	public static final String COURSE = "course";
	public static final String COURSE_INFO = "course_info";

	public static final String PERIOD = "period";
	public static final String PERIODS = "periods";
	
	public static final String USER = "user";
	
	public static final String NEWS_PUBLISHER = "news_publisher";
	public static final String NEWS_SELECTED_NEWSITEM = "news_selected_newsitem";
	public static final String NEWS_NEWSDETAIL_PAGE = "news_newsdetail_page";

	public static final String UPLOADED_FILE = "LAST_UPLOADED_FILE";
	
	// service beans
	public static final String LECTURE_SERVICE = "lectureService";
	public static final String SECURITY_SERVICE ="securityService";
	public static final String ONLINE_STATISTIC_SERVICE = "onlineStatisticService";
	
	// Session View Controller
	public static final String INSTITUTE_REGISTRATION_CONTROLLER = "instituteRegistration";


	// outcomes 
	public static final String HOME = "home";
	public static final String FAILURE = "failure";
	public static final String SUCCESS = "success";
	public static final String DESKTOP = "desktop";

	public static final String INSTITUTE_PAGE = "institute";
	public static final String INSTITUTE_PERIOD_PAGE = "institute_period";
	public static final String INSTITUTE_PERIODS_PAGE = "institute_periods";
	public static final String INSTITUTE_PERIOD_REMOVE_PAGE = "institute_period_remove";
	public static final String INSTITUTE_COURSE_TYPES_PAGE = "institute_coursetypes";
	public static final String INSTITUTE_COURSE_REMOVE_PAGE = "institute_course_remove";
	public static final String INSTITUTE_COURSE_TYPE_REMOVE_PAGE = "institute_coursetype_remove";
	public static final String INSTITUTE_MEMBERS_PAGE = "institute_members";
	public static final String INSTITUTE_NEWS_PAGE = "institute_news";
	public static final String INSTITUTE_NEWS_EDIT_PAGE = "institute_news_edit";

	public static final String INSTITUTE_REGISTRATION_START_PAGE = "institute_registration_start";
	public static final String INSTITUTE_REGISTRATION_STEP1_PAGE = "institute_registration_step1";
	public static final String INSTITUTE_REGISTRATION_STEP2_PAGE = "institute_registration_step2";

	public static final String USER_PROFILE_PAGE = "user_profile";
	public static final String USER_PROFILE_VIEW_PAGE = "user_profile_view";

	public static final String SHOW_USER_PROFILE = "showuser";

	// session constants of the last view - used for back buttons
	public static final String LAST_VIEW = "last_view";

	public static final String SECURE_PASSWORD_CHANGE = "secure_password_change";

	// course pages
	public static final String COURSE_PAGE = "course_main";

	public static final String COURSE_OPTIONS_PAGE = "course_options";

	public static final String OUTCOME_BACKWARD = "view:backward";

	public static final String COURSE_TYPE_COURSE_SELECTION_PAGE = "coursetype_course_selection";

	
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
	public static final String DISCUSSION_REMOVETHREAD = "discussion_removethread";
	public static final String DISCUSSION_FORUM = "discussion_forum";
	
	public static final String ZIP_DOWNLOAD_URL = "/zips/documents.zip";
	
	public static final String USER_IMAGE_NAME = "useravartar";
	
	public static final String UPLOAD_FILE_MANAGER = "uploadFileManager";

	public static final String BRAINCONTEST_ATTACHMENTS = "braincontest_attachments";

	public static final String COURSE_NEWS_EDIT_PAGE = "course_news_edit";

	public static final String COURSE_NEWS_PAGE = "course_news";

	public static final String REGISTRATION_DATA = "registrationData";

	public static final String USER_SESSION_KEY = "user";
	
	public static final String NEWSLETTER_NEWSLETTER = "newsletter_newsletter";
	public static final String NEWSLETTER_MAIL = "newsletter_mail";
	public static final String NEWSLETTER_NEWMAIL = "newsletter_newmail";
	public static final String NEWSLETTER_SUBSCRIBERS = "newsletter_subscribers";
	public static final String NEWSLETTER_MAIN = "course_newsletter";
	public static final String NEWSLETTER_SHOWMAIL = "newsletter_showmail";

	public static final String SEARCH_RESULT = "search_result";
	
	public static final String EXTENDED_SEARCH = "extended_search";
	public static final String EXTENDED_SEARCH_RESULT = "extended_search_result";

	public static final String NEWSLETTER_EXPORT = "newsletter_export";
	
	public static final String BREADCRUMBS = "crumbs";

	public static final String ONLINE_SESSION_ID = "org.openuss.statistics.onlinesession.id";
	
	public static final String SYSTEM_STATISTIC = "systemStatistic";
	
	// extended search criteria
	public static final int EXTENDED_SEARCH_SCOPE_UNIVERSITIES = 1;
	public static final int EXTENDED_SEARCH_SCOPE_COMPANIES = 2;
	public static final int EXTENDED_SEARCH_SCOPE_OTHER = 3;
	
	public static final int EXTENDED_SEARCH_RESULT_TYPE_ORGANISATION = 1;
	public static final int EXTENDED_SEARCH_RESULT_TYPE_SUBORGANISATION = 2;
	public static final int EXTENDED_SEARCH_RESULT_TYPE_INSTITUTION = 3;
	public static final int EXTENDED_SEARCH_RESULT_TYPE_COURSE_TYPE = 4;
	public static final int EXTENDED_SEARCH_RESULT_TYPE_COURSE = 5;
	
	/******************************solarplexus*****************/
	public static final String UNIVERSITY_REGISTRATION_CONTROLLER = "universityRegistration";
	public static final String DEPARTMENT_REGISTRATION_CONTROLLER = "departmentRegistration";
	
	public static final String UNIVERSITY_REGISTRATION_START_PAGE= "university_registration_start";
	public static final String UNIVERSITY_REGISTRATION_STEP1_PAGE = "university_registration_step1";
	public static final String UNIVERSITY_REGISTRATION_STEP2_PAGE = "university_registration_step";
	
	public static final String DEPARTMENT_REGISTRATION_START_PAGE= "department_registration_start";
	public static final String DEPARTMENT_REGISTRATION_STEP1_PAGE = "department_registration_step1";
	public static final String DEPARTMENT_REGISTRATION_STEP2_PAGE = "department_registration_step1";
	public static final String UNIVERSITY_PAGE = "university";
	public static final String UNIVERSITY = "university";
	public static final String DEPARTMENT = "department";
	public static final String DEPARTMENT_PAGE = "department";
}