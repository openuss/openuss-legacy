package org.openuss.lecture;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.openuss.search.DomainResult;
import org.openuss.security.User;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public class InstituteIndexerTest extends AbstractDependencyInjectionSpringContextTests {

	private InstituteIndexer instituteIndexer;
	
	private InstituteDao instituteDao = new InstituteDaoMock();
	
	private LectureSearcher lectureSearcher;
							   	
	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();
		instituteIndexer.setInstituteDao(instituteDao);
	}

	public void testIndexer() {
		Institute institute = new LectureBuilder().createInstitute(User.Factory.newInstance()).getInstitute();

		instituteDao.create(institute);
		
		instituteIndexer.setDomainObject(institute);
		instituteIndexer.create();
		
		institute.setName("neuer name");
		
		instituteIndexer.update();
		
		instituteIndexer.delete();
	}
	
	public void testIndexingAndSearching() {
		Institute institute = new LectureBuilder().createInstitute(User.Factory.newInstance()).getInstitute();
		institute.setOwnerName("test owner name grob");
		instituteDao.create(institute);
		
		instituteIndexer.setDomainObject(institute);
		instituteIndexer.create();
		
		List<DomainResult> results = lectureSearcher.search("grob");
		DomainResult[] resultObjs = (DomainResult[]) results.toArray(new DomainResult[results.size()]);
		logger.debug("--- RESULTS ---> "+ArrayUtils.toString(resultObjs));
		assertNotNull(results);
		assertTrue(results.size() >= 1);
		
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
