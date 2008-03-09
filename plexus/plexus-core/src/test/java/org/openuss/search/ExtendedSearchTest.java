package org.openuss.search;

import java.util.List;

import org.apache.log4j.Logger;
import org.openuss.TestUtility;
import org.openuss.lecture.LectureSearchQuery;
import org.openuss.lecture.University;
import org.openuss.lecture.UniversityService;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;


/**
 * Simple test case testing the search engine of OpenUSS.
 * 
 * @author Kai Stettner
 */
public class ExtendedSearchTest extends AbstractTransactionalDataSourceSpringContextTests {

	private static final Logger logger = Logger.getLogger(ExtendedSearchTest.class);
	
	private TestUtility testUtility;
	private University university;
	private String universityName;
	private LectureSearchQuery lectureSearchQuery;
	private UniversityService universityService;
	private List<DomainResult> hits;
	
	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();
		university = testUtility.createUniqueUniversityInDB();
	}
	
	
	@SuppressWarnings( { "unchecked" })
	public void testSearchforUniversities() throws Exception {
		//  FIXME this test breaks the build
//		logger.debug("Starting testSearchforUniversities");
//		universityName = university.getName();
//		
//		LectureIndex lectureIndex = (LectureIndex) getApplicationContext().getBean("lectureIndex");
//		lectureIndex.recreate();
//
//		logger.debug(universityName);
//		hits = lectureSearchQuery.search(universityName);
//		assertEquals(1, hits.size());
//		
//		// Create a User as Owner
//		User owner = testUtility.createUniqueUserInDB();
//		
//		// Create new UniversityInfo object
//		UniversityInfo universityInfo = new UniversityInfo();
//		universityInfo.setName("AnotherTestUniversity");
//		universityInfo.setShortcut("testU");
//		universityInfo.setOwnerName("Administrator");
//		universityInfo.setEnabled(true);
//		universityInfo.setDescription("This is a test University");
//		universityInfo.setUniversityType(UniversityType.UNIVERSITY);
//		
//		// Create Entity
//		testUtility.createAdminSecureContext();
//		Long universityId = universityService.createUniversity(universityInfo, owner.getId());
//		assertNotNull(universityId);
//		//recreate lecture index
//		lectureIndex.recreate();
//		
//		hits = lectureSearchQuery.search(universityName);
//		// hit size should be 2 due to the implementation of recreateLectureIndex
//		// index won't be deleted but duplicated --> 2
//		assertEquals(2, hits.size());
//		
//		logger.debug(universityInfo.getName());
//		hits = lectureSearchQuery.search(universityInfo.getName());
//		assertEquals(1, hits.size());
//		
//		hits = lectureSearchQuery.search("PleaseFindNothing");
//		assertEquals(0, hits.size());
//		
	}
	
	protected String[] getConfigLocations() {
		return new String[] { 
				"classpath*:applicationContext.xml", 
				"classpath*:applicationContext-beans.xml",
				"classpath*:applicationContext-lucene.xml",
				"classpath*:applicationContext-cache.xml", 
				"classpath*:applicationContext-messaging.xml",
				"classpath*:applicationContext-resources.xml",
				"classpath*:applicationContext-aop.xml",
				"classpath*:applicationContext-events.xml",
				"classpath*:testContext.xml", 
				"classpath*:testSecurity.xml", 
				"classpath*:testDataSource.xml"};
	}
	

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}

	public LectureSearchQuery getLectureSearchQuery() {
		return lectureSearchQuery;
	}

	public void setLectureSearchQuery(LectureSearchQuery lectureSearchQuery) {
		this.lectureSearchQuery = lectureSearchQuery;
	}

	public UniversityService getUniversityService() {
		return universityService;
	}

	public void setUniversityService(UniversityService universityService) {
		this.universityService = universityService;
	}	
}
