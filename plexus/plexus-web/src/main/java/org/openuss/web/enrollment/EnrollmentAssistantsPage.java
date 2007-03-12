package org.openuss.web.enrollment; 

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.security.User;
import org.openuss.security.UserInfo;
import org.openuss.web.Constants;

@Bean(name = "views$secured$enrollment$enrollmentassistants", scope = Scope.REQUEST)
@View
public class EnrollmentAssistantsPage extends AbstractEnrollmentPage{
	private static final Logger logger = Logger.getLogger(EnrollmentAssistantsPage.class);
	
	private AssistantsDataProvider data = new AssistantsDataProvider();
	
	private String aspirant;
	
	public String save(){
		logger.debug("Enrollment assistants page - saved");
		return Constants.SUCCESS;
	}
	
	public void changedAssistant(ValueChangeEvent event){
		logger.debug("changed enrollment assistants");		
	}


	private class AssistantsDataProvider extends AbstractPagedTable<UserInfo> {

		private DataPage<UserInfo> page; 
		
		@Override 
		public DataPage<UserInfo> getDataPage(int startRow, int pageSize) {		
			ArrayList<UserInfo> al = new ArrayList<UserInfo>();			
			UserInfo ui1 = new UserInfo(new Long(1234), "cag", "Sebastian", "Roekens", "abc123", "plexus@openuss-plexus.com", true, false, false, new Date(System.currentTimeMillis()));
			UserInfo ui2 = new UserInfo(new Long(12345), "dueppe", "Ingo", "Düppe", "12345", "plexus@openuss-plexus.com", true, true, false, new Date(System.currentTimeMillis()));
			UserInfo ui3 = new UserInfo(new Long(1111), "bundy", "Al", "Bundy", "dumpfbacke", "plexus@openuss-plexus.com", true, false, true, new Date(System.currentTimeMillis()));
			al.add(ui1); al.add(ui2); al.add(ui3);
			page = new DataPage<UserInfo>(al.size(),0,al);
			return page;
		}
	}

	public String showProfile() {
		UserInfo userInfo = data.getRowData();
		User user = User.Factory.newInstance();
		user.setId(userInfo.getId());
		setSessionBean("showuser", user);
		return Constants.USER_PROFILE_VIEW_PAGE;
	}
		
	public String delete() {
		logger.debug("enrollment member deleted");
		return Constants.SUCCESS;		
	}
	
	public String addAspirant(){
		logger.debug("enrollment assistant aspirant added");
		return Constants.SUCCESS;
	}

	public Collection<SelectItem> getAspirantList(){
		SelectItem si = new SelectItem();
		SelectItem si2 = new SelectItem();
		SelectItem si3 = new SelectItem();
		si.setLabel("Sebastian Roekens (plexus@openuss-plexus.com");
		si.setValue("cag");
		si2.setLabel("Ingo Düpppe (plexus@openuss-plexus.com");
		si2.setValue("dueppe");
		si3.setLabel("Al Bundy (plexus@openuss-plexus.com");
		si3.setValue("bundy");
		Vector<SelectItem> v = new Vector<SelectItem>();
		v.add(si); v.add(si2); v.add(si3);
		return v;	
	}
	
	public AssistantsDataProvider getData() {
		return data;
	}

	public void setData(AssistantsDataProvider data) {
		this.data = data;
	}

	public String getAspirant() {
		return aspirant;
	}

	public void setAspirant(String aspirant) {
		this.aspirant = aspirant;
	}
	
}