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

	@Prerender
	public void prerender() throws Exception { // NOPMD idueppe
		super.prerender();
		logger.debug("starting method prerender");

		// FIXME Factory - why we are doing this?
		if (user != null && user.getId() == null) {
			logger.debug("in user if clause");
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

}