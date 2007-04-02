package org.openuss.web;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Preprocess;
import org.openuss.desktop.Desktop;
import org.openuss.desktop.DesktopException;
import org.openuss.desktop.DesktopService;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.security.User;

/**
 * Abstract BasePage  
 * @author Ingo Dueppe
 */
public abstract class BasePage extends BaseBean {

	private static final Logger logger = Logger.getLogger(BasePage.class);

	@Property(value = "#{desktop}")
	protected Desktop desktop;
	
	@Property(value = "#{desktopService}")
	protected DesktopService desktopService;
	
	@Property(value = "#{sessionScope.user}")
	protected User user;
	
	/**
	 * Refreshing faculty entity 
	 * @throws DesktopException 
	 */
	@Preprocess
	public void preprocess() throws Exception {
		// FIXME Tell, don't ask user.isExisting() <-- user.getId() == null 
		if (desktop == null && user != null && user.getId() != null) {
			logger.debug("preprocess - getting desktop session object");
			desktop = desktopService.getDesktopByUser(user);
			setSessionBean(Constants.DESKTOP, desktop);
		} 
	}

	public Desktop getDesktop() {
		return desktop;
	}

	public void setDesktop(Desktop desktop) {
		this.desktop = desktop;
	}

	public DesktopService getDesktopService() {
		return desktopService;
	}

	public void setDesktopService(DesktopService desktopService) {
		this.desktopService = desktopService;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
