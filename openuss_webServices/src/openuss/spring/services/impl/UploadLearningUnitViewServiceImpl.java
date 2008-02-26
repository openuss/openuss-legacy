package openuss.spring.services.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.activation.DataHandler;

import openuss.spring.services.UploadLearningUnitViewService;

/**
 * Internal service for uploading learning unit view fslv files.
 * @author Carsten Fiedler
 */
public class UploadLearningUnitViewServiceImpl implements UploadLearningUnitViewService {
	
    /**
     * Uploads learning unit view file (fslv-file).
     * @param <code>String</code> username
     * @param <code>String</code> password
     * @param <code>String</code> enrollmentId
     * @param <code>String</code> learningUnitId
     * @param <code>String</code> learningUnitViewId
     * @param <code>DataHandler</code> handler
     * @throws IOException
     */
	public boolean uploadLearningUnitView(String username, String password, String enrollmentId, String learningUnitId,
    		String learningUnitViewId, DataHandler handler) throws IOException {
        boolean uploadFinishedSuccessfull = false;
    	// test outputs
    	System.out.println("-----------------------");
    	System.out.println("LearningUnitViewUpload:");
    	System.out.println("-----------------------");
    	System.out.println("username: " + username);
    	System.out.println("password: " + password);
    	System.out.println("enrollmentId: " + enrollmentId);
    	System.out.println("learningUnitId: " + learningUnitId);
    	System.out.println("learningUnitViewId: " + learningUnitViewId);
    	// set upload directory
    	// check if upload directory exists
    	checkForViewDirectory(enrollmentId, learningUnitId, learningUnitViewId);
    	String uploadDir = "openUss_samples" 
        	+ System.getProperty("file.separator") + enrollmentId
        	+ System.getProperty("file.separator") + learningUnitId
        	+ System.getProperty("file.separator") + learningUnitViewId
        	+ System.getProperty("file.separator");
        String fileName = uploadDir + learningUnitViewId + ".fslv";
        if(new File(uploadDir + "contents_dummy.xml").exists()) { new File(uploadDir + "contents_dummy.xml").delete(); }
        if(new File(uploadDir + "contents.xml").exists()) { new File(uploadDir + "contents.xml").delete(); }
    	BufferedInputStream in = new BufferedInputStream(handler.getDataSource().getInputStream());
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fileName));
        byte[] data = new byte[1024*8];
        int size = 0;
        while((size=in.read(data))!=-1) {
            out.write(data, 0, size);
        }
        in.close();
        out.close();
        return uploadFinishedSuccessfull;
    }
	
	private void checkForViewDirectory(String enrollmentId, String learningUnitId, String learningUnitViewId) {
    	if (!new File("openUss_samples" + System.getProperty("file.separator") + enrollmentId).exists()) {
    		new File("openUss_samples" + System.getProperty("file.separator") + enrollmentId).mkdir();
    		if (!new File("openUss_samples" 
    				+ System.getProperty("file.separator") + enrollmentId
    				+ System.getProperty("file.separator") + learningUnitId).exists()) {
    			new File("openUss_samples" 
    					+ System.getProperty("file.separator") + enrollmentId
        				+ System.getProperty("file.separator") + learningUnitId).mkdir();
    			if (!new File("openUss_samples" 
    					+ System.getProperty("file.separator") + enrollmentId
        				+ System.getProperty("file.separator") + learningUnitId
        				+ System.getProperty("file.separator") + learningUnitViewId).exists()) {
    				new File("openUss_samples" 
        					+ System.getProperty("file.separator") + enrollmentId
            				+ System.getProperty("file.separator") + learningUnitId
            				+ System.getProperty("file.separator") + learningUnitViewId).mkdir();
    			}
    		}
    	}
	}
}
