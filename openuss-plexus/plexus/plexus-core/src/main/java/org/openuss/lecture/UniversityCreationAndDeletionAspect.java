package org.openuss.lecture;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.desktop.DesktopException;
import org.openuss.desktop.DesktopInfo;
import org.openuss.desktop.DesktopService2;

/**
 * Aspect for University Creation and Deletion.
 * @author Florian Dondorf
 */
public class UniversityCreationAndDeletionAspect {


	private static final Logger logger = Logger.getLogger(UniversityCreationAndDeletionAspect.class);
	private DesktopService2 desktopService;
	
	/**
	 * Create bookmark of given university for the creating user.
	 * @param universityInfo, userId
	 */
	public void bookmarkUniversity (UniversityInfo universityInfo,Long userId) {
		logger.debug("----------> BEGIN method bookmarkUniversity <----------");
		
		Validate.notNull(universityInfo, "UniversityCreationAndDeletionAspect.bookmarkUniversity - the universityInfo cannot be null.");
		Validate.notNull(userId, "UniversityCreationAndDeletionAspect.bookmarkUniversity - the userId cannot be null.");
		
		try {
			// Get DesktopInfo
			DesktopInfo desktopInfo = desktopService.findDesktopByUser(userId);
			
			// Link University
			desktopService.linkUniversity(desktopInfo.getId(), universityInfo.getId());
		} catch (DesktopException de) {
			logger.error(de.getMessage());
		}
		
		logger.debug("----------> End method bookmarkUniversity <----------");
	}

	
	/**
	 * Delete bookmarks of the given university from all users.
	 * @param universityId
	 */
	public void deleteBookmarksOfUniversity (Long universityId) {
		logger.debug("----------> BEGIN method deleteBookmarksOfUniversity <----------");
		
		Validate.notNull(universityId, "UniversityCreationAndDeletionAspect.deleteBookmarksOfUniversity - the universityId cannot be null.");
		
		try {
			desktopService.unlinkAllFromUniversity(universityId);
		} catch (DesktopException de) {
			logger.error(de.getMessage());
		}
		
		logger.debug("----------> End method deleteBookmarksOfUniversity <----------");
	}


	public DesktopService2 getDesktopService() {
		return desktopService;
	}


	public void setDesktopService(DesktopService2 desktopService) {
		this.desktopService = desktopService;
	}
	
}
