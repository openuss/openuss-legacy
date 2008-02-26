package openuss.webServices;

import java.io.IOException;
import javax.activation.DataHandler;

import openuss.spring.services.AuthenticationService;
import openuss.spring.services.DownloadContentsXmlFileService;
import openuss.spring.services.DownloadLearningUnitViewService;
import openuss.spring.services.UploadLearningUnitViewService;

import org.springframework.remoting.jaxrpc.ServletEndpointSupport;


/**
 * FreestyleLearningSynchronizeProxy.
 * Class for handling web services from Freestyle Learning.
 * @author Carsten Fiedler
 */
public class FreestyleLearningSynchronizeProxy extends ServletEndpointSupport 
	implements AuthenticationService, DownloadContentsXmlFileService, DownloadLearningUnitViewService, UploadLearningUnitViewService {
	
	// internal openuss services
	private AuthenticationService authenticationService;
	private UploadLearningUnitViewService uploadLearningUnitViewService;
	private DownloadLearningUnitViewService downloadLearningUnitViewService;
	private DownloadContentsXmlFileService downloadContentsXmlFileService;
	
    public void onInit() {
    	System.out.println("FreestyleLearningSynchronizeProxy: onInit");
    	this.authenticationService = (AuthenticationService) getWebApplicationContext().getBean("AuthenticationService");
    	this.uploadLearningUnitViewService = (UploadLearningUnitViewService) getWebApplicationContext().getBean("UploadLearningUnitViewService");
    	this.downloadLearningUnitViewService = (DownloadLearningUnitViewService) getWebApplicationContext().getBean("DownloadLearningUnitViewService");
    	this.downloadContentsXmlFileService = (DownloadContentsXmlFileService) getWebApplicationContext().getBean("DownloadContentsXmlFileService");
    }
    
    /**
     * Invkoes internal service for user authentication.
     * @param <code>String</code> username
     * @param <code>String</code> password
     * @return <code>boolean</code> true if user exists
     */
    public boolean authenticateUser(String username, String password) {
    	System.out.println("FreestyleLearningSynchronizeProxy: Authenticating user: " + username);
        return authenticationService.authenticateUser(username, password);
    }
    
    /**
     * Invokes internal service for uploading a learning unit view file (fslv-file).
     * @param <code>String</code> username
     * @param <code>String</code> password
     * @param <code>String</code> enrollmentId
     * @param <code>String</code> learningUnitId
     * @param <code>String</code> learningUnitViewId
     * @param <code>DataHandler</code> handler
     * @return <code>boolean</code> true if upload was successfull
     * @throws IOException
     */
    public boolean uploadLearningUnitView(String username, String password, String enrollmentId, String learningUnitId,
    		String learningUnitViewId, DataHandler handler) throws IOException {
	    	System.out.println("FreestyleLearningSynchronizeProxy: Downloading contents.xml...");
	        return uploadLearningUnitViewService.uploadLearningUnitView(username, password, 
	        		enrollmentId, learningUnitId, learningUnitViewId, handler);
    }
    
    /**
     * nvokes internal service for downloading learning unit view file (fslv-file).
     * @param <code>String</code> username
     * @param <code>String</code> password
     * @param <code>String</code> enrollmentId
     * @param <code>String</code> learningUnitId
     * @param <code>String</code> learningUnitViewId
     * @param <code>DataHandler</code> handler
     * @throws IOException
     */
    public DataHandler downloadLearningUnitView(String username, String password, String enrollmentId, String learningUnitId,
    		String learningUnitViewId) throws IOException {
	    	System.out.println("FreestyleLearningSynchronizeProxy: Downloading Learning Unit View...");
	        return downloadLearningUnitViewService.downloadLearningUnitView(username, password, 
	        		enrollmentId, learningUnitId, learningUnitViewId);
    }
    
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
    public DataHandler downloadContentsXmlFile(String username, String password, String enrollmentId, String learningUnitId,
    		String learningUnitViewId) throws IOException {
    	System.out.println("FreestyleLearningSynchronizeProxy: Downloading contents.xml...");
        return downloadContentsXmlFileService.downloadContentsXmlFile(username, password, 
        		enrollmentId, learningUnitId, learningUnitViewId);
    }
}
