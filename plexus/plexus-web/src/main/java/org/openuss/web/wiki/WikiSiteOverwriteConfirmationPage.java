package org.openuss.web.wiki;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.Constants;

/**
 * Backing Bean for wikioverwritesite.xhtml.
 * @author Projektseminar WS 07/08, Team Collaboration
 *
 */
@Bean(name = "views$secured$wiki$wikioverwritesite", scope = Scope.REQUEST)
@View
public class WikiSiteOverwriteConfirmationPage extends AbstractWikiPage {
	
	private static final Logger LOGGER = Logger.getLogger(WikiSiteOverwriteConfirmationPage.class);

	@Prerender
	public void prerender() {
		try {
			super.prerender();
			
			breadcrumbs.loadCourseCrumbs(courseInfo);
			
			BreadCrumb newCrumb = new BreadCrumb();
			newCrumb.setName(i18n("wiki_site_overwrite_header"));
			newCrumb.setHint(i18n("wiki_site_overwrite_header"));
			breadcrumbs.addCrumb(newCrumb);
		} catch (Exception e) {
			LOGGER.error(e);
		}
	}
	
	/**
	 * Overwrite latest Version and return Wiki Main Page.
	 * @return Wiki Main Page.
	 */
	public String overwriteSite()  {
		siteVersionInfo.setId(null);
		if (siteVersionInfo.getName() == null) {
			siteVersionInfo.setName((String) getSessionBean(Constants.WIKI_NEW_SITE_NAME));
		}
		
		Date creationDate = new Date();
		
		LOGGER.debug("Overwrting Site " + siteVersionInfo.getName() + ", (Version: " + SimpleDateFormat.getInstance().format(creationDate) + ").");
		
		siteVersionInfo.setCreationDate(creationDate);
		siteVersionInfo.setAuthorId(user.getId());
		siteVersionInfo.setDomainId(courseInfo.getId());
		siteVersionInfo.setDeleted(false);
		siteVersionInfo.setReadOnly(false);
		siteVersionInfo.setStable(false);
		
		getWikiService().saveWikiSite(siteVersionInfo);
		
		setSiteVersionId(null);
		
		addMessage(i18n("wiki_site_save_succeeded"));
		return Constants.WIKI_MAIN_PAGE;
	}
	
	/**
	 * Validator to check whether the user has accepted the user agreement or not.
	 * 
	 * @param context FacesContext.
	 * @param toValidate UIComponent that has to be validated.
	 * @param value Inserted Value.
	 */
	public void validateOverwriteConfirmation(FacesContext context, UIComponent toValidate, Object value) {
		boolean accept = (Boolean) value;
		if (!accept) {
			((UIInput) toValidate).setValid(false);
			addError(toValidate.getClientId(context), i18n("wiki_error_need_to_confirm_overwrite"), null);
		}
	}
}
