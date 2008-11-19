// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.statistics;

import java.sql.SQLException;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * @see org.openuss.statistics.SystemStatistics
 */
public class SystemStatisticDaoImpl extends SystemStatisticDaoBase {
	/**
	 * @see org.openuss.statistics.SystemStatisticDao#toSystemStatisticInfo(org.openuss.statistics.SystemStatistic,
	 *      org.openuss.statistics.SystemStatisticInfo)
	 */
	public void toSystemStatisticInfo(SystemStatistic sourceEntity, SystemStatisticInfo targetVO) {
		super.toSystemStatisticInfo(sourceEntity, targetVO);
	}

	/**
	 * @see org.openuss.statistics.SystemStatisticDao#toSystemStatisticInfo(org.openuss.statistics.SystemStatistic)
	 */
	public SystemStatisticInfo toSystemStatisticInfo(final SystemStatistic entity) {
		return super.toSystemStatisticInfo(entity);
	}

	/**
	 * Retrieves the entity object that is associated with the specified value
	 * object from the object store. If no such entity object exists in the
	 * object store, a new, blank entity is created
	 */
	private SystemStatistic loadSystemStatisticFromSystemStatisticInfo(SystemStatisticInfo systemStatisticInfo) {
		SystemStatistic systemStatistic = this.load(systemStatisticInfo.getId());
		if (systemStatistic == null) {
			systemStatistic = new SystemStatisticImpl();
		}
		return systemStatistic;
	}

	/**
	 * @see org.openuss.statistics.SystemStatisticDao#SystemStatisticInfoToEntity(org.openuss.statistics.SystemStatisticInfo)
	 */
	public SystemStatistic systemStatisticInfoToEntity(SystemStatisticInfo SystemStatisticInfo) {
		SystemStatistic entity = this.loadSystemStatisticFromSystemStatisticInfo(SystemStatisticInfo);
		this.systemStatisticInfoToEntity(SystemStatisticInfo, entity, true);
		return entity;
	}

	/**
	 * @see org.openuss.statistics.SystemStatisticDao#SystemStatisticInfoToEntity(org.openuss.statistics.SystemStatisticInfo,
	 *      org.openuss.statistics.SystemStatistic)
	 */
	public void systemStatisticInfoToEntity(SystemStatisticInfo sourceVO, SystemStatistic targetEntity, boolean copyIfNull) {
		super.systemStatisticInfoToEntity(sourceVO, targetEntity, copyIfNull);
	}

	@Override
	protected SystemStatistic handleCurrent() throws Exception {
		final String hqlCount = "select count(*), " +
				"(select count(*) from CourseImpl as c), " +
				"(select count(*) from UserImpl as u), " +
				"(select count(*) from FileEntryImpl as f), " +
				"(select count(*) from PostImpl as p), " +
				"(select count(*) from UniversityImpl as o), " +
				"(select count(*) from DepartmentImpl as d) " +
				"from InstituteImpl as i";
		return (SystemStatistic) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Object[] result = (Object[]) session.createQuery(hqlCount).uniqueResult();
				SystemStatistic systemStatistic = new SystemStatisticImpl();
				systemStatistic.setInstitutes((Long) result[0]);
				systemStatistic.setCourses((Long) result[1]);
				systemStatistic.setUsers((Long) result[2]);
				systemStatistic.setDocuments((Long) result[3]);
				systemStatistic.setPosts((Long) result[4]);
				systemStatistic.setUniversities((Long) result[5]);
				systemStatistic.setDepartments((Long) result[6]);
				systemStatistic.setCreateTime(new Date(System.currentTimeMillis()));
				create(systemStatistic);
				return systemStatistic;
			}
		});
	}

	@Override
	protected SystemStatistic handleFindNewest() throws Exception {
		final String hqlCount = "select s from SystemStatisticImpl as s where s.createTime = (select max(s2.createTime) from SystemStatisticImpl as s2)";
		return (SystemStatistic) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery(hqlCount).uniqueResult();
			}
		});
	}

}
