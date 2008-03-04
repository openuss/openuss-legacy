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
import org.openuss.documents.FileInfo;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.Constants;

@Bean(name = "views$secured$papersubmission$submissionfileremove", scope = Scope.REQUEST)
@View
public class PaperSubmissionFileRemovePage extends AbstractPaperSubmissionPage {
		
	private static final Logger logger = Logger.getLogger(PaperSubmissionFileRemovePage.class);

	@Property(value="#{sessionScope.papersubmission_selected_fileentries}")
	private List<FolderEntryInfo> entries;

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
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
		logger.trace("removing entries");
		if (entries != null) {
			documentService.removeFolderEntries(entries);
			removeSessionBean(Constants.PAPERSUBMISSION_SELECTED_FILEENTRIES);
			addMessage(i18n("documents_message_removing_files_succeed"));
		}
		return Constants.OUTCOME_BACKWARD;
	}

	/**
	 * Cancel the removing of folder entries. Deselect the entries
	 * @return OUTCOME_BACKWARD
	 */
	public String cancelEntries() {
		removeSessionBean(Constants.PAPERSUBMISSION_SELECTED_FILEENTRIES);
		return Constants.OUTCOME_BACKWARD;
	}
	
	
	
	/**
	 * Validator to check wether or not the removement is accepted
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