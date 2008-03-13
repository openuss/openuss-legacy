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
import org.openuss.internalMessage.InternalMessageInfo;
import org.openuss.internalMessage.InternalMessageService;
import org.openuss.security.User;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * 
 * @author Ralf Plattfaut
 *
 */
@Bean(name = "views$secured$internalMessages$messagecenter", scope = Scope.REQUEST)
@View
public class InternalMessageMainPage extends BasePage {
	
	@Property(value= "#{"+Constants.SHOW_USER_PROFILE+"}")
	public User profile;
	
	private static final Logger logger = Logger
			.getLogger(InternalMessageMainPage.class);
	
	@Property(value = "#{internalMessageService}")
	private InternalMessageService internalMessageService;
	
	private int numberOfUnreadMessages;
	
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
		profile.setId(this.inboxData.getRowData().getSenderId());
		setSessionAttribute(Constants.SHOW_USER_PROFILE, profile);
		return Constants.USER_PROFILE_VIEW_PAGE;
	}
	
	
		public String deleteSent(){
		logger.debug("delete sent message");
		InternalMessageInfo imInfo = this.outboxData.getRowData();
		return this.deleteMessageRequest(imInfo);
	}
	
	public String deleteRecieved(){
		logger.debug("delete received message");
		InternalMessageInfo imInfo = this.inboxData.getRowData();
		return this.deleteMessageRequest(imInfo);
	}
	
	private String deleteMessageRequest(InternalMessageInfo imInfo){
		setSessionBean(Constants.OPENUSS4US_INTERNALMESSAGE_MESSAGE, imInfo);
		return Constants.OPENUSS4US_MESSAGECENTER_REQUESTDELETE;
	}
			
	public String answerMessage(){
		InternalMessageInfo imInfo = new InternalMessageInfo();
		imInfo.setSubject("Re: " + this.inboxData.getRowData().getSubject());
		setSessionBean(Constants.OPENUSS4US_INTERNALMESSAGE_MESSAGE, imInfo);		
		profile.setId(this.inboxData.getRowData().getSenderId());
		setSessionAttribute(Constants.SHOW_USER_PROFILE, profile);
		return Constants.OPENUSS4US_MESSAGECENTER_CREATE;
	}
	
	public String readSentMessage(){
		InternalMessageInfo imInfo = this.outboxData.getRowData();
		return this.readMessage(imInfo);
	}
	
	public String readReceivedMessage(){
		InternalMessageInfo imInfo = this.inboxData.getRowData();
		internalMessageService.setRead(imInfo);
		return this.readMessage(imInfo);
	}
	
	private String readMessage(InternalMessageInfo imInfo){
		setSessionBean(Constants.OPENUSS4US_INTERNALMESSAGE_MESSAGE, imInfo);
		return Constants.OPENUSS4US_MESSAGECENTER_READMSG;
	}

	@Override
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
		breadcrumbs.loadBaseCrumbs();
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

	public int getNumberOfUnreadMessages() {
		numberOfUnreadMessages = internalMessageService.getNumberOfUnreadMessages();
		logger.debug(numberOfUnreadMessages + " unread messages found");
		return numberOfUnreadMessages;
	}

	public void setNumberOfUnreadMessages(int numberOfUnreadMessages) {
		this.numberOfUnreadMessages = numberOfUnreadMessages;
	}

	public User getProfile() {
		return profile;
	}

	public void setProfile(User profile) {
		this.profile = profile;
	}
}
