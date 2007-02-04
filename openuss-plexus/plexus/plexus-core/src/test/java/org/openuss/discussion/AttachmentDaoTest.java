// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.discussion;


/**
 * JUnit Test for Spring Hibernate AttachmentDao class.
 * @see org.openuss.discussion.AttachmentDao
 */
public class AttachmentDaoTest extends AttachmentDaoTestBase {
	
	public void testAttachmentDaoCreate() {
		Attachment attachment = new AttachmentImpl();
		attachment.setFileName("name");
		attachment.setFileSize(20);
		attachment.setFileData("THIS IS THE FILE CONTENT".getBytes());
		assertNull(attachment.getId());
		attachmentDao.create(attachment);
		assertNotNull(attachment.getId());
	}
}