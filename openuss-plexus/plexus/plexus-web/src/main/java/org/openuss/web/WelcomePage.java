package org.openuss.web;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.apache.shale.tiger.managed.Scope;
import org.openuss.lecture.LectureService;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.statistics.OnlineStatisticService;
import org.openuss.statistics.SystemStatisticInfo;

@Bean(name="views$welcome", scope=Scope.REQUEST)
@View
public class WelcomePage extends BasePage{

	@Property (value="#{"+Constants.LECTURE_SERVICE+"}")
	private LectureService lectureService;
	
	@Property (value="#{"+Constants.SECURITY_SERVICE+"}")
	private SecurityService securityService;
	
	@Property (value="#{"+Constants.SYSTEM_STATISTIC+"}")
	private SystemStatisticInfo systemStatistic;	
	
	@Property (value="#{"+Constants.ONLINE_STATISTIC_SERVICE+"}")
	private OnlineStatisticService onlineStatisticService;
	
	@Prerender
	public void prerender() throws Exception{
		super.prerender();
		if (user != null && user.getId()==null) {
				user = User.Factory.newInstance();
				user.setPreferences(null);
				user.setContact(null);
				setSessionBean(Constants.USER, null);
		}
		setRequestBean(Constants.BREADCRUMBS, null);
		setSystemStatistic(getOnlineStatisticService().getSystemStatistics());
		setSessionBean(Constants.SYSTEM_STATISTIC, getSystemStatistic());
	}
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	public LectureService getLectureService() {
		return lectureService;
	}
	public void setLectureService(LectureService lectureService) {
		this.lectureService = lectureService;
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
	public void setOnlineStatisticService(
			OnlineStatisticService onlineStatisticService) {
		this.onlineStatisticService = onlineStatisticService;
	}

}