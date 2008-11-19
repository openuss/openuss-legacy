// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.desktop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseType;
import org.openuss.lecture.Department;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.Institute;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.University;
import org.openuss.lecture.UniversityInfo;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * @see org.openuss.desktop.Desktop
 * @author Ingo Dueppe, Ron Haus
 */
public class DesktopDaoImpl extends DesktopDaoBase {

	@Override
	@SuppressWarnings( { "unchecked" })
	public Collection findByUniversity(final int transform, final University university) {
		return this
				.findByUniversity(
						transform,
						"select d from org.openuss.desktop.Desktop as d, org.openuss.lecture.University e where e=:university and e in elements(d.universities)",
						university);
	}
	
	@Override
	@SuppressWarnings( { "unchecked" })
	public java.util.Collection findByDepartment(final int transform, final org.openuss.lecture.Department department) {
		return this
				.findByDepartment(
						transform,
						"select d from org.openuss.desktop.Desktop as d, org.openuss.lecture.Department e where e=:department and e in elements(d.departments)",
						department);
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

		// User
		if (sourceEntity.getUser() != null) {
			targetVO.setUserInfo(this.getUserDao().toUserInfo(sourceEntity.getUser()));
		}

		// Universities
		targetVO.setUniversityInfos(new ArrayList(sourceEntity.getUniversities().size()));
		for (University university : sourceEntity.getUniversities()) {
			if (university != null)
				targetVO.getUniversityInfos().add(this.getUniversityDao().toUniversityInfo(university));
		}

		// Departments
		targetVO.setDepartmentInfos(new ArrayList(sourceEntity.getDepartments().size()));
		for (Department department : sourceEntity.getDepartments()) {
			if (department != null)
				targetVO.getDepartmentInfos().add(this.getDepartmentDao().toDepartmentInfo(department));
		}
		
		// Institutes
		targetVO.setInstituteInfos(new ArrayList(sourceEntity.getInstitutes().size()));
		for (Institute institute : sourceEntity.getInstitutes()) {
			if (institute != null)
				targetVO.getInstituteInfos().add(this.getInstituteDao().toInstituteInfo(institute));
		}

		// CourseTypes
		targetVO.setCourseTypeInfos(new ArrayList(sourceEntity.getCourseTypes().size()));

		// Courses
		targetVO.setCourseInfos(new ArrayList(sourceEntity.getCourses().size()));
		for (Course course : sourceEntity.getCourses()) {
			if (course != null)
				targetVO.getCourseInfos().add(this.getCourseDao().toCourseInfo(course));
		}

	}

	/**
	 * @see org.openuss.desktop.DesktopDao#toDesktopInfo(org.openuss.desktop.Desktop)
	 */
	public DesktopInfo toDesktopInfo(final Desktop entity) {
		return super.toDesktopInfo(entity);
	}

	/**
	 * Retrieves the entity object that is associated with the specified value object from the object store. If no such
	 * entity object exists in the object store, a new, blank entity is created
	 */
	private Desktop loadDesktopFromDesktopInfo(DesktopInfo desktopInfo) {

		Desktop desktop = new DesktopImpl();
		if (desktopInfo.getId() != null) {
			desktop = this.load(desktopInfo.getId());
		}
		return desktop;
	}

	/**
	 * @see org.openuss.desktop.DesktopDao#desktopInfoToEntity(org.openuss.desktop.DesktopInfo)
	 */
	public Desktop desktopInfoToEntity(DesktopInfo desktopInfo) {
		Desktop entity = this.loadDesktopFromDesktopInfo(desktopInfo);
		this.desktopInfoToEntity(desktopInfo, entity, true);
		return entity;
	}

	/**
	 * @see org.openuss.desktop.DesktopDao#desktopInfoToEntity(org.openuss.desktop.DesktopInfo,
	 *      org.openuss.desktop.Desktop)
	 */
	@SuppressWarnings( { "unchecked" })
	public void desktopInfoToEntity(DesktopInfo sourceVO, Desktop targetEntity,	boolean copyIfNull) {
		super.desktopInfoToEntity(sourceVO, targetEntity, copyIfNull);

		// User
		if (copyIfNull && (sourceVO.getUserInfo() != null)) {
			targetEntity.setUser(this.getUserDao().load(sourceVO.getUserInfo().getId()));
		}

		// Universities
		if (copyIfNull && (sourceVO.getUniversityInfos() != null)) {
			targetEntity.getUniversities().clear();
			Iterator iter1 = sourceVO.getUniversityInfos().iterator();
			while (iter1.hasNext()) {
				targetEntity.getUniversities().add(this.getUniversityDao().load(((UniversityInfo) iter1.next()).getId()));
			}
		}

		// Departments
		if (copyIfNull && (sourceVO.getDepartmentInfos() != null)) {
			targetEntity.getDepartments().clear();
			Iterator iter2 = sourceVO.getDepartmentInfos().iterator();
			while (iter2.hasNext()) {
				targetEntity.getDepartments().add(this.getDepartmentDao().load(((DepartmentInfo) iter2.next()).getId()));
			}
		}
		
		// Institutes
		if (copyIfNull && (sourceVO.getInstituteInfos() != null)) {
			targetEntity.getInstitutes().clear();
			Iterator iter2 = sourceVO.getInstituteInfos().iterator();
			while (iter2.hasNext()) {
				targetEntity.getInstitutes().add(this.getInstituteDao().load(((InstituteInfo) iter2.next()).getId()));
			}
		}

		// CourseTypes
		if (copyIfNull && (sourceVO.getCourseTypeInfos() != null)) {
			targetEntity.getCourseTypes().clear();
			Iterator iter2 = sourceVO.getCourseTypeInfos().iterator();
			while (iter2.hasNext()) {
				targetEntity.getCourseTypes().add(this.getCourseTypeDao().load(((CourseType) iter2.next()).getId()));
			}
		}
		
		// Courses
		if (copyIfNull && (sourceVO.getCourseInfos() != null)) {
			targetEntity.getCourses().clear();
			Iterator iter2 = sourceVO.getCourseInfos().iterator();
			while (iter2.hasNext()) {
				targetEntity.getCourses().add(this.getCourseDao().load(((Course) iter2.next()).getId()));
			}
		}

	}

	@Override
	protected boolean handleIsCourseBookmarked(final Long courseId, final Long userId) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean handleIsDepartmentBookmarked(Long departmentId, Long userId) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean handleIsInstituteBookmarked(final Long instituteId, final Long userId) throws Exception {
		// TODO everything
		final String hqlIsBookmarked = "SELECT t, v FROM DISCUSSION_TOPIC t, ViewState v WHERE t.id = v.id and t.domainIdentifier = :domainIdentifer and v.userId = :userId";
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(org.hibernate.Session session) throws HibernateException {
				Query query = session.createQuery(hqlIsBookmarked);
				query.setLong("instituteId",instituteId);
				query.setLong("userId",userId);
				return query.list();  
			}
		}, true);
		return true;		
		
	}

}