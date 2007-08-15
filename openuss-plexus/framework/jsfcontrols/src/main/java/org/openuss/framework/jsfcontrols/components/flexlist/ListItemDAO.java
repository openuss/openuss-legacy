package org.openuss.framework.jsfcontrols.components.flexlist;

import java.io.Serializable;

public class ListItemDAO implements Serializable {
	private String title;
	private String url;
	private String metaInformation;
	private Boolean isBookmark;
	private String removeBookmarkUrl;
	
	
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
	
	public Boolean getIsBookmark() {
		return isBookmark;
	}
	
	public void setIsBookmark(Boolean isBookmark) {
		this.isBookmark = isBookmark;
	}
	
	
}
