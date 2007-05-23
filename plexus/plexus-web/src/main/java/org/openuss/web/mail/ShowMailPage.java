package org.openuss.web.mail;


import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;

@Bean(name = "views$secured$mailinglist$showmail", scope = Scope.REQUEST)
@View
public class ShowMailPage extends AbstractMailPage{
	@Prerender
	public void prerender() throws Exception {	
		super.prerender();
	}	
}
