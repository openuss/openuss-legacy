package org.openuss.web.groups.components;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.chat.ChatRoomInfo;
import org.openuss.chat.ChatRoomServiceException;
import org.openuss.chat.ChatService;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.web.Constants;

/**
 * Chat Main Page for groups
 * @author Sebastian Roekens
 * @author Thomas Jansing
 */
@Bean(name = "views$secured$groups$components$chatmain", scope = Scope.REQUEST)
@View
public class ChatMainPage extends AbstractGroupPage {
	
	private static final Logger logger = Logger.getLogger(ChatMainPage.class);
	
	private ChatRoomDataProvider rooms = new ChatRoomDataProvider();
	
	@Property(value="#{chatService}")
	private ChatService chatService;
	
	private String roomTopic;
	
	private List<ChatRoomInfo> groupRooms;

	@Prerender
	public void prerender() throws Exception { // NOPMD by devopenuss on 11.03.08 14:23
		super.prerender();
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setName(i18n("course_command_chat"));
		crumb.setHint(i18n("course_command_chat"));
		breadcrumbs.addCrumb(crumb);
	}
	
	private class ChatRoomDataProvider extends AbstractPagedTable<ChatRoomInfo> {
		
		private static final long serialVersionUID = -1918372320518667092L;

		private DataPage<ChatRoomInfo> page;
		
		@Override
		public DataPage<ChatRoomInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				groupRooms = chatService.getRooms(groupInfo);
				sort(groupRooms);
				page = new DataPage<ChatRoomInfo>(groupRooms.size(), 0, groupRooms);
			}
			return page;
		}
	}
	
	public String createRoom() {
		logger.info("create new chat room");
		chatService.createRoom(groupInfo, groupInfo.getName(), roomTopic);
		addMessage(i18n("chatservice_chatroom_created"));
		groupRooms = null;
		return Constants.SUCCESS;
	}
	
	public String deleteRoom() {
		logger.info("delete chat room");
		ChatRoomInfo room = rooms.getRowData();
		try {
			chatService.deleteRoom(room.getId());
			addMessage(i18n("chatservice_chatroom_deleted"));
		} catch (ChatRoomServiceException e) {
			logger.info(e);
			addError(i18n(e.getMessage()));
		}
		return Constants.SUCCESS;
	}

	public ChatRoomDataProvider getRooms() {
		return rooms;
	}

	public void setRooms(ChatRoomDataProvider rooms) {
		this.rooms = rooms;
	}

	public ChatService getChatService() {
		return chatService;
	}

	public void setChatService(ChatService chatService) {
		this.chatService = chatService;
	}

	public String getRoomTopic() {
		return roomTopic;
	}

	public void setRoomTopic(String roomName) {
		this.roomTopic = roomName;
	}

	public List<ChatRoomInfo> getGroupRooms() {
		return groupRooms;
	}

	public void setGroupRooms(List<ChatRoomInfo> groupRooms) {
		this.groupRooms = groupRooms;
	}

}