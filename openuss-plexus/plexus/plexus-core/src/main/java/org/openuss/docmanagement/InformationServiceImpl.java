// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.docmanagement;

//import javax.faces.context.FacesContext;

import java.util.Collection;
import java.util.Iterator;

import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.log4j.Logger;
import org.openuss.lecture.Enrollment;
import org.openuss.lecture.EnrollmentMemberInfo;
import org.openuss.lecture.EnrollmentService;
import org.openuss.lecture.Faculty;
import org.openuss.lecture.LectureService;
import org.openuss.security.User;

/**
 * @see org.openuss.docmanagement.InformationService
 */
public class InformationServiceImpl
    extends org.openuss.docmanagement.InformationServiceBase
{
	private final SecurityContext securityContext = SecurityContextHolder.getContext();
	
	private static final Logger logger = Logger.getLogger(InformationServiceImpl.class); 

	public LectureService lectureService;
	
	public EnrollmentService enrollmentService;
	
	
	/**
     * @see org.openuss.docmanagement.InformationService#userIsAssistant(java.lang.Long)
     */
    protected boolean handleUserIsAssistant(java.lang.Long id, String user)
        throws java.lang.Exception
    {
        Enrollment e;
        Faculty faculty = getLectureService().getFaculty(id);        
		Collection enrollments = faculty.getEnrollments();
		Collection assistants;
		Iterator i = enrollments.iterator();
		Iterator i2;
		EnrollmentMemberInfo emi;
		while (i.hasNext()) {
			e = (Enrollment) i.next();
			assistants = getEnrollmentService().getAssistants(e);
			i2 = assistants.iterator();
			while (i2.hasNext()) {
				emi = (EnrollmentMemberInfo) i2.next();
				if (emi.getUsername().equals(user))
					return true;
			}
		}
		return false;
    }

    /**
     * @see org.openuss.docmanagement.InformationService#userIsOwner(java.lang.String)
     */
    protected boolean handleUserIsOwner(java.lang.String path, String user)
        throws java.lang.Exception
    {
        // @todo implement protected boolean handleUserIsOwner(java.lang.String path)
        return false;
    }

    /**
     * @see org.openuss.docmanagement.InformationService#userIsParticipant(java.lang.Long)
     */
    protected boolean handleUserIsParticipant(java.lang.Long id, String user)
        throws java.lang.Exception
    {
        // @todo implement protected boolean handleUserIsParticipant(java.lang.Long id)
        return false;
    }

	public EnrollmentService getEnrollmentService() {
		return enrollmentService;
	}

	public void setEnrollmentService(EnrollmentService enrollmentService) {
		this.enrollmentService = enrollmentService;
	}

	public LectureService getLectureService() {
		return lectureService;
	}

	public void setLectureService(LectureService lectureService) {
		this.lectureService = lectureService;
	}

	@Override
	protected boolean handleUserIsAssitant(Enrollment enrollment, String user) throws Exception {		
		Collection assistants = getEnrollmentService().getAssistants(enrollment);
		Iterator i = assistants.iterator();
		while (i.hasNext()) {
			
			if (((EnrollmentMemberInfo)i.next()).getUsername().equals(user))return true;
		}
		return false;
	}

}