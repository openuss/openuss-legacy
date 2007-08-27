package org.openuss.aop;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.desktop.DesktopException;
import org.openuss.desktop.DesktopInfo;
import org.openuss.desktop.DesktopService2;
import org.openuss.lecture.DepartmentInfo;

/**
 * Aspect for Department Creation and Deletion.
 * @author Florian Dondorf
 */
public class DepartmentCreationAndDeletionAspect {

	private static final Logger logger = Logger.getLogger(InstituteCreationAndDeletionAspect.class);
	private DesktopService2 desktopService;
	
	/**
	 * Create bookmark of given department for the creating user.
	 * @param departmentInfo, userId
	 */
	public void bookmarkDepartment (DepartmentInfo departmentInfo,Long userId) {
		logger.debug("----------> BEGIN method bookmarkDepartment <----------");
		
		Validate.notNull(departmentInfo, "DepartmentCreationAndDeletionAspect.bookmarkDepartment - the departmentInfo cannot be null.");
		Validate.notNull(userId, "DepartmentCreationAndDeletionAspect.bookmarkDepartment - the userId cannot be null.");
		
		try {
			// Get DesktopInfo
			DesktopInfo desktopInfo = desktopService.findDesktopByUser(userId);
			
			// Link Department
			desktopService.linkDepartment(desktopInfo.getId(), departmentInfo.getId());
		} catch (DesktopException de) {
			logger.error(de.getMessage());
		}
		
		logger.debug("----------> End method bookmarkDepartment <----------");
	}

	
	/**
	 * Delete bookmarks of the given department from all users.
	 * @param departmentId
	 */
	public void deleteBookmarksOfDepartment (Long departmentId) {
		logger.debug("----------> BEGIN method deleteBookmarksOfDepartment <----------");
		
		Validate.notNull(departmentId, "DepartmentCreationAndDeletionAspect.deleteBookmarksOfDepartment - the departmentId cannot be null.");
		
		try {
			desktopService.unlinkAllFromDepartment(departmentId);
		} catch (DesktopException de) {
			logger.error(de.getMessage());
		}
		
		logger.debug("----------> End method deleteBookmarksOfDepartment <----------");
	}


	public DesktopService2 getDesktopService() {
		return desktopService;
	}

	public void setDesktopService(DesktopService2 desktopService) {
		this.desktopService = desktopService;
	}
	
}
