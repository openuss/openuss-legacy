package org.openuss.web.servlets;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.context.SecurityContextHolder;
import org.openuss.chat.ChatMessageInfo;
import org.openuss.chat.ChatRoomInfo;
import org.openuss.chat.ChatService;
import org.openuss.chat.ChatUserInfo;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Chat-Server component for the AJAX chat introduced in the Portlets Project of Herbie
 * 
 * @author Daniel Schulz
 * @author Pavel Saratchev
 * @author Ingo Dueppe
 * @author Sebastian Roekens
 */
public class ChatServlet extends HttpServlet {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ChatServlet.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 2345372829083632624L;
	

	/**
	 * stores chat rooms associated with domain identifier
	 */
	private List<Long> rooms;
	
	private List<ChatMessageInfo> messages;
	
	private ChatService chatService;

	
	/**
	 * @return XML representation of the room, starting with first new message
	 * @param startMessage
	 */
	public String toXml(ChatRoomInfo room, long lastMessage) {
		chatService.update();
		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		xml.append("<room id=\"" + room.getDomainId() + "\">\n");
		xml.append("\t<documentName>" + room.getDomainName() + "</documentName>\n");
		xml.append("\t<topic>" + room.getCurrentTopic() + "</topic>\n");
		// list of users currently in this chat room
		xml.append("\t<users>\n");
		xml.append("\t\t<user>SYSTEM</user>\n");
		for (Object user : chatService.getChatUsers(room.getDomainId())) {
			xml.append("\t\t<user>" + ((ChatUserInfo)user).getDisplayName() + "</user>\n");
		}
		xml.append("\t</users>\n");

		// list of new messages
		xml.append("\t<messages>\n");
		long creationtime = System.currentTimeMillis();
		ChatMessageInfo message;
		for (Object objectMessage : chatService.getMessages(room.getDomainId(), lastMessage)) {
			message = (ChatMessageInfo) objectMessage;
			if (message.getTime().getTime() > lastMessage) {
				xml.append("\t\t<message>\n");
				xml.append("\t\t\t<sender>" + message.getUsername() + "</sender>\n");
				xml.append("\t\t\t<content>" + message.getMessage() + "</content>\n");
				String time = (new java.text.SimpleDateFormat("HH:mm:ss")).format(message.getTime());
				xml.append("\t\t\t<creationtime>" + time + "</creationtime>\n");
				xml.append("\t\t</message>\n");
			}
		}
		xml.append("\t</messages>\n");

		xml.append("\t<time>" + creationtime + "</time>\n");
		xml.append("</room>\n");
		return xml.toString();
	}
	
	
	/**
	 * returns the chat rooms associated with a given ID automatically creates a
	 * new chat room if needed
	 */
	public synchronized Long getRoom(long identifier, HttpServletRequest req) {
		Long room = new Long(identifier);
		if (!rooms.contains(room)){
			rooms.add(room);
			ChatMessageInfo chatInit = new ChatMessageInfo();
			chatInit.setTime(new Date(System.currentTimeMillis()));
			chatInit.setMessage("Chatroom initiated.");
			chatService.sendSystemMessage(chatInit, identifier);
		}
		return room;
	}

	/**
	 * initialization
	 */
	public void init() {
		rooms = new ArrayList<Long>();
		final WebApplicationContext wac = WebApplicationContextUtils
		.getRequiredWebApplicationContext(getServletContext());
		chatService = (ChatService) wac.getBean("chatService", ChatService.class);
	}

	/**
	 * Handles ajax chat requests:
	 * <ul>
	 * 	<li>send</li>
	 *  <li>refresh</li>
	 *  <li>change topic</li>
	 *  <li>user entrers</li>
	 *  <li>user leaves</li>
	 * <ul>
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws java.io.IOException {
		String action = req.getParameter("action");
		long documentId = Long.parseLong(req.getParameter("documentId"));
		Long room = getRoom(documentId, req);
		ChatRoomInfo chatRoom = chatService.getRoom(documentId);
		if (action.equals("send")) {
			// user sends new message
			Long lastMessage = Long.parseLong(req.getParameter("lastMessage"));
			String message = req.getParameter("message");
			ChatMessageInfo newMessage = new ChatMessageInfo();
			newMessage.setMessage(message);
			newMessage.setTime(new Date(System.currentTimeMillis()));			
			chatService.sendMessage(newMessage, documentId);
//			int userId = Integer.parseInt(req.getParameter("userId"));
//			synchronized (room) {
//				room.addMessage(userId, message);
//			}
			
			String roomXml = toXml(chatService.getRoom(documentId), lastMessage);
			res.setContentType("text/xml; charset=utf-8");
			res.getWriter().write(roomXml);
			logger.debug(roomXml);
		} else if (action.equals("topic")) {
			// user changes topic
			int userId = Integer.parseInt(req.getParameter("userId"));
			String topic = req.getParameter("topic");			
			chatRoom.setCurrentTopic(topic);
			chatService.setRoom(chatRoom);
		} else if (action.equals("enter")) {
			// user enters
			chatService.login(documentId);
			StringBuffer xml = new StringBuffer();
			xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
			xml.append("<newuser>\n");
			xml.append("\t<user>" + SecurityContextHolder.getContext().getAuthentication().getName() + "</user>\n");
			xml.append("\t<time>" + chatRoom.getLastMsgStamp() + "</time>\n");
			xml.append("</newuser>\n");
			res.setContentType("text/xml");
			res.getWriter().write(xml.toString());
			logger.debug(xml);
		} else if (action.equals("leave")) {
			// user leaves
			chatService.logout(documentId);
		}

		// refresh
		else if (action.equals("refresh")) {
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