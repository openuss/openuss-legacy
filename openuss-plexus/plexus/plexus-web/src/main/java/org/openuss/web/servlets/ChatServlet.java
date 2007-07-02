package org.openuss.web.servlets;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.context.SecurityContextHolder;
import org.openuss.web.chat.Room;

/**
 * Chat-Server component for the AJAX chat introduced in the Portlets Project of Herbie
 * 
 * @author Daniel Schulz
 * @author Pavel Saratchev
 * @author Ingo Dueppe
 */
public class ChatServlet extends HttpServlet {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ChatServlet.class);

	private static final long serialVersionUID = 1L;

	/**
	 * stores chat rooms associated with domain identifier
	 */
	private HashMap<Long, Room> rooms;

	/**
	 * returns the chat rooms associated with a given ID automatically creates a
	 * new chat room if needed
	 */
	public synchronized Room getRoom(long identifier, HttpServletRequest req) {
		Room room = rooms.get(identifier);
		if (room == null) {
			room = new Room(identifier);
			room.addMessage(0, "Chatroom initiated.");
			rooms.put(identifier, room);
		}
		return room;
	}

	/**
	 * initialization
	 */
	public void init() {
		rooms = new HashMap<Long, Room>();
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
		Room room = getRoom(documentId, req);

		if (action.equals("send")) {
			// user sends new message
			Long lastMessage = Long.parseLong(req.getParameter("lastMessage"));
			int userId = Integer.parseInt(req.getParameter("userId"));
			String message = req.getParameter("message");
			synchronized (room) {
				room.addMessage(userId, message);
			}
			String roomXml = room.toXml(lastMessage, userId);
			res.setContentType("text/xml; charset=utf-8");
			res.getWriter().write(roomXml);
			logger.debug(roomXml);
		} else if (action.equals("topic")) {
			// user changes topic
			int userId = Integer.parseInt(req.getParameter("userId"));
			String topic = req.getParameter("topic");
			synchronized (room) {
				room.changeTopic(userId, topic);
			}
		} else if (action.equals("enter")) {
			// user enters
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			long userId;
			synchronized (room) {
				userId = room.addUser(username);
			}
			StringBuffer xml = new StringBuffer();
			xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
			xml.append("<newuser>\n");
			xml.append("\t<user>" + userId + "</user>\n");
			xml.append("\t<time>" + room.getLastMSGStamp() + "</time>\n");
			xml.append("</newuser>\n");
			res.setContentType("text/xml");
			res.getWriter().write(xml.toString());
			logger.debug(xml);
		} else if (action.equals("leave")) {
			// user leaves
			long userId = Long.parseLong(req.getParameter("userId"));
			synchronized (room) {
				room.removeUser(userId);
			}
		}

		// refresh
		else if (action.equals("refresh")) {
			Long lastMessage = Long.parseLong(req.getParameter("lastMessage"));
			int userId = Integer.parseInt(req.getParameter("userId"));
			String roomXml = room.toXml(lastMessage, userId);
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