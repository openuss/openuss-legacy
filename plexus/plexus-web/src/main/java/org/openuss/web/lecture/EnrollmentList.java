package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.openuss.framework.web.jsf.model.PagedDataModel;
import org.openuss.lecture.Enrollment;

/**
 * @author Ingo Dueppe
 *
 */

@Bean(name="enrollmentList",scope=Scope.REQUEST)
public class EnrollmentList extends PagedDataModel<Enrollment>{

	private static final long serialVersionUID = -4251392756355097647L;

}
