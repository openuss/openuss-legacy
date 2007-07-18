package org.openuss.web;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Preprocess;
import org.openuss.desktop.Desktop;
import org.openuss.desktop.DesktopException;
import org.openuss.desktop.DesktopService;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
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
	
	@Property(value = "#{"+Constants.BREADCRUMBS+"}")
	protected List<BreadCrumb> crumbs;
	
	/**
	 * Refreshing institute entity 
	 * @throws DesktopException 
	 */
	@Preprocess
	public void preprocess() throws Exception {
		crumbs.clear();
		
		if (desktop == null) {
			if (user != null && user.getId() != null) {
				logger.debug("preprocess - getting desktop session object");
				desktop = desktopService.getDesktopByUser(user);
				setSessionBean(Constants.DESKTOP, desktop);
			} 
			
			if (desktop != null) {
				logger.error("could not find desktor for user "+user);
				addError("message_error_no_desktop_found");
				redirect(Constants.HOME);
			}
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

	public List<BreadCrumb> getCrumbs() {
		return crumbs;
	}

	public void setCrumbs(List<BreadCrumb> crumbs) {
		this.crumbs = crumbs;
	}

}
