package org.openuss.braincontest;

import java.util.Date;

/**
 * 
 * @author Ingo Dueppe
 * 
 */
public interface Answer {

	public AnswerPK getAnswerPk();

	public void setAnswerPk(AnswerPK answerPk);

	public Date getAnsweredAt();

	public void setAnsweredAt(Date answeredAt);

	public abstract Long getImageId();

	public abstract String getDisplayName();

}