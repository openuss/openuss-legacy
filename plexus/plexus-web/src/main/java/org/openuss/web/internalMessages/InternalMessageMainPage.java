package org.openuss.web.internalMessages;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.internalMessage.*;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * 
 * @author Ralf Plattfaut
 *
 */
@Bean(name = "views$secured$internalmessages$messagecenter", scope = Scope.REQUEST)
@View
public class InternalMessageMainPage extends BasePage {
	private static final Logger logger = Logger
			.getLogger(InternalMessageMainPage.class);
	
	@Property(value = "#{internalMessageService}")
	private InternalMessageService internalMessageService;
	
	//Inbox
	
	private InboxDataProvider inboxData = new InboxDataProvider();
	
	private class InboxDataProvider extends AbstractPagedTable<InternalMessageInfo> {

		private static final long serialVersionUID = -2279124328223684525L;
		
		private DataPage<InternalMessageInfo> page; 
		
		@SuppressWarnings("unchecked")
		@Override 
		public DataPage<InternalMessageInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<InternalMessageInfo> al = internalMessageService.getAllReceivedInternalMessages();
				sort(al);
				page = new DataPage<InternalMessageInfo>(al.size(),0,al);
			}
			return page;
		}
	}


	//Sent messages
	
	private OutboxDataProvider outboxData = new OutboxDataProvider();

	private class OutboxDataProvider extends AbstractPagedTable<InternalMessageInfo> {

		private static final long serialVersionUID = -2279124328223684525L;
		
		private DataPage<InternalMessageInfo> page; 
		
		@SuppressWarnings("unchecked")
		@Override 
		public DataPage<InternalMessageInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<InternalMessageInfo> al = internalMessageService.getAllSentInternalMessages();
				sort(al);
				page = new DataPage<InternalMessageInfo>(al.size(),0,al);
			}
			return page;
		}
	}
	
	public String linkToSender(){
//		profile.setId(this.data.getRowData().getUserId());
//		setSessionAttribute(Constants.SHOW_USER_PROFILE, profile);
		return Constants.USER_PROFILE_VIEW_PAGE;
	}
	
	public String linkToRecipient(){
//		profile.setId(this.data.getRowData().getUserId());
//		setSessionAttribute(Constants.SHOW_USER_PROFILE, profile);
		return Constants.USER_PROFILE_VIEW_PAGE;
	}	

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		addPageCrumb();
	}

	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("openuss4us_command_messagecenter"));
		crumb.setHint(i18n("openuss4us_command_messagecenter"));
		breadcrumbs.loadOpenuss4usCrumbs();
		breadcrumbs.addCrumb(crumb);
	}

	public InternalMessageService getInternalMessageService() {
		return internalMessageService;
	}

	public void setInternalMessageService(
			InternalMessageService internalMessageService) {
		this.internalMessageService = internalMessageService;
	}

	public InboxDataProvider getInboxData() {
		return inboxData;
	}

	public OutboxDataProvider getOutboxData() {
		return outboxData;
	}

	public void setInboxData(InboxDataProvider inboxData) {
		this.inboxData = inboxData;
	}

	public void setOutboxData(OutboxDataProvider outboxData) {
		this.outboxData = outboxData;
	}
}
