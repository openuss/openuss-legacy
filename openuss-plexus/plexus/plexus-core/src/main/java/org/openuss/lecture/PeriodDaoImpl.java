// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;
/**
 * @see org.openuss.lecture.Period
 */
public class PeriodDaoImpl
    extends org.openuss.lecture.PeriodDaoBase
{
	/**
	 * @see org.openuss.lecture.PeriodDao#toPeriodInfo(org.openuss.lecture.Period,
	 *      org.openuss.lecture.PeriodInfo)
	 */
	public void toPeriodInfo(org.openuss.lecture.Period sourceEntity,
			org.openuss.lecture.PeriodInfo targetVO) {
		super.toPeriodInfo(sourceEntity, targetVO);
		if (sourceEntity.getUniversity() != null) {
			targetVO.setUniversityId(sourceEntity.getUniversity().getId());
		}
	}

	/**
	 * @see org.openuss.lecture.PeriodDao#toPeriodInfo(org.openuss.lecture.Period)
	 */
	public org.openuss.lecture.PeriodInfo toPeriodInfo(final org.openuss.lecture.Period entity) {
		PeriodInfo periodInfo = super.toPeriodInfo(entity);
		if (entity.getUniversity() != null) {
			periodInfo.setUniversityId(entity.getUniversity().getId());
		}
		return periodInfo;
	}

	/**
	 * Retrieves the entity object that is associated with the specified value object from the object store. If no such
	 * entity object exists in the object store, a new, blank entity is created
	 */
	private org.openuss.lecture.Period loadPeriodFromPeriodInfo(
			org.openuss.lecture.PeriodInfo periodInfo) {

		org.openuss.lecture.Period period = org.openuss.lecture.Period.Factory.newInstance();
		if (periodInfo.getId() != null) {
			period = this.load(periodInfo.getId());
		}
		return period;
	}

	/**
	 * @see org.openuss.lecture.PeriodDao#periodInfoToEntity(org.openuss.lecture.PeriodInfo)
	 */
	public org.openuss.lecture.Period periodInfoToEntity(org.openuss.lecture.PeriodInfo periodInfo) {
		org.openuss.lecture.Period entity = this.loadPeriodFromPeriodInfo(periodInfo);
		this.periodInfoToEntity(periodInfo, entity, true);
		entity.setUniversity(this.getUniversityDao().load(periodInfo.getUniversityId()));
		return entity;
	}

	/**
	 * @see org.openuss.lecture.PeriodDao#periodInfoToEntity(org.openuss.lecture.PeriodInfo,
	 *      org.openuss.lecture.Period)
	 */
	public void periodInfoToEntity(org.openuss.lecture.PeriodInfo sourceVO,
			org.openuss.lecture.Period targetEntity, boolean copyIfNull) {
		super.periodInfoToEntity(sourceVO, targetEntity, copyIfNull);
	}

}