package org.openuss.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.openuss.chat.ChatMessageInfo;
import org.openuss.chat.ChatRoomInfo;
import org.openuss.chat.ChatService;
import org.openuss.chat.ChatUserInfo;
import org.openuss.security.SecurityService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Chat-Server component for the AJAX chat introduced in the Portlets Project of
 * Herbie
 * 
 * @author Daniel Schulz
 * @author Pavel Saratchev
 * @author Ingo Dueppe
 * @author Sebastian Roekens
 */
public class ChatServlet extends HttpServlet {

	/** Serial Version UID */
	private static final long serialVersionUID = 2345372829083632624L;

	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(ChatServlet.class);

	/** ChatService */
	private transient ChatService chatService;
	
	/** SecurityService */
	private transient SecurityService securityService;

	/**
	 * @return XML representation of the room, starting with first new message
	 * @param startMessage
	 */
	public String toXml(ChatRoomInfo room, long lastMessage) {
		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		xml.append("<room id=\"" + room.getDomainId() + "\">\n");
		xml.append("\t<documentName>" + room.getName() + "</documentName>\n");
		xml.append("\t<topic>" + room.getTopic() + "</topic>\n");

		// list of users currently in this chat room
		xml.append("\t<users>\n");
		addUsersXML(room, xml);
		xml.append("\t</users>\n");

		// list of new messages
		xml.append("\t<messages>\n");
		for (ChatMessageInfo message: (List<ChatMessageInfo>) chatService.getRecentMessages(room.getId(),lastMessage)) {
			lastMessage = message.getId();
			xml.append("\t\t<message>\n");
			xml.append("\t\t\t<sender>" + message.getDisplayName() + "</sender>\n");
			xml.append("\t\t\t<content>" + message.getText() + "</content>\n");
			String time = (new java.text.SimpleDateFormat("HH:mm:ss")).format(message.getTime());
			xml.append("\t\t\t<creationtime>" + time + "</creationtime>\n");
			xml.append("\t\t</message>\n");
		}
		xml.append("\t</messages>\n");

		// set timestamp
		xml.append("\t<last>" + lastMessage + "</last>\n");
		xml.append("</room>\n");
		return xml.toString();
	}

	private void addUsersXML(ChatRoomInfo room, StringBuffer xml) {
		for (ChatUserInfo user : (List<ChatUserInfo>) chatService.getChatUsers(room.getId())) {
			xml.append("\t\t<user>" + user.getDisplayName() + "</user>\n");
		}
	}

	/**
	 * initialization
	 */
	public void init() {
		final WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		chatService = (ChatService) wac.getBean("chatService", ChatService.class);
		securityService = (SecurityService) wac.getBean("securityService", SecurityService.class);
	}

	/**
	 * Handles ajax chat requests:
	 * <ul>
	 * <li>send</li>
	 * <li>refresh</li>
	 * <li>change topic</li>
	 * <li>user entrers</li>
	 * <li>user leaves</li>
	 * <ul>
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws java.io.IOException {
		String action = req.getParameter("action");
		long roomId = Long.parseLong(req.getParameter("roomId"));
		ChatRoomInfo chatRoom = chatService.getRoom(roomId);
		if (chatRoom == null) {
			res.sendError(HttpServletResponse.SC_FOUND);
			return;
		}
		if (action.equals("send")) {
			// user sends new message
			Long lastMessage = Long.parseLong(req.getParameter("lastMessage"));
			String message = req.getParameter("message");
			chatService.sendMessage(roomId, message);

			String roomXml = toXml(chatService.getRoom(roomId), lastMessage);
			res.setContentType("text/xml; charset=utf-8");
			res.getWriter().write(roomXml);
			logger.debug(roomXml);
			// } else if (action.equals("topic")) {
			// // user changes topic
			// int userId = Integer.parseInt(req.getParameter("userId"));
			// String topic = req.getParameter("topic");
			// chatRoom.setTopic(topic);
			// chatService.setRoom(chatRoom);
		} else if (action.equals("enter")) {
			chatService.enterRoom(roomId);
			StringBuffer xml = new StringBuffer();
			xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
			xml.append("<newuser>\n");
			xml.append("\t<user>" +	securityService.getCurrentUser().getId() + "</user>\n");
			 xml.append("\t<last>" + chatService.getLastMessage(roomId) + "</last>\n");
			 xml.append("</newuser>\n");
			 res.setContentType("text/xml");
			 res.getWriter().write(xml.toString());
			 logger.debug(xml);
		} else if (action.equals("leave")) {
			chatService.leaveRoom(roomId);
		} else if (action.equals("refresh")) {
			Long lastMessage = Long.parseLong(req.getParameter("lastMessage"));
			String roomXml = toXml(chatRoom, lastMessage);
			res.setContentType("text/xml; charset=utf-8");
			res.getWriter().write(roomXml);
			logger.debug(roomXml);
		}
	}

	/**
	 * for debugging purposes only
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		doPost(req, res);
	}
}