package org.openuss.web.statistics;

import org.apache.log4j.Logger;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.openuss.statistics.OnlineInfo;
import org.openuss.statistics.OnlineStatisticService;

/**
 * WorkAround until cluster enabled version is available
 * @author Ingo Dueppe
 */
@Bean(name="whoisonline", scope=Scope.APPLICATION)
public class WhoIsOnline {

	private static final Logger logger = Logger.getLogger(WhoIsOnline.class);
	
	@Property(value="#{onlineStatisticService}")
	private OnlineStatisticService onlineStatisticService;
	
	private OnlineInfo onlineInfo;
	
	private Date nextRefresh = new Date();
	
	private synchronized void refresh() {
		Date now = new Date();
		if (onlineInfo == null || nextRefresh.before(now)) {
			logger.debug("refreshing who is online info");
			onlineInfo = onlineStatisticService.getOnlineInfo();
			nextRefresh = DateUtils.addSeconds(now, 2);
		}
	}
	
	public Long getUserCount() {
		refresh();
		return onlineInfo.getUsers();
	}
	
	public Long getGuestCount() {
		refresh();
		return onlineInfo.getTotal() - onlineInfo.getUsers();
	}

	public OnlineStatisticService getOnlineStatisticService() {
		return onlineStatisticService;
	}

	public void setOnlineStatisticService(OnlineStatisticService onlineStatisticService) {
		this.onlineStatisticService = onlineStatisticService;
	}

}
