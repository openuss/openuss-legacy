package org.openuss.aop;

import org.apache.log4j.Logger;
import org.openuss.lecture.Department;
import org.openuss.lecture.DepartmentDao;
import org.openuss.lecture.Institute;
import org.openuss.lecture.InstituteDao;
import org.openuss.lecture.OrganisationDao;
import org.openuss.lecture.University;
import org.openuss.lecture.UniversityDao;
import org.openuss.security.User;
import org.openuss.security.UserDao;

/**
 * Aspect for Sending Mails.
 * 
 * @author Ron Haus
 */

public class MailSenderAspectImpl {

	private static final Logger logger = Logger.getLogger(MailSenderAspectImpl.class);

	private UserDao userDao;
	private OrganisationDao organisationDao;
	private UniversityDao universityDao;
	private DepartmentDao departmentDao;
	private InstituteDao instituteDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public OrganisationDao getOrganisationDao() {
		return organisationDao;
	}

	public void setOrganisationDao(OrganisationDao organisationDao) {
		this.organisationDao = organisationDao;
	}

	public UniversityDao getUniversityDao() {
		return universityDao;
	}

	public void setUniversityDao(UniversityDao universityDao) {
		this.universityDao = universityDao;
	}

	public DepartmentDao getDepartmentDao() {
		return departmentDao;
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	public InstituteDao getInstituteDao() {
		return instituteDao;
	}

	public void setInstituteDao(InstituteDao instituteDao) {
		this.instituteDao = instituteDao;
	}

	/**
	 * Sends Emails whenever a Member is added to an Organisation.
	 */
	public void sendAddMemberMail(Long organisationId, Long userId) {
		logger.debug("sendAddMemberMail - User " + userId + " has been added to Organisation " + organisationId);

		// Loading User
		User user = userDao.load(userId);

		// Identifying type of Organisation
		University university = universityDao.load(organisationId);
		if (university != null) {
			logger.debug("sendAddMemberMail - Organisation of type University identified");
			this.sendAddMemberMailForUniversity(university, user);
		} else {
			Department department = departmentDao.load(organisationId);
			if (department != null) {
				logger.debug("sendAddMemberMail - Organisation of type Department identified");
				this.sendAddMemberMailForDepartment(department, user);
			} else {
				Institute institute = instituteDao.load(organisationId);
				if (institute != null) {
					logger.debug("sendAddMemberMail - Organisation of type Institute identified");
					this.sendAddMemberMailForInstitute(institute, user);
				}
			}
		}
	}

	private void sendAddMemberMailForUniversity(University university, User user) {
		logger.debug("sendAddMemberMailForUniversity - Sending Email to User " + user.getUsername()
				+ " and Members of University " + university.getName());
	}

	private void sendAddMemberMailForDepartment(Department department, User user) {
		logger.debug("sendAddMemberMailForDepartment - Sending Email to User " + user.getUsername()
				+ " and Members of Department " + department.getName());
	}

	private void sendAddMemberMailForInstitute(Institute institute, User user) {
		logger.debug("sendAddMemberMailForInstitute - Sending Email to User " + user.getUsername()
				+ " and Members of Institute " + institute.getName());
	}

	/**
	 * Sends Emails whenever a Member is removed from an Organisation.
	 */
	public void sendRemoveMemberMail(Long organisationId, Long userId) {
		logger.debug("sendRemoveMemberMail - User " + userId + " has been removed from Organisation " + organisationId);

		// Loading User
		User user = userDao.load(userId);

		// Identifying type of Organisation
		University university = universityDao.load(organisationId);
		if (university != null) {
			logger.debug("sendRemoveMemberMail - Organisation of type University identified");
			this.sendAddMemberMailForUniversity(university, user);
		} else {
			Department department = departmentDao.load(organisationId);
			if (department != null) {
				logger.debug("sendRemoveMemberMail - Organisation of type Department identified");
				this.sendAddMemberMailForDepartment(department, user);
			} else {
				Institute institute = instituteDao.load(organisationId);
				if (institute != null) {
					logger.debug("sendRemoveMemberMail - Organisation of type Institute identified");
					this.sendAddMemberMailForInstitute(institute, user);
				}
			}
		}
	}
}
