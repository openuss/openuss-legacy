package org.openuss.security.acl;

import org.acegisecurity.AcegiSecurityException;
import org.acegisecurity.acl.basic.SimpleAclEntry;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.providers.dao.cache.EhCacheBasedUserCache;
import org.acegisecurity.providers.encoding.Md5PasswordEncoder;
import org.apache.log4j.Logger;
import org.openuss.lecture.Faculty;
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
	private static final String TEST_ADMIN = "admin";
	private static final long FACULTY_TEST_ID = 4711;

	private static final Logger logger = Logger.getLogger(AclPermissionIntegrationTest.class);

	private LectureService lectureService;
	private UserDao userDao;
	private GroupDao groupDao;

	private PermissionDao permissionDao;
	private ObjectIdentityDao objectIdentityDao;

	private Faculty faculty;
	
	private Group roleAdmin;
	private Group groupFaculty;
	
	private EhCacheBasedUserCache cache ;

	@Override
	protected void onSetUpInTransaction() throws Exception {
		cache.getCache().flush();
		createSecureContext(TEST_ADMIN, TEST_ROLE_ADMIN);
		faculty = Faculty.Factory.newInstance();
		faculty.setId(FACULTY_TEST_ID);
	}

	public void testAclAccessDeniedMethodInvocation() {
		try {
			lectureService.getFaculty(faculty.getId());
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
		oi.setId(FACULTY_TEST_ID);

		Permission permission = Permission.Factory.newInstance();
		permission.setAclObjectIdentity(oi);
		permission.setMask(SimpleAclEntry.READ | SimpleAclEntry.ADMINISTRATION);
		permission.setRecipient(roleAdmin);

		objectIdentityDao.create(oi);

		try {
			lectureService.getFaculty(faculty.getId());
			fail();
		} catch (AcegiSecurityException ase) {
			logger.info("access denied");
		} catch (LectureServiceException e) {
			fail(e.getMessage());
		}

		oi.addPermission(permission);

		permissionDao.create(permission);

		try {
			lectureService.getFaculty(faculty.getId());
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
		oi.setId(FACULTY_TEST_ID);

		Permission permission = Permission.Factory.newInstance();
		permission.setAclObjectIdentity(oi);
		permission.setMask(SimpleAclEntry.READ | SimpleAclEntry.ADMINISTRATION);
		permission.setRecipient(roleAdmin);

		objectIdentityDao.create(oi);
		oi.addPermission(permission);

		ObjectIdentity oi2 = ObjectIdentity.Factory.newInstance();
		oi2.setId(1234L);
		oi2.setParent(oi);
		
		objectIdentityDao.create(oi2);
		permissionDao.create(permission);

		Faculty faculty2 = Faculty.Factory.newInstance();
		faculty2.setId(1234L);

		try {
			lectureService.getFaculty(faculty.getId());
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
		oi.setId(FACULTY_TEST_ID);
//		oi.setObjectIdentityClass("org.openuss.lecture.Faculty");

		Permission permission = Permission.Factory.newInstance();
		permission.setAclObjectIdentity(oi);
		permission.setMask(SimpleAclEntry.READ | SimpleAclEntry.ADMINISTRATION);
		permission.setRecipient(groupFaculty);

		objectIdentityDao.create(oi);
		oi.addPermission(permission);

		ObjectIdentity oi2 = ObjectIdentity.Factory.newInstance();
		oi2.setId(1234L);
//		oi2.setObjectIdentityClass("org.openuss.lecture.Faculty");
		oi2.setParent(oi);
		objectIdentityDao.create(oi2);
		permissionDao.create(permission);

		Faculty faculty2 = Faculty.Factory.newInstance();
		faculty2.setId(1234L);

		try {
			lectureService.getFaculty(faculty.getId());
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
		
		roleAdmin = groupDao.load(Roles.ADMINISTRATOR);
		user.addGroup(roleAdmin);
		roleAdmin.addMember(user);
		userDao.create(user);
		groupDao.create(roleAdmin);

		assertNotNull(user.getId());
		assertNotNull(roleAdmin.getId());
		
		
		groupFaculty = Group.Factory.newInstance();
		groupFaculty.setName("GROUP_FACULTY");
		groupFaculty.setLabel("Group label of the Authory");
		groupFaculty.addMember(user);
		user.addGroup(groupFaculty);
		groupDao.create(groupFaculty);
		userDao.update(user);

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,
				"password");
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	@Override
	protected String[] getConfigLocations() {
		return new String[] {
			"classpath*:applicationContext-tests.xml",
			"classpath*:applicationContext.xml", 
			"classpath*:applicationContext-localDataSource.xml",
			"classpath*:applicationContext-beans.xml", 
			"classpath*:beanRefFactory", 
			"classpath*:testSecurity.xml",
			"classpath*:testDataSource.xml" };
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
}
