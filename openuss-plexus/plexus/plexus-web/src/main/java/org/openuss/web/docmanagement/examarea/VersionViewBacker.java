package org.openuss.web.docmanagement.examarea;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.docmanagement.ExaminationService;
import org.openuss.web.docmanagement.AbstractEnrollmentDocPage;



@Bean(name="versionViewBacker", scope=Scope.SESSION)
@View
public class VersionViewBacker extends AbstractEnrollmentDocPage{
	
	@Property(value="#{examinationService}")
	public ExaminationService examinationService;

	public ExaminationService getExaminationService() {
		return examinationService;
	}

	public void setExaminationService(ExaminationService examinationService) {
		this.examinationService = examinationService;
	}
}