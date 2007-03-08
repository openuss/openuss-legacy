package org.openuss.web.lecture; 

import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.web.Constants;
import org.openuss.security.UserInfo;


@Bean(name = "views$secured$enrollment$enrollmentmain", scope = Scope.REQUEST)
@View
public class EnrollmentMainPage extends AbstractEnrollmentPage{
	private static final Logger logger = Logger.getLogger(EnrollmentMainPage.class);
	
	
	private String password; 

	
	ArrayList<UserInfo> data;
	
	@Prerender
	public void prerender() {
		UserInfo ui1 = new UserInfo(new Long(1234), "cag", "Sebastian", "Roekens", "abc123", "plexus@openuss-plexus.com", true, false, false, new Date(System.currentTimeMillis()));
		UserInfo ui2 = new UserInfo(new Long(12345), "dueppe", "Ingo", "D�ppe", "12345", "plexus@openuss-plexus.com", true, true, false, new Date(System.currentTimeMillis()));
		UserInfo ui3 = new UserInfo(new Long(1111), "bundy", "Al", "Bundy", "dumpfbacke", "plexus@openuss-plexus.com", true, false, true, new Date(System.currentTimeMillis()));
		data.add(ui1); data.add(ui2); data.add(ui3);		
	}
	

	public String applyWithPassword(){
		logger.debug("enrollment entry with password applied");
		return Constants.SUCCESS;
	}
	
	public String apply(){
		logger.debug("enrollment entry applied");
		return Constants.SUCCESS;
	}
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public ArrayList<UserInfo> getData() {
		return data;
	}


	public void setData(ArrayList<UserInfo> data) {
		this.data = data;
	}

}