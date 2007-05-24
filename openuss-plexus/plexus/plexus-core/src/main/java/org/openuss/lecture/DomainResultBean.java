package org.openuss.lecture;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.openuss.search.DomainResult;

/**
 * Value Object of DomainResult
 * 
 * @author Ingo Dueppe
 *
 */
public class DomainResultBean implements DomainResult {

	private static final long serialVersionUID = 1297801100348861030L;
	
	private Long id;
	private Date modified;
	private String name;
	private String details;
	private String domainType;
	
	private float score;

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getDetails() {
		return details;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDetails(String details) {
		this.details = details;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getDomainType() {
		return domainType;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDomainType(String domainType) {
		this.domainType = domainType;
	}

	/**
	 * {@inheritDoc}
	 */
	public Long getId() {
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * {@inheritDoc}
	 */
	public Date getModified() {
		return modified;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setModified(Date modified) {
		this.modified = modified;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
