delete from braincontest_answer;
delete from braincontest;
delete from desktop_desktop_course;
delete from desktop_desktop_courseType;
delete from desktop_desktop_faculty;
delete from desktop_desktop;
delete from discussion_formula;
delete from discussion_forumwatch;
delete from discussion_topicwatch;
update discussion_topic  set FIRST_FK = null, LAST_FK = null;
delete from discussion_post;
delete from discussion_topic;
delete from discussion_forum;
update documents_folderentry set PARENT_FK = null;
delete from documents_fileentry;
delete from documents_folder;
delete from documents_folderentry;
delete from repository_file;
delete from news_newsitem;
delete from course_member;
delete from mailinglist_subcriber;
delete from mailinglist_mail;
delete from mailinglist_mailinglist;
delete from message_templateparameter;
delete from message_templatemessage;
delete from message_recipient;
delete from message_message;
update lecture_course e set e.faculty_fk = null;
update lecture_period e set e.faculty_fk = null;
update lecture_courseType e set e.faculty_fk = null;
delete from lecture_faculty_aspirant;
delete from lecture_faculty_group;
delete from lecture_faculty_member;
delete from lecture_faculty;
delete from lecture_course;
delete from lecture_period;
delete from lecture_courseType;
update security_object_identity set parent_fk = null;
delete from security_group2authority;
delete from security_group;
delete from security_permission;
delete from security_object_identity;
delete from security_activationcode;
delete from security_user;
delete from security_user_contact;
delete from security_user_preferences;
delete from security_user_profile;
delete from security_authority;
delete from system_property;