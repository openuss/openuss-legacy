package org.openuss.web.wiki;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;
import org.openuss.wiki.WikiSiteInfo;

/**
 * Backing Bean for wikiremoveconfirmationsite.xhtml.
 * @author Projektseminar WS 07/08, Team Collaboration
 *
 */
@Bean(name = "views$secured$wiki$wikiremovesite", scope = Scope.REQUEST)
@View
public class WikiSiteRemoveConfirmationPage extends AbstractWikiPage {
	
	private static final Logger LOGGER = Logger.getLogger(WikiSiteRemoveConfirmationPage.class);

	@Prerender
	public void prerender() throws LectureException {
		try {
			super.prerender();
			breadcrumbs.loadCourseCrumbs(courseInfo);
			
			final BreadCrumb newCrumb = new BreadCrumb();
			newCrumb.setName(i18n("wiki_site_remove_header"));
			newCrumb.setHint(i18n("wiki_site_remove_header"));
			breadcrumbs.addCrumb(newCrumb);
		} catch (Exception e) {
			LOGGER.error(e);
		}
	}
	
	/**
	 * Removes a Site including all Versions and returns Wiki Main Page.
	 * @return Wiki Main Page.
	 */
	public String removeSite() {		
		final WikiSiteInfo site = (WikiSiteInfo) getSessionBean(Constants.WIKI_SITE_TO_REMOVE);
		LOGGER.debug("Removing Site " + site.getName() + ".");
		getWikiService().deleteWikiSite(site.getWikiSiteId());
		
		setSessionBean(Constants.WIKI_SITE_TO_REMOVE, null);
		
		addMessage(i18n("wiki_site_removed_succeed"));
		return Constants.WIKI_MAIN_PAGE;
	}
	
	/**
	 * Validator to check whether the user has accepted the user agreement or not.
	 * 
	 * @param context FacesContext.
	 * @param toValidate UIComponent that has to be validated.
	 * @param value Inserted Value.
	 */
	public void validateRemoveConfirmation(FacesContext context, UIComponent toValidate, Object value) {
		boolean accept = (Boolean) value;
		if (!accept) {
			((UIInput) toValidate).setValid(false);
			addError(toValidate.getClientId(context), i18n("error_need_to_confirm_removement"), null);
		}
	}
}
