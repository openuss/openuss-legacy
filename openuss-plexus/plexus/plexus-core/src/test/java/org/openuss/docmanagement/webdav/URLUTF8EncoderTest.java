/**
 * 
 */
package org.openuss.docmanagement.webdav;

import junit.framework.TestCase;
import org.openuss.docmanagement.webdav.URLUTF8Encoder;

/**
 * @author tobikley
 *
 */
public class URLUTF8EncoderTest extends TestCase {

	/**
	 * @param arg0
	 */
	public URLUTF8EncoderTest(String arg0) {
		super(arg0);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link org.openuss.docmanagement.webdav.URLUTF8Encoder#encode(java.lang.String)}.
	 */
	public final void testDecodeEncode() {
		String testString = "ƒ÷‹‰ˆ¸ﬂ";
		assertTrue(URLUTF8Encoder.decode(URLUTF8Encoder.encode(testString)) != testString);
	}

}
