package org.openuss.web.security.profile;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * @author Sebastian Roekens
 *
 */
@Bean(name="views$secured$user$logindata", scope=Scope.REQUEST)
@View
public class LoginDataPage extends BasePage{
	
	@Prerender
	public void prerender() {
		setSessionBean(Constants.BREADCRUMBS, null);
	}
	

}
