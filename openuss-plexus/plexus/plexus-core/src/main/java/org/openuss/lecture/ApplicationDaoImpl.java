// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

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
			targetVO.setDepartmentId(sourceEntity.getDepartment().getId());
		}
		if (sourceEntity.getInstitute() != null) {
			targetVO.setInstituteId(sourceEntity.getInstitute().getId());
		}
		if (sourceEntity.getApplyingUser() != null) {
			targetVO.setApplyingUserId(sourceEntity.getApplyingUser().getId());
		}
		if (sourceEntity.getConfirmingUser() != null) {
			targetVO.setConfirmingUserId(sourceEntity.getConfirmingUser().getId());
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

		Application application = Application.Factory.newInstance();
		if (applicationInfo.getId() != null) {
			application = this.load(applicationInfo.getId());
		}
		return application;
	}

	/**
	 * @see org.openuss.lecture.ApplicationDao#applicationInfoToEntity(org.openuss.lecture.ApplicationInfo)
	 */
	public Application applicationInfoToEntity(ApplicationInfo applicationInfo) {
		if (applicationInfo.getDepartmentId() == null) {
			throw new IllegalArgumentException(
					"ApplicationDaoImpl.applicationInfoToEntity - the DepartmentID cannot be null");
		}
		if (applicationInfo.getInstituteId() == null) {
			throw new IllegalArgumentException(
					"ApplicationDaoImpl.applicationInfoToEntity - the InstituteID cannot be null");
		}
		if (applicationInfo.getApplyingUserId() == null) {
			throw new IllegalArgumentException(
					"ApplicationDaoImpl.applicationInfoToEntity - the ApplyingUserID cannot be null");
		}
		Application entity = this.loadApplicationFromApplicationInfo(applicationInfo);
		this.applicationInfoToEntity(applicationInfo, entity, true);
		entity.setDepartment(this.getDepartmentDao().load(applicationInfo.getDepartmentId()));
		entity.setInstitute(this.getInstituteDao().load(applicationInfo.getInstituteId()));
		entity.setApplyingUser(this.getUserDao().load(applicationInfo.getApplyingUserId()));
		if (applicationInfo.getConfirmingUserId() != null) {
			entity.setConfirmingUser(this.getUserDao().load(applicationInfo.getConfirmingUserId()));
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

}