package org.openuss.web;

/**
 * Contains any constant that is used in conjunction with scopes. 
 * 
 * @author Ingo Dueppe
 * @author Kai Stettner
 */
public class Constants {
	// request parameters 
	public static final String REPOSITORY_FILE_ID = "fileid";
	
	// Session Entity Beans
	public static final String UNIVERSITY = "university";
	public static final String UNIVERSITY_INFO = "universityInfo";
	public static final String DEPARTMENT = "department";
	public static final String DEPARTMENT_INFO = "departmentInfo";
	public static final String INSTITUTE = "institute";
	public static final String INSTITUTE_INFO = "instituteInfo";
	public static final String COURSE_TYPE = "courseType";
	public static final String COURSE_TYPE_INFO = "courseTypeInfo";
	public static final String COURSE = "course";
	public static final String COURSE_INFO = "courseInfo";
	public static final String DESKTOP_INFO = "desktopInfo";

	public static final String PERIOD = "period";
	public static final String PERIOD_INFO = "periodInfo";
	public static final String PERIODS = "periods";
	
	public static final String USER = "user";
	
	public static final String NEWS_PUBLISHER = "news_publisher";
	public static final String NEWS_SELECTED_NEWSITEM = "news_selected_newsitem";
	public static final String NEWS_NEWSDETAIL_PAGE = "news_newsdetail_page";

	public static final String UPLOADED_FILE = "LAST_UPLOADED_FILE";
	
	// service beans
	public static final String ORGANISATION_SERVICE = "organisationService";
	public static final String UNIVERSITY_SERVICE = "universityService";
	public static final String DEPARTMENT_SERVICE = "departmentService";
	public static final String INSTITUTE_SERVICE = "instituteService";
	public static final String COURSE_TYPE_SERVICE = "courseTypeService";
	public static final String COURSE_SERVICE = "courseService";
	public static final String LECTURE_SERVICE = "lectureService";
	public static final String SECURITY_SERVICE ="securityService";
	public static final String ONLINE_STATISTIC_SERVICE = "onlineStatisticService";
	
	// Session View Controller
	public static final String INSTITUTE_REGISTRATION_CONTROLLER = "instituteRegistration";
	
	// session constants of the last view - used for back buttons
	public static final String LAST_VIEW = "last_view";
	public static final String OUTCOME_BACKWARD = "view:backward";
	public static final String SECURE_PASSWORD_CHANGE = "secure_password_change";

	// outcomes 
	public static final String HOME = "home";
	public static final String FAILURE = "failure";
	public static final String SUCCESS = "success";
	public static final String DESKTOP = "desktop";
	public static final String ERROR = "error";
	public static final String MYUNI_INSTITUTE_COURSECOUNT_STRING = "MYUNI_INSITUTE_COURSECOUNT_STRING";
	
	// university and periods navigation outcomes and combobox long constants
	public static final String UNIVERSITIES_PAGE = "universities";
	public static final String UNIVERSITIES_ADMIN_PAGE = "admin_universities";
	public static final String UNIVERSITY_PAGE = "university";
	public static final String UNIVERSITY_EDIT_PAGE = "university_edit";
	public static final String UNIVERSITY_DEPARTMENTS_PAGE = "university_departments";
	public static final String UNIVERSITY_PERIODS_PAGE = "university_periods";
	public static final String UNIVERSITY_PRIVILEGES_PAGE = "university_privileges";
	public static final String UNIVERSITY_CONFIRM_REMOVE_PAGE = "university_remove_confirmation";
	public static final String UNIVERSITY_CONFIRM_DISABLE_PAGE = "university_disable_confirmation";
	public static final Long   UNIVERSITIES_ENABLED = -113L;
	public static final Long   UNIVERSITIES_DISABLED = -114L;
	public static final String UNIVERSITY_REGISTRATION_CONTROLLER = "universityRegistration";
	public static final String UNIVERSITY_REGISTRATION_START_PAGE= "university_registration_start";
	public static final String UNIVERSITY_REGISTRATION_STEP1_PAGE = "university_registration_step1";
	public static final String UNIVERSITY_REGISTRATION_STEP2_PAGE = "university_registration_step";
	public static final String UNIVERSITY_PERIOD_EDIT_PAGE = "university_period_edit";
	public static final String UNIVERSITY_PERIOD_ADD_PAGE = "university_period_add";
	public static final String UNIVERSITY_PERIOD_REMOVE_PAGE = "university_period_remove";
	public static final Long   PERIODS_ACTIVE = -117L;
	public static final Long   PERIODS_PASSIVE = -118L;
	
	// department navigation outcomes and combobox long constants
	public static final String DEPARTMENTS_PAGE = "departments";
	public static final String DEPARTMENT_PAGE = "department";
	public static final String DEPARTMENT_EDIT = "department_edit";
	public static final String DEPARTMENTS_ADMIN_PAGE = "admin_departments";
	public static final String DEPARTMENT_CONFIRM_REMOVE_PAGE = "department_remove_confirmation";
	public static final String DEPARTMENT_CONFIRM_DISABLE_PAGE = "department_disable_confirmation";
	public static final Long   DEPARTMENTS_ENABLED = -115L;
	public static final Long   DEPARTMENTS_DISABLED = -116L;
	public static final Long   DEPARTMENTS_NO_UNIVERSITY_SELECTED = -119L;
	public static final String DEPARTMENT_REGISTRATION_CONTROLLER = "departmentRegistration";
	public static final String DEPARTMENT_REGISTRATION_START_PAGE= "department_registration_start";
	public static final String DEPARTMENT_REGISTRATION_STEP1_PAGE = "department_registration_step1";
	public static final String DEPARTMENT_REGISTRATION_STEP2_PAGE = "department_registration_step1";

	// institute navigation outcomes and combobox long constants
	public static final String INSTITUTE_PAGE = "institute";
	public static final String INSTITUTE_PERIOD_PAGE = "institute_period";
	public static final String INSTITUTE_PERIODS_PAGE = "institute_periods";
	public static final String INSTITUTE_PERIOD_REMOVE_PAGE = "institute_period_remove";
	public static final String INSTITUTES_ADMIN_PAGE = "admin_institutes";
	public static final String INSTITUTE_COURSE_REMOVE_PAGE = "institute_course_remove";
	public static final String INSTITUTE_COURSE_TYPE_REMOVE_PAGE = "institute_coursetype_remove";
	public static final String INSTITUTE_COURSE_TYPES_PAGE = "institute_coursetypes";
	public static final String INSTITUTE_COURSES_PAGE = "institute_courses";
	public static final String INSTITUTE_CONFIRM_DISABLE_PAGE = "institute_disable_confirmation";
	public static final String INSTITUTE_MEMBERS_PAGE = "institute_members";
	public static final String INSTITUTE_NEWS_PAGE = "institute_news";
	public static final String INSTITUTE_NEWS_EDIT_PAGE = "institute_news_edit";
	public static final String INSTITUTE_REGISTRATION_START_PAGE = "institute_registration_start";
	public static final String INSTITUTE_REGISTRATION_STEP1_PAGE = "institute_registration_step1";
	public static final String INSTITUTE_REGISTRATION_STEP2_PAGE = "institute_registration_step2";
	public static final String INSTITUTES_PAGE = "manage_institute";
	public static final String INSTITUTE_CONFIRM_REMOVE_PAGE = "institute_remove_confirmation";
	public static final String INSTITUTE_DEPARTMENTS_PAGE = "institute_departments";

	// user navigation outcomes and comobox long constants
	public static final String USER_PROFILE_PAGE = "user_profile";
	public static final String USER_PROFILE_VIEW_PAGE = "user_profile_view";
	public static final String SHOW_USER_PROFILE = "showuser";
	public static final String USER_IMAGE_NAME = "useravartar";
	public static final String USER_SESSION_KEY = "user";
	public static final Long   USER_SUPER_ADMIN = -10L;

	// course and course type navigation outcomes and combobox long constants
	public static final String COURSE_PAGE = "course_main";
	public static final String COURSE_OPTIONS_PAGE = "course_options";
	public static final String COURSE_TYPE_EDITING_FLAG = "course_type_editing";
	public static final String COURSE_TYPE_COURSE_SELECTION_PAGE = "coursetype_course_selection";
	public static final String COURSE_TYPE_CONFIRM_REMOVE_PAGE = "course_type_remove_confirmation";
	public static final String COURSE_CONFIRM_REMOVE_PAGE = "course_remove_confirmation";
	public static final Long   COURSES_ALL_PERIODS = -111L;
	public static final Long   COURSES_ALL_ACTIVE_PERIODS = -112L;
	public static final Long   COURSE_CHOOSE_ASSISTANT = -120L;
	public static final String COURSE_CHOSSE_ASSISTANT_TEXT = "course_assistants_select";
	public static final String COURSE_CHOOSE_ERROR = "course_assistants_select_error";

	// documents navigation outcomes and combobox long constants
	public static final String DOCUMENTS_CURRENT_FOLDER = "documents_current_folder";
	public static final String DOCUMENTS_SELECTED_FOLDER = "documents_selected_folder";
	public static final String DOCUMENTS_SELECTED_FILEENTRY = "documents_selected_file";
	public static final String DOCUMENTS_SELECTED_FILEENTRIES = "documents_selected_files";
	public static final String DOCUMENTS_SELECTED_FOLDERENTRIES = "documents_selected_folderentries";
	public static final String ZIP_FILE_NAME = "zip_file_name";

	public static final String DOCUMENTS_MAIN_PAGE = "documents_main";
	public static final String DOCUMENTS_ADD_ZIP_PAGE = "documents_add_zip";
	public static final String DOCUMENTS_EDIT_FOLDER_PAGE = "documents_edit_folder";
	public static final String DOCUMENTS_EDIT_FILEENTRY_PAGE = "documents_edit_file";
	public static final String DOCUMENTS_REMOVE_FOLDERENTRY_PAGE = "documents_remove_folderentries";
	public static final String DOCUMENTS_FOLDERENTRY_SELECTION = "documents_entry_selection";
	public static final String ZIP_DOWNLOAD_URL = "/zips/documents.zip";

	

	// braincontest navigation outcomes and combobox long constants
	public static final String BRAINCONTEST_NEWCONTEST = "braincontest_new";
	public static final String BRAINCONTEST_MAIN = "braincontest_main";
	public static final String BRAINCONTEST_TOP = "braincontest_top";
	public static final String BRAINCONTEST_SOLVE = "braincontest_solve";
	public static final String BRAINCONTEST_SOLVED = "braincontest_solved";
	public static final String BRAINCONTEST_WRONG = "braincontest_wrong";
	public static final String BRAINCONTENT_CONTEST = "braincontest_contest";
	public static final String BRAINCONTEST_ATTACHMENTS = "braincontest_attachments";

	// discussion navigation outcomes and combobox long constants
	public static final String DISCUSSION_DISCUSSIONENTRY = "discussion_discussionentry";
	public static final String DISCUSSION_NEW = "discussion_new";
	public static final String DISCUSSION_TOPIC = "discussion_topic";
	public static final String DISCUSSION_MAIN = "discussion_main";
	public static final String DISCUSSION_THREAD = "discussion_thread";
	public static final String DISCUSSION_THREADLENGTH = "discussion_threadlength";
	public static final String DISCUSSION_REMOVETHREAD = "discussion_removethread";
	public static final String DISCUSSION_FORUM = "discussion_forum";
	public static final String DISCUSSION_THREAD_NOT_FOUND = "discussion_thread_not_found";
	public static final String DISCUSSION_POST_NOT_FOUND = "discussion_post_not_found";
	public static final String DISCUSSION_POST_NOT_AUTHOR = "discussion_post_not_author";
	
	// news navigation outcomes and combobox long constants
	public static final String COURSE_NEWS_EDIT_PAGE = "course_news_edit";
	public static final String COURSE_NEWS_PAGE = "course_news";
	
	// newsletter navigation outcomes and combobox long constants
	public static final String NEWSLETTER_NEWSLETTER = "newsletter_newsletter";
	public static final String NEWSLETTER_MAIL = "newsletter_mail";
	public static final String NEWSLETTER_NEWMAIL = "newsletter_newmail";
	public static final String NEWSLETTER_SUBSCRIBERS = "newsletter_subscribers";
	public static final String NEWSLETTER_MAIN = "course_newsletter";
	public static final String NEWSLETTER_SHOWMAIL = "newsletter_showmail";
	public static final String NEWSLETTER_EXPORT = "newsletter_export";
	
	// search and extended search navigation outcomes and combobox long constants
	public static final String SEARCH_RESULT = "search_result";
	public static final String EXTENDED_SEARCH = "extended_search";
	public static final String EXTENDED_SEARCH_VIEW = "extendedSearch";
	public static final String EXTENDED_SEARCH_RESULT = "extended_search_result";
	public static final Long EXTENDED_SEARCH_GET_ALL = -1L;
	public static final Long EXTENDED_SEARCH_NO_RECORDS_FOUND = -2L;
	public static final int EXTENDED_SEARCH_SCOPE_UNIVERSITIES = 1;
	public static final int EXTENDED_SEARCH_SCOPE_COMPANIES = 2;
	public static final int EXTENDED_SEARCH_SCOPE_OTHER = 3;
	public static final int EXTENDED_SEARCH_RESULT_TYPE_ALL = 0;
	public static final int EXTENDED_SEARCH_RESULT_TYPE_ORGANISATION = 1;
	public static final int EXTENDED_SEARCH_RESULT_TYPE_SUBORGANISATION = 2;
	public static final int EXTENDED_SEARCH_RESULT_TYPE_INSTITUTION = 3;
	public static final int EXTENDED_SEARCH_RESULT_TYPE_COURSE_TYPE = 4;
	public static final int EXTENDED_SEARCH_RESULT_TYPE_COURSE = 5;
	
	public static final String SYSTEM_PROPERTIES = "SYSTEM_PROPERTIES";
	
	// miscellaneous navigation outcomes and combobox long constants
	public static final String ORGANISATION_IMAGE_NAME = "organisationlogo";
	public static final String UPLOAD_FILE_MANAGER = "uploadFileManager";
	public static final String REGISTRATION_DATA = "registrationData";
	public static final String BREADCRUMBS = "crumbs";
	public static final String ONLINE_SESSION_ID = "org.openuss.statistics.onlinesession.id";
	public static final String SYSTEM_STATISTIC = "systemStatistic";
	public static final String VISIT = "visit";
	public static final String STEP3 = "step3";	
	public static final String FILE_NOT_FOUND = "documents_file_not_found";
	
	// strings used by string builder
	public static final String SPACE = " ";
	public static final String NEWLINE = "\r\n";
	public static final String ARROW = " -> ";

	// papersubmission 
	public static final String PAPERSUBMISSION_EXAM_INFO = "examInfo";
	public static final String PAPERSUBMISSION_PAPER_INFO = "paperSubmissionInfo";
	public static final String PAPERSUBMISSION_EXAMLIST_PAGE = "examlist";
	public static final String PAPERSUBMISSION_EXAM_REMOVE_PAGE = "papersubmission_exam_remove";
	public static final String PAPERSUBMISSION_SUBMISSION_SELECTION = "papersubmission_submission_selection";
	public static final String PAPERSUBMISSION_EXTENDEDFOLDERENTRY_SELECTION = "papersubmission_extendedfolderentry_selection";
	public static final String PAPERSUBMISSION_SELECTED_FILEENTRY = "papersubmission_selected_file";
	public static final String PAPERSUBMISSION_FILE_EDIT_PAGE = "papersubmission_file_edit";
	public static final String PAPERSUBMISSION_OVERVIEW_PAGE   = "submissionview";
	public static final String PAPERSUBMISSION_LECTURE_OVERVIEW_PAGE   = "submissionviewlecturer";
	public static final String PAPERSUBMISSION_CURRENT_FOLDER = "papersubmission_current_folder";
	public static final String PAPERSUBMISSION_FOLDERENTRY_SELECTION = "papersubmission_folderentry_selection";
	public static final String PAPERSUBMISSION_SELECTED_FILEENTRIES = "papersubmission_selected_fileentries";
	public static final String PAPERSUBMISSION_FILE_REMOVE_PAGE = "papersubmission_file_remove";
	
	// collaboration
	public static final String COLLABORATION_WORKSPACE_INFO = "workspaceInfo";
	public static final String COLLABORATION_MAIN_PAGE      = "collaboration";
	public static final String COLLABORATION_WORKSPACE_PAGE   = "workspaceview";
	public static final String COLLABORATION_CONFIRM_REMOVE_PAGE = "collaboration_confirm_remove";
	public static final String COLLABORATION_REMOVE_FOLDERENTRY_PAGE = "collaboration_remove_folderentries";
	public static final String COLLABORATION_EDIT_FOLDER_PAGE = "collaboration_edit_folder";
	public static final String COLLABORATION_EDIT_FILEENTRY_PAGE = "collaboration_edit_file";
	public static final String COLLABORATION_WORKSPACE_MEMBER_SELECTION = "collaboration_member_selection";
	public static final String COLLABORATION_FOLDERENTRY_SELECTION = "collaboration_entry_selection";
	public static final String COLLABORATION_SELECTED_FILEENTRY = "collaboration_selected_file";
	public static final String COLLABORATION_SELECTED_FOLDERENTRIES = "collaboration_selected_folderentries";
	public static final String COLLABORATION_SELECTED_FILEENTRIES = "collaboration_selected_fileentries";
	public static final String COLLABORATION_SELECTED_FOLDER = "collaboration_selected_folder";
	public static final String COLLABORATION_CURRENT_FOLDER = "collaboration_current_folder";

	// wiki
	public static final String WIKI_CURRENT_SITE_VERSION = "wiki_current_site_version";
	public static final String WIKI_STARTSITE_NAME = "index";
	public static final String WIKI_STARTSITE_NAME_I18N = "wiki_index_page_readable";
	public static final String WIKI_MAIN_PAGE = "course_wiki";
	public static final String WIKI_EDIT_PAGE = "wiki_edit";
	public static final String WIKI_OVERVIEW = "wiki_overview";
	public static final String WIKI_NEW_SITE_NAME = "wiki_new_site_name";
	public static final String WIKI_IMAGE = "wiki_image";
	public static final String WIKI_CHOOSE_IMAGE_PAGE = "wiki_choose_image";
	public static final String WIKI_REMOVE_IMAGE_PAGE = "wiki_remove_image";
	public static final String WIKI_REMOVE_SITE_PAGE = "wiki_remove_site";
	public static final String WIKI_SITE_TO_REMOVE = "wiki_site_to_remove";
	public static final String WIKI_VERSION_PAGE = "wiki_version";
	public static final String WIKI_IMPORT_PAGE = "wiki_import";
	public static final String WIKI_IMPORT_CONFIRMATION_PAGE = "wiki_import_confirmation";
	public static final String WIKI_IMPORT_COURSE = "wiki_import_course";
	public static final String WIKI_IMPORT_TYPE = "wiki_import_type";
	public static final String WIKI_IMPORT_TYPE_IMPORT_WIKI_SITES = "wiki_import_type_import_wiki_sites";
	public static final String WIKI_IMPORT_TYPE_IMPORT_WIKI_VERSIONS = "wiki_import_type_import_wiki_versions";
	public static final String WIKI_OVERWRITE_PAGE = "wiki_overwrite";
	public static final String WIKI_MAIN_HEADER = "wiki_main_header";
	public static final String WIKI_NEW_SITE_HEADER = "wiki_new_site_create";	
	public static final String WIKI_IMPORT_WIKI = "wiki_import_wiki";
	public static final String WIKI_NEW_TAG = "__new__";
	public static final String WIKI_EDIT_LINKS_NEWPAGE = "wiki_editlinks_newpage";
	public static final String WIKI_SITE_SAVE_SUCCEEDED = "wiki_site_save_succeeded";
	public static final String WIKI_IMAGE_REMOVE_HEADER = "wiki_image_remove_header";
	public static final String WIKI_IMAGE_REMOVE_SUCCEEDED = "wiki_image_removed_succeed";
	public static final String WIKI_IMAGE_CANNOT_BE_REMOVED = "wiki_image_cannot_be_removed";
	public static final String WIKI_NEW_SITE_BACKUP = "wiki_create_backup";

	
}
