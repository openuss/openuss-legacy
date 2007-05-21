package org.openuss.web.mail;

import java.util.Date;
import java.util.List;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.mailinglist.MailDetail;
import org.openuss.security.User;
import org.openuss.web.Constants;

@Bean(name = "views$secured$mailinglist$mailinglistnewmailpage", scope = Scope.REQUEST)
@View
public class MailingListNewMailPage extends AbstractMailingListPage{
	
	@Property(value= "#{"+Constants.USER+"}")
	private User user;
	
	@Property(value = "#{"+Constants.MAILINGLIST_MAIL+"}")
	private MailDetail mail;
	
	@SuppressWarnings("unchecked")
	@Prerender
	public void prerender() throws Exception {	
		super.prerender();
	}	

	public String delete(){
		return Constants.SUCCESS;
	}
	
	public String send(){		
		return Constants.SUCCESS;
	}
	
	public String sendDraft(){		
		return Constants.SUCCESS;	
	}
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public MailDetail getMail() {
		return mail;
	}


	public void setMail(MailDetail mail) {
		this.mail = mail;
	}
}