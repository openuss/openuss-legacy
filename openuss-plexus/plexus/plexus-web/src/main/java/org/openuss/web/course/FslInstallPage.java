package org.openuss.web.course;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.apache.shale.tiger.managed.Scope;

@Bean(name="views$secured$course$fslinstall", scope=Scope.REQUEST)
@View
public class FslInstallPage extends AbstractCoursePage{

	@Prerender
	public void prerender() throws Exception{
		super.prerender();
	}
}