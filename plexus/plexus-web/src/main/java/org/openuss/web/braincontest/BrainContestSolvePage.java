package org.openuss.web.braincontest;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;

import org.openuss.braincontest.BrainContest;
import org.openuss.braincontest.Answer;
import org.openuss.braincontest.BrainContestApplicationException;
import org.openuss.braincontest.BrainContestInfo;
import org.openuss.braincontest.BrainContestService;
import org.openuss.lecture.LectureException;
import org.openuss.security.User;
import org.openuss.web.Constants;
import org.openuss.web.enrollment.AbstractEnrollmentPage;

@Bean(name = "views$secured$braincontest$braincontestsolve", scope = Scope.REQUEST)
@View
public class BrainContestSolvePage extends AbstractEnrollmentPage {
	private static final Logger logger = Logger
			.getLogger(BrainContestSolvePage.class);

	@Property(value = "#{brainContest}")
	private BrainContestInfo brainContest;

	@Property(value = "#{brainContestService}")
	private BrainContestService brainContestService;

	@Property(value = "#{user}")
	private User user;

	private String answer;

	private boolean topList;

	@Prerender
	public void prerender() {

	}

	public String save() throws BrainContestApplicationException {
		brainContestService.answer(answer, user, brainContest, topList);
		return Constants.SUCCESS;
	}

	public BrainContestInfo getBrainContest() {
		return brainContest;
	}

	public void setBrainContest(BrainContestInfo brainContest) {
		this.brainContest = brainContest;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public boolean isTopList() {
		return topList;
	}

	public void setTopList(boolean topList) {
		this.topList = topList;
	}

	public BrainContestService getBrainContestService() {
		return brainContestService;
	}

	public void setBrainContestService(BrainContestService brainContestService) {
		this.brainContestService = brainContestService;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}