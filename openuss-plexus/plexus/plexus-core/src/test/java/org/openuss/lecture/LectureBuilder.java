package org.openuss.lecture;

import org.openuss.TestUtility;
import org.openuss.security.User;

/**
 * Builds institute, periods, courseType, and course structures.
 *  
 * @author Ingo Dueppe
 */
public class LectureBuilder {
	
	private User owner;
	
	private TestUtility testUtility;
	
	private University university;
	
	private UniversityDao universityDao;
	
	private Department department;
	
	private DepartmentDao departmentDao;
	
	private Institute institute;
	
	private InstituteDao instituteDao;
	
	private static int code = 0;
	
	public LectureBuilder createUniversity() {
		university = testUtility.createUniqueUniversityInDB();
		return this;
	}
	
	public LectureBuilder createDepartment() {
		department = testUtility.createUniqueDepartmentInDB();
		return this;
	}
	
	public LectureBuilder createInstitute(User owner) {
		institute = Institute.Factory.newInstance();
		institute.setName(unique());
		institute.setOwnername("institute owner");
		institute.setShortcut(unique());
		institute.setEmail("email@email.com");
		institute.setAddress("Leonardo-Campus 3");
		institute.setPostcode("48149");
		institute.setCity("Münster");
		institute.setLocale("de");
		institute.setTheme("plexus");
		institute.setWebsite("www.openuss.org");
		institute.setOwner(owner);
		return this;
	}
	
	public LectureBuilder addCourseType() {
		CourseType courseType = CourseType.Factory.newInstance();
		courseType.setName(unique());
		courseType.setShortcut(unique());
		courseType.setDescription("description");
		institute.add(courseType);
		return this;
	}
	
	public LectureBuilder addPeriod() {
		Period period = Period.Factory.newInstance();
		period.setName("test-period");
		period.setDescription("description");
		university.getPeriods().add(period);
		period.setUniversity(university);
		return this;
	}
	
	public LectureBuilder addCourse() {
		return addCourse(0, 0);
	}
	
	public LectureBuilder addCourse(int indexCourseType, int indexPeriod ) {
		CourseType courseType = institute.getCourseTypes().get(indexCourseType);
		Period period = university.getPeriods().get(indexPeriod);
		addCourse(institute, courseType, period);
		return this;
	}
	
	public LectureBuilder persist() {
		if (institute.getId() == null) {
			instituteDao.create(institute);
		} else {
			instituteDao.update(institute);
		}
		return this;
	}
	
	public Course getCourse() {
		return getCourse(0);
	}
	
	public Course getCourse(int index) {
		return institute.getCourses().get(index);
	}
	
	public LectureBuilder remove() {
		if (institute.getId() != null)
			instituteDao.remove(institute);
		return this;
	}
	
	private String unique() {
		return ""+(System.currentTimeMillis()+code++);
	}
	
	public CourseType addCourseType(Institute institute) {
		CourseType courseType = LectureFactory.createCourseType();
		courseType.setInstitute(institute);
		institute.add(courseType);
		return courseType;
	}
	
	public Period addPeriod(University university) {
		Period period = LectureFactory.createPeriod();
		period.setUniversity(university);
		university.getPeriods().add(period);
		return period;
	}
	
	public Course addCourse(Institute institute, CourseType courseType, Period period) {
		Course course = LectureFactory.createCourse();
		courseType.add(course);
		course.setCourseType(courseType);
		course.setShortcut(unique());
		period.add(course);
		course.setPeriod(period);
		institute.add(course);
		course.setInstitute(institute);
		return course;
	}
	
	public User getOwner() {
		return owner;
	}
	
	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Institute getInstitute() {
		return institute;
	}

	public void setInstitute(Institute institute) {
		this.institute = institute;
	}

	public InstituteDao getInstituteDao() {
		return instituteDao;
	}

	public void setInstituteDao(InstituteDao instituteDao) {
		this.instituteDao = instituteDao;
	}

	public University getUniversity() {
		return university;
	}

	public void setUniversity(University university) {
		this.university = university;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public UniversityDao getUniversityDao() {
		return universityDao;
	}

	public void setUniversityDao(UniversityDao universityDao) {
		this.universityDao = universityDao;
	}

	public DepartmentDao getDepartmentDao() {
		return departmentDao;
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
}
