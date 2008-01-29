package org.openuss.web.wiki;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;


@Bean(name = "views$secured$wiki$editlinks", scope = Scope.REQUEST)
@View
public class WikiEditLinksPage extends AbstractWikiPage{
	private static final Logger logger = Logger.getLogger(WikiEditLinksPage.class);
	
	@Override
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		
	}
	
}