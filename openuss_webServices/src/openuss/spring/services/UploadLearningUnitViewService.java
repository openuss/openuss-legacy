package openuss.spring.services;

import java.io.IOException;

import javax.activation.DataHandler;

/**
 * Internal service for uploading learning unit view fslv files.
 * @author Carsten Fiedler
 */
public interface UploadLearningUnitViewService {
	public boolean uploadLearningUnitView(String username, String password, String enrollmentId, String learningUnitId,
    		String learningUnitViewId, DataHandler handler) throws IOException;
}
