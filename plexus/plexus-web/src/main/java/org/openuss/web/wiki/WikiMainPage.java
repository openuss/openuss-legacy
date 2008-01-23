package org.openuss.web.wiki;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.web.Constants;
import org.openuss.web.course.AbstractCoursePage;
import org.openuss.wiki.WikiPage;


@Bean(name = "views$secured$wiki$wikimain", scope = Scope.REQUEST)
@View
public class WikiMainPage extends AbstractCoursePage{
	private static final Logger logger = Logger.getLogger(WikiMainPage.class);
	
	public String save(){
		logger.debug("WikiPage saved");
		return Constants.SUCCESS;
	}


}