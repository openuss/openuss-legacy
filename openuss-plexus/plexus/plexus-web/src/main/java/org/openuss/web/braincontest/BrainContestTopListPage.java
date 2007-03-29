package org.openuss.web.braincontest; 

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.braincontest.AnswerInfo;
import org.openuss.braincontest.BrainContestInfo;
import org.openuss.braincontest.BrainContestService;
import org.openuss.documents.FileInfo;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.web.Constants;
import org.openuss.web.enrollment.AbstractEnrollmentPage;

@Bean(name = "views$secured$braincontest$braincontesttop", scope = Scope.REQUEST)
@View
public class BrainContestTopListPage extends AbstractEnrollmentPage{
	
	@Property(value="#{brainContestService}")
	private BrainContestService brainContestService;
	
	@Property(value="#{braincontest_contest}")
	private BrainContestInfo brainContest;
	
	private static final Logger logger = Logger.getLogger(BrainContestTopListPage.class);
	
	private BrainContestTopListDataProvider data = new BrainContestTopListDataProvider();

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
		}		
	}
	
	
	private class BrainContestTopListDataProvider extends AbstractPagedTable<AnswerInfo> {

		private DataPage<AnswerInfo> page; 
		
		@SuppressWarnings("unchecked")
		@Override 
		public DataPage<AnswerInfo> getDataPage(int startRow, int pageSize) {			
			List<AnswerInfo> al = brainContestService.getAnswers(brainContest);
			if (al==null) al = new ArrayList<AnswerInfo>();
			page = new DataPage<AnswerInfo>(al.size(),0,al);
			return page;
		}
	}

	public BrainContestTopListDataProvider getData() {
		return data;
	}

	public void setData(BrainContestTopListDataProvider data) {
		this.data = data;
	}

	public BrainContestService getBrainContestService() {
		return brainContestService;
	}

	public void setBrainContestService(BrainContestService brainContestService) {
		this.brainContestService = brainContestService;
	}

	public BrainContestInfo getBrainContest() {
		return brainContest;
	}

	public void setBrainContest(BrainContestInfo brainContest) {
		this.brainContest = brainContest;
	}
	
}