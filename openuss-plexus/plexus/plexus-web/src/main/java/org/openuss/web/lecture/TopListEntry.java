package org.openuss.web.lecture;

import java.io.Serializable;
import java.util.Date;

import org.openuss.security.UserInfo;

public class TopListEntry implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -856885954635431870L;

	public int rank;

	public Date answerTime;

	public UserInfo userInfo;

	public TopListEntry(int rank, Date answerTime, UserInfo userInfo) {
		this.rank = rank;
		this.answerTime = answerTime;
		this.userInfo = userInfo;
	}

	public Date getAnswerTime() {
		return answerTime;
	}

	public void setAnswerTime(Date answerTime) {
		this.answerTime = answerTime;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
}
