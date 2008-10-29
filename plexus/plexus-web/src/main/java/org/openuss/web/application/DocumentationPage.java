package org.openuss.web.application;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.web.BasePage;

/**
 * @author Sebastian Roekens
 */
@View
@Bean(name = "views$public$documentation", scope = Scope.REQUEST)
public class DocumentationPage extends BasePage{

	/**
	 * @throws Exception
	 */
	@Prerender
	public void prerender(){
		//breadcrumbs shall not be displayed here
//		setRequestBean(Constants.BREADCRUMBS, null);
	}


}
