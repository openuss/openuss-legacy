package org.openuss.web.discussion.groups;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.openuss.discussion.DiscussionSearchDomainResult;
import org.openuss.groups.UserGroupInfo;


/**
 * Discussion Search Result Container
 *  
 * @author Thomas Jansing
 * @author Juergen de Braaf
 * @author Peter Schuh
 * @author Tobias Brockmann
 * @author Lutz D. Kramer
 */

@Bean(name="discussion_search_results", scope=Scope.SESSION)
public class GroupDiscussionSearchResults implements Serializable {
	
	private static final long serialVersionUID = 2103663293293922929L;
	
	private static final Logger logger = Logger.getLogger(GroupDiscussionSearchResults.class);
	
	private List<DiscussionSearchDomainResult> hits;
	
	private String textToSearch;
	private String submitter;
	private boolean titleOnly;
	private boolean isFuzzy;
	private Long postId;
	
	@Property(value = "#{groupInfo}")	
	protected UserGroupInfo groupInfo;
		
	private List<SelectItem> postIds;
	private List<SelectItem> groupIds;
	
	public GroupDiscussionSearchResults(){
		titleOnly = false;
		isFuzzy = true;
		postId = 0L;		
		postIds = new ArrayList<SelectItem>();
		groupIds = new ArrayList<SelectItem>();
	}
	
	public List<DiscussionSearchDomainResult> getHits() {
		return hits;
	}

	public void setHits(List<DiscussionSearchDomainResult> hits) {
		this.hits = hits;
	}

	public String getTextToSearch() {
		return textToSearch;
	}

	public void setTextToSearch(String textToSearch) {
		this.textToSearch = textToSearch;
	}
	
	public boolean isTitleOnly() {
		return titleOnly;
	}

	public void setTitleOnly(boolean titleOnly) {
		this.titleOnly = titleOnly;
	}
		
	public String getSubmitter() {
		return submitter;
	}

	public void setSubmitter(String submitter) {
		this.submitter = submitter;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}
	
	public UserGroupInfo getGroupInfo() {		
		return groupInfo;
	}

	public void setGroupInfo(UserGroupInfo groupInfo) {
		this.groupInfo = groupInfo;
	}


	/**
	 * Indicates whether the result data table is rendered or not
	 * @return
	 */
	public String getVisibilityResultTable(){
		logger.debug("getVisibilityResultTable - hits counts: "+this.getHitCounts());
		if(this.getHitCounts() > 0){
			return "true";
		} else {
			return "false";
		}
	}

	public List<SelectItem> getPostIds() {
		return postIds;
	}

	public void setPostIds(List<SelectItem> postIds) {
		this.postIds = postIds;
	}
	
	public List<SelectItem> getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(List<SelectItem> courseIds) {
		this.groupIds = courseIds;
	}

	public int getHitCounts() {
		return hits != null ? hits.size() : 0;
	}

	public boolean getIsFuzzy() { // NOPMD by devopenuss on 11.03.08 14:21
		return isFuzzy;
	}

	public void setIsFuzzy(boolean isFuzzy) {
		this.isFuzzy = isFuzzy;
	}
	
}
