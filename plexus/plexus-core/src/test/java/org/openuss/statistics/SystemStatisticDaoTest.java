// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.statistics;

import java.util.Date;


/**
 * JUnit Test for Spring Hibernate SystemStatisticsDao class.
 * @see org.openuss.statistics.SystemStatisticsDao
 */
public class SystemStatisticDaoTest extends SystemStatisticDaoTestBase {
	
	public void testSystemStatisticsDaoCreate() {
		SystemStatistic systemStatistic = SystemStatistic.Factory.newInstance();
		systemStatistic.setUsers(new Long(1));
		systemStatistic.setUniversities(new Long(1));
		systemStatistic.setDepartments(new Long(1));
		systemStatistic.setInstitutes(new Long(1));
		systemStatistic.setCourses(new Long(1));
		systemStatistic.setDocuments(new Long(1));
		systemStatistic.setPosts(new Long(1));
		systemStatistic.setCreateTime(new Date());
		assertNull(systemStatistic.getId());
		systemStatisticDao.create(systemStatistic);
		assertNotNull(systemStatistic.getId());
	}
	
	public void testFindNewest() {
		systemStatisticDao.create(systemStatisticDao.current());
		SystemStatistic statistic = systemStatisticDao.findNewest();
		assertNotNull(statistic);
	}
}