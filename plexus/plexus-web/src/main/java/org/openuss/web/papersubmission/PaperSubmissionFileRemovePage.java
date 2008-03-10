package org.openuss.web.papersubmission;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.documents.DocumentApplicationException;
import org.openuss.documents.FolderEntryInfo;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;

@Bean(name = "views$secured$papersubmission$submissionfileremove", scope = Scope.REQUEST)
@View
public class PaperSubmissionFileRemovePage extends AbstractPaperSubmissionPage {
		
	private static final Logger LOGGER = Logger.getLogger(PaperSubmissionFileRemovePage.class);

	@Property(value="#{sessionScope.papersubmission_selected_fileentries}")
	private List<FolderEntryInfo> entries;

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink(PageLinks.PAPERSUBMISSION_EXAM);
		crumb.setName(i18n("papersubmission_paperlist_header"));
		crumb.setHint(i18n("papersubmission_paperlist_header"));

		breadcrumbs.loadCourseCrumbs(courseInfo);
		breadcrumbs.addCrumb(crumb);
		
		crumb = new BreadCrumb();
		crumb.setName(examInfo.getName());
		crumb.setHint(examInfo.getName());
		
		if(courseInfo != null && courseInfo.getId() != null 
				&& examInfo != null && examInfo.getId() != null){
			
			crumb.setLink(PageLinks.PAPERSUBMISSION_SUBMISSIONVIEW);
			crumb.addParameter("course",courseInfo.getId());
			crumb.addParameter("exam",examInfo.getId());
		}
		
		breadcrumbs.addCrumb(crumb);
		
		crumb = new BreadCrumb();
		crumb.setName(i18n("documents_remove_header"));
		crumb.setHint(i18n("documents_remove_header"));
		breadcrumbs.addCrumb(crumb);
	}	
	
	/** 
	 * Remove the current course and all its data
	 * @return OUTCOME_BACKWARD
	 * @throws DocumentApplicationException 
	 */
	public String removeEntries() throws DocumentApplicationException {
		LOGGER.trace("removing entries");
		if (entries != null) {
			documentService.removeFolderEntries(entries);
			removeSessionBean(Constants.PAPERSUBMISSION_FOLDERENTRY_SELECTION);
			addMessage(i18n("documents_message_removing_files_succeed"));
		}
		return Constants.OUTCOME_BACKWARD;
	}

	/**
	 * Cancel the removing of folder entries. Deselect the entries
	 * @return OUTCOME_BACKWARD
	 */
	public String cancelEntries() {
		removeSessionBean(Constants.PAPERSUBMISSION_FOLDERENTRY_SELECTION);
		return Constants.OUTCOME_BACKWARD;
	}
	
	/**
	 * Validator to check whether or not the removement is accepted
	 * @param context
	 * @param toValidate
	 * @param value
	 */
	public void validateRemoveConfirmation(FacesContext context, UIComponent toValidate, Object value) {
		boolean accept = (Boolean) value;
		if (!accept) {
			((UIInput)toValidate).setValid(false);
			addError(toValidate.getClientId(context), i18n("error_need_to_confirm_removement"), null);
		}
	}

	public List<FolderEntryInfo> getEntries() {
		return entries;
	}

	public void setEntries(List<FolderEntryInfo> entries) {
		this.entries = entries;
	}

}