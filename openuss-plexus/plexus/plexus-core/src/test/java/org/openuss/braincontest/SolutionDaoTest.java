// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.braincontest;


/**
 * JUnit Test for Spring Hibernate SolutionDao class.
 * @see org.openuss.braincontest.SolutionDao
 */
public class SolutionDaoTest extends SolutionDaoTestBase {
	
	public void testSolutionDaoCreate() {
		Solution solution = new SolutionImpl();
		solution.setSolution(" ");
		assertNull(solution.getId());
		solutionDao.create(solution);
		assertNotNull(solution.getId());
	}
}