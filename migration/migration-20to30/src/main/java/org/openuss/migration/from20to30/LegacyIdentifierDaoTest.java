package org.openuss.migration.from20to30;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public class LegacyIdentifierDaoTest extends AbstractDependencyInjectionSpringContextTests {

	private LegacyIdentifierDao identifierMap;
	
	public void testInsert() {
		assertNotNull(identifierMap.getDataSource());
		String legacyId = RandomStringUtils.randomAlphanumeric(30);
		Long id = System.currentTimeMillis();
		
		identifierMap.insert(legacyId, id);
		assertEquals(id, identifierMap.getId(legacyId));
		assertNull(identifierMap.getId("NOTAVAILABLE"));
	}
	
	protected String[] getConfigLocations() {
		return new String[] {
			"classpath*:applicationContext.xml",
//			"classpath*:applicationContext-activation.xml",
//			"classpath*:applicationContext-cache.xml",
			"classpath*:applicationContext-entities.xml",
			"classpath*:applicationContext-localDataSource.xml",
			"classpath*:applicationContext-legacy.xml",
//			"classpath*:applicationContext-adapters.xml",
//			"classpath*:applicationContext-commands.xml",
			"classpath*:applicationContext-messaging.xml",
			"classpath*:applicationContext-newsletter.xml",
//			"classpath*:applicationContext-lucene.xml",
			"classpath*:applicationContext-resources.xml",
//			"classpath*:applicationContext-aop.xml",
			"classpath*:applicationContext-migration.xml",
			"classpath*:migrationSecurity.xml"};
	}

	public LegacyIdentifierDao getIdentifierMap() {
		return identifierMap;
	}

	public void setIdentifierMap(LegacyIdentifierDao identifierMap) {
		this.identifierMap = identifierMap;
	}	
	

}
