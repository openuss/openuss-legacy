package org.openuss.web.component.genericflexlist;

import javax.faces.webapp.UIComponentTag;
import javax.faces.component.UIComponent;

public class GenericFlexListTag extends UIComponentTag {
	String title;
	String showButtonTitle;
	String hideButtonTitle;
	
	public void release()
	{
		super.release();
		title = null;
		showButtonTitle = null;
		hideButtonTitle = null;
	}
	
	
	protected void setProperties(UIComponent component)
	{
		super.setProperties(component);
		
		if(title != null)
			component.getAttributes().put("title", title);
		
		if(showButtonTitle != null)
			component.getAttributes().put("showButtonTitle", showButtonTitle);
		
		if(hideButtonTitle != null)
			component.getAttributes().put("hideButtonTitle", hideButtonTitle);
		
	}
	
	
	public String getComponentType() {
		// TODO Auto-generated method stub
		return "genericflexlist";
	}

	
	public String getRendererType() {
		// TODO Auto-generated method stub
		return null;
	}


	public String getHideButtonTitle() {
		return hideButtonTitle;
	}


	public void setHideButtonTitle(String hideButtonTitle) {
		this.hideButtonTitle = hideButtonTitle;
	}


	public String getShowButtonTitle() {
		return showButtonTitle;
	}


	public void setShowButtonTitle(String showButtonTitle) {
		this.showButtonTitle = showButtonTitle;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}

}
