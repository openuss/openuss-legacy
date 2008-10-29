package org.openuss.braincontest;

import java.util.Date;
import java.util.List;

import org.openuss.foundation.DomainObject;

/**
 * 
 * @author Ingo Dueppe
 * 
 */
public interface BrainContest extends DomainObject {

	public Long getId();

	public void setId(Long id);

	public Long getDomainIdentifier();

	public void setDomainIdentifier(Long domainIdentifier);

	public Date getReleaseDate();

	public void setReleaseDate(Date releaseDate);

	public String getDescription();

	public void setDescription(String description);

	public String getTitle();

	public void setTitle(String title);

	public Integer getTries();

	public void setTries(Integer tries);

	public String getSolution();

	public void setSolution(String solution);

	public List<Answer> getAnswers();

	public void setAnswers(List<Answer> answers);

	public abstract boolean isReleased();

	public abstract Integer getAnswersCount();

	public abstract void addAnswer(Answer answer);

	public abstract boolean validateAnswer(String answer);

}