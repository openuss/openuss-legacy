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
public class SystemStatisticDaoImpl
    extends org.openuss.statistics.SystemStatisticDaoBase
{
    /**
     * @see org.openuss.statistics.SystemStatisticDao#toSystemStatisticInfo(org.openuss.statistics.SystemStatistic, org.openuss.statistics.SystemStatisticInfo)
     */
    public void toSystemStatisticInfo(
        org.openuss.statistics.SystemStatistic sourceEntity,
        org.openuss.statistics.SystemStatisticInfo targetVO)
    {
        super.toSystemStatisticInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.statistics.SystemStatisticDao#toSystemStatisticInfo(org.openuss.statistics.SystemStatistic)
     */
    public org.openuss.statistics.SystemStatisticInfo toSystemStatisticInfo(final org.openuss.statistics.SystemStatistic entity)
    {
        return super.toSystemStatisticInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.statistics.SystemStatistic loadSystemStatisticFromSystemStatisticInfo(org.openuss.statistics.SystemStatisticInfo SystemStatisticInfo)
    {
        org.openuss.statistics.SystemStatistic SystemStatistic = this.load(SystemStatisticInfo.getId());
        if (SystemStatistic == null)
        {
            SystemStatistic = org.openuss.statistics.SystemStatistic.Factory.newInstance();
        }
        return SystemStatistic;
    }
    
    /**
     * @see org.openuss.statistics.SystemStatisticDao#SystemStatisticInfoToEntity(org.openuss.statistics.SystemStatisticInfo)
     */
    public org.openuss.statistics.SystemStatistic systemStatisticInfoToEntity(org.openuss.statistics.SystemStatisticInfo SystemStatisticInfo)
    {
        org.openuss.statistics.SystemStatistic entity = this.loadSystemStatisticFromSystemStatisticInfo(SystemStatisticInfo);
        this.systemStatisticInfoToEntity(SystemStatisticInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.statistics.SystemStatisticDao#SystemStatisticInfoToEntity(org.openuss.statistics.SystemStatisticInfo, org.openuss.statistics.SystemStatistic)
     */
    public void systemStatisticInfoToEntity(
        org.openuss.statistics.SystemStatisticInfo sourceVO,
        org.openuss.statistics.SystemStatistic targetEntity,
        boolean copyIfNull)
    {
        super.systemStatisticInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }


	@Override
	protected SystemStatistic handleCurrent() throws Exception {
		final String hqlCount = "select count(*), " +
				"(select count(*) from CourseImpl as c), " +
				"(select count(*) from UserImpl as u), " +
				"(select count(*) from FileEntryImpl as f), " +
				"(select count(*) from PostImpl as p) " +
				"from InstituteImpl as i";
		return (SystemStatistic) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Object[] result = (Object[]) session.createQuery(hqlCount).uniqueResult();
				SystemStatistic systemStatistic = SystemStatistic.Factory.newInstance();
				systemStatistic.setInstitutes((Long) result[0]);
				systemStatistic.setCourses((Long) result[1]);
				systemStatistic.setUsers((Long) result[2]);
				systemStatistic.setDocuments((Long) result[3]);
				systemStatistic.setPosts((Long) result[4]);
				systemStatistic.setCreateTime(new Date(System.currentTimeMillis()));
				create(systemStatistic);
				return systemStatistic;
			}});
	}


	@Override
	protected SystemStatistic handleFindNewest() throws Exception {
		final String hqlCount = "select s from SystemStatisticImpl as s where s.createTime in (select max(s2.createTime) from SystemStatisticImpl as s2)";
		return (SystemStatistic) getHibernateTemplate().execute(new HibernateCallback(){
		public Object doInHibernate(Session session) throws HibernateException, SQLException {
			return session.createQuery(hqlCount).uniqueResult();
		}});
	}

}