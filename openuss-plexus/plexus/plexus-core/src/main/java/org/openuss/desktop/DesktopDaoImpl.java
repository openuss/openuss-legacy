// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.desktop;

import java.util.ArrayList;
import java.util.Iterator;

import org.openuss.lecture.Course;
import org.openuss.lecture.CourseType;
import org.openuss.lecture.Department;
import org.openuss.lecture.Institute;
import org.openuss.lecture.University;

/**
 * @see org.openuss.desktop.Desktop
 * @author Ingo Dueppe, Ron Haus
 */
public class DesktopDaoImpl extends org.openuss.desktop.DesktopDaoBase {

	@Override
	@SuppressWarnings( { "unchecked" })
	public java.util.Collection findByUniversity(final int transform, final org.openuss.lecture.University university) {
		return this
				.findByUniversity(
						transform,
						"select d from org.openuss.desktop.Desktop as d, org.openuss.lecture.University e where e=:university and e in elements(d.universities)",
						university);
	}

	@Override
	@SuppressWarnings( { "unchecked" })
	public java.util.Collection findByCourse(final int transform, final org.openuss.lecture.Course course) {
		return this
				.findByCourse(
						transform,
						"select d from org.openuss.desktop.Desktop as d, org.openuss.lecture.Course e where e=:course and e in elements(d.courses)",
						course);
	}

	@Override
	@SuppressWarnings( { "unchecked" })
	public java.util.Collection findByInstitute(final int transform, final Institute institute) {
		return this
				.findByInstitute(
						transform,
						"select d from org.openuss.desktop.Desktop as d, org.openuss.lecture.Institute f where f=:institute and f in elements(d.institutes)",
						institute);
	}

	@Override
	@SuppressWarnings( { "unchecked" })
	public java.util.Collection findByCourseType(final int transform, final CourseType courseType) {
		return this
				.findByCourseType(
						transform,
						"select d from org.openuss.desktop.Desktop as d, org.openuss.lecture.CourseType s where s=:courseType and s in elements(d.courseTypes)",
						courseType);
	}

	/**
	 * @see org.openuss.desktop.DesktopDao#toDesktopInfo(org.openuss.desktop.Desktop, org.openuss.desktop.DesktopInfo)
	 */
	@SuppressWarnings( { "unchecked" })
	public void toDesktopInfo(org.openuss.desktop.Desktop sourceEntity, org.openuss.desktop.DesktopInfo targetVO) {
		super.toDesktopInfo(sourceEntity, targetVO);

		// UserID
		if (sourceEntity.getUser() != null) {
			targetVO.setUserId(sourceEntity.getUser().getId());
		}

		// UniversityIDs
		targetVO.setUniversityIds(new ArrayList(sourceEntity.getUniversities().size()));
		for (University university : sourceEntity.getUniversities()) {
			targetVO.getUniversityIds().add(university.getId());
		}

		// DepartmentIDs
		targetVO.setDepartmentIds(new ArrayList(sourceEntity.getDepartments().size()));
		for (Department department : sourceEntity.getDepartments()) {
			targetVO.getDepartmentIds().add(department.getId());
		}
		
		// InstituteIDs
		targetVO.setInstituteIds(new ArrayList(sourceEntity.getInstitutes().size()));
		for (Institute institute : sourceEntity.getInstitutes()) {
			targetVO.getInstituteIds().add(institute.getId());
		}

		// CourseTypeIDs
		targetVO.setCourseTypeIds(new ArrayList(sourceEntity.getCourseTypes().size()));
		for (CourseType courseType : sourceEntity.getCourseTypes()) {
			targetVO.getCourseTypeIds().add(courseType.getId());
		}

		// CourseIDs
		targetVO.setCourseIds(new ArrayList(sourceEntity.getCourses().size()));
		for (Course course : sourceEntity.getCourses()) {
			targetVO.getCourseIds().add(course.getId());
		}

	}

	/**
	 * @see org.openuss.desktop.DesktopDao#toDesktopInfo(org.openuss.desktop.Desktop)
	 */
	public org.openuss.desktop.DesktopInfo toDesktopInfo(final org.openuss.desktop.Desktop entity) {
		return super.toDesktopInfo(entity);
	}

	/**
	 * Retrieves the entity object that is associated with the specified value object from the object store. If no such
	 * entity object exists in the object store, a new, blank entity is created
	 */
	private org.openuss.desktop.Desktop loadDesktopFromDesktopInfo(org.openuss.desktop.DesktopInfo desktopInfo) {

		Desktop desktop = Desktop.Factory.newInstance();
		if (desktopInfo.getId() != null) {
			desktop = this.load(desktopInfo.getId());
		}
		return desktop;
	}

	/**
	 * @see org.openuss.desktop.DesktopDao#desktopInfoToEntity(org.openuss.desktop.DesktopInfo)
	 */
	public org.openuss.desktop.Desktop desktopInfoToEntity(org.openuss.desktop.DesktopInfo desktopInfo) {
		org.openuss.desktop.Desktop entity = this.loadDesktopFromDesktopInfo(desktopInfo);
		this.desktopInfoToEntity(desktopInfo, entity, true);
		return entity;
	}

	/**
	 * @see org.openuss.desktop.DesktopDao#desktopInfoToEntity(org.openuss.desktop.DesktopInfo,
	 *      org.openuss.desktop.Desktop)
	 */
	@SuppressWarnings( { "unchecked" })
	public void desktopInfoToEntity(org.openuss.desktop.DesktopInfo sourceVO, org.openuss.desktop.Desktop targetEntity,
			boolean copyIfNull) {
		super.desktopInfoToEntity(sourceVO, targetEntity, copyIfNull);

		// User
		if (copyIfNull && (sourceVO.getUserId() != null)) {
			targetEntity.setUser(this.getUserDao().load(sourceVO.getUserId()));
		}

		// Universities
		if (copyIfNull && (sourceVO.getUniversityIds() != null)) {
			Iterator iter1 = sourceVO.getUniversityIds().iterator();
			while (iter1.hasNext()) {
				targetEntity.getUniversities().add(this.getUniversityDao().load((Long) iter1.next()));
			}
		}

		// Departments
		if (copyIfNull && (sourceVO.getDepartmentIds() != null)) {
			Iterator iter2 = sourceVO.getDepartmentIds().iterator();
			while (iter2.hasNext()) {
				targetEntity.getDepartments().add(this.getDepartmentDao().load((Long) iter2.next()));
			}
		}

		// Institutes

		// CourseTypes

		// Courses

	}

}