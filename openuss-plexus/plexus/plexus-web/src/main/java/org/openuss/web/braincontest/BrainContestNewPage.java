package org.openuss.web.braincontest; 

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.braincontest.BrainContestApplicationException;
import org.openuss.braincontest.BrainContestInfo;
import org.openuss.braincontest.BrainContestService;
import org.openuss.web.Constants;
import org.openuss.web.enrollment.AbstractEnrollmentPage;

@Bean(name = "views$secured$braincontest$braincontestnew", scope = Scope.REQUEST)
@View
public class BrainContestNewPage extends AbstractEnrollmentPage{
	private static final Logger logger = Logger.getLogger(BrainContestNewPage.class);
	
	@Property(value = "#{brainContest}")
	private BrainContestInfo brainContest;
	
	@Property(value = "#{brainContestService}")
	private BrainContestService brainContestService;

	public void init(){
		if (this.brainContest==null) this.brainContest = new BrainContestInfo(); 
	}
	
	public String save() throws BrainContestApplicationException{
		if (this.brainContest.getId()==null) brainContestService.createContest(brainContest);
		else if (this.brainContest.getId()==null) brainContestService.saveContest(brainContest);
		return Constants.SUCCESS;
	}
	
	public String removeAttachment(){
		logger.debug("braincontest attachment removed");
		return Constants.SUCCESS;
	}

	public BrainContestInfo getBrainContest() {
		return brainContest;
	}

	public void setBrainContest(BrainContestInfo brainContest) {
		this.brainContest = brainContest;
	}

	public BrainContestService getBrainContestService() {
		return brainContestService;
	}

	public void setBrainContestService(BrainContestService brainContestService) {
		this.brainContestService = brainContestService;
	}

}