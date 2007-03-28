package org.openuss.web.braincontest;

import java.util.List;

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
import org.openuss.documents.FileInfo;
import org.openuss.documents.FolderEntry;
import org.openuss.lecture.LectureException;
import org.openuss.security.User;
import org.openuss.web.Constants;
import org.openuss.web.enrollment.AbstractEnrollmentPage;

@Bean(name = "views$secured$braincontest$braincontestsolve", scope = Scope.REQUEST)
@View
public class BrainContestSolvePage extends AbstractEnrollmentPage {
	private static final Logger logger = Logger
			.getLogger(BrainContestSolvePage.class);

	@Property(value = "#{braincontest_contest}")
	private BrainContestInfo brainContest;

	@Property(value = "#{brainContestService}")
	private BrainContestService brainContestService;

	@Property(value = "#{user}")
	private User user;

	@Property(value = "#{braincontest_answer}")
	private AnswerWebInfo answer;
	
	@Property(value = "#{braincontest_attachment}")
	private FileInfo attachment;
	
	
	public boolean result;

	@SuppressWarnings("unchecked")
	@Prerender
	public void prerender() {		
		if (!isPostBack()) {
			if ( brainContest != null && brainContest.getId() != null) {
				brainContest = brainContestService.getContest(brainContest);
			}
			if (brainContest == null || brainContest.getId() == null) {
				addError(i18n("braincontest_message_contest_not_found"));
				redirect(Constants.BRAINCONTEST_MAIN);
			}
		} 
		if (brainContest!=null){
			if (!brainContest.isReleased()) {
				addError(i18n("braincontest_message_contest_not_released"));
				redirect(Constants.BRAINCONTEST_MAIN);
			}
			List<FileInfo> attachments = brainContestService.getAttachments(brainContest);
			if (attachments!= null)	setAttachment(attachments.get(0));
		}		
	}

	public String save() throws BrainContestApplicationException {
		this.result = brainContestService.answer(answer.getAnswer(), user, brainContest, answer.isTopList());		
		logger.debug("answer triggered");		
		return Constants.BRAINCONTEST_RESULT;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public AnswerWebInfo getAnswer() {
		return answer;
	}

	public void setAnswer(AnswerWebInfo answer) {
		this.answer = answer;
	}

	public FileInfo getAttachment() {
		return attachment;
	}

	public void setAttachment(FileInfo attachment) {
		this.attachment = attachment;
	}

}