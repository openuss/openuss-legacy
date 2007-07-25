package org.openuss.framework.jsfcontrols.components.flexlist;

public class ListItemDAO {
	private String title;
	private String url;
	private String metaInformation;
	
	
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
	
	
}
