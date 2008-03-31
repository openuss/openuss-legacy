package org.openuss.framework.utilities;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.TestCase;

public class ImageUtilsTest extends TestCase {

	public void testImages() {
		InputStream image1 = getResourceAsStream("images/test1.jpg");
		BufferedImage bufferedImage = ImageUtils.resizeImage(image1, ImageUtils.IMAGE_JPEG, 60, 60);

		assertEquals(45, bufferedImage.getHeight());
		assertEquals(60, bufferedImage.getWidth());

		InputStream image2 = getResourceAsStream("images/test2.gif");
		BufferedImage bufferedImage2 = ImageUtils.resizeImage(image2, ImageUtils.IMAGE_JPEG, 60, 60);

		assertEquals(8, bufferedImage2.getHeight());
		assertEquals(60, bufferedImage2.getWidth());
	}

	public void testImageInputStream() throws IOException {
		InputStream image = getResourceAsStream("images/test1.jpg");
		byte[] bytes = ImageUtils.resizeImageToByteArray(image, ImageUtils.IMAGE_UNKNOWN, 60, 60);
		assertNotNull(bytes);
		assertEquals(1375, bytes.length );

		InputStream image2 = getResourceAsStream("images/test2.gif");
		byte[] bytes2 = ImageUtils.resizeImageToByteArray(image2, ImageUtils.IMAGE_UNKNOWN, 60, 60);
		assertNotNull(bytes2);
		assertEquals(767, bytes2.length );
	}

	private InputStream getResourceAsStream(String name) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
	}

}
