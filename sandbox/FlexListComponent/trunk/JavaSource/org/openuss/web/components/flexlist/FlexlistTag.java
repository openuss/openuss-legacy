package org.openuss.web.components.flexlist;

import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentTag;
import org.openuss.web.components.flexlist.ListItemDAO;

public class FlexlistTag extends UIComponentTag {
	private String title;
	private String showButtonTitle;
	private String hideButtonTitle;
	private ListItemDAO visibleItems;
	private ListItemDAO hiddenItems;
	
/*	public BeanInterface getDataSource()
	{
		return dataSource;
	}
	
	public void setDataSource(BeanInterface dataSource)
	{
		this.dataSource = (BeanInterface)dataSource;
	}
*/
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public void release() {
		// the super class method should be called
		super.release();
		title = null;
		showButtonTitle = null;
		hideButtonTitle = null;
		visibleItems = null;
		hiddenItems = null;
	}
	protected void setProperties(UIComponent component) {
		// the super class method should be called
		super.setProperties(component);
		
		if(title != null)
			component.getAttributes().put("title", title);
		
		if(showButtonTitle != null)
			component.getAttributes().put("showButtonTitle", showButtonTitle);
		
		if(hideButtonTitle != null)
			component.getAttributes().put("hideButtonTitle", hideButtonTitle);
		
		if(visibleItems != null)
			component.getAttributes().put("visibleItems", visibleItems);
		
		if(hiddenItems != null)
			component.getAttributes().put("hiddenItems", hiddenItems);
	}
	
	public String getComponentType() {
		return "flexlist";
		}
	public String getRendererType() {
		// null means the component renders itself
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

	public ListItemDAO getHiddenItems() {
		return hiddenItems;
	}

	public void setHiddenItems(ListItemDAO hiddenItems) {
		this.hiddenItems = hiddenItems;
	}

	public ListItemDAO getVisibleItems() {
		return visibleItems;
	}

	public void setVisibleItems(ListItemDAO visibleItems) {
		this.visibleItems = visibleItems;
	}
}
