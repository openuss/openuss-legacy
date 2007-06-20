// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import org.openuss.security.SecurityTestUtils;
import org.openuss.security.User;
import org.openuss.security.UserDao;


/**
 * JUnit Test for Spring Hibernate PeriodDao class.
 * @see org.openuss.lecture.PeriodDao
 */
public class PeriodDaoTest extends PeriodDaoTestBase {
	
	private InstituteDao instituteDao;
	private UserDao userDao;
	
	public void testPeriodDaoCreate() {
		User user = SecurityTestUtils.createDefaultUser();
		userDao.create(user);
		
		Institute institute = Institute.Factory.newInstance();
		institute.setName("name");
		institute.setOwnername("ownername");
		institute.setOwner(user);
		institute.setShortcut("shortcut");
		assertNull(institute.getId());
		instituteDao.create(institute);
		assertNotNull(institute.getId());
		
		Period period = new PeriodImpl();
		period.setInstitute(institute);
		period.setName(" ");
		assertNull(period.getId());
		periodDao.create(period);
		assertNotNull(period.getId());
	}
	
	public void testPeriodFactory() {
		Period period = Period.Factory.newInstance();
		assertNotNull(period);
	}
	
	public void testInstituteDaoInjection() {
		assertNotNull(instituteDao);
	}

	public InstituteDao getInstituteDao() {
		return instituteDao;
	}

	public void setInstituteDao(InstituteDao instituteDao) {
		this.instituteDao = instituteDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}