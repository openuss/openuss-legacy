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

/** Controller for the wikiimageremoveconfirmation.xhtml view.
 * 
 * @author Christian Beer
 */
@Bean(name = "views$secured$wiki$wikiremoveimage", scope = Scope.REQUEST)
@View
public class WikiImageRemoveConfirmationPage extends AbstractWikiPage {
	
	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(WikiImageRemoveConfirmationPage.class);

	private static final long serialVersionUID = -202000019652888870L;

	@Prerender
	public void prerender() throws LectureException {
		try {
			super.prerender();
			breadcrumbs.loadCourseCrumbs(courseInfo);
			
			BreadCrumb newCrumb = new BreadCrumb();
			newCrumb.setName(i18n("wiki_image_remove_header"));
			newCrumb.setHint(i18n("wiki_image_remove_header"));
			breadcrumbs.addCrumb(newCrumb);
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	/**
	 * Delete course including all data
	 * @return outcome
	 * @throws LectureException
	 */
	public String removeImage() throws LectureException {
		try {
			FolderEntryInfo entry = (FolderEntryInfo) getSessionBean(Constants.WIKI_IMAGE);
			getWikiService().deleteImage(entry.getId());
			
			setSessionBean(Constants.WIKI_IMAGE, null);
			addMessage(i18n("wiki_image_removed_succeed"));
			return Constants.WIKI_CHOOSE_IMAGE_PAGE;
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(i18n("workspace_cannot_be_removed"));
			return Constants.WIKI_CHOOSE_IMAGE_PAGE;
		}
	}
	
	/**
	 * Validator to check wether the user has accepted the user agreement or not.
	 * 
	 * @param context
	 * @param toValidate
	 * @param value
	 */
	public void validateRemoveConfirmation(FacesContext context, UIComponent toValidate, Object value) {
		boolean accept = (Boolean) value;
		if (!accept) {
			((UIInput) toValidate).setValid(false);
			addError(toValidate.getClientId(context), i18n("error_need_to_confirm_removement"), null);
		}
	}
}
