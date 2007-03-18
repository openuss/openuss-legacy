package org.openuss.web.lecture; 

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;


import org.openuss.braincontest.BrainContest;
import org.openuss.braincontest.Answer;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;
import org.openuss.web.enrollment.AbstractEnrollmentPage;

@Bean(name = "views$secured$enrollment$braincontestsolve", scope = Scope.REQUEST)
@View
public class BrainContestSolvePage extends AbstractEnrollmentPage{
	private static final Logger logger = Logger.getLogger(BrainContestSolvePage.class);
	
	@Property(value = "#{brainContest}")
	private BrainContest brainContest;

	@Property(value = "#{brainContestAnswer}")
	private Answer brainContestAnswer;

	@Prerender
	public void prerender() throws LectureException {
		logger.debug("prerender - injecting demo data to brainContest");
		brainContest.setTitle("Normative Entscheidungstheorie");
		brainContest.setDescription("Test Description");
		brainContest.setAttachment(null);		
	}

	public String save(){
		logger.debug("braincontest answer saved");
		return Constants.SUCCESS;
	}

	public BrainContest getBrainContest() {
		return brainContest;
	}

	public void setBrainContest(BrainContest brainContest) {
		this.brainContest = brainContest;
	}

	public Answer getBrainContestAnswer() {
		return brainContestAnswer;
	}

	public void setBrainContestAnswer(Answer brainContestAnswer) {
		this.brainContestAnswer = brainContestAnswer;
	}
	
	
		
}