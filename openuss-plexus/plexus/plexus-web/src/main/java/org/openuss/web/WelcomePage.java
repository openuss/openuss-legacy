package org.openuss.web;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.apache.shale.tiger.managed.Scope;
import org.openuss.security.User;

@Bean(name="views$welcome", scope=Scope.REQUEST)
@View
public class WelcomePage extends BasePage{

	@Property (value="#{"+Constants.USER+"}")
	private User user;
	
	@Prerender
	public void prerender() {
		if (user != null && user.getId()==null) {
				user = User.Factory.newInstance();
				user.setPreferences(null);
				user.setContact(null);
				setSessionBean(Constants.USER, null);
		}
	}
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}