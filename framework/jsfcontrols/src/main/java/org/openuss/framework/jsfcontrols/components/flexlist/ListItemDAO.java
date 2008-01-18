package org.openuss.framework.jsfcontrols.components.flexlist;

import java.io.Serializable;

public class ListItemDAO implements Serializable {
	
	private static final long serialVersionUID = -7920738562941995001L;
	
	private String title;
	private String url;
	private String metaInformation;
	private String removeBookmarkUrl;
	private Boolean newsletterSubscribed;
	private String newsletterActionUrl;
	private Boolean forumSubscribed;
	private String forumActionUrl;
	
	
	public String getMetaInformation() {
		return metaInformation;
	}
	public void setMetaInformation(String metaInformation) {
		this.metaInformation = metaInformation;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setUrl(String url)
	{
		this.url = url;
	}
	
	public String getUrl()
	{
		return url;
	}
	
	public String getRemoveBookmarkUrl() {
		return removeBookmarkUrl;
	}
	
	public void setRemoveBookmarkUrl(String removeBookmarkUrl) {
		this.removeBookmarkUrl = removeBookmarkUrl;
	}
	public Boolean getNewsletterSubscribed() {
		return newsletterSubscribed;
	}
	public void setNewsletterSubscribed(Boolean newsletterSubscribed) {
		this.newsletterSubscribed = newsletterSubscribed;
	}
	public String getNewsletterActionUrl() {
		return newsletterActionUrl;
	}
	public void setNewsletterActionUrl(String newsletterActionUrl) {
		this.newsletterActionUrl = newsletterActionUrl;
	}
	public Boolean getForumSubscribed() {
		return forumSubscribed;
	}
	public void setForumSubscribed(Boolean forumSubscribed) {
		this.forumSubscribed = forumSubscribed;
	}
	public String getForumActionUrl() {
		return forumActionUrl;
	}
	public void setForumActionUrl(String forumActionUrl) {
		this.forumActionUrl = forumActionUrl;
	}
}
