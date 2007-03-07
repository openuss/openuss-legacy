// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.braincontest;


/**
 * JUnit Test for Spring Hibernate BrainContestDao class.
 * @see org.openuss.braincontest.BrainContestDao
 */
public class BrainContestDaoTest extends BrainContestDaoTestBase {
	
	public void testBrainContestDaoCreate() {
		BrainContest brainContest = new BrainContestImpl();
		brainContest.setReleaseDate(" ");
		brainContest.setDescription(" ");
		brainContest.setTitle(" ");
		assertNull(brainContest.getId());
		brainContestDao.create(brainContest);
		assertNotNull(brainContest.getId());
	}
}