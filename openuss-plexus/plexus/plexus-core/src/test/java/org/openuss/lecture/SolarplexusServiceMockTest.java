package org.openuss.lecture;

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
		
		assertTrue(allUniversities.size() == 5);		
	}
	
	public void testFindUniversitiesByEnabled(){
		logger.debug("Method testFindAllUniversitiesByEnabled: Started");

		List universitiesEnabled = universityMock.findUniversitiesByEnabled(true);
		List universitiesDisabled = universityMock.findUniversitiesByEnabled(false);
		
		assertTrue(universitiesEnabled.size() == 4);
		assertTrue(universitiesDisabled.size() == 1);
		
	}
	
	public void testFindDepartmentsByUniversityAndEnabled(){
		logger.debug("Method testFindDepartmentsByUniversityAndEnabled: Started");
		
		List departments = departmentMock.findDepartmentsByUniversityAndEnabled(new Long(100), true);
		
		assertTrue(departments.size() == 3);
	}
	
	public void testfindDepartment(){
		logger.debug("Method testfindDepartment: Started");
		
		DepartmentInfo departmentInfo = departmentMock.findDepartment(new Long(1101));
		
		assertTrue(departmentInfo.getName().equals("Fachbereich 3 (Jura)"));
	}

}
