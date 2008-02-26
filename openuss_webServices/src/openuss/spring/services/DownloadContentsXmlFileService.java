package openuss.spring.services;

import java.io.IOException;
import javax.activation.DataHandler;

/**
 * Internal service for downloading contents.xml..
 * @author Carsten Fiedler
 */
public interface DownloadContentsXmlFileService {
	public DataHandler downloadContentsXmlFile(String username, String password, 
			String enrollmentId, String learningUnitId,	String learningUnitViewId) throws IOException;
}
