// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.migration;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openuss.lecture.AccessType;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseMemberInfo;
import org.openuss.security.Group;
import org.openuss.security.GroupType;
import org.openuss.security.Roles;
import org.openuss.security.User;
import org.openuss.security.acegi.acl.EntityObjectIdentity;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.security.acl.ObjectIdentity;
import org.openuss.security.acl.Permission;

/**
 * @see org.openuss.migration.MigrationService
 */
public class MigrationServiceImpl extends MigrationServiceBase {

	private static final Logger logger = Logger.getLogger(MigrationServiceImpl.class);

	private Group getParticipantsGroup(Course course) {
		Set<Group> groups = course.getGroups();
		for (Group group : groups) {
			if (group.getName().contains("PARTICIPANTS")) {
				return group;
			}
		}
		return null;
	}

	private void updateAccessTypePermission(Course course) {
		Group group = getParticipantsGroup(course);
		if (course.getAccessType() == AccessType.ANONYMOUS) {
			// TODO check if change needed
			getSecurityService().setPermissions(Roles.ANONYMOUS, course, LectureAclEntry.READ);
		} else {
			getSecurityService().setPermissions(Roles.ANONYMOUS, course, LectureAclEntry.NOTHING);
		}

		if (course.getAccessType() == AccessType.OPEN || course.getAccessType() == AccessType.ANONYMOUS) {
			getSecurityService().addAuthorityToGroup(Roles.USER, group);
		} else {
			getSecurityService().removeAuthorityFromGroup(Roles.USER, group);
		}
	}

	private void deleteMultiplePermissions(User user, Course course) {
		ObjectIdentity objectIdentity = null;
		try {
			objectIdentity = getObjectIdentityDao().load(new EntityObjectIdentity(course).getIdentifier());
		} catch (IllegalAccessException e) {
			logger.debug(e);
		} catch (InvocationTargetException e) {
			logger.debug(e);
		}
		List<Permission> coursePermissions = getPermissionDao().findPermissions(objectIdentity);
		List<Permission> multiplePermissions = new ArrayList<Permission>();
		for (Permission permission : coursePermissions) {
			if (permission.getRecipient().getId().equals(user.getId())) {
				multiplePermissions.add(permission);
			}
		}
		if (multiplePermissions.size() > 1) {
			for (int i = 1; i < coursePermissions.size(); i++) {
				Permission removeMe = coursePermissions.get(i);
				removeMe.setRecipient(null);
				objectIdentity.removePermission(removeMe);
				getObjectIdentityDao().update(objectIdentity);
				getPermissionDao().remove(removeMe);
			}
		}
	}

	/**
	 * @see org.openuss.migration.MigrationService#migrate()
	 */
	protected void handleMigrate() throws java.lang.Exception {
		List<Course> courses = getCourseService().findAllCourses();
		// delete multiple entries in permission table

		for (Course course : courses) {
			List<CourseMemberInfo> members = getCourseService().getParticipants(
					(CourseInfo) getCourseDao().load(getCourseDao().TRANSFORM_COURSEINFO, course.getId()));
			for (CourseMemberInfo member : members) {
				User user = getCourseMemberDao().courseMemberInfoToEntity(member).getUser();

				// delete multiple entries in permission table
				deleteMultiplePermissions(user, course);
			}
		}

		for (Course course : courses) {
			Group participantsGroup = getSecurityService().createGroup("COURSE_" + course.getId() + "_PARTICIPANTS",
					"autogroup_participant_label", null, GroupType.PARTICIPANT);
			Set<Group> groups = course.getGroups();
			if (groups == null) {
				groups = new HashSet<Group>();
			}
			groups.add(participantsGroup);
			course.setGroups(groups);
			getCourseDao().update(course);

			// Security
			getSecurityService().setPermissions(participantsGroup, course, LectureAclEntry.COURSE_PARTICIPANT);

			List<CourseMemberInfo> members = getCourseService().getParticipants(
					(CourseInfo) getCourseDao().load(getCourseDao().TRANSFORM_COURSEINFO, course.getId()));
			for (CourseMemberInfo member : members) {
				User user = getCourseMemberDao().courseMemberInfoToEntity(member).getUser();

				Permission permission = getSecurityService().getPermissions(user, course);
				if (permission != null && permission.getMask().intValue() == 1040) {
					getSecurityService().removePermission(user, course);
				}
				getSecurityService().addAuthorityToGroup(user, participantsGroup);
			}
		}
	}
}