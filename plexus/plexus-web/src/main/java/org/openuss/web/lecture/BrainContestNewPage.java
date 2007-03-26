package org.openuss.web.lecture; 

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.braincontest.BrainContest;
import org.openuss.web.Constants;
import org.openuss.web.enrollment.AbstractEnrollmentPage;

@Bean(name = "views$secured$enrollment$braincontestnew", scope = Scope.REQUEST)
@View
public class BrainContestNewPage extends AbstractEnrollmentPage{
	private static final Logger logger = Logger.getLogger(BrainContestNewPage.class);
	
	@Property(value = "#{brainContest}")
	private BrainContest brainContest;

	public String save(){
		logger.debug("braincontest saved");
		return Constants.SUCCESS;
	}

	public String removeAttachment(){
		logger.debug("braincontest attachment removed");
		return Constants.SUCCESS;
	}

	public BrainContest getBrainContest() {
		return brainContest;
	}

	public void setBrainContest(BrainContest brainContest) {
		this.brainContest = brainContest;
	}

}