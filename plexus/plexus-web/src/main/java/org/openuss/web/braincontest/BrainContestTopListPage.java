package org.openuss.web.braincontest;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.braincontest.AnswerInfo;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.web.Constants;

/**
 * @author Sebastian Roekens
 */
@Bean(name = "views$secured$braincontest$braincontesttop", scope = Scope.REQUEST)
@View
public class BrainContestTopListPage extends AbstractBrainContestPage {

	private static final Logger logger = Logger.getLogger(BrainContestTopListPage.class);

	private BrainContestTopListDataProvider data = new BrainContestTopListDataProvider();

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		if (isRedirected()){
			return;
		}		
		if (getBrainContest() == null || getBrainContest().getId() == null) {
			addError(i18n("braincontest_message_contest_not_found"));
			redirect(Constants.BRAINCONTEST_MAIN);
			return;
		}
		if (getBrainContest() != null && !getBrainContest().isReleased()&&!isAssistant()) {
			addError(i18n("braincontest_message_contest_not_released"));
			redirect(Constants.BRAINCONTEST_MAIN);
			return;
		}
		addPageCrumb();
	}

	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("braincontest_toplist_header"));
		crumb.setHint(i18n("braincontest_toplist_header"));
		breadcrumbs.addCrumb(crumb);
	}

	private class BrainContestTopListDataProvider extends AbstractPagedTable<AnswerInfo> {

		private static final long serialVersionUID = -6154567464715182827L;

		private DataPage<AnswerInfo> page;

		@SuppressWarnings("unchecked")
		@Override
		public DataPage<AnswerInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				logger.debug("fetching answers");
				List<AnswerInfo> answers = getBrainContestService().getAnswers(getBrainContest());
				page = new DataPage<AnswerInfo>(answers.size(), 0, answers);
			}
			return page;
		}
	}

	public BrainContestTopListDataProvider getData() {
		return data;
	}

	public void setData(BrainContestTopListDataProvider data) {
		this.data = data;
	}

}