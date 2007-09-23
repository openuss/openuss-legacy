// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import org.apache.commons.lang.ObjectUtils;

/**
 * @see org.openuss.lecture.Period
 */
public class PeriodDaoImpl extends PeriodDaoBase {
	/**
	 * @see org.openuss.lecture.PeriodDao#toPeriodInfo(org.openuss.lecture.Period,
	 *      org.openuss.lecture.PeriodInfo)
	 */
	public void toPeriodInfo(Period sourceEntity, PeriodInfo targetVO) {
		super.toPeriodInfo(sourceEntity, targetVO);
		if (sourceEntity.getUniversity() != null) {
			targetVO.setUniversityId(sourceEntity.getUniversity().getId());
		}
	}

	/**
	 * @see org.openuss.lecture.PeriodDao#toPeriodInfo(org.openuss.lecture.Period)
	 */
	public PeriodInfo toPeriodInfo(final Period entity) {
		PeriodInfo periodInfo = super.toPeriodInfo(entity);
		if (entity.getUniversity() != null) {
			periodInfo.setUniversityId(entity.getUniversity().getId());
		}

		if (entity.isActive()) {
			periodInfo.setActive(true);
		} else {
			periodInfo.setActive(false);
		}

		if (entity.isRemovable()) {
			periodInfo.setRemovable(true);
		} else {
			periodInfo.setRemovable(false);
		}
		return periodInfo;
	}

	/**
	 * Retrieves the entity object that is associated with the specified value
	 * object from the object store. If no such entity object exists in the
	 * object store, a new, blank entity is created
	 */
	private Period loadPeriodFromPeriodInfo(PeriodInfo periodInfo) {

		Period period = Period.Factory.newInstance();
		if (periodInfo.getId() != null) {
			period = this.load(periodInfo.getId());
		}
		return period;
	}

	/**
	 * @see org.openuss.lecture.PeriodDao#periodInfoToEntity(org.openuss.lecture.PeriodInfo)
	 */
	public Period periodInfoToEntity(PeriodInfo periodInfo) {
		Period entity = this.loadPeriodFromPeriodInfo(periodInfo);
		if (entity == null) {
			entity = Period.Factory.newInstance();
		}
		this.periodInfoToEntity(periodInfo, entity, true);
		if (entity.getUniversity() == null && periodInfo.getUniversityId() != null || !ObjectUtils.equals(periodInfo.getUniversityId(), entity.getUniversity().getId())) {
			entity.setUniversity(this.getUniversityDao().load(periodInfo.getUniversityId()));
		}
		return entity;
	}

	/**
	 * @see org.openuss.lecture.PeriodDao#periodInfoToEntity(org.openuss.lecture.PeriodInfo,
	 *      org.openuss.lecture.Period)
	 */
	public void periodInfoToEntity(org.openuss.lecture.PeriodInfo sourceVO, org.openuss.lecture.Period targetEntity,
			boolean copyIfNull) {
		super.periodInfoToEntity(sourceVO, targetEntity, copyIfNull);
	}

}