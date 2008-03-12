package org.openuss.web.seminarpool;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;

@Bean(name = "phaseController", scope = Scope.REQUEST)
@View
public class PhaseController extends AbstractSeminarpoolPage{
	public String getPhase(){
		int phase = seminarpoolInfo.getSeminarpoolStatus().getValue();
		return i18n("seminarpool_status_"+phase);
	}
}
