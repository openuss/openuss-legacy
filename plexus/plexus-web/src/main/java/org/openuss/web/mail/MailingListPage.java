package org.openuss.web.mail;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.web.Constants;
import org.openuss.web.enrollment.AbstractEnrollmentPage;

//TODO remove me
@Bean(name = "views$secured$enrollment$mailinglist", scope = Scope.REQUEST)
@View
public class MailingListPage extends AbstractEnrollmentPage {
	private static final Logger logger = Logger.getLogger(MailingListPage.class);
	
//	private MailingListDataProvider data = new MailingListDataProvider();
	
	public String delete() {
		logger.debug("MailingList delete Item");
		return Constants.SUCCESS;
	}

	public String send() {
		logger.debug("MailingList send Item");
		return Constants.SUCCESS;
	}

	public String change(){
		logger.debug("MailingList change Item");
		return Constants.SUCCESS;
	}
	
	public String changeStatus(){
		logger.debug("MailingList status change");
		return Constants.SUCCESS;
		
	}
		
//	private class MailingListDataProvider extends AbstractPagedTable<MailingJob> {
//
//		private DataPage<MailingJob> page; 
//		
//		@Override 
//		public DataPage<MailingJob> getDataPage(int startRow, int pageSize) {
//			ArrayList<MailingJob> al = new ArrayList<MailingJob>();			
//			MailingJob mj1 = MailingJob.Factory.newInstance();
//			MailingJob mj2 = MailingJob.Factory.newInstance();
//			MailingJob mj3 = MailingJob.Factory.newInstance();
//			MailingJob mj4 = MailingJob.Factory.newInstance();
//			
//			mj1.setId(new Long(1));
//			mj1.setMailTitle("Lösungen zu Fallstudien");
//			mj1.setSendingTime(new Timestamp(System.currentTimeMillis()));
//			//mj1.setStatus(MailingStatus.DRAFT);
//			
//			mj2.setId(new Long(2));
//			mj2.setMailTitle("RWC Seminar");
//			mj2.setSendingTime(new Timestamp(System.currentTimeMillis()));
//			//mj2.setStatus(MailingStatus.INQUEUE);
//
//			mj3.setId(new Long(3));
//			mj3.setMailTitle("SAP Workshop");
//			mj3.setSendingTime(new Timestamp(System.currentTimeMillis()));
//			//mj3.setStatus(MailingStatus.PLANNED);
//
//			mj4.setId(new Long(4));
//			mj4.setMailTitle("OpenUSS");
//			mj4.setSendingTime(new Timestamp(System.currentTimeMillis()));
//			//mj4.setStatus(MailingStatus.SEND);
//
//			al.add(mj1); al.add(mj2); al.add(mj3); al.add(mj4);
//			page = new DataPage<MailingJob>(al.size(),0,al);
//			return page;
//		}
//	}

//	public MailingListDataProvider getData() {
//		return data;
//	}
//
//	public void setData(MailingListDataProvider data) {
//		this.data = data;
//	}
	


}
