// OpenUSS - Open Source University Support System
/**
* This is only generated once! It will never be overwritten.
* You can (and have to!) safely modify it by hand.
*/
package org.openuss.seminarpool;

import java.sql.Timestamp;
import java.util.Date;


import org.openuss.security.Membership;


/**
* JUnit Test for Spring Hibernate SeminarpoolDao class.
* @see org.openuss.seminarpool.SeminarpoolDao
*/
public class SeminarpoolDaoTest extends SeminarpoolDaoTestBase {
	
	
	public void testSeminarpoolDaoCreate() {
		Seminarpool seminarpool = Seminarpool.Factory.newInstance();
		seminarpool.setMaxSeminarAllocations(5);
		seminarpool.setPriorities(5);
		seminarpool.setRegistrationStartTime(new Timestamp(12345L));
		seminarpool.setRegistrationEndTime(new Timestamp(12345L));
		seminarpool.setName(" ");
		seminarpool.setShortcut(" ");
		seminarpool.setDescription(" ");
		seminarpool.setSeminarpoolStatus(SeminarpoolStatus.CONFIRMEDPHASE);
		Membership membership = Membership.Factory.newInstance();
		seminarpool.setMembership(membership);
		assertNull(seminarpool.getId());
		seminarpoolDao.create(seminarpool);
		assertNotNull(seminarpool.getId());
	}
}