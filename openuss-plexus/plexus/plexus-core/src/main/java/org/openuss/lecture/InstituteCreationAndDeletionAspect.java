package org.openuss.lecture;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.desktop.DesktopException;
import org.openuss.desktop.DesktopInfo;
import org.openuss.desktop.DesktopService2;

/**
 * Aspect for Institute Creation and Deletion.
 * @author Florian Dondorf
 */
public class InstituteCreationAndDeletionAspect {
	
	private static final Logger logger = Logger.getLogger(InstituteCreationAndDeletionAspect.class);
	private DesktopService2 desktopService;
	
	/**
	 * Create bookmark of given institute for the creating user.
	 * @param instituteInfo, userId
	 */
	public void bookmarkInstitute (InstituteInfo instituteInfo,Long userId) {
		logger.debug("----------> BEGIN method bookmarkInstitute <----------");
		
		Validate.notNull(instituteInfo, "InstituteCreationAndDeletionAspect.bookmarkInstitute - the instituteInfo cannot be null.");
		Validate.notNull(userId, "InstituteCreationAndDeletionAspect.bookmarkInstitute - the userId cannot be null.");
		
		try {
			// Get DesktopInfo
			DesktopInfo desktopInfo = desktopService.findDesktopByUser(userId);
			
			// Link Institute
			desktopService.linkInstitute(desktopInfo.getId(), instituteInfo.getId());
		} catch (DesktopException de) {
			logger.error(de.getMessage());
		}
		
		logger.debug("----------> End method bookmarkInstitute <----------");
	}

	
	/**
	 * Delete bookmarks of the given institute from all users.
	 * @param instituteId
	 */
	public void deleteBookmarksOfInstitute (Long instituteId) {
		logger.debug("----------> BEGIN method bookmarkInstitute <----------");
		
		Validate.notNull(instituteId, "InstituteCreationAndDeletionAspect.deleteBookmarksOfInstitute - the instituteId cannot be null.");
		
		// Get DesktopInfo
		try {
			desktopService.unlinkAllFromInstitute(instituteId);
		} catch (DesktopException de) {
			logger.error(de.getMessage());
		}
		
		logger.debug("----------> End method bookmarkInstitute <----------");
	}
	
	
	public DesktopService2 getDesktopService() {
		return desktopService;
	}

	public void setDesktopService(DesktopService2 desktopService) {
		this.desktopService = desktopService;
	}

	
}
