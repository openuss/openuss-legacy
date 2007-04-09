package org.openuss.web.chat;


/**
 * Chat User for the AJAX chat introduced in the Portlets Project of Herbie
 * @author Daniel Schulz
 * @author Pavel Saratchev
 * @author Ingo Düppe
 */
public class ChatUser {
	
	/**
	 * ChatUserID
	 */
	private Long userId;
	
	/**
	 * username
	 */
	private String name;
	
	/**
	 * last time the user was active in milliseconds
	 */	
	long lastActive;
	
	/**
	 * constructor
	 * @param userId
	 * @param name
	 */
	public ChatUser(long userId, String name) {
		this.userId = userId;
		this.name = name;
		update();
	}
	
	/**
	 * @return UserID
	 */
	public Long getUserId() {
		return userId;
	}
	
	/**
	 * @return username
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * update time tracker
	 */
	public void update() {
		lastActive = System.currentTimeMillis();
	}
	
	/**
	 * @return last time user was active
	 */
	public long getLastActive() {
		return lastActive;
	}

    /**
     * Returns <code>true</code> if the argument is an Authority instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof ChatUser))
        {
            return false;
        }
        final ChatUser that = (ChatUser)object;
        if (this.userId == null || that.getUserId() == null || !this.userId.equals(that.getUserId()))
        {
            return false;
        }
        return true;
    }

    /**
     * Returns a hash code based on this entity's identifiers.
     */
	public int hashCode() {
		return userId == null? super.hashCode():userId.hashCode();
	}
	
	
}