package org.openuss.lecture;

import org.apache.log4j.Logger;
import org.openuss.desktop.DesktopException;
import org.openuss.desktop.DesktopInfo;
import org.openuss.desktop.DesktopService2;

/**
 * Aspect for Institute Creation 
 * @author Florian Dondorf
 */
public class InstituteCreationAspect {
	
	private static final Logger logger = Logger.getLogger(InstituteCreationAspect.class);
	private DesktopService2 desktopService;
	
	/**
	 * Create bookmark of given institute for the creating user.
	 * @param instituteInfo, userId
	 */
	public void bookmarkInstitute (InstituteInfo instituteInfo,Long userId) {
		logger.debug("----------> BEGIN method bookmarkInstitute <----------");
		
		try {
			// Get User
			DesktopInfo desktopInfo = desktopService.findDesktopByUser(userId);
			
			// Link Institute
			desktopService.linkInstitute(desktopInfo.getId(), instituteInfo.getId());
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
