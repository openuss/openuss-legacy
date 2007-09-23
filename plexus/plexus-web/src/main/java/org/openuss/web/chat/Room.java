package org.openuss.web.chat;

import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;

import org.apache.log4j.Logger;

/**
 * Chat-Room for the AJAX chat introduced in the Portlets Project of Herbie
 * 
 * @deprecated
 * 
 * @author Daniel Schulz
 * @author Pavel Saratchev
 * @author Ingo Dueppe
 */
public class Room {

	private static final Logger logger = Logger.getLogger(Room.class);

	/**
	 * Identifier of the associated domain object
	 */
	private long identifier;

	/**
	 * Name of the associated domain object
	 */
	private String domainName;

	/**
	 * list of messages
	 */
	private ArrayList<Message> contents;

	/**
	 * list of users online
	 */
	private ArrayList<ChatUser> chatUsers;

	/**
	 * current topic
	 */
	private String currentTopic;

	/**
	 * Systemuser
	 */
	private static final ChatUser SYSTEMUSER = new ChatUser(0, "System");

	/**
	 * cleanup time used by cleanupThread
	 */
	private static final long CLEANUPTIME = 30000;

	/**
	 * constructor
	 * 
	 * @param documentId
	 */
	public Room(Long identifier) {
		this.identifier = identifier;
		this.domainName = "name";

		contents = new ArrayList<Message>();
		chatUsers = new ArrayList<ChatUser>();
		currentTopic = "";

		Thread cleanupThread = new Thread(new Runnable() {
			public void run() {
				while (true) {
					cleanUsers();
					cleanMessages();
					try {
						Thread.sleep(CLEANUPTIME);
					} catch (InterruptedException e) {
						logger.error(e);
					}
				}
			}
		});
		cleanupThread.start();
	}

	/**
	 * @return user object
	 */
	private ChatUser getUserById(long userId) {
		if (userId == 0) {
			return SYSTEMUSER;
		}
		for (ChatUser user : chatUsers) {
			if (user.getUserId() == userId) {
				return user;
			}
		}
		return SYSTEMUSER;
	}

	/**
	 * @return timestamp of newest message
	 */
	public long getLastMSGStamp() {
		return contents.get(contents.size() - 1).getCreationTime();
	}

	/**
	 * adds a message
	 * 
	 * @param sender
	 * @param content
	 */
	public void addMessage(int userId, String content) {
		ChatUser user;
		if (userId == 0) {
			user = SYSTEMUSER;
		} else {
			user = getUserById(userId);
		}
		contents.add(new Message(user, content));
		user.update();
	}

	/**
	 * changes topic and adds correspondent system message
	 * 
	 * @param sender
	 * @param topic
	 */
	public void changeTopic(long userId, String topic) {
		currentTopic = topic;
		ChatUser user = getUserById(userId);
		contents.add(new Message(SYSTEMUSER, user.getName() + " changes Topic to: " + topic));
		user.update();
	}

	/**
	 * registers new user and adds correspondent system message
	 * 
	 * @param user
	 */
	public long addUser(String sender) {
		long userId;
		if (chatUsers.isEmpty()) {
			userId = 1;
		} else {
			userId = chatUsers.get(chatUsers.size() - 1).getUserId() + 1;
		}
		chatUsers.add(new ChatUser(userId, sender));
		contents.add(new Message(SYSTEMUSER, sender + " enters chat."));
		return userId;
	}

	/**
	 * removes user and adds correspondent system message
	 * 
	 * @param user
	 */
	public void removeUser(long userId) {
		ChatUser user = getUserById(userId);
		if (user != null) {
			String username = user.getName();
			chatUsers.remove(user);
			contents.add(new Message(SYSTEMUSER, username + " leaves chat."));
		}
	}

	/**
	 * cleans inactive users (lost connection)
	 */
	private synchronized void cleanUsers() {
		for (ListIterator<ChatUser> iterator = chatUsers.listIterator();iterator.hasNext();) {
			ChatUser user = iterator.next();
			if ((user.getLastActive() + CLEANUPTIME) < System.currentTimeMillis()) {
				contents.add(new Message(SYSTEMUSER, user.getName() + " leaves chat."));
				iterator.remove();
			}				
		}	
	}

	/**
	 * cleans messages
	 */
	private synchronized void cleanMessages() {
		for (ListIterator<Message> iterator = contents.listIterator();iterator.hasNext();) {
			Message message = iterator.next();
			if ((message.getCreationTime() + CLEANUPTIME) < System.currentTimeMillis()) {
				iterator.remove();
			}
		}
	}

	/**
	 * @return XML representation of the room, starting with first new message
	 * @param startMessage
	 */
	public synchronized String toXml(long lastMessage, int userId) {
		getUserById(userId).update();
		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		xml.append("<room id=\"" + identifier + "\">\n");
		xml.append("\t<documentName>" + domainName + "</documentName>\n");
		xml.append("\t<topic>" + currentTopic + "</topic>\n");
		// list of users currently in this chat room
		xml.append("\t<users>\n");
		xml.append("\t\t<user>SYSTEM</user>\n");
		for (ChatUser user : chatUsers) {
			xml.append("\t\t<user>" + user.getName() + "</user>\n");
		}
		xml.append("\t</users>\n");

		// list of new messages
		xml.append("\t<messages>\n");
		long creationtime = System.currentTimeMillis();
		for (Message message : contents) {
			if (message.getCreationTime() > lastMessage) {
				xml.append("\t\t<message>\n");
				xml.append("\t\t\t<sender>" + message.getSender().getName() + "</sender>\n");
				xml.append("\t\t\t<content>" + message.getContent() + "</content>\n");
				creationtime = message.getCreationTime();
				String time = (new java.text.SimpleDateFormat("HH:mm:ss")).format(new Date(creationtime));
				xml.append("\t\t\t<creationtime>" + time + "</creationtime>\n");
				xml.append("\t\t</message>\n");
			}
		}
		xml.append("\t</messages>\n");

		xml.append("\t<time>" + creationtime + "</time>\n");
		xml.append("</room>\n");
		return xml.toString();
	}
}