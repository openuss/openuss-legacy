/* BrainContest */
delete from braincontest_answer;
delete from braincontest;
/* Desktop */
delete from desktop_desktop_enrollment;
delete from desktop_desktop_subject;
delete from desktop_desktop_faculty;
delete from desktop_desktop;
/* Discussion */
delete from discussion_formula;
delete from discussion_forumwatch;
delete from discussion_topicwatch;
update discussion_topic  set FIRST_FK = null, LAST_FK = null;
delete from discussion_post;
delete from discussion_topic;
delete from discussion_forum;
/* Documents */
update documents_folderentry set PARENT_FK = null;
delete from documents_fileentry;
delete from documents_folder;
delete from documents_folderentry;
/* Repository */
delete from repository_file;
/* News */
delete from news_newsitem;
/* Enrollment */
delete from enrollment_member;
/* Mailinglist */
delete from mailinglist;
delete from mailinglist_recipients;
delete from mailing_job;
delete from mail_to_send;
delete from mail_templatemodel;
delete from mail_template;
delete from mail_body;
/* Lecture */
update lecture_enrollment e set e.faculty_fk = null;
update lecture_period e set e.faculty_fk = null;
update lecture_subject e set e.faculty_fk = null;
delete from lecture_faculty_aspirant;
delete from lecture_faculty_group;
delete from lecture_faculty_member;
delete from lecture_faculty;
delete from lecture_enrollment;
delete from lecture_period;
delete from lecture_subject;
/* Security */
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
/* system */
delete from system_property;
