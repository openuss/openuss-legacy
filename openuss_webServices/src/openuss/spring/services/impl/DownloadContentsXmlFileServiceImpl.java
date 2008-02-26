package openuss.spring.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import openuss.spring.services.DownloadContentsXmlFileService;

public class DownloadContentsXmlFileServiceImpl implements DownloadContentsXmlFileService {

	/**
     * Invokes internal service for downloading contents.xml 
     * for synchronizing learning unit views in Freestyle Learning.
     * @param <code>String</code> username
     * @param <code>String</code> password
     * @param <code>String</code> enrollmentId
     * @param <code>String</code> learningUnitId
     * @param <code>String</code> learningUnitViewId
     * @param <code>DataHandler</code> handler for sending web service with attachment
     */
	public DataHandler downloadContentsXmlFile(String username, String password, 
			String enrollmentId, String learningUnitId,	String learningUnitViewId) throws IOException {
		// test outputs
		System.out.println("------------------------");
		System.out.println("ContentsXmlFileDownload:");
		System.out.println("------------------------");
		System.out.println("username: " + username);
		System.out.println("password: " + password);
		System.out.println("enrollmentId: " + enrollmentId);
		System.out.println("learningUnitId" + learningUnitId);
		System.out.println("learningUnitViewId: " + learningUnitViewId);
		System.out.println("------------------------");
		// get contents.xml for view
		checkForViewDirectory(enrollmentId, learningUnitId, learningUnitViewId);
		String pathToFslvFile = "openUss_samples" 
			+ System.getProperty("file.separator") + enrollmentId 
			+ System.getProperty("file.separator") + learningUnitId 
			+ System.getProperty("file.separator") + learningUnitViewId 
			+ System.getProperty("file.separator") + learningUnitViewId + ".fslv";
		String pathToContentsXmlFile = getContentsXmlFileFromFslvFile(pathToFslvFile, enrollmentId, learningUnitId, learningUnitViewId);
		if(pathToContentsXmlFile==null) {
			 pathToContentsXmlFile = createEmptyContentsXmlFile(enrollmentId, learningUnitId, learningUnitViewId);
		}
		// create data handler for sending contents.xml to client
		DataHandler dhSource = new DataHandler(new FileDataSource(pathToContentsXmlFile));
		// test output
		System.out.println("DownloadContentsXmlFileServiceImpl, returning web service with: " + pathToContentsXmlFile);
		System.out.println("------------------------");
		return dhSource;
	}
	
    private String getContentsXmlFileFromFslvFile(String pathToFslvFile, String enrollmentId,String learningUnitId, String learningUnitViewId) {
    	String pathToContentsXmlFile = null;
    	File fslvFile = new File(pathToFslvFile);
    	if(fslvFile.exists()) {
	    	try {
	    		final File inputFile = fslvFile;
		        if (inputFile!=null) {
		            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(inputFile));
		            ZipEntry entry = zipInputStream.getNextEntry();
		            while (entry!=null) {
		            	if(entry.getName().equals("contents.xml")) {
		            		FileOutputStream out = new FileOutputStream("openUss_samples" 
		            				+ System.getProperty("file.separator") + enrollmentId  
		            				+ System.getProperty("file.separator") + learningUnitId 
		            				+ System.getProperty("file.separator") + learningUnitViewId 
		            				+ System.getProperty("file.separator") + "contents.xml");
		                    byte[] buf = new byte[4096];
			                int len;
			                while ((len = zipInputStream.read(buf)) > 0) {
			                	out.write(buf, 0, len);
			                }
			                out.close();
			                pathToContentsXmlFile = "openUss_samples" 
			                	+ System.getProperty("file.separator") + enrollmentId 
			                	+ System.getProperty("file.separator") + learningUnitId 
		            			+ System.getProperty("file.separator") + learningUnitViewId 
		            			+ System.getProperty("file.separator") + entry.getName();
			                break;
	                   	} else {
	                   		zipInputStream.closeEntry();
			                entry = zipInputStream.getNextEntry();
		                }
		            }
		            zipInputStream.close();
		        }
	    	} catch(Exception e) { e.printStackTrace(); }
    	}
    	return pathToContentsXmlFile;
    }
    
    private String createEmptyContentsXmlFile(String enrollmentId, String learningUnitId, String learningUnitViewId) {
    	String pathToContentsXmlFile = null;
		try {
			File file = new File(
						"openUss_samples"
						+ System.getProperty("file.separator") + enrollmentId 
						+ System.getProperty("file.separator") + learningUnitId 
						+ System.getProperty("file.separator") + learningUnitViewId
						+ System.getProperty("file.separator") + "contents_dummy.xml");
			String text = "dummy";
			FileOutputStream outputStream = new FileOutputStream(file);
			for (int i=0; i < text.length(); i++){
				outputStream.write((byte)text.charAt(i));
			}
			outputStream.close();
			pathToContentsXmlFile = "openUss_samples" 
            	+ System.getProperty("file.separator") + enrollmentId 
            	+ System.getProperty("file.separator") + learningUnitId 
    			+ System.getProperty("file.separator") + learningUnitViewId
    			+ System.getProperty("file.separator") + "contents_dummy.xml";
		} catch(Exception e) {
			e.printStackTrace();
		}
		return pathToContentsXmlFile;
    }
    
	private void checkForViewDirectory(String enrollmentId, String learningUnitId, String learningUnitViewId) {
		
		System.out.println("checkForViewDirectory: " + learningUnitViewId);
		
    	if (!new File("openUss_samples" + System.getProperty("file.separator") + enrollmentId).exists()) {
    		System.out.println("Creating: " + "openUss_samples" + System.getProperty("file.separator") + enrollmentId);
    		new File("openUss_samples" + System.getProperty("file.separator") + enrollmentId).mkdir();
    	}
    	if (!new File("openUss_samples" 
    			+ System.getProperty("file.separator") + enrollmentId
    			+ System.getProperty("file.separator") + learningUnitId).exists()) {
    		System.out.println("Creating: " + "openUss_samples" 
    			+ System.getProperty("file.separator") + enrollmentId
    			+ System.getProperty("file.separator") + learningUnitId);
    		new File("openUss_samples" 
    			+ System.getProperty("file.separator") + enrollmentId
        		+ System.getProperty("file.separator") + learningUnitId).mkdir();
    	}
    	if (!new File("openUss_samples" 
    			+ System.getProperty("file.separator") + enrollmentId
        		+ System.getProperty("file.separator") + learningUnitId
        		+ System.getProperty("file.separator") + learningUnitViewId).exists()) {
    		System.out.println("Creating: " + "openUss_samples" 
        		+ System.getProperty("file.separator") + enrollmentId
        		+ System.getProperty("file.separator") + learningUnitId
        		+ System.getProperty("file.separator") + learningUnitViewId);
    		new File("openUss_samples" 
 				+ System.getProperty("file.separator") + enrollmentId
   				+ System.getProperty("file.separator") + learningUnitId
   				+ System.getProperty("file.separator") + learningUnitViewId).mkdir();
    	}
	}
}
