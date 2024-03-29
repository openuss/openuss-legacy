package org.openuss.web;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.security.SecurityService;
import org.openuss.security.UserInfo;
import org.openuss.statistics.OnlineStatisticService;
import org.openuss.statistics.SystemStatisticInfo;
import org.openuss.web.desktop.MyUniPage;

@Bean(name = "views$welcome", scope = Scope.REQUEST)
@View
public class WelcomePage extends BasePage {

	private static final Logger logger = Logger.getLogger(WelcomePage.class);

	@Property(value = "#{" + Constants.SECURITY_SERVICE + "}")
	private SecurityService securityService;

	@Property(value = "#{" + Constants.SYSTEM_STATISTIC + "}")
	private SystemStatisticInfo systemStatistic;

	@Property(value = "#{" + Constants.ONLINE_STATISTIC_SERVICE + "}")
	private OnlineStatisticService onlineStatisticService;
	
	@Property(value = "#{views$secured$myuni$myuni}")
	private MyUniPage myUniPage;

	@Prerender
	public void prerender() throws Exception { // NOPMD idueppe
		logger.debug("starting method prerender");
		
		myUniPage.prerender();

		// FIXME Refactory - why we are doing this?
		if (user != null && user.getId() == null) {
			user = new UserInfo();
			setSessionBean(Constants.USER, null);
		}
		setRequestBean(Constants.BREADCRUMBS, null);
		setSystemStatistic(getOnlineStatisticService().getSystemStatistics());
		setSessionBean(Constants.SYSTEM_STATISTIC, getSystemStatistic());
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public SystemStatisticInfo getSystemStatistic() {
		return systemStatistic;
	}

	public void setSystemStatistic(SystemStatisticInfo systemStatistic) {
		this.systemStatistic = systemStatistic;
	}

	public OnlineStatisticService getOnlineStatisticService() {
		return onlineStatisticService;
	}

	public void setOnlineStatisticService(OnlineStatisticService onlineStatisticService) {
		this.onlineStatisticService = onlineStatisticService;
	}

	public void setMyUniPage(MyUniPage myUniPage) {
		this.myUniPage = myUniPage;
	}

}