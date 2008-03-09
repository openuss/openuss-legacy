package org.openuss.web.braincontest;

import java.util.Date;
import java.util.List;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.braincontest.BrainContestApplicationException;
import org.openuss.braincontest.BrainContestInfo;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.web.Constants;

/**
 * @author Sebastian Roekens
 */
@Bean(name = "views$secured$braincontest$braincontestmain", scope = Scope.REQUEST)
@View
public class BrainContestMainPage extends AbstractBrainContestPage {

	private BrainContestMainDataProvider data = new BrainContestMainDataProvider();

	public String remove() throws BrainContestApplicationException {
		BrainContestInfo bci = this.data.getRowData();
		getBrainContestService().removeContest(bci);
		addMessage(i18n("braincontest_main_deleted", bci.getTitle()));
		return Constants.SUCCESS;
	}

	public String change() {
		addContestToSession();
		return Constants.BRAINCONTEST_NEWCONTEST;
	}

	private void addContestToSession() {
		BrainContestInfo bci = this.data.getRowData();
		if (bci.getTries() == null) {
			bci.setTries(0);
		}
		setSessionBean(Constants.BRAINCONTENT_CONTEST, bci);
	}

	public String topList() {
		addContestToSession();
		return Constants.BRAINCONTEST_TOP;
	}

	public String solve() {
		addContestToSession();
		return Constants.BRAINCONTEST_SOLVE;
	}

	public String newContest() {
		BrainContestInfo bci = new BrainContestInfo();
		bci.setReleaseDate(new Date(System.currentTimeMillis()));
		setSessionBean(Constants.BRAINCONTENT_CONTEST, bci);
		return Constants.BRAINCONTEST_NEWCONTEST;
	}

	public String editContest() {
		BrainContestInfo bci = getBrainContestService().getContest(this.data.getRowData());
		setSessionBean(Constants.BRAINCONTENT_CONTEST, bci);
		return Constants.BRAINCONTEST_NEWCONTEST;
	}

	@SuppressWarnings("unchecked")
	private class BrainContestMainDataProvider extends AbstractPagedTable<BrainContestInfo> {

		private static final long serialVersionUID = -7344640723752102800L;

		private DataPage<BrainContestInfo> page;

		@Override
		public DataPage<BrainContestInfo> getDataPage(int startRow, int pageSize) {
			if (page != null) {
				List<BrainContestInfo> contests = getBrainContestService().getContests(courseInfo);
				page = new DataPage<BrainContestInfo>(contests.size(), 0, contests);
				sort(contests);
			}
			return page;
		}
	}

	public BrainContestMainDataProvider getData() {
		return data;
	}

	public void setData(BrainContestMainDataProvider data) {
		this.data = data;
	}

}