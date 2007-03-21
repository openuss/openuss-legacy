// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.docmanagement;

//import javax.faces.context.FacesContext;

import java.util.Collection;
import java.util.Iterator;

import org.acegisecurity.Authentication;
import org.acegisecurity.acl.AclEntry;
import org.acegisecurity.acl.AclManager;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.log4j.Logger;
import org.openuss.framework.web.jsf.util.FacesUtils;
import org.openuss.lecture.Enrollment;
import org.openuss.lecture.Faculty;
import org.openuss.security.User;

/**
 * @see org.openuss.docmanagement.InformationService
 */
public class InformationServiceImpl
    extends org.openuss.docmanagement.InformationServiceBase
{
	private final SecurityContext securityContext = SecurityContextHolder.getContext();
	
	private static final Logger logger = Logger.getLogger(InformationServiceImpl.class); 
/*
	private static AclEntry[] getAclEntries(Object domainObject) {
		AclManager aclManager = getAclManager();

		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		AclEntry[] acls = aclManager.getAcls(domainObject, auth);
	
		if (logger.isDebugEnabled()) {
			logger.debug("Authentication: '" + auth + "' has: " + ((acls == null) ? 0 : acls.length)
					+ " AclEntrys for domain object: '" + domainObject + "' from AclManager: ' "
					+ aclManager.toString() + "'");
		}
		return acls;
	}
	
	private static AclManager getAclManager() {
		if (_aclManager == null) {
			FacesContext facesContext = FacesUtils.getFacesContext();
			_aclManager = (AclManager) facesContext.getApplication().createValueBinding("#{aclManager}").getValue(facesContext);
		}	
		return _aclManager;
	}*/	
    /**
     * @see org.openuss.docmanagement.InformationService#userIsAssistant(java.lang.Long)
     */
    protected boolean handleUserIsAssistant(java.lang.Long id)
        throws java.lang.Exception
    {
    	
        String username = securityContext.getAuthentication().getName();
        logger.debug("Current user is: "+ username);
       // Integer i = securityContext.getAuthentication().getAuthorities().
        Enrollment e;
        Faculty faculty = getLectureService().getFaculty(id);        
		Collection enrollments = faculty.getEnrollments();
		Collection assistants;
		Iterator i = enrollments.iterator();
		Iterator i2;
		User user;
		while (i.hasNext()) {
			e = (Enrollment) i.next();
			assistants = getEnrollmentService().getAssistants(e);
			i2 = assistants.iterator();
			while (i2.hasNext()) {
				user = (User) i2.next();
				if (user.getUsername().equals(username))
					return true;
			}
		}
		return false;
    }

    /**
     * @see org.openuss.docmanagement.InformationService#userIsOwner(java.lang.String)
     */
    protected boolean handleUserIsOwner(java.lang.String path)
        throws java.lang.Exception
    {
        // @todo implement protected boolean handleUserIsOwner(java.lang.String path)
        return false;
    }

    /**
     * @see org.openuss.docmanagement.InformationService#userIsParticipant(java.lang.Long)
     */
    protected boolean handleUserIsParticipant(java.lang.Long id)
        throws java.lang.Exception
    {
        // @todo implement protected boolean handleUserIsParticipant(java.lang.Long id)
        return false;
    }

}