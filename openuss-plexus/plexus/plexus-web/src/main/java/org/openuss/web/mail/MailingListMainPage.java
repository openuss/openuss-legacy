package org.openuss.web.mail;

import java.util.List;

import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.mailinglist.MailInfo;
import org.openuss.web.Constants;

public class MailingListMainPage extends AbstractMailingListPage{
	
	private MailingListDataProvider data = new MailingListDataProvider();
	
	private class MailingListDataProvider extends AbstractPagedTable<MailInfo> {

		private DataPage<MailInfo> page; 
		
		@SuppressWarnings("unchecked")
		@Override 
		public DataPage<MailInfo> getDataPage(int startRow, int pageSize) {		
			List<MailInfo> al = enrollmentMailingListService.getMails(enrollmentInfo);			
			page = new DataPage<MailInfo>(al.size(),0,al);
			return page;
		}
	}

	public String newMail(){
		//TODO implement me
		return Constants.SUCCESS;
	}
	
	public String delMail(){
		//TODO implement me
		return Constants.SUCCESS;
	}
	
	public String changeMail(){
		//TODO implement me
		return Constants.SUCCESS;
	}
	
	public String toggleMailingListStatus(){
		//TODO implement me
		return Constants.SUCCESS;
	}
	
	public String sendMail(){
		//TODO implement me
		return Constants.SUCCESS;
	}
	
	public String listSubscribers(){
		//TODO implement me
		return Constants.SUCCESS;
	}
	
	public String exportSubscribers(){
		//TODO implement me
		return Constants.SUCCESS;
	}
	
	public MailingListDataProvider getData() {
		return data;
	}

	public void setData(MailingListDataProvider data) {
		this.data = data;
	}
}