package org.openuss.lecture;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.openuss.search.DomainResult;
import org.openuss.security.User;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 * Test case for the spring indexing and searching of institutes.
 * Tests:
 * 	-	Normal Indexing and Searching
 * 	- 	Updating of the index and new search
 * Creating and Deleting of the index is testes via the Callback methods.
 * 
 * @author Ingo Dueppe
 * @author Kai Stettner
 */
public class InstituteIndexerTest extends AbstractDependencyInjectionSpringContextTests {

	private static final Logger logger = Logger.getLogger(InstituteIndexerTest.class);
	
	private InstituteIndexer instituteIndexer;
	
	private InstituteDao instituteDao = new InstituteDaoMock();
	
	private LectureSearcher lectureSearcher;
	
	private Institute institute;
						   	
	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();
		logger.debug("Method onSetUp: Started");
		
		instituteIndexer.setInstituteDao(instituteDao);
		
		institute = new LectureBuilder().createInstitute(User.Factory.newInstance()).getInstitute();
		// set owner to common owner "Mustermann"
		institute.setOwnername("Mustermann");
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
		
		List<DomainResult> results = lectureSearcher.search("Mustermann");
		DomainResult[] resultObjs = (DomainResult[]) results.toArray(new DomainResult[results.size()]);
		logger.debug("--- RESULTS ---> "+ArrayUtils.toString(resultObjs));
		logger.debug(" Result size is: "+results.size());
		assertNotNull(results);
		assertTrue(results.size() == 1);
		
	}
	
	public void testIndexerUpdatingAndSearching() {
		logger.debug("Method testIndexerUpdatingAndSearching: Started");
		// set owner to new owner "Musterfrau"
		institute.setOwnername("Musterfrau");
		
		instituteIndexer.update();
		
		List<DomainResult> results = lectureSearcher.search("Musterfrau");
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

}
