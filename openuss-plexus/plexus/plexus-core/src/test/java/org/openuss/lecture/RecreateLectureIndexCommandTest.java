package org.openuss.lecture;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 * Test case for recreating lecture index command
 * 
 * @author Ingo Dueppe
 */
public class RecreateLectureIndexCommandTest extends AbstractDependencyInjectionSpringContextTests {

	private LectureIndexImpl lectureIndex;
	
	
	public void testLectureIndex() throws Exception {
//		lectureIndex.recreate();
	}
	
	protected String[] getConfigLocations() {
		return new String[] { 
			"classpath*:applicationContext.xml", 
			"classpath*:applicationContext-beans.xml",
			"classpath*:applicationContext-tests.xml", 
			"classpath*:applicationContext-lucene.xml",
			"classpath*:applicationContext-cache.xml", 
			"classpath*:applicationContext-messaging.xml",
			"classpath*:applicationContext-resources.xml",
			"classpath*:beanRefFactory", 
			"classpath*:testSecurity.xml", 
			"classpath*:testDataSource.xml"};
	}

	public LectureIndexImpl getLectureIndex() {
		return lectureIndex;
	}

	public void setLectureIndex(LectureIndexImpl lectureIndex) {
		this.lectureIndex = lectureIndex;
	}

	
}
