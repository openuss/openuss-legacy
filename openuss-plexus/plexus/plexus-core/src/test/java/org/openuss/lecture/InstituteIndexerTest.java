package org.openuss.lecture;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.openuss.TestUtility;
import org.openuss.search.DomainResult;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * Test case for the spring indexing and searching of institutes.
 * Tests:
 * 	-	Normal Indexing and Searching
 * 	- 	Updating of the index and new search
 * Creating and Deleting of the index is tested via the Callback methods.
 * 
 * @author Ingo Dueppe
 * @author Kai Stettner
 */
public class InstituteIndexerTest extends AbstractTransactionalDataSourceSpringContextTests {

	private static final Logger logger = Logger.getLogger(InstituteIndexerTest.class);
	
	private InstituteIndexer instituteIndexer;
	
	private InstituteDao instituteDao = new InstituteDaoMock();
	
	private LectureSearcher lectureSearcher;
	
	private Institute institute;
	
	private TestUtility testUtility;
						   	
	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();
		logger.debug("Method onSetUp: Started");
		
		instituteIndexer.setInstituteDao(instituteDao);
		
		institute = testUtility.createUniqueInstituteInDB();

		instituteDao.create(institute);
		
		instituteIndexer.setDomainObject(institute);
		
		instituteIndexer.create();
	}
	
	@Override
	protected void onTearDown() throws Exception {
		super.onTearDown();
		logger.debug("Method onTearDown: Started");
		instituteIndexer.delete();
	}

	
	
	public void testIndexingAndSearching() {
		logger.debug("Method testIndexingAndSearching: Started");
		
		List<DomainResult> results = lectureSearcher.search("A unique Insitute", false);
		DomainResult[] resultObjs = (DomainResult[]) results.toArray(new DomainResult[results.size()]);
		logger.debug("--- RESULTS ---> "+ArrayUtils.toString(resultObjs));
		logger.debug(" Result size is: "+results.size());
		assertNotNull(results);
		assertTrue(results.size() == 1);
		
	}
	
	public void testIndexerUpdatingAndSearching() {
		logger.debug("Method testIndexerUpdatingAndSearching: Started");
		// set description to new description "A pathetic institute"
		institute.setDescription("A pathetic institute");
		
		instituteIndexer.update();
		
		List<DomainResult> results = lectureSearcher.search("pathetic", false);
		DomainResult[] resultObjs = (DomainResult[]) results.toArray(new DomainResult[results.size()]);
		logger.debug("--- RESULTS ---> "+ArrayUtils.toString(resultObjs));
		logger.debug(" Result size is: "+results.size());
		assertNotNull(results);
		assertTrue(results.size() == 1);
		
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
	public InstituteIndexer getInstituteIndexer() {
		return instituteIndexer;
	}

	public void setInstituteIndexer(InstituteIndexer instituteIndexer) {
		this.instituteIndexer = instituteIndexer;
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
