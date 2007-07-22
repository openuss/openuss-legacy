package jsftest;

import org.openuss.web.components.flexlist.BeanInterface;
import org.openuss.web.components.flexlist.ListItemDAO;
import org.openuss.web.components.flexlist.UIFlexlist;
import org.openuss.web.components.flexlist.FlexlistTag;
import java.util.ArrayList;

public class TestBackingBean {
	private UIFlexlist flexlist;
	
	public TestBackingBean() {
		// TODO Auto-generated constructor stub
		
	}

		
	public UIFlexlist getFlexlist()
	{
		return flexlist;
	}
	
	public void setFlexlist(UIFlexlist flexlist)
	{
		this.flexlist = flexlist;
		
		initValues();
	}
	
	private void initValues()
	{
		flexlist.getAttributes().put("title", getTitle());
		flexlist.getAttributes().put("showButtonTitle", getShowButtonTitle());
		flexlist.getAttributes().put("hideButtonTitle", getHideButtonTitle());
		flexlist.getAttributes().put("visibleItems", getVisibleItems());
		flexlist.getAttributes().put("hiddenItems", getHiddenItems());
	}
	


	public String getHideButtonTitle() {
		return "Weniger...";
	}

	public String getShowButtonTitle() {
		return "Mehr...";
	}

	public String getTitle() {
		return "Test-Liste";
	}


	public ArrayList getVisibleItems() {
		ArrayList list = new ArrayList();
		ListItemDAO newItem;
		
		newItem = new ListItemDAO();
		newItem.setTitle("Item 1");
		newItem.setInformation("Info 1");
		list.add(newItem);
		
		newItem = new ListItemDAO();
		newItem.setTitle("Item 2");
		newItem.setInformation("Info 2");
		list.add(newItem);
		
		return list;
	}

	public ArrayList getHiddenItems() {
		ArrayList list = new ArrayList();
		ListItemDAO newItem;
		
		newItem = new ListItemDAO();
		newItem.setTitle("Item 1");
		newItem.setInformation("Info 1");
		list.add(newItem);
		
		newItem = new ListItemDAO();
		newItem.setTitle("Item 2");
		newItem.setInformation("Info 2");
		list.add(newItem);
		
		return list;
	}
}
