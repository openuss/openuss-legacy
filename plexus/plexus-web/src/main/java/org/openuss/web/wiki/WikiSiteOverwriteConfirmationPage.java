package org.openuss.web.wiki;

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
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;

/** Controller for the wikisiteoverwriteconfirmation.xhtml view.
 * 
 * @author Christian Beer
 */
@Bean(name = "views$secured$wiki$wikioverwritesite", scope = Scope.REQUEST)
@View
public class WikiSiteOverwriteConfirmationPage extends AbstractWikiPage {
	
	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(WikiSiteOverwriteConfirmationPage.class);

	private static final long serialVersionUID = -202000019652888870L;

	@Prerender
	public void prerender() throws LectureException {
		try {
			super.prerender();
			breadcrumbs.loadCourseCrumbs(courseInfo);
			
			BreadCrumb newCrumb = new BreadCrumb();
			newCrumb.setName(i18n("wiki_site_overwrite_header"));
			newCrumb.setHint(i18n("wiki_site_overwrite_header"));
			breadcrumbs.addCrumb(newCrumb);
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	/**
	 * Overwrite latest version
	 * @return outcome 
	 */
	public String overwriteSite()  {
		addMessage(i18n("wiki_site_save_succeeded"));
		
		this.siteVersionInfo.setId(null);
		if (this.siteVersionInfo.getName() == null) {
			this.siteVersionInfo.setName((String)getSessionBean(Constants.WIKI_NEW_SITE_NAME));
		}
		this.siteVersionInfo.setCreationDate(new Date());
		this.siteVersionInfo.setAuthorId(user.getId());
		this.siteVersionInfo.setDomainId(this.courseInfo.getId());
		this.siteVersionInfo.setDeleted(false);
		this.siteVersionInfo.setReadOnly(false);
		this.siteVersionInfo.setStable(false);
		
		getWikiService().saveWikiSite(this.siteVersionInfo);
		
		setSiteVersionId(null);
		
		return Constants.WIKI_MAIN_PAGE;
	}
	
	/**
	 * Validator to check wether the user has accepted the user agreement or not.
	 * 
	 * @param context
	 * @param toValidate
	 * @param value
	 */
	public void validateOverwriteConfirmation(FacesContext context, UIComponent toValidate, Object value) {
		boolean accept = (Boolean) value;
		if (!accept) {
			((UIInput) toValidate).setValid(false);
			addError(toValidate.getClientId(context), i18n("error_need_to_confirm_overwrite"), null);
		}
	}
}
