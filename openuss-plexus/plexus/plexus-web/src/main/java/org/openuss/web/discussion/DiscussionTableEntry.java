package org.openuss.web.discussion;

import java.io.Serializable;
import java.sql.Date;

public class DiscussionTableEntry implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8250269562260982477L;
	
	public Boolean viewed;
	
	public String name;
	
	public int answers;
	
	public String author;
	
	public int clicks;
	
	public Date lastEntryTime;
	
	public String lastAuthor;
	
	public int pages;
	
	public DiscussionTableEntry(Boolean viewed, String name, int answers, String author, int clicks, Date lastEntryTime, String lastAuthor, int pages){
		this.viewed = viewed;
		this.name = name;
		this.answers = answers;
		this.author = author;
		this.clicks = clicks;
		this.lastEntryTime = lastEntryTime;
		this.lastAuthor = lastAuthor;	
		this.pages = pages;
	}

	public int getAnswers() {
		return answers;
	}

	public void setAnswers(int answers) {
		this.answers = answers;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getClicks() {
		return clicks;
	}

	public void setClicks(int clicks) {
		this.clicks = clicks;
	}

	public String getLastAuthor() {
		return lastAuthor;
	}

	public void setLastAuthor(String lastAuthor) {
		this.lastAuthor = lastAuthor;
	}

	public Date getLastEntryTime() {
		return lastEntryTime;
	}

	public void setLastEntryTime(Date lastEntryTime) {
		this.lastEntryTime = lastEntryTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getViewed() {
		return viewed;
	}

	public void setViewed(Boolean viewed) {
		this.viewed = viewed;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}
	
	
}