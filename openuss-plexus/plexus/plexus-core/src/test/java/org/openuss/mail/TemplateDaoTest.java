// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.mail;


/**
 * JUnit Test for Spring Hibernate TemplateDao class.
 * @see org.openuss.mail.TemplateDao
 */
public class TemplateDaoTest extends TemplateDaoTestBase {
	
	public void testTemplateDaoCreate() {
		Template template = new TemplateImpl();
		template.setTemplate(" ");
		assertNull(template.getId());
		templateDao.create(template);
		assertNotNull(template.getId());
	}
}