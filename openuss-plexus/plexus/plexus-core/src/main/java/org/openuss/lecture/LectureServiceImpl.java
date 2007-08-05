// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openuss.registration.RegistrationException;
import org.openuss.security.Group;
import org.openuss.security.GroupType;
import org.openuss.security.Roles;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.system.SystemProperties;

/**
 * @see org.openuss.lecture.LectureService
 */
public class LectureServiceImpl extends LectureServiceBase{

	private static final Logger logger = Logger.getLogger(LectureServiceImpl.class);
	
	@Override
	protected Collection<InstituteInfo> handleGetInstitutes(boolean enabledOnly) throws Exception {
		if (enabledOnly) {
			return getInstituteDao().loadAllByEnabled(InstituteDao.TRANSFORM_INSTITUTEINFO, true);
		} else {
			return getInstituteDao().loadAll(InstituteDao.TRANSFORM_INSTITUTEINFO);
		}
	}

	@Override
	protected Institute handleGetInstitute(Long instituteId) throws Exception {
		Institute institute = getInstituteDao().load(instituteId);
		if (institute == null && logger.isInfoEnabled()) {
			logger.info("Institute with id" + instituteId + " cannot be found!");
		}
		return institute;
	}

	@Override
	protected CourseType handleGetCourseType(Long courseTypeId) throws Exception {
		return getCourseTypeDao().load(courseTypeId);
	}

	@Override
	protected Period handleGetPeriod(Long periodId) throws Exception {
		if (periodId != null)
			return getPeriodDao().load(periodId);
		else
			return null;
	}

	@Override
	protected Course handleGetCourse(Long courseId) throws Exception {
		return getCourseDao().load(courseId);

	}

	@Override
	protected boolean handleIsNoneExistingInstituteShortcut(Institute self, String shortcut) throws Exception {
		Institute found = getInstituteDao().findByShortcut(shortcut);
		return isEqualOrNull(self, found);
	}

	@Override
	protected boolean handleIsNoneExistingCourseShortcut(Course self, String shortcut) throws Exception {
		Course found = getCourseDao().findByShortcut(shortcut);
		return isEqualOrNull(self, found);
	}

	@Override
	protected boolean handleIsNoneExistingCourseTypeShortcut(CourseType self, String shortcut) throws Exception {
		CourseType found = getCourseTypeDao().findByShortcut(shortcut);
		return isEqualOrNull(self, found);
	}

	@Override
	protected boolean handleIsNoneExistingCourseTypeName(CourseType self, String name) throws Exception {
		CourseType found = getCourseTypeDao().findByName(name);
		return isEqualOrNull(self, found);
	}

	@Override
	protected Institute handleAdd(Long instituteId, CourseType courseType) throws Exception {
		if (logger.isDebugEnabled())
			logger.debug("Add CourseType " + courseType.getName() + " from institute " + instituteId);
		Institute institute = getInstitute(instituteId);
		institute.add(courseType);
		courseType.setInstitute(institute);
		persist(institute);
		return institute;
	}

	@Override
	protected Institute handleAdd(Long instituteId, Period period) throws Exception {
		throw new UnsupportedOperationException("Adding periods to institutes is no longer valid.");
		/*
		Institute institute = getInstitute(instituteId);
		institute.add(period);
		period.setInstitute(institute);
		if (institute.getActivePeriod() == null) {
			institute.setActivePeriod(period);
		}
		persist(institute);
		return institute;
		*/
	}

	@Override
	protected void handlePersist(Institute institute) throws Exception {
		if (logger.isDebugEnabled())
			logger.debug("Method handlePersist: Save institute " + institute.getName());

		if (institute.getId() != null) {
			getInstituteDao().update(institute);
		} else {
			logger.error("Institute object without id, use createInstitute method instead!!!");
			throw new LectureServiceException("Use createInstitute method instead!");
		}
	}

	@Override
	protected void handleCreateInstitute(Institute institute) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Creating new institute " + institute.getName());
		}
		institute.setEnabled(false);

		institute = getInstituteDao().create(institute);
		

		/*
		// define the security
		SecurityService securityService = getSecurityService();

		// create object identity
		securityService.createObjectIdentity(institute, null);

		// create system defined groups for institute
		Group admins = securityService.createGroup(
					"INSTITUTE_" + institute.getId() + "_ADMINS", 
					"autogroup_administrator_label", null, GroupType.ADMINISTRATOR);
		Group assistants = securityService.createGroup(
					"INSTITUTE_" + institute.getId() + "_ASSISTANTS", 
					"autogroup_assistant_label", null, GroupType.ASSISTANT);
		Group tutors = securityService.createGroup(
					"INSTITUTE_" + institute.getId() + "_TUTORS", 
					"autogroup_tutor_label", null,GroupType.TUTOR);

		securityService.addAuthorityToGroup(institute.getOwner(), admins);

		securityService.setPermissions(admins, institute, LectureAclEntry.INSTITUTE_ADMINISTRATION);
		securityService.setPermissions(assistants, institute, LectureAclEntry.INSTITUTE_ASSIST);
		securityService.setPermissions(tutors, institute, LectureAclEntry.INSTITUTE_TUTOR);

		institute.getGroups().add(admins);
		institute.getGroups().add(assistants);
		institute.getGroups().add(tutors);

		institute.getMembers().add(institute.getOwner());

		// save changes
		getInstituteDao().update(institute);

		// define security rights of institute
		fireCreatedInstitute(institute);
		
		//send activation mail
		sendActivationCode(institute);
		*/
	}

	protected void handleSendActivationCode(Institute institute) throws RegistrationException {
		String activationCode = getRegistrationService().generateInstituteActivationCode(institute);
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("institutename", institute.getName()+"("+institute.getShortcut()+")");
		parameters.put("institutelink", getSystemService().getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue()+"actions/public/lecture/instituteactivation.faces?code="+activationCode);
		getMessageService().sendMessage(institute.getShortcut(), "institute.activation.subject", "instituteactivation", parameters, institute.getEmail(), institute.getLocale());
	}

	
	@Override
	protected void handlePersist(CourseType courseType) throws Exception {
		if (courseType.getId() == null) {
			getCourseTypeDao().create(courseType);
		} else {
			getCourseTypeDao().update(courseType);
		}
	}

	@Override
	protected void handlePersist(Period period) throws Exception {
		if (period.getId() == null) {
			getPeriodDao().create(period);
		} else {
			getPeriodDao().update(period);
		}
	}

	@Override
	protected void handlePersist(Course course) throws Exception {
		storeCourse(course);
		updateAccessTypePermission(course);
	}

	private void storeCourse(Course course) {
		if (course.getId() == null) {
			getCourseDao().create(course);
		} else {
			getCourseDao().update(course);
		}
	}

	private void updateAccessTypePermission(Course course) {
		if (course.getAccessType() != AccessType.OPEN) {
			getSecurityService().setPermissions(Roles.USER, course, LectureAclEntry.NOTHING);
		} else if (course.getAccessType() == AccessType.OPEN) {
			getSecurityService().setPermissions(Roles.USER, course, LectureAclEntry.COURSE_PARTICIPANT);
		}
	}

	@Override
	protected void handleRemoveInstitute(Long instituteId) throws Exception {
		Institute institute = getInstitute(instituteId);
		if (institute == null)
			throw new LectureServiceException("Institute not found " + instituteId);

		// fire events
		/*
		for (Course course : institute.getCourses()) {
			fireRemovingCourse(course);
		}*/

		for (CourseType courseType : institute.getCourseTypes()) {
			fireRemovingCourseType(courseType);
		}

		fireRemovingInstitute(institute);

		getRegistrationService().removeInstituteCodes(institute);
		getInstituteDao().remove(institute);
	}

	@Override
	protected void handleRemoveCourseType(Long courseTypeId) throws Exception {
		if (logger.isDebugEnabled())
			logger.debug("Remove CourseType " + courseTypeId);
		// refresh institute
		CourseType courseType = getCourseType(courseTypeId);

		// fire course delete
		for (Course course : courseType.getCourses()) {
			fireRemovingCourse(course);
		}
		fireRemovingCourseType(courseType);

		getCourseDao().remove(courseType.getCourses());

		getCourseTypeDao().remove(courseType);
	}

	@Override
	protected void handleRemovePeriod(Long periodId) throws Exception {
		throw new UnsupportedOperationException("Removing periods in institutes is no longer valid. Use UniversityService.handleRemove().");
		/*
		if (logger.isDebugEnabled())
			logger.debug("Remove period " + periodId);
		Period period = getPeriod(periodId);

		// fire course delete
		for (Course course : period.getCourses()) {
			fireRemovingCourse(course);
		}

		// remove associated courses
		getCourseDao().remove(period.getCourses());

		Institute institute = period.getInstitute();

		// check active period settings
		if (institute != null) {
			if (period.equals(institute.getActivePeriod())) {
				institute.setActivePeriod(null);
				persist(institute);
			}

			// remove period from institute
			institute.remove(period);
		}

		// delete period
		getPeriodDao().remove(period);
		*/
	}

	@Override
	protected void handleRemoveCourse(Long courseId) throws Exception {
		if (logger.isDebugEnabled())
			logger.debug("Remove course " + courseId);
		Course course = getCourse(courseId);
		// fire remove course event
		fireRemovingCourse(course);

		// remove associations
		/*
		Institute institute = course.getInstitute();
		Period period = course.getPeriod();
		period.remove(course);
		persist(period);
		CourseType courseType = course.getCourseType();
		courseType.remove(course);
		persist(courseType);
		institute.remove(course);

		persist(institute);*/
		getCourseDao().remove(course);
	}

	@Override
	protected Course handleCreateCourse(Long courseTypeId, Long periodId) throws Exception {
		// refresh instances
		CourseType courseType = getCourseType(courseTypeId);
		Period period = getPeriod(periodId);

		//TODO: Change this functionality
		/*
		if (!ObjectUtils.equals(courseType.getInstitute(), period.getInstitute()))
			throw new LectureServiceException("CourseType and period must be associated to the same institute!");
		*/

		Institute institute = courseType.getInstitute();

		Course course = Course.Factory.newInstance();
		courseType.add(course);
		period.add(course);
		//institute.add(course);

		//course.setInstitute(institute);
		course.setCourseType(courseType);
		course.setPeriod(period);

		persist(institute);
		persist(courseType);
		persist(period);
		storeCourse(course);

		storeCourse(course);

		getSecurityService().createObjectIdentity(course, institute);
		updateAccessTypePermission(course);

		return course;
	}

	@Override
	protected void handleSetActivePeriod(Long instituteId, Period period) throws Exception {
		throw new UnsupportedOperationException("Setting active periods in LectureService is no longer valid. Periods are automatically active or not due to their date.");
		/*
		// refresh instances
		Institute institute = getInstitute(instituteId);
		institute.setActivePeriod(period);
		persist(institute);
		*/
	}

	@Override
	protected void handleRegisterListener(LectureListener listener) throws Exception {
		if (listeners == null) {
			listeners = new HashSet<LectureListener>();
		}
		listeners.add(listener);
	}

	@Override
	protected List handleGetInstituteAspirants(Long instituteId) throws Exception {
		Institute institute = getInstituteDao().load(instituteId);
		// need to get ride of persistent back so use a new ArrayList
		List aspirants = new ArrayList<User>(institute.getMembership().getAspirants());
		getUserDao().toUserInfoCollection(aspirants);
		return aspirants;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected InstituteSecurity handleGetInstituteSecurity(Long instituteId) throws Exception {
		return (InstituteSecurity) getInstituteDao().load(InstituteDao.TRANSFORM_INSTITUTESECURITY, instituteId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void handleAddInstituteAspirant(Long userId, Long instituteId) throws Exception {
		Institute institute = getInstitute(instituteId);
		User user = getUser(userId);

		if (!institute.getMembership().getMembers().contains(user)) {
			institute.getMembership().getAspirants().add(user);
			getInstituteDao().update(institute);
		} else {
			throw new LectureException("user_is_already_a_member_of_the_institute");
		}
		//send mail to administrators
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("institutename", institute.getName()+"("+institute.getShortcut()+")");
		parameters.put("instituteapplicantlink", getSystemService().getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue()+"views/secured/lecture/auth/aspirants.faces?institute="+institute.getId());
		getMessageService().sendMessage(institute.getShortcut(), "institute.application.subject", "instituteapplication", parameters, getInstituteAdmins(institute));
	}

	private List<User> getInstituteAdmins(Institute institute){
		// XXX Polish me!
		Collection<Group> groups = institute.getMembership().getGroups();
		Iterator i = groups.iterator();
		Group adminGroup = null;
		Group group;
		List instituteMembers;
		User member;
		List<User> administrators = new ArrayList<User>();
		//find administrator group of institute
		while (i.hasNext()){
			group = (Group) i.next();
			if (group.getGroupType()==GroupType.ADMINISTRATOR){
				adminGroup = group;
			}
		}
		//add members of administrator group to list of administrators
		instituteMembers = institute.getMembership().getMembers();
		i = instituteMembers.iterator();
		while (i.hasNext()){
			member = (User) i.next();
			if (member.getGroups().contains(adminGroup)) {
				administrators.add(member); 
			}
		}
		return administrators;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void handleAddInstituteMember(Long userId, Long instituteId) throws Exception {
		Institute institute = getInstitute(instituteId);
		User user = getUser(userId);

		if (institute.getMembership().getMembers().contains(user)) {
			throw new LectureException("user_is_already_a_member_of_the_institute");
		}

		institute.getMembership().getMembers().add(user);
		// if user was an aspirant remove him from the list
		institute.getMembership().getAspirants().remove(user);
		persist(institute);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void handleRejectInstituteAspirant(Long userId, Long instituteId) throws Exception {
		Institute institute = getInstitute(instituteId);
		User user = getUser(userId);
		institute.getMembership().getAspirants().remove(user);
		persist(institute);
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("institutename", institute.getName()+"("+institute.getShortcut()+")");
		getMessageService().sendMessage(institute.getShortcut(), "institute.application.subject", "instituteapplicationreject", parameters, user);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void handleRemoveInstituteMember(Long userId, Long instituteId) throws Exception {
		Institute institute = getInstitute(instituteId);
		User user = getUser(userId);

		final SecurityService securityService = getSecurityService();
		for (Group group : institute.getMembership().getGroups()) {
			securityService.removeAuthorityFromGroup(user, group);
		}
		institute.getMembership().getMembers().remove(user);
		persist(institute);
	}

	@Override
	protected void handleSetGroupOfMember(InstituteMember member, Long instituteId) throws Exception {
		logger.debug("setting groups of member");
		Institute institute = getInstituteDao().load(instituteId);
		User user = getUser(member.getId());

		if (!institute.getMembership().getMembers().contains(user)) {
			throw new LectureException("User is not a member of the institute!");
		}

		// cache group ids
		final List<Long> groupIds = new ArrayList<Long>();
		for (InstituteGroup group : member.getGroups()) {
			groupIds.add(group.getId());
		}

		// remove and add user to the new groups
		final SecurityService securityService = getSecurityService();
		for (Group group : institute.getMembership().getGroups()) {
			if (group.getMembers().contains(user) && !(groupIds.contains(group.getId()))) {
				securityService.removeAuthorityFromGroup(user, group);
			} else if (!group.getMembers().contains(user) && (groupIds.contains(group.getId()))) {
				securityService.addAuthorityToGroup(user, group);
			}
		}
	}

	@Override
	protected void handleAcceptInstituteAspirant(Long userId, Long instituteId) throws Exception {
		Institute institute = getInstitute(instituteId);
		User user = getUser(userId);
		// check if user was really an aspirant of the institute
		if (institute.getMembership().getAspirants().contains(user)) {
			institute.getMembership().getAspirants().remove(user);
			institute.getMembership().getMembers().add(user);
			getInstituteDao().update(institute);
		}
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("institutename", institute.getName()+"("+institute.getShortcut()+")");
		parameters.put("institutelink", getSystemService().getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue()+"views/secured/lecture/institute.faces?institute="+institute.getId());
		getMessageService().sendMessage(institute.getShortcut(), "institute.application.subject", "instituteapplicationapply", parameters, user);
	}

	@Override
	protected void handleSetOwnerOfInstitute(Long userId, Long instituteId) throws Exception {
		// TODO Auto-generated method stub
	}

	/*------------------- private methods -------------------- */

	private Set<LectureListener> listeners;

	protected void handleUnregisterListener(LectureListener listener) throws Exception {
		if (listeners != null) {
			listeners.remove(listener);
		}
	}

	// FIXME use event handler instead 
	
	private void fireRemovingCourse(Course course) throws LectureException {
		if (listeners != null) {
			logger.debug("fire removing course event");
			for (LectureListener listener : listeners) {
				listener.removingCourse(course);
			}
		}
	}

	private void fireRemovingCourseType(CourseType courseType) throws LectureException {
		if (listeners != null) {
			logger.debug("fire removing courseType event");
			for (LectureListener listener : listeners) {
				listener.removingCourseType(courseType);
			}
		}
	}

	private void fireRemovingInstitute(Institute institute) throws LectureException {
		if (listeners != null) {
			logger.debug("fire removing institute event");
			for (LectureListener listener : listeners) {
				listener.removingInstitute(institute);
			}
		}
	}

	private void fireCreatedInstitute(Institute institute) throws LectureException {
		if (listeners != null) {
			logger.debug("fire created institute event");
			for (LectureListener listener : listeners) {
				listener.createdInstitute(institute);
			}
		}
	}

	private User getUser(Long userId) throws LectureException {
		User user = User.Factory.newInstance();
		user = getSecurityService().getUser(userId);
		if (user == null) {
			logger.error("Coun't find user with id " + userId);
			throw new LectureException("user_cannot_be_found");
		}
		return user;
	}

	/**
	 * Convenience method for isNonExisting methods.<br/> Checks wheter or not
	 * the found record is equal to self entry.
	 * <ul>
	 * <li>self == null AND found == null => <b>true</b></li>
	 * <li>self == null AND found <> null => <b>false</b></li>
	 * <li>self <> null AND found == null => <b>true</b></li>
	 * <li>self <> null AND found <> null AND self == found => <b>true</b></li>
	 * <li>self <> null AND found <> null AND self <> found => <b>false</b></li>
	 * </ul>
	 * 
	 * @param self
	 *            current record
	 * @param found
	 *            in database
	 * @return true or false
	 */
	private boolean isEqualOrNull(Object self, Object found) {
		if (self == null || found == null) {
			return found == null;
		} else {
			return self.equals(found);
		}
	}

	@Override
	protected InstituteInfo handleGetInstitute(Institute institute) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}