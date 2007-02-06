// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.news;



/**
 * JUnit Test for Spring Hibernate NewsCategoryDao class.
 * @see org.openuss.news.NewsCategoryDao
 */
public class NewsCategoryDaoTest extends NewsCategoryDaoTestBase {
	
	public void testCreate() {
		NewsCategory newsCategory = new NewsCategoryImpl();
		newsCategory.setName(" ");
		assertNull(newsCategory.getId());
		newsCategoryDao.create(newsCategory);
		assertNotNull(newsCategory.getId());
	}
	
	public void testLoadAndRemove() {
		NewsCategory newsCategory = NewsCategory.Factory.newInstance("Description");
		newsCategoryDao.create(newsCategory);
		
		assertNotNull(newsCategory.getId());

		Long id = newsCategory.getId();
		newsCategoryDao.remove(id);
		newsCategory = newsCategoryDao.load(id);
		assertNull(newsCategory);
	}
	
	public void testUpdate() {
		NewsCategory newsCategory = NewsCategory.Factory.newInstance("Description");
		newsCategoryDao.create(newsCategory);
		
		assertNotNull(newsCategory.getId());
		
		newsCategory.setName("New Description");
		newsCategoryDao.update(newsCategory);
		NewsCategory testCategory = newsCategoryDao.load(newsCategory.getId());
		
		assertEquals(testCategory, newsCategory);	
	}
	
}