package org.openuss.web.statistics;

import javax.servlet.http.HttpSession;

import org.acegisecurity.event.authentication.AuthenticationSuccessEvent;
import org.acegisecurity.ui.session.HttpSessionCreatedEvent;
import org.acegisecurity.ui.session.HttpSessionDestroyedEvent;
import org.apache.log4j.Logger;
import org.openuss.statistics.OnlineStatisticService;
import org.openuss.web.Constants;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * 
 * @author Ingo Dueppe
 */
public class OnlineSessionTracker implements ApplicationListener{

	private static final Logger logger = Logger.getLogger(OnlineSessionTracker.class);
	
	private OnlineStatisticService onlineStatisticService;

	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof HttpSessionCreatedEvent) {
			logSessionCreated(((HttpSessionCreatedEvent)event).getSession());
		} else if (event instanceof HttpSessionDestroyedEvent) {
			logSessionDestroyed(((HttpSessionDestroyedEvent)event).getSession());
		} else if (event instanceof AuthenticationSuccessEvent) {
			logger.debug("==>"+((AuthenticationSuccessEvent)event).getAuthentication());
		}
	}

	private void logSessionDestroyed(HttpSession session) {
		logger.debug("session destroyed");
		Long sessionId = (Long) session.getAttribute(Constants.ONLINE_SESSION_ID);
		if (sessionId != null) {
			onlineStatisticService.logSessionEnd(sessionId);
		} else {
			logger.warn("session ends without session id!");
		}
	}

	public void logSessionCreated(HttpSession session) {
		logger.debug("session created");
		Long sessionId = (Long) session.getAttribute(Constants.ONLINE_SESSION_ID);
		sessionId = onlineStatisticService.logSessionStart(sessionId);
		session.setAttribute(Constants.ONLINE_SESSION_ID, sessionId);
	}

	public OnlineStatisticService getOnlineStatisticService() {
		return onlineStatisticService;
	}

	public void setOnlineStatisticService(OnlineStatisticService onlineStatisticService) {
		this.onlineStatisticService = onlineStatisticService;
	}

}
