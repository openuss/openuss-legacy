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
		SystemStatistic systemStatistic = new SystemStatisticImpl();
		systemStatistic.setUsers(1L);
		systemStatistic.setUniversities(1L);
		systemStatistic.setDepartments(1L);
		systemStatistic.setInstitutes(1L);
		systemStatistic.setCourses(1L);
		systemStatistic.setDocuments(1L);
		systemStatistic.setPosts(1L);
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