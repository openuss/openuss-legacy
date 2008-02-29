package org.openuss.web.wiki;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.documents.FolderEntryInfo;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;

/**
 * Backing Bean for wikiremoveimage.xhtml.
 * @author Projektseminar WS 07/08, Team Collaboration
 *
 */
@Bean(name = "views$secured$wiki$wikiremoveimage", scope = Scope.REQUEST)
@View
public class WikiImageRemoveConfirmationPage extends AbstractWikiPage {
	
	private static final Logger LOGGER = Logger.getLogger(WikiImageRemoveConfirmationPage.class);

	private static final long serialVersionUID = -202000019652888870L;

	@Prerender
	public void prerender() throws LectureException {
		try {
			super.prerender();
			breadcrumbs.loadCourseCrumbs(courseInfo);
			
			final BreadCrumb newCrumb = new BreadCrumb();
			newCrumb.setName(i18n(Constants.WIKI_IMAGE_REMOVE_HEADER));
			newCrumb.setHint(i18n(Constants.WIKI_IMAGE_REMOVE_HEADER));
			breadcrumbs.addCrumb(newCrumb);
		} catch (Exception exception) {
			LOGGER.error(exception);
		}
	}
	
	/**
	 * Removes a specific image and return the Wiki Choose Image Page.
	 * @return Wiki Choose Image Page.
	 */
	public String removeImage() {
		try {
			FolderEntryInfo entry = (FolderEntryInfo) getSessionBean(Constants.WIKI_IMAGE);
			getWikiService().deleteImage(entry.getId());
			
			setSessionBean(Constants.WIKI_IMAGE, null);
			addMessage(i18n(Constants.WIKI_IMAGE_REMOVE_SUCCEEDED));
			return Constants.WIKI_CHOOSE_IMAGE_PAGE;
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(i18n(Constants.WIKI_IMAGE_CANNOT_BE_REMOVED));
			return Constants.WIKI_CHOOSE_IMAGE_PAGE;
		}
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
