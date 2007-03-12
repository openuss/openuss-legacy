package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.web.enrollment.AbstractEnrollmentPage;

/**
 * @author Ingo Dueppe
 * 
 */
@Bean(name = "views$secured$enrollment$main", scope = Scope.REQUEST)
@View
public class EnrollmentPage extends AbstractEnrollmentPage {

	private static final long serialVersionUID = 1394531398550932611L;

}
