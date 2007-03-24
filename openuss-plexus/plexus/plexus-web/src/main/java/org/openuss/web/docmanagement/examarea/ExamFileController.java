package org.openuss.web.docmanagement.examarea;

import java.sql.Timestamp;

import org.acegisecurity.context.SecurityContextHolder;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.View;
import org.apache.log4j.Logger;
import org.openuss.docmanagement.BigFile;
import org.openuss.docmanagement.BigFileImpl;
import org.openuss.docmanagement.DeadlineException;
import org.openuss.docmanagement.DocConstants;
import org.openuss.docmanagement.DocManagementException;
import org.openuss.docmanagement.DocRights;
import org.openuss.docmanagement.ExaminationService;
import org.openuss.docmanagement.SystemFolderException;

import org.openuss.docmanagement.NotAFileException;
import org.openuss.docmanagement.NotAFolderException;
import org.openuss.docmanagement.PathNotFoundException;
import org.openuss.docmanagement.ResourceAlreadyExistsException;
import org.openuss.web.docmanagement.AbstractDocPage;

@Bean(name = "examFileController", scope = Scope.SESSION)
@View
public class ExamFileController extends AbstractDocPage {

	@Property(value = "#{examinationService}")
	ExaminationService examinationService;

	public BigFile file;

	public static final Logger logger = Logger
			.getLogger(ExamFileController.class);

	public String save() {
		// visibility is only 0, if folder is a new folder
		file.setPath("/"+DocConstants.EXAMAREA+"/"+enrollment.getId().toString());
		file.setOwner(SecurityContextHolder.getContext().getAuthentication().getName());
		file.setDistributionTime(new Timestamp(System.currentTimeMillis()));
		file.setVisibility(DocRights.EDIT_OWNER | DocRights.READ_ASSIST | DocRights.READ_OWNER);
		try {
			if (!hasWritePermission(file)) {
				noPermission();
				return DocConstants.EXAMEXPLORER;
			}
			examinationService.addSubmission(file);
		} catch (NotAFolderException e) {
			handleNotAFolderException(e);
		} catch (PathNotFoundException e) {
			handlePathNotFoundException(e);
		} catch (ResourceAlreadyExistsException e) {
			handleResourceAlreadyExistsException(e);
		} catch (NotAFileException e) {
			handleNotAFileException(e);
		} catch (DeadlineException e){
			handleDeadlineException(e);
		} catch (SystemFolderException e) {
			handleDocManagementException(e);		
		} catch (DocManagementException e) {
			handleDocManagementException(e);
		}

		return DocConstants.EXAMEXPLORER;
	}

	public BigFile getFile() {
		if (file == null)
			file = new BigFileImpl();
		return file;
	}

	public void setFile(BigFile file) {
		this.file = file;
	}

	public boolean isVisibleForAll() {
		return (file.getVisibility() & DocRights.READ_ALL) > 0;
	}

	public ExaminationService getExaminationService() {
		return examinationService;
	}

	public void setExaminationService(ExaminationService examinationService) {
		this.examinationService = examinationService;
	}
}