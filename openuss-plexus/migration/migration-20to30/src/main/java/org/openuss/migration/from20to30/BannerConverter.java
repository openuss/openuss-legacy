package org.openuss.migration.from20to30;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import org.openuss.utility.FileObjectWrapper;

public class BannerConverter {
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		FileInputStream fis = new FileInputStream("W:/openuss-plexus/documentation/src/styleguide/banner3.gif");
		byte[] data = new byte[fis.available()];

		int count = fis.read(data);
		if (count != data.length) {
			throw new Exception("Lost data...");
		}

		fis.close();
		
		FileObjectWrapper fow = new FileObjectWrapper(data);
		
		FileOutputStream fos = new FileOutputStream("W:/banner-fow.gif");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(fow);

		oos.close();
		fos.close();
		
	}

}
