package org.openuss.web.chat;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.web.course.AbstractCoursePage;

/**
 * @author Sebastian Roekens
 */
@Bean(name = "views$secured$course$chatmain", scope = Scope.REQUEST)
@View
public class ChatMainPage extends AbstractCoursePage {

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
	}

}