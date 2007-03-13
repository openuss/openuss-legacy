package org.openuss.web.discussion;

import java.io.Serializable;
import java.sql.Date;

import org.openuss.repository.RepositoryFile;
import org.openuss.security.User;

public class ThreadEntry implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6832485158538627371L;

	private User author;
	
	private String message;
	
	private Date date;
	
	private String title;
	
	private RepositoryFile attachment;
	
	
	
	public ThreadEntry(User author, String message, Date date, String title, RepositoryFile attachment){
		this.author = author;
		this.message = message;
		this.date = date;
		this.title = title;
		this.attachment = attachment;
	}



	public RepositoryFile getAttachment() {
		return attachment;
	}



	public void setAttachment(RepositoryFile attachment) {
		this.attachment = attachment;
	}



	public User getAuthor() {
		return author;
	}



	public void setAuthor(User author) {
		this.author = author;
	}



	public Date getDate() {
		return date;
	}



	public void setDate(Date date) {
		this.date = date;
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}
	
	
}