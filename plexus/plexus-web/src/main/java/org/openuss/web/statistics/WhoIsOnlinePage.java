package org.openuss.web.statistics;


import java.util.List;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.statistics.OnlineStatisticService;
import org.openuss.statistics.OnlineUserInfo;
import org.openuss.web.BasePage;

/**
 * @author Sebastian Roekens
 */
@Bean(name = "views$secured$statistics$whoisonline", scope = Scope.REQUEST)
@View
public class WhoIsOnlinePage extends BasePage {

	private OnlineSessionDataProvider data = new OnlineSessionDataProvider();
	
	@Property(value="#{onlineStatisticService}")
	private OnlineStatisticService onlineStatisticService; 

	private class OnlineSessionDataProvider extends AbstractPagedTable<OnlineUserInfo> {

		private static final long serialVersionUID = -6154567464715182827L;
		
		private DataPage<OnlineUserInfo> page;

		@SuppressWarnings("unchecked")
		@Override
		public DataPage<OnlineUserInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<OnlineUserInfo> users = getOnlineStatisticService().getActiveUsers();
				sort(users);
				page = new DataPage<OnlineUserInfo>(users.size(), 0, users);
			}
			return page;
		}
	}
	
	@Prerender
	public void prerender() {
		breadcrumbs.init();
		BreadCrumb newCrumb = new BreadCrumb();
		
		newCrumb.setName(i18n("whoisonline_header"));
		newCrumb.setHint(i18n("whoisonline_header"));
		
		breadcrumbs.addCrumb(newCrumb);
		
	}

	public OnlineSessionDataProvider getData() {
		return data;
	}

	public void setData(OnlineSessionDataProvider data) {
		this.data = data;
	}

	public OnlineStatisticService getOnlineStatisticService() {
		return onlineStatisticService;
	}

	public void setOnlineStatisticService(OnlineStatisticService onlineStatisticService) {
		this.onlineStatisticService = onlineStatisticService;
	}

}