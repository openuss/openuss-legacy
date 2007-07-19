// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openuss.TestUtility;


/**
 * JUnit Test for Spring Hibernate MembershipDao class.
 * @see org.openuss.security.MembershipDao
 * @author Ron Haus
 */
public class MembershipDaoTest extends MembershipDaoTestBase {
	
	private TestUtility testUtility;
	
	public TestUtility getTestUtility() {
		return testUtility;
	}

	public void setTestUtility(TestUtility testUtility) {
		this.testUtility = testUtility;
	}
	
	public void testMembershipDaoCreate() {
		
		List<User> users = new ArrayList<User>(5);
		for(int i=0;i<5;i++) {
			users.add(testUtility.createUserInDB());
		}
		
		Membership membership = Membership.Factory.newInstance();
		membership.getMembers().add(users.get(0));
		membership.getAspirants().add(users.get(1));
		membership.getAspirants().add(users.get(2));
		membership.getMembers().add(users.get(3));
		membership.getMembers().add(users.get(4));
		
		assertNull(membership.getId());
		membershipDao.create(membership);
		assertNotNull(membership.getId());
		
		Membership membership2 = Membership.Factory.newInstance();
		membership.getMembers().add(users.get(0));
		membership2.getAspirants().add(users.get(1));
		membership2.getAspirants().add(users.get(2));
		membership2.getMembers().add(users.get(3));
		membership2.getMembers().add(users.get(4));
		
		assertNull(membership2.getId());
		membershipDao.create(membership2);
		assertNotNull(membership2.getId());
		
		//Synchronize with Database
		SessionFactory sessionFactory = (SessionFactory) applicationContext.getBean("sessionFactory");
		Session session = sessionFactory.getCurrentSession();
		session.flush();
		
	}
}