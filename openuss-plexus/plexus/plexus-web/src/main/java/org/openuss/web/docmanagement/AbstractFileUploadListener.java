package org.openuss.web.docmanagement;

import java.io.IOException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.openuss.docmanagement.BigFile;
import org.openuss.docmanagement.BigFileImpl;

public abstract class AbstractFileUploadListener extends AbstractChangeActionListener{
	public static final Logger uploadLogger = Logger.getLogger(AbstractFileUploadListener.class); 
	
	public BigFile uploadedFile2File(UploadedFile uf, String path){
		try {
			BigFile bf = new BigFileImpl(
					null, 
					new Timestamp(System.currentTimeMillis()),
					uf.getSize(),
					null,
					uf.getContentType(),
					clearIeFileName(uf.getName()),
					path,
					null,
					1,
					0,
					uf.getInputStream(), "");
			return bf;
		} catch (IOException e) {
			uploadLogger.error("IOException: ", e);
		}
		return null;		
	}

	public  String clearIeFileName(String name){
		int ie = name.lastIndexOf("\\");
		if (!(ie==-1)){
			name = name.substring(ie+1);
		}
		return name;
	}
}