package org.openuss.web.upload;

import junit.framework.TestCase;

public class UploadListenerTest extends TestCase {

	public void testExtractFileName() {
		UploadListener ul = new UploadListener();
		assertEquals("filename.pdf",ul.extractFileName("d:\\folder\\filename.pdf"));
		assertEquals("filename.pdf",ul.extractFileName("/folder/filename.pdf"));
		assertEquals("filename.pdf",ul.extractFileName("//filename.pdf"));
		assertEquals("filename.pdf",ul.extractFileName("\\filename.pdf"));
		assertEquals("filename.pdf",ul.extractFileName("filename.pdf"));
		assertEquals("",ul.extractFileName("/"));
	}

}
