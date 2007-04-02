delete from braincontest_answer;
delete from braincontest;

delete from desktop_desktop_subject;
delete from desktop_desktop_faculty;
delete from desktop_desktop;
delete from news_newsitem;
delete from lecture_faculty_aspirant;
delete from lecture_faculty_group;
delete from lecture_faculty_member;

update lecture_enrollment e set e.faculty_fk = null;
update lecture_period e set e.faculty_fk = null;
update lecture_subject e set e.faculty_fk = null;

update discussion_post d set d.submitter_fk = null;
update discussion_post d set d.topic_fk = null;
delete from discussion_topic;

delete from enrollment_member;
delete from lecture_faculty;
delete from lecture_enrollment;
delete from lecture_period;
delete from lecture_subject;
delete from security_group2authority;
delete from security_group;
delete from security_permission;

update security_object_identity set parent_fk = null;
delete from security_object_identity;
delete from security_activationcode;
delete from security_user;
delete from security_user_contact;
delete from security_user_preferences;
delete from security_user_profile;
delete from security_authority;
delete from system_property;