package org.openuss.migration.from20to30;

import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

public class LegacyIdentifierDaoTest extends AbstractTransactionalDataSourceSpringContextTests {

	private LegacyIdentifierDao identifierDao;
	
	public void testInsertUserId() {
		int size = identifierDao.loadAllNewUserIds().size();
		assertNotNull(identifierDao.getDataSource());
		String legacyId = generateString();
		Long id = System.currentTimeMillis();
		
		identifierDao.insertUserId(legacyId, id);
		identifierDao.insertUserId(generateString(),id);
		identifierDao.insertUserId(generateString(),id+1);
		
		List<Long> newIds = identifierDao.loadAllNewUserIds();
		assertTrue(newIds.contains(id));
		assertTrue(newIds.contains(id+1));
		assertEquals(size+2, newIds.size());
				
		assertEquals(id, identifierDao.getUserId(legacyId));
		assertNull(identifierDao.getUserId("NOTAVAILABLE"));
	}
	
	public void testRenamed() {
		String id = generateString();
		identifierDao.insertRenamed(id, "username");
		
		identifierDao.insertRenamed(id, null);
		
		assertTrue(identifierDao.isRenamedName(id));
		assertFalse(identifierDao.isRenamedName(generateString()));
	}
	
	public void testConsolidation() {
		Long id = System.currentTimeMillis();
		String name = generateString();

		identifierDao.insertConsolidated(id, generateString());
		identifierDao.insertConsolidated(id, generateString());
		identifierDao.insertConsolidated(id, generateString());
		identifierDao.insertConsolidated(id, generateString());
		identifierDao.insertConsolidated(id, name);
		
		List<String> names = identifierDao.allConsolidatedUserNames(id);
		assertEquals(5, names.size());
		assertTrue(names.contains(name));

		
		List<String> names2 = identifierDao.allConsolidatedUserNames(id+1);
		assertEquals(0, names2.size());
		
	}

	private String generateString() {
		return RandomStringUtils.randomAlphanumeric(30);
	}
	
	protected String[] getConfigLocations() {
		setAutowireMode(AUTOWIRE_BY_NAME);
		
		return new String[] {
			"classpath*:applicationContext.xml",
//			"classpath*:applicationContext-activation.xml",
//			"classpath*:applicationContext-cache.xml",
			"classpath*:applicationContext-entities.xml",
			"classpath*:applicationContext-localDataSource.xml",
			"classpath*:applicationContext-legacy.xml",
//			"classpath*:applicationContext-adapters.xml",
			"classpath*:applicationContext-commands.xml",
			"classpath*:applicationContext-messaging.xml",
			"classpath*:applicationContext-newsletter.xml",
			"classpath*:applicationContext-events.xml",
//			"classpath*:applicationContext-lucene.xml",
			"classpath*:applicationContext-resources.xml",
			"classpath*:applicationContext-migration.xml",
			"classpath*:testDataSource.xml",
			"classpath*:migrationSecurity.xml"};
	}

	public LegacyIdentifierDao getIdentifierDao() {
		return identifierDao;
	}

	public void setIdentifierDao(LegacyIdentifierDao identifierDao) {
		this.identifierDao = identifierDao;
	}	
	

}
