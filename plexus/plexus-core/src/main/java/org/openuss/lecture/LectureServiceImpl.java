// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.openuss.security.Group;
import org.openuss.security.GroupType;
import org.openuss.security.Roles;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.acl.LectureAclEntry;

/**
 * @see org.openuss.lecture.LectureService
 */
public class LectureServiceImpl extends org.openuss.lecture.LectureServiceBase {

	private static final Logger logger = Logger.getLogger(LectureServiceImpl.class);

	public LectureServiceImpl() {}

	@Override
	protected Collection handleGetFaculties(boolean enabledOnly) throws Exception {
		if (enabledOnly)
			return getFacultyDao().loadAllEnabled(FacultyDao.TRANSFORM_FACULTYDETAILS);
		else
			return getFacultyDao().loadAll(FacultyDao.TRANSFORM_FACULTYDETAILS);
	}

	@Override
	protected Faculty handleGetFaculty(Long facultyId) throws Exception {
		Faculty faculty = getFacultyDao().load(facultyId);
		if (faculty == null && logger.isInfoEnabled()) {
			logger.info("Faculty with id" + facultyId + " cannot be found!");
		}
		return faculty;
	}

	@Override
	protected Subject handleGetSubject(Long subjectId) throws Exception {
		return getSubjectDao().load(subjectId);
	}

	@Override
	protected Period handleGetPeriod(Long periodId) throws Exception {
		if (periodId != null)
			return getPeriodDao().load(periodId);
		else
			return null;
	}

	@Override
	protected Enrollment handleGetEnrollment(Long enrollmentId) throws Exception {
		return getEnrollmentDao().load(enrollmentId);

	}

	@Override
	protected boolean handleIsNoneExistingFacultyShortcut(Faculty self, String shortcut) throws Exception {
		Faculty found = getFacultyDao().findByShortcut(shortcut);
		return isEqualOrNull(self, found);
	}

	@Override
	protected boolean handleIsNoneExistingEnrollmentShortcut(Enrollment self, String shortcut) throws Exception {
		Enrollment found = getEnrollmentDao().findByShortcut(shortcut);
		return isEqualOrNull(self, found);
	}

	@Override
	protected boolean handleIsNoneExistingSubjectShortcut(Subject self, String shortcut) throws Exception {
		Subject found = getSubjectDao().findByShortcut(shortcut);
		return isEqualOrNull(self, found);
	}

	@Override
	protected boolean handleIsNoneExistingSubjectName(Subject self, String name) throws Exception {
		Subject found = getSubjectDao().findByName(name);
		return isEqualOrNull(self, found);
	}

	@Override
	protected Faculty handleAdd(Long facultyId, Subject subject) throws Exception {
		if (logger.isDebugEnabled())
			logger.debug("Add Subject " + subject.getName() + " from faculty " + facultyId);
		Faculty faculty = getFaculty(facultyId);
		faculty.add(subject);
		subject.setFaculty(faculty);
		persist(faculty);
		return faculty;
	}

	@Override
	protected Faculty handleAdd(Long facultyId, Period period) throws Exception {
		Faculty faculty = getFaculty(facultyId);
		faculty.add(period);
		period.setFaculty(faculty);
		if (faculty.getActivePeriod() == null) {
			faculty.setActivePeriod(period);
		}
		persist(faculty);
		return faculty;
	}

	@Override
	protected void handlePersist(Faculty faculty) throws Exception {
		if (logger.isDebugEnabled())
			logger.debug("Save faculty " + faculty.getName());

		if (faculty.getId() != null) {
			getFacultyDao().update(faculty);
		} else {
			logger.error("Faculty object without id, use createFaculty method instead!!!");
			throw new LectureServiceException("Use createFaculty method instead!");
		}
	}

	@Override
	protected void handleCreateFaculty(Faculty faculty) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Creating new faculty " + faculty.getName());
		}

		faculty = getFacultyDao().create(faculty);

		// define the security
		SecurityService securityService = getSecurityService();

		// create object identity
		securityService.createObjectIdentity(faculty, null);

		// create system defined groups for faculty
		// FIXME Administrators, Assistants, and Tutor labels of Groups should
		// be configurable from the database through system properties-
		Group admins = securityService.createGroup("FACULTY_" + faculty.getId() + "_ADMINS", "Administrators", null,
				GroupType.ADMINISTRATOR);
		Group assistants = securityService.createGroup("FACULTY_" + faculty.getId() + "_ASSISTANTS", "Assistants",
				null, GroupType.ASSISTANT);
		Group tutors = securityService.createGroup("FACULTY_" + faculty.getId() + "_TUTORS", "Tutors", null,
				GroupType.TUTOR);

		securityService.addAuthorityToGroup(faculty.getOwner(), admins);

		securityService.setPermissions(admins, faculty, LectureAclEntry.FACULTY_ADMINISTRATION);
		securityService.setPermissions(assistants, faculty, LectureAclEntry.FACULTY_ASSIST);
		securityService.setPermissions(tutors, faculty, LectureAclEntry.FACULTY_TUTOR);

		faculty.getGroups().add(admins);
		faculty.getGroups().add(assistants);
		faculty.getGroups().add(tutors);

		faculty.getMembers().add(faculty.getOwner());

		// save changes
		getFacultyDao().update(faculty);

		// define security rights of faculty
		fireCreatedFaculty(faculty);

	}

	@Override
	protected void handlePersist(Subject subject) throws Exception {
		if (subject.getId() == null) {
			getSubjectDao().create(subject);
		} else {
			getSubjectDao().update(subject);
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
	protected void handlePersist(Enrollment enrollment) throws Exception {
		Group roleUser = Group.Factory.newInstance();
		roleUser.setId(Roles.USER);
		roleUser = getSecurityService().getGroup(roleUser);
		if (enrollment.getAccessType()!=AccessType.OPEN){
			getSecurityService().setPermissions(roleUser, enrollment, LectureAclEntry.NOTHING);		
		}
		else if (enrollment.getAccessType()==AccessType.OPEN){
			getSecurityService().setPermissions(roleUser, enrollment, LectureAclEntry.ENROLLMENT_PARTICIPANT);			
		}
		if (enrollment.getId() == null) {
			getEnrollmentDao().create(enrollment);
		} else {
			getEnrollmentDao().update(enrollment);
		}
	}

	@Override
	protected void handleRemoveFaculty(Long facultyId) throws Exception {
		Faculty faculty = getFaculty(facultyId);
		if (faculty == null)
			throw new LectureServiceException("Faculty not found " + facultyId);

		// fire events
		for (Enrollment enrollment : faculty.getEnrollments()) {
			fireRemovingEnrollment(enrollment);
		}

		for (Subject subject : faculty.getSubjects()) {
			fireRemovingSubject(subject);
		}

		fireRemovingFaculty(faculty);

		getFacultyDao().remove(faculty);
	}

	@Override
	protected void handleRemoveSubject(Long subjectId) throws Exception {
		if (logger.isDebugEnabled())
			logger.debug("Remove Subject " + subjectId);
		// refresh faculty
		Subject subject = getSubject(subjectId);

		// fire enrollment delete
		for (Enrollment enrollment : subject.getEnrollments()) {
			fireRemovingEnrollment(enrollment);
		}
		fireRemovingSubject(subject);

		getEnrollmentDao().remove(subject.getEnrollments());

		getSubjectDao().remove(subject);
	}

	@Override
	protected void handleRemovePeriod(Long periodId) throws Exception {
		if (logger.isDebugEnabled())
			logger.debug("Remove period " + periodId);
		Period period = getPeriod(periodId);

		// fire enrollment delete
		for (Enrollment enrollment : period.getEnrollments()) {
			fireRemovingEnrollment(enrollment);
		}

		// remove associated enrollments
		getEnrollmentDao().remove(period.getEnrollments());

		Faculty faculty = period.getFaculty();

		// check active period settings
		if (faculty != null) {
			if (period.equals(faculty.getActivePeriod())) {
				faculty.setActivePeriod(null);
				persist(faculty);
			}

			// remove period from faculty
			faculty.remove(period);
		}

		// delete period
		getPeriodDao().remove(period);
	}

	@Override
	protected void handleRemoveEnrollment(Long enrollmentId) throws Exception {
		if (logger.isDebugEnabled())
			logger.debug("Remove enrollment " + enrollmentId);
		Enrollment enrollment = getEnrollment(enrollmentId);
		// fire remove enrollment event
		fireRemovingEnrollment(enrollment);

		// remove associations
		Faculty faculty = enrollment.getFaculty();
		Period period = enrollment.getPeriod();
		period.remove(enrollment);
		persist(period);
		Subject subject = enrollment.getSubject();
		subject.remove(enrollment);
		persist(subject);
		faculty.remove(enrollment);

		persist(faculty);
		getEnrollmentDao().remove(enrollment);
	}

	@Override
	protected Enrollment handleCreateEnrollment(Long subjectId, Long periodId) throws Exception {
		// refresh instances
		Subject subject = getSubject(subjectId);
		Period period = getPeriod(periodId);

		if (!ObjectUtils.equals(subject.getFaculty(), period.getFaculty()))
			throw new LectureServiceException("Subject and period must be associated to the same faculty!");

		Faculty faculty = subject.getFaculty();

		Enrollment enrollment = Enrollment.Factory.newInstance();
		subject.add(enrollment);
		period.add(enrollment);
		faculty.add(enrollment);

		enrollment.setFaculty(faculty);
		enrollment.setSubject(subject);
		enrollment.setPeriod(period);

		persist(faculty);
		persist(subject);
		persist(period);
		persist(enrollment);

		generateShortcut(enrollment);
		persist(enrollment);
		
		getSecurityService().createObjectIdentity(enrollment, faculty);

		return enrollment;
	}

	public static void generateShortcut(Enrollment enrollment) {
		// FIXME make this method robust against unique key violations
		Subject subject = enrollment.getSubject();
		String id = String.valueOf(enrollment.getId());
		int index = subject.getShortcut().length();
		if (index + id.length() >= 30)
			index -= id.length();
		
		String shortcut = subject.getShortcut().substring(0, index - 1) + id;

		enrollment.setShortcut(shortcut);
	}

	@Override
	protected void handleSetActivePeriod(Long facultyId, Period period) throws Exception {
		// refresh instances
		Faculty faculty = getFaculty(facultyId);
		faculty.setActivePeriod(period);
		persist(faculty);
	}

	@Override
	protected void handleRegisterListener(LectureListener listener) throws Exception {
		if (listeners == null) {
			listeners = new HashSet<LectureListener>();
		}
		listeners.add(listener);
	}

	@Override
	protected List handleGetFacultyAspirants(Long facultyId) throws Exception {
		Faculty faculty = getFacultyDao().load(facultyId);
		// need to get ride of persistant back so use a new arraylist
		List aspirants = new ArrayList<User>(faculty.getAspirants());
		getUserDao().toUserInfoCollection(aspirants);
		return aspirants;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected FacultySecurity handleGetFacultySecurity(Long facultyId) throws Exception {
		return (FacultySecurity) getFacultyDao().load(FacultyDao.TRANSFORM_FACULTYSECURITY, facultyId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void handleAddFacultyAspirant(Long userId, Long facultyId) throws Exception {
		Faculty faculty = getFaculty(facultyId);
		User user = getUser(userId);

		if (!faculty.getMembers().contains(user)) {
			faculty.getAspirants().add(user);
			getFacultyDao().update(faculty);
		} else {
			throw new LectureException("user_is_already_a_member_of_the_faculty");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void handleAddFacultyMember(Long userId, Long facultyId) throws Exception {
		Faculty faculty = getFaculty(facultyId);
		User user = getUser(userId);

		if (faculty.getMembers().contains(user)) {
			throw new LectureException("user_is_already_a_member_of_the_faculty");
		}

		faculty.getMembers().add(user);
		// if user was an aspirant remove him from the list
		faculty.getAspirants().remove(user);
		persist(faculty);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void handleRejectFacultyAspirant(Long userId, Long facultyId) throws Exception {
		Faculty faculty = getFaculty(facultyId);
		User user = getUser(userId);
		faculty.getAspirants().remove(user);
		persist(faculty);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void handleRemoveFacultyMember(Long userId, Long facultyId) throws Exception {
		Faculty faculty = getFaculty(facultyId);
		User user = getUser(userId);

		final SecurityService securityService = getSecurityService();
		for (Group group : faculty.getGroups()) {
			securityService.removeAuthorityFromGroup(user, group);
		}
		faculty.getMembers().remove(user);
		persist(faculty);
	}

	@Override
	protected void handleSetGroupOfMember(FacultyMember member, Long facultyId) throws Exception {
		logger.debug("setting groups of member");
		Faculty faculty = getFacultyDao().load(facultyId);
		User user = getUser(member.getId());

		if (!faculty.getMembers().contains(user)) {
			throw new LectureException("User is not a member of the faculty!");
		}

		// cache group ids
		final List<Long> groupIds = new ArrayList<Long>();
		for (FacultyGroup group : member.getGroups()) {
			groupIds.add(group.getId());
		}

		// remove and add user to the new groups
		final SecurityService securityService = getSecurityService();
		for (Group group : faculty.getGroups()) {
			if (group.getMembers().contains(user) && !(groupIds.contains(group.getId()))) {
				securityService.removeAuthorityFromGroup(user, group);
			} else if (!group.getMembers().contains(user) && (groupIds.contains(group.getId()))) {
				securityService.addAuthorityToGroup(user, group);
			}
		}
	}

	@Override
	protected void handleAcceptFacultyAspirant(Long userId, Long facultyId) throws Exception {
		Faculty faculty = getFaculty(facultyId);
		User user = getUser(userId);
		// check if user was really an aspirant of the faculty
		if (faculty.getAspirants().contains(user)) {
			faculty.getAspirants().remove(user);
			faculty.getMembers().add(user);
			getFacultyDao().update(faculty);
		}
	}

	@Override
	protected void handleSetOwnerOfFaculty(Long userId, Long facultyId) throws Exception {
		// TODO Auto-generated method stub
	}

	/*------------------- private methods -------------------- */

	private Set<LectureListener> listeners;

	protected void handleUnregisterListener(LectureListener listener) throws Exception {
		if (listeners != null) {
			listeners.remove(listener);
		}
	}

	private void fireRemovingEnrollment(Enrollment enrollment) throws LectureException {
		if (listeners != null) {
			logger.debug("fire removing enrollment event");
			for (LectureListener listener : listeners) {
				listener.removingEnrollment(enrollment);
			}
		}
	}

	private void fireRemovingSubject(Subject subject) throws LectureException {
		if (listeners != null) {
			logger.debug("fire removing subject event");
			for (LectureListener listener : listeners) {
				listener.removingSubject(subject);
			}
		}
	}

	private void fireRemovingFaculty(Faculty faculty) throws LectureException {
		if (listeners != null) {
			logger.debug("fire removing faculty event");
			for (LectureListener listener : listeners) {
				listener.removingFaculty(faculty);
			}
		}
	}

	private void fireCreatedFaculty(Faculty faculty) throws LectureException {
		if (listeners != null) {
			logger.debug("fire created faculty event");
			for (LectureListener listener : listeners) {
				listener.createdFaculty(faculty);
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

}