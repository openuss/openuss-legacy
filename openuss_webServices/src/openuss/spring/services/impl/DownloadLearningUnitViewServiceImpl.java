package openuss.spring.services.impl;

import java.io.File;
import java.io.IOException;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import openuss.spring.services.DownloadLearningUnitViewService;

/**
 * Internal service for downloading learning unit view fslv files.
 * @author Carsten Fiedler
 */
public class DownloadLearningUnitViewServiceImpl implements DownloadLearningUnitViewService {
    
	/**
     * Invokes internal service for downloading learning unit view file (fslv-file).
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
		// test outputs
		System.out.println("-------------------------");
		System.out.println("LearningUnitViewDownload:");
		System.out.println("-------------------------");
		System.out.println("username: " + username);
		System.out.println("password: " + password);
		System.out.println("enrollmentId: " + enrollmentId);
		System.out.println("learningUnitId: " + learningUnitId);
		System.out.println("learningUnitViewId: " + learningUnitViewId);
		System.out.println("-------------------------");
		// build path for export
		String downLoadDirectory = "openUss_samples" 
			+ System.getProperty("file.separator") + enrollmentId 
			+ System.getProperty("file.separator") + learningUnitId 
			+ System.getProperty("file.separator") + learningUnitViewId 
			+ System.getProperty("file.separator");
		String pathToFslvFile = downLoadDirectory + learningUnitViewId + ".fslv";
		if(new File(downLoadDirectory + "contents.xml").exists()) { new File(downLoadDirectory + "contents.xml").delete(); }
		// create data handler for sending fslv file to client
		DataHandler dhSource = new DataHandler(new FileDataSource(pathToFslvFile));
		System.out.println("FreestyleLearningSynchronizeProxy, returning web service with: " + pathToFslvFile);
		System.out.println("-------------------------");
		return dhSource;
    }
}
