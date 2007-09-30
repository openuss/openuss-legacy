// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import org.acegisecurity.AccessDeniedException;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.context.SecurityContextImpl;
import org.acegisecurity.providers.TestingAuthenticationToken;
import org.apache.log4j.Logger;
import org.openuss.TestUtility;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * JUnit Test for Spring Hibernate LectureService class.
 * 
 * @see org.openuss.lecture.LectureService
 * 
 * @author Ingo D�ppe
 */
public class LectureServiceTest extends AbstractTransactionalDataSourceSpringContextTests{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(LectureServiceTest.class);

	private LectureService lectureService;
	
	private TestUtility testUtility;

	public LectureService getLectureService() {
		return lectureService;
	}

	public void setLectureService(LectureService lectureService) {
		this.lectureService = lectureService;
	}

	public void testDeniedAccess() {
		logger.info("----> BEGIN denied access to createInstitute test");
		try {
			createSecureContext("ROLE_ANONYMOUS");
			Institute institute = Institute.Factory.newInstance();
			try {
				lectureService.persist(institute);
			} catch (LectureServiceException e) {
				fail(e.getMessage());
				logger.error(e);
			}
			fail("Should have thrown AccessDeniedException");
		} catch (AccessDeniedException expected) {
			assertNotNull(expected);
		}
		logger.info("----> END denied access to createInstitute test");
	}

	public void testCreateInstitute() {
		logger.info("----> BEGIN access to createInstitute test");
		createSecureContext("ROLE_ADMIN");
		Institute institute = testUtility.createPersistInstituteWithDefaultUser();
		try {
			lectureService.persist(institute);
		} catch (LectureServiceException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		destroySecureContext();
		logger.info("----> END access to createInstitute test");
	}

	private static void createSecureContext(String roleName) {
		TestingAuthenticationToken authentication = new TestingAuthenticationToken("principal", "credentials",	new GrantedAuthority[] { new GrantedAuthorityImpl(roleName) });
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private static void destroySecureContext() {
		SecurityContextHolder.setContext(new SecurityContextImpl());
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
				"classpath*:applicationContext-commands.xml",
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

}