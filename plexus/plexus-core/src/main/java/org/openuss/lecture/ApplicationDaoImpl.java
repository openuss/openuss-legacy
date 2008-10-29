// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import org.apache.commons.lang.Validate;

/**
 * @see org.openuss.lecture.Application
 * @author Ron Haus
 */
public class ApplicationDaoImpl extends org.openuss.lecture.ApplicationDaoBase {
	/**
	 * @see org.openuss.lecture.ApplicationDao#toApplicationInfo(org.openuss.lecture.Application,
	 *      org.openuss.lecture.ApplicationInfo)
	 */
	public void toApplicationInfo(Application sourceEntity, ApplicationInfo targetVO) {
		super.toApplicationInfo(sourceEntity, targetVO);
		if (sourceEntity.getDepartment() != null) {
			targetVO.setDepartmentInfo(this.getDepartmentDao().toDepartmentInfo(sourceEntity.getDepartment()));
		}
		if (sourceEntity.getInstitute() != null) {
			targetVO.setInstituteInfo(this.getInstituteDao().toInstituteInfo(sourceEntity.getInstitute()));
		}
		if (sourceEntity.getApplyingUser() != null) {
			targetVO.setApplyingUserInfo(this.getUserDao().toUserInfo(sourceEntity.getApplyingUser()));
		}
		if (sourceEntity.getConfirmingUser() != null) {
			targetVO.setConfirmingUserInfo(this.getUserDao().toUserInfo(sourceEntity.getConfirmingUser()));
		}
	}

	/**
	 * @see org.openuss.lecture.ApplicationDao#toApplicationInfo(org.openuss.lecture.Application)
	 */
	public ApplicationInfo toApplicationInfo(final Application entity) {
		return super.toApplicationInfo(entity);
	}

	/**
	 * Retrieves the entity object that is associated with the specified value object from the object store. If no such
	 * entity object exists in the object store, a new, blank entity is created
	 */
	private Application loadApplicationFromApplicationInfo(ApplicationInfo applicationInfo) {

		Application application = new ApplicationImpl();
		if (applicationInfo.getId() != null) {
			application = this.load(applicationInfo.getId());
		}
		return application;
	}

	/**
	 * @see org.openuss.lecture.ApplicationDao#applicationInfoToEntity(org.openuss.lecture.ApplicationInfo)
	 */
	public Application applicationInfoToEntity(ApplicationInfo applicationInfo) {
		Validate.notNull(applicationInfo.getDepartmentInfo(),"the DepartmentID cannot be null");
		Validate.notNull(applicationInfo.getInstituteInfo(), "the InstituteID cannot be null");
		Validate.notNull(applicationInfo.getApplyingUserInfo(), "the ApplyingUserID cannot be null");
		Application entity = this.loadApplicationFromApplicationInfo(applicationInfo);
		if (entity == null) {
			entity = new ApplicationImpl();
		}
		this.applicationInfoToEntity(applicationInfo, entity, true);
		
		entity.setDepartment(this.getDepartmentDao().load(applicationInfo.getDepartmentInfo().getId()));
		entity.setInstitute(this.getInstituteDao().load(applicationInfo.getInstituteInfo().getId()));
		entity.setApplyingUser(this.getUserDao().load(applicationInfo.getApplyingUserInfo().getId()));
		if (applicationInfo.getConfirmingUserInfo() != null) {
			entity.setConfirmingUser(this.getUserDao().load(applicationInfo.getConfirmingUserInfo().getId()));
		}
		return entity;
	}

	/**
	 * @see org.openuss.lecture.ApplicationDao#applicationInfoToEntity(org.openuss.lecture.ApplicationInfo,
	 *      org.openuss.lecture.Application)
	 */
	public void applicationInfoToEntity(ApplicationInfo sourceVO, Application targetEntity, boolean copyIfNull) {
		super.applicationInfoToEntity(sourceVO, targetEntity, copyIfNull);
	}
	
	/**
     * @see org.openuss.lecture.ApplicationDao#findByDepartmentAndConfirmed(int, org.openuss.lecture.Department, boolean)
     */
    public java.util.List<?> findByDepartmentAndConfirmed(final int transform, final org.openuss.lecture.Department department, final boolean confirmed)
    {
        return this.findByDepartmentAndConfirmed(transform, "from org.openuss.lecture.Application as f where f.confirmed = :confirmed and (f.department = :department) order by f.applicationDate desc", department, confirmed);
    }
    
    /**
     * @see org.openuss.lecture.ApplicationDao#findByInstituteAndDepartment(int, java.lang.String, org.openuss.lecture.Institute, org.openuss.lecture.Department)
     */
    @SuppressWarnings("unchecked")
    public java.lang.Object findByInstituteAndDepartment(final int transform, final java.lang.String queryString, final org.openuss.lecture.Institute institute, final org.openuss.lecture.Department department)
    {
        try
        {
            org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
            queryObject.setParameter(0, institute);
            queryObject.setParameter(1, department);
            java.util.Set results = new java.util.LinkedHashSet(queryObject.list());
            java.lang.Object result = null;
            if (results != null)
            {
            	// even more than one result is ok: 
            	// the applications are returned chronologically (latest first), 
            	// so just use the latest application
            	if(results.size() > 0){
            		result = results.iterator().next();
            	}
            	
            }
            result = transformEntity(transform, (org.openuss.lecture.Application)result);
            return result;
        }
        catch (org.hibernate.HibernateException ex)
        {
            throw super.convertHibernateAccessException(ex);
        }
    }

}