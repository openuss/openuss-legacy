package org.openuss.web;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.desktop.DesktopDao;
import org.openuss.desktop.DesktopException;
import org.openuss.desktop.DesktopInfo;
import org.openuss.desktop.DesktopService2;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.security.User;
import org.openuss.security.UserContact;
import org.openuss.security.UserPreferences;

/**
 * Abstract BasePage  
 * @author Ingo Dueppe
 * @author Kai Stettner
 */
public abstract class BasePage extends BaseBean {

	private static final Logger logger = Logger.getLogger(BasePage.class);

	@Property(value = "#{desktopInfo}")
	protected DesktopInfo desktopInfo;
	
	@Property(value = "#{desktopService2}")
	protected DesktopService2 desktopService2;
	
	@Property(value ="#{desktopDao}")
	protected DesktopDao desktopDao;
	
	@Property(value = "#{sessionScope.user}")
	protected User user;
	
	@Property(value = "#{breadcrumbs}")
	protected BreadCrumbs breadcrumbs;
	
	/**
	 * Refreshing organisation entity 
	 * @throws DesktopException 
	 */
	@Preprocess
	public void preprocess() throws Exception {
		logger.debug("Starting method preprocess");
		
		if (desktopInfo == null) {
			if (user != null && user.getId() != null) {
				logger.debug("preprocess - getting desktop session object");
				desktopInfo = desktopService2.findDesktopByUser(user.getId());
				logger.debug(desktopInfo.getId());
				setSessionBean(Constants.DESKTOP_INFO, desktopInfo);
			} 
			
			if (desktopInfo != null) {
				logger.error("could not find desktop for user "+user);
				addError("message_error_no_desktop_found");
				redirect(Constants.HOME);
			}
		}
	}
	
	@Prerender
	public void prerender() throws Exception {
		if ((user!=null)&&(user.getId()==null)){	
			user = User.Factory.newInstance();
			user.setPreferences(UserPreferences.Factory.newInstance());
			user.setContact(UserContact.Factory.newInstance());
			setSessionBean(Constants.USER, user);
		}
	}
	

	public DesktopInfo getDesktopInfo() {
		return desktopInfo;
	}
	
	public void setDesktopInfo(DesktopInfo desktopInfo) {
		this.desktopInfo = desktopInfo;
	}
	
	public DesktopService2 getDesktopService2() {
		return desktopService2;
	}

	public void setDesktopService2(DesktopService2 desktopService2) {
		this.desktopService2 = desktopService2;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public DesktopDao getDesktopDao() {
		return desktopDao;
	}

	public void setDesktopDao(DesktopDao desktopDao) {
		this.desktopDao = desktopDao;
	}

	public BreadCrumbs getBreadcrumbs() {
		return breadcrumbs;
	}

	public void setBreadcrumbs(BreadCrumbs breadcrumbs) {
		this.breadcrumbs = breadcrumbs;
	}

}
