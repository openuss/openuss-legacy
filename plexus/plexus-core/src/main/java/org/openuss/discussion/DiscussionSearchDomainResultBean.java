package org.openuss.discussion;

import java.text.DecimalFormat;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.openuss.search.DiscussionSearchDomainResult;

/**
 * Value Object of DomainResult
 * 
 * @author Thomas Jansing
 * @author Juergen de Braaf
 * @author Peter Schuh
 * @author Tobias Brockmann
 */
public class DiscussionSearchDomainResultBean implements DiscussionSearchDomainResult {

	private static final long serialVersionUID = 1297801100348861030L;
	
	private Long id;
	private Date modified;
	private String title;
	private String submitter;
	private String courseId;
	private String postId;	
	
	private float score;

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}
	
	public String getFormattedScore(){ 
		DecimalFormat df = new DecimalFormat("###.##"); 
		return df.format(this.score*100)+" %";
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
		return modified != null ? new Date(modified.getTime()) : null;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setModified(Date modified) {
		this.modified = modified != null ? new Date(modified.getTime()) : null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	public String getSubmitter() {
		return submitter;
	}

	public void setSubmitter(String submitter) {
		this.submitter = submitter;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getCourseId() {
		return courseId;
	}

	/**
	 * 
	 */
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getPostId() {
		return postId;
	}

	/**
	 * 
	 */
	public void setPostId(String postId) {
		this.postId = postId;
	}
	
	
	public String getDebugInfo(){
		//TODO Debug Ausgabe
		return "DEBUG: "		
					+"CourseID: "+courseId;
	}

}
