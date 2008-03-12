package org.openuss.web.seminarpool;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.seminarpool.SeminarpoolStatus;

@Bean(name = "phaseController", scope = Scope.REQUEST)
@View
public class PhaseController extends AbstractSeminarpoolPage{
	public String getPhase(){
		int phase = seminarpoolInfo.getSeminarpoolStatus().getValue();
		return i18n("seminarpool_status_"+phase);
	}
	
	public boolean getStatusBeforeRegistrationComplete() {
		return (seminarpoolInfo.getSeminarpoolStatus().getValue() < SeminarpoolStatus.REGISTRATIONCOMPLETEPHASE.getValue());
	}
	public boolean getStatusBeforeEqualsRegistrationComplete(){
		return (seminarpoolInfo.getSeminarpoolStatus().getValue() <= SeminarpoolStatus.REGISTRATIONCOMPLETEPHASE.getValue());
	}
	public boolean getStatusAfterPreparations() {
		return (seminarpoolInfo.getSeminarpoolStatus().getValue()>0);
	}
	
	
	public boolean getStatusRegistrationComplete() {
		return (seminarpoolInfo.getSeminarpoolStatus() == SeminarpoolStatus.REGISTRATIONCOMPLETEPHASE);
	}


	public boolean getStatusAfterRegistrationComplete() {
		return (seminarpoolInfo.getSeminarpoolStatus().getValue() > 2);
	}


	public boolean getStatusReview() {
		return (seminarpoolInfo.getSeminarpoolStatus().getValue() == 3);
	}


	public boolean getStatusAfterReview() {
		return (seminarpoolInfo.getSeminarpoolStatus().getValue() >= 3);
	}
}
