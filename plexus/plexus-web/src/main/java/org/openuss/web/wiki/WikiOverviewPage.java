package org.openuss.web.wiki;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.web.course.AbstractCoursePage;
import org.openuss.wiki.WikiService;
import org.openuss.wiki.WikiSiteInfo;

public class WikiOverviewPage extends AbstractWikiPage {
	
	private static final Logger LOGGER = Logger.getLogger(WikiEditLinksPage.class);
	
	@Override
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
	}

}
