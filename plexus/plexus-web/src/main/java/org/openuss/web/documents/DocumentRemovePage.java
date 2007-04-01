package org.openuss.web.documents;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.documents.DocumentApplicationException;
import org.openuss.documents.FolderEntryInfo;
import org.openuss.web.Constants;

@Bean(name = "views$secured$documents$remove", scope = Scope.REQUEST)
@View
public class DocumentRemovePage extends AbstractDocumentPage{
		
	private static final Logger logger = Logger.getLogger(DocumentRemovePage.class);
	

	@Property(value="#{sessionScope.documents_selected_folderentries}")
	private List<FolderEntryInfo> entries;
	
	/**
	 * Remove the current enrollment and all its data
	 * @return OUTCOME_BACKWARD
	 * @throws DocumentApplicationException 
	 */
	public String removeEntries() throws DocumentApplicationException {
		logger.trace("removing entries");
		if (entries != null) {
			documentService.removeFolderEntries(entries);
			removeSessionBean(Constants.DOCUMENTS_SELECTED_FOLDERENTRIES);
			addMessage(i18n("documents_message_removing_files_succeed"));
		}
		return Constants.OUTCOME_BACKWARD;
	}

	/**
	 * Cancel the removing of folder entries. Deselect the entries
	 * @return OUTCOME_BACKWARD
	 */
	public String cancelEntries() {
		removeSessionBean(Constants.DOCUMENTS_SELECTED_FOLDERENTRIES);
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