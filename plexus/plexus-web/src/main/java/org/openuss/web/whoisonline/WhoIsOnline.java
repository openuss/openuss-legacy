package org.openuss.web.whoisonline;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.openuss.web.servlets.ActiveSessionCounter;

/**
 * WorkAround until cluster enabled version is available
 * @author Ingo Dueppe
 */
@Bean(name="whoisonline", scope=Scope.APPLICATION)
public class WhoIsOnline {
	
	public int getCount() {
		return ActiveSessionCounter.getSessionCount();
	}

}
