package org.openuss.lecture;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.openuss.TestUtility;
import org.openuss.search.DomainResult;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

public class DepartmentIndexerTest extends AbstractTransactionalDataSourceSpringContextTests {
	
private static final Logger logger = Logger.getLogger(DepartmentIndexerTest.class);
	
	private DepartmentIndexer departmentIndexer;
	
	private DepartmentDao departmentDao = new DepartmentDaoMock();
	
	private LectureSearcher lectureSearcher;
	
	private Department department;
	
	private TestUtility testUtility;
						   	
	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();
		logger.debug("Method onSetUp: Started");
		departmentIndexer.setDepartmentDao(departmentDao);
		department = testUtility.createUniqueDepartmentInDB();
		department.setDescription(RandomStringUtils.randomAlphabetic(30));
		departmentDao.create(department);
		departmentIndexer.setDomainObject(department);
		departmentIndexer.create();
	}
	
	@Override
	protected void onTearDown() throws Exception {
		super.onTearDown();
		logger.debug("Method onTearDown: Started");
		departmentIndexer.delete();
	}

	public void testIndexingAndSearching() {
		logger.debug("Method testIndexingAndSearching: Started");
		
		List<DomainResult> results = lectureSearcher.search(department.getDescription());
		DomainResult[] resultObjs = (DomainResult[]) results.toArray(new DomainResult[results.size()]);
		logger.debug("--- RESULTS ---> "+ArrayUtils.toString(resultObjs));
		logger.debug(" Result size is: "+results.size());
		assertNotNull(results);
		assertEquals(1, results.size());
	}
	
	public void testIndexerUpdatingAndSearching() {
		logger.debug("Method testIndexerUpdatingAndSearching: Started");
		// set description to new description "A pathetic department"
		
		String description = RandomStringUtils.randomAlphabetic(30);
		department.setDescription("test "+description+" test");
		departmentIndexer.update();
		
		List<DomainResult> results = lectureSearcher.search(description);
		DomainResult[] resultObjs = (DomainResult[]) results.toArray(new DomainResult[results.size()]);
		logger.debug("--- RESULTS ---> "+ArrayUtils.toString(resultObjs));
		logger.debug(" Result size is: "+results.size());
		assertNotNull(results);
		assertEquals(1, results.size());
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
	public DepartmentIndexer getDepartmentIndexer() {
		return departmentIndexer;
	}

	public void setDepartmentIndexer(DepartmentIndexer departmentIndexer) {
		this.departmentIndexer = departmentIndexer;
	}

	public LectureSearcher getLectureSearcher() {
		return lectureSearcher;
	}

	public void setLectureSearcher(LectureSearcher lectureSearcher) {
		this.lectureSearcher = lectureSearcher;
	}
	
	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}

}
