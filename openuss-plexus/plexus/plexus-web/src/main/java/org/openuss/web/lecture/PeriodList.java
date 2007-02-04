package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.openuss.framework.web.jsf.model.PagedDataModel;
import org.openuss.lecture.Period;

@Bean(name="periodList",scope=Scope.SESSION)
public class PeriodList extends PagedDataModel<Period> {

	private static final long serialVersionUID = 3498536803887766383L;

}
