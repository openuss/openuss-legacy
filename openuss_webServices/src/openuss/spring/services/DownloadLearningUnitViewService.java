package openuss.spring.services;

import java.io.IOException;

import javax.activation.DataHandler;

/**
 * Internal service for downloading learning unit view fslv files.
 * @author Carsten Fiedler
 */
public interface DownloadLearningUnitViewService {
    public DataHandler downloadLearningUnitView(String username, String password, String enrollmentId, String learningUnitId,
    		String learningUnitViewId) throws IOException;
}
