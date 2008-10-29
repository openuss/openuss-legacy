package org.openuss.braincontest;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Ingo Dueppe
 * 
 */
public class AnswerInfo implements Serializable {

	private static final long serialVersionUID = 1250281882242951029L;

	public AnswerInfo() {
		this.answeredAt = null;
		this.solverName = null;
		this.imageId = null;
		this.solverId = null;
		this.contestId = null;
	}

	public AnswerInfo(Date answeredAt, String solverName, Long imageId, Long solverId, Long contestId) {
		this.answeredAt = answeredAt;
		this.solverName = solverName;
		this.imageId = imageId;
		this.solverId = solverId;
		this.contestId = contestId;
	}

	/**
	 * Copies constructor from other AnswerInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public AnswerInfo(AnswerInfo otherBean) {
		this(otherBean.getAnsweredAt(), otherBean.getSolverName(), otherBean.getImageId(), otherBean.getSolverId(),
				otherBean.getContestId());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(AnswerInfo otherBean) {
		this.setAnsweredAt(otherBean.getAnsweredAt());
		this.setSolverName(otherBean.getSolverName());
		this.setImageId(otherBean.getImageId());
		this.setSolverId(otherBean.getSolverId());
		this.setContestId(otherBean.getContestId());
	}

	private Date answeredAt;

	public Date getAnsweredAt() {
		return this.answeredAt;
	}

	public void setAnsweredAt(Date answeredAt) {
		this.answeredAt = answeredAt;
	}

	private String solverName;

	public String getSolverName() {
		return this.solverName;
	}

	public void setSolverName(String solverName) {
		this.solverName = solverName;
	}

	private Long imageId;

	public Long getImageId() {
		return this.imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	private Long solverId;

	public Long getSolverId() {
		return this.solverId;
	}

	public void setSolverId(Long solverId) {
		this.solverId = solverId;
	}

	private Long contestId;

	public Long getContestId() {
		return this.contestId;
	}

	public void setContestId(Long contestId) {
		this.contestId = contestId;
	}

}