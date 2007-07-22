package org.openuss.web.components.flexlist;

public interface BeanInterface {
	
	public String getTitle();
	public ListItemDAO[] getVisibleItemList();
	public ListItemDAO[] getHiddenItemList();
	
	public String getShowButtonTitle();
	public String getHideButtonTitle();

}
