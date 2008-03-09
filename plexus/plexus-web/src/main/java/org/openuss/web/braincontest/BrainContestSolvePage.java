package org.openuss.web.braincontest;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.braincontest.BrainContestApplicationException;
import org.openuss.documents.FileInfo;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.security.UserInfo;
import org.openuss.web.Constants;

/**
 * @author Sebastian Roekens
 */
@Bean(name = "views$secured$braincontest$braincontestsolve", scope = Scope.REQUEST)
@View
public class BrainContestSolvePage extends AbstractBrainContestPage {
	private static final Logger logger = Logger.getLogger(BrainContestSolvePage.class);

	@Property(value = "#{user}")
	private UserInfo user;

	@Property(value = "#{braincontest_answer}")
	private AnswerWebInfo answer;

	@Property(value = "#{braincontest_attachment}")
	private FileInfo attachment;

	public boolean result;

	@SuppressWarnings("unchecked")
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		if (!isPostBack()) {
			if (getBrainContest() != null && getBrainContest().getId() != null) {
				setBrainContest(getBrainContestService().getContest(getBrainContest()));
				setSessionBean(Constants.BRAINCONTENT_CONTEST, getBrainContest());
			}
			if (getBrainContest() == null || getBrainContest().getId() == null) {
				addError(i18n("braincontest_message_contest_not_found"));
				redirect(Constants.BRAINCONTEST_MAIN);
			}
		}
		if (getBrainContest() != null) {
			if (!getBrainContest().isReleased() && !isAssistant()) {
				addError(i18n("braincontest_message_contest_not_released"));
				redirect(Constants.BRAINCONTEST_MAIN);
			}
			List<FileInfo> attachments = getBrainContestService().getAttachments(getBrainContest());
			if (attachments != null && !attachments.isEmpty()) {
				setAttachment(attachments.get(0));
			}
		}
		addPageCrumb();
	}

	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(getBrainContest().getTitle());
		crumb.setName(getBrainContest().getTitle());
		breadcrumbs.addCrumb(crumb);
	}

	public String save() throws BrainContestApplicationException {
		this.result = getBrainContestService().answer(answer.getAnswer(), user, getBrainContest(), answer.isTopList());
		logger.debug("answer triggered");
		if (this.result) {
			return Constants.BRAINCONTEST_SOLVED;
		}
		return Constants.BRAINCONTEST_WRONG;
	}

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
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