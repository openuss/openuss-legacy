package org.openuss.web;

import javax.faces.el.ValueBinding;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.LectureException;

/**
 * 
 * @author Julian Reimann
 */
@View
@Bean(name = "views$public$messagebox", scope = Scope.REQUEST)
public class MessageboxPage extends BasePage {

	@Prerender
	public void prerender() throws LectureException {
		breadcrumbs.init();
		
		BreadCrumb newCrumb = new BreadCrumb();
		ValueBinding binding = getFacesContext().getApplication().createValueBinding("#{messagebox.title}");
		String title = (String)binding.getValue(getFacesContext());
		
		if(title != null)
		{
			newCrumb.setName(title);
			newCrumb.setHint(title);
			
			breadcrumbs.addCrumb(newCrumb);
		}
	}
	
}
