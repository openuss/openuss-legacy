package org.openuss.web.docmanagement;  
import java.util.zip.*;
import java.io.*;

import org.apache.log4j.Logger;
import org.openuss.docmanagement.BigFile;

public class ZipMe {
	public static final Logger logger = Logger.getLogger(ZipMe.class);
	
	public static InputStream zipMe(BigFile[] files, String zipFileName){
		int read = 0;
		InputStream in;
		byte[] data = new byte[1024];
		ZipOutputStream out;
		try {
			out = new ZipOutputStream(new FileOutputStream(zipFileName));
			out.setMethod(ZipOutputStream.DEFLATED);
			for (int i=0; i < files.length; i++) {
				ZipEntry entry = new ZipEntry(files[i].getName());
				in = files[i].getFile();			
				out.putNextEntry(entry);
				while((read = in.read(data, 0, 1024)) != -1)
					out.write(data, 0, read);
				out.closeEntry(); 
				in.close();
			}
			out.close();
			
			return new FileInputStream(zipFileName);
		} catch (FileNotFoundException e) {
			logger.error("FileNotFoundException: ",e);
		} catch (IOException e) {
			logger.error("IOException: ",e);
		}
		return null;	
	}	
}

