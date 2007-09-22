package org.openuss.security.acl;

import org.acegisecurity.AcegiSecurityException;
import org.acegisecurity.acl.basic.SimpleAclEntry;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.providers.dao.cache.EhCacheBasedUserCache;
import org.acegisecurity.providers.encoding.Md5PasswordEncoder;
import org.apache.log4j.Logger;
import org.openuss.TestUtility;
import org.openuss.lecture.Institute;
import org.openuss.lecture.LectureService;
import org.openuss.lecture.LectureServiceException;
import org.openuss.security.Group;
import org.openuss.security.GroupDao;
import org.openuss.security.Roles;
import org.openuss.security.User;
import org.openuss.security.UserDao;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * 
 * @author Ingo Dueppe
 */
public class AclPermissionIntegrationTest extends AbstractTransactionalDataSourceSpringContextTests {

	private static final String TEST_ROLE_ADMIN = "ROLE_ADMIN";
	private static final String TEST_ADMIN = "test_admin";

	private static final Logger logger = Logger.getLogger(AclPermissionIntegrationTest.class);

	private LectureService lectureService;
	private UserDao userDao;
	private GroupDao groupDao;

	private PermissionDao permissionDao;
	private ObjectIdentityDao objectIdentityDao;

	private Institute institute;
	
	private Group roleUser;
	private Group groupInstitute;
	
	private EhCacheBasedUserCache cache ;
	
	private TestUtility testUtility;

	private long instituteTestID;

	@Override
	protected void onSetUpInTransaction() throws Exception {
		instituteTestID = testUtility.unique();
		cache.getCache().flush();
		createSecureContext(TEST_ADMIN, TEST_ROLE_ADMIN);
		institute = Institute.Factory.newInstance();
		institute.setId(instituteTestID);
	}

	public void testAclAccessDeniedMethodInvocation() {
		try {
			lectureService.getInstitute(institute.getId());
			fail();
		} catch (AcegiSecurityException ase) {
			logger.error(ase);
		} catch (LectureServiceException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}

	public void testAclAccessGrantedToUserCheckedMethodInvocation() {
		ObjectIdentity oi = ObjectIdentity.Factory.newInstance();
		oi.setId(instituteTestID);

		Permission permission = Permission.Factory.newInstance();
		permission.setAclObjectIdentity(oi);
		permission.setMask(SimpleAclEntry.READ | SimpleAclEntry.ADMINISTRATION);
		permission.setRecipient(roleUser);

		objectIdentityDao.create(oi);

		try {
			lectureService.getInstitute(institute.getId());
			fail();
		} catch (AcegiSecurityException ase) {
			logger.info("access denied");
		} catch (LectureServiceException e) {
			fail(e.getMessage());
		}

		oi.addPermission(permission);

		permissionDao.create(permission);

		try {
			lectureService.getInstitute(institute.getId());
			logger.info("access granted");
		} catch (AcegiSecurityException e) {
			logger.error(e);
			fail(e.getMessage());
		} catch (LectureServiceException e) {
			logger.info("access granted");
		}
	}

	public void testAclHirarchyAccessGrantedToUserCheckedMethodInvocation() {
		ObjectIdentity oi = ObjectIdentity.Factory.newInstance();
		oi.setId(instituteTestID);

		Permission permission = Permission.Factory.newInstance();
		permission.setAclObjectIdentity(oi);
		permission.setMask(SimpleAclEntry.READ | SimpleAclEntry.ADMINISTRATION);
		permission.setRecipient(roleUser);

		objectIdentityDao.create(oi);
		oi.addPermission(permission);

		long oid = testUtility.unique();
		
		ObjectIdentity oi2 = ObjectIdentity.Factory.newInstance();
		oi2.setId(oid);
		oi2.setParent(oi);
		
		objectIdentityDao.create(oi2);
		permissionDao.create(permission);

		Institute institute2 = Institute.Factory.newInstance();
		institute2.setId(oid);

		try {
			lectureService.getInstitute(institute.getId());
			logger.info("access granted");
		} catch (AcegiSecurityException e) {
			logger.error(e);
			fail(e.getMessage());
		} catch (LectureServiceException e) {
			logger.info("access granted");
		}
	}
	
	public void testAclHirarchyAccessGrantedToRoleCheckedMethodInvocation() {
		ObjectIdentity oi = ObjectIdentity.Factory.newInstance();
		oi.setId(instituteTestID);

		Permission permission = Permission.Factory.newInstance();
		permission.setAclObjectIdentity(oi);
		permission.setMask(SimpleAclEntry.READ | SimpleAclEntry.ADMINISTRATION);
		permission.setRecipient(groupInstitute);

		objectIdentityDao.create(oi);
		oi.addPermission(permission);

		long oid = testUtility.unique();
		ObjectIdentity oi2 = ObjectIdentity.Factory.newInstance();
		oi2.setId(oid);
		oi2.setParent(oi);
		objectIdentityDao.create(oi2);
		permissionDao.create(permission);

		Institute institute2 = Institute.Factory.newInstance();
		institute2.setId(oid);

		try {
			lectureService.getInstitute(institute.getId());
			logger.info("access granted");
		} catch (Exception e) {
			logger.error(e);
			fail(e.getMessage());
		}
	}
	
	private void createSecureContext(String username, String rolename) {
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String password = encoder.encodePassword("password", null);

		User user = User.Factory.newInstance();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail("email");
		user.setEnabled(true);
		
		roleUser = groupDao.load(Roles.USER_ID);
		user.addGroup(roleUser);
		roleUser.addMember(user);
		userDao.create(user);
		groupDao.create(roleUser);

		assertNotNull(user.getId());
		assertNotNull(roleUser.getId());
		
		
		groupInstitute = Group.Factory.newInstance();
		groupInstitute.setName("GROUP_INSTITUTE");
		groupInstitute.setLabel("Group label of the Authory");
		groupInstitute.addMember(user);
		user.addGroup(groupInstitute);
		groupDao.create(groupInstitute);
		userDao.update(user);

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,
				"password");
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	@Override
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

	public LectureService getLectureService() {
		return lectureService;
	}

	public void setLectureService(LectureService lectureService) {
		this.lectureService = lectureService;
	}

	public GroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public ObjectIdentityDao getObjectIdentityDao() {
		return objectIdentityDao;
	}

	public void setObjectIdentityDao(ObjectIdentityDao objectIdentityDao) {
		this.objectIdentityDao = objectIdentityDao;
	}

	public PermissionDao getPermissionDao() {
		return permissionDao;
	}

	public void setPermissionDao(PermissionDao permissionDao) {
		this.permissionDao = permissionDao;
	}

	public EhCacheBasedUserCache getCache() {
		return cache;
	}

	public void setCache(EhCacheBasedUserCache cache) {
		this.cache = cache;
	}

	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
}
