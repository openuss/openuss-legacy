package org.openuss.lecture;

import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

/**
 * Test case for the Department Service Mock
 * 
 * @author Malte Stockmann
 */
public class SolarplexusServiceMockTest extends TestCase {

	private static final Logger logger = Logger.getLogger(SolarplexusServiceMockTest.class);
	
	private UniversityServiceMock universityMock = new UniversityServiceMock();
	private DepartmentServiceMock departmentMock = new DepartmentServiceMock();
	private InstituteServiceMock instituteMock = new InstituteServiceMock();
	private CourseTypeServiceMock courseTypeService = new CourseTypeServiceMock();
							   	
	protected void setUp() throws Exception {
		super.setUp();
		logger.debug("Method setUp: Started");
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
		logger.debug("Method tearDown: Started");
	}

	public void testFindAllUniversities(){
		logger.debug("Method testFindAllUniversities: Started");

		List allUniversities = universityMock.findAllUniversities();
		
		assertEquals(5, allUniversities.size());
	}
	
	public void testFindUniversitiesByEnabled(){
		logger.debug("Method testFindAllUniversitiesByEnabled: Started");

		List universitiesEnabled = universityMock.findUniversitiesByEnabled(true);
		List universitiesDisabled = universityMock.findUniversitiesByEnabled(false);
		
		assertEquals(4, universitiesEnabled.size());
		assertEquals(1, universitiesDisabled.size());
		
	}
	
	public void testFindDepartmentsByUniversityAndEnabled(){
		logger.debug("Method testFindDepartmentsByUniversityAndEnabled: Started");
		
		List departments = departmentMock.findDepartmentsByUniversityAndEnabled(100L, true);
		
		assertEquals(3, departments.size());
	}
	
	public void testFindDepartment(){
		logger.debug("Method testFindDepartment: Started");
		
		DepartmentInfo departmentInfo = departmentMock.findDepartment(1101L);
		
		assertEquals("Fachbereich 3 (Jura)", departmentInfo.getName());
	}
	
	public void testFindInstitutesByDepartment(){
		logger.debug("Method testFindInstitutesByDepartment: Started");
				
		List institutes = instituteMock.findInstitutesByDepartmentAndEnabled(1102L, true);
		Iterator iter = institutes.iterator();
		InstituteInfo instituteInfo = (InstituteInfo) iter.next();
		
		assertEquals(2, institutes.size());
		assertEquals("Lehrstuhl 1", instituteInfo.getName());
	}
	
	public void testFindInstitutesByDepartmentAndEnabled(){
		logger.debug("Method testFindInstitutesByDepartmentAndEnabled: Started");
				
		List institutes = instituteMock.findInstitutesByDepartmentAndEnabled(1102L, false);

		assertEquals(0, institutes.size());
	}
	
	public void testFindCourseTypeByInstitute(){
		logger.debug("Method testFindCourseTypeByInstitute: Started");
		
		List courseTypes1 = courseTypeService.findCourseTypesByInstitute(11101L);
		List courseTypes2 = courseTypeService.findCourseTypesByInstitute(11102L);
		CourseTypeInfo courseTypeInfo;
		Iterator iter;
		
		iter = courseTypes1.iterator();
		courseTypeInfo = (CourseTypeInfo) iter.next();
		assertEquals("Einführung", courseTypeInfo.getName());
		
		iter = courseTypes2.iterator();
		courseTypeInfo = (CourseTypeInfo) iter.next();
		assertEquals("Grundlagen", courseTypeInfo.getName());		
	}

}
