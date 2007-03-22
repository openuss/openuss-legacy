package org.openuss.web.docmanagement.examarea;

import java.sql.Timestamp;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.View;
import org.apache.log4j.Logger;
import org.openuss.docmanagement.BigFile;
import org.openuss.docmanagement.BigFileImpl;
import org.openuss.docmanagement.DistributionService;
import org.openuss.docmanagement.DocConstants;
import org.openuss.docmanagement.DocManagementException;
import org.openuss.docmanagement.DocRights;
import org.openuss.docmanagement.ExaminationService;
import org.openuss.docmanagement.Link;
import org.openuss.docmanagement.LinkImpl;
import org.openuss.docmanagement.NotAFileException;
import org.openuss.docmanagement.NotAFolderException;
import org.openuss.docmanagement.PathNotFoundException;
import org.openuss.docmanagement.ResourceAlreadyExistsException;
import org.openuss.web.docmanagement.AbstractEnrollmentDocPage;



@Bean(name="examFileController", scope=Scope.SESSION)
@View
public class ExamFileController extends AbstractEnrollmentDocPage{
	
	@Property(value = "#{examinationService}")
	ExaminationService examinationService;
	
	public BigFile file;
	
	public static final Logger logger = Logger.getLogger(ExamFileController.class);
	
 	public String save(){
/*
 		if (!link) {
			//visibility is only 0, if folder is a new folder
			old = (file.getVisibility() != 0);
			if (!old) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				ValueBinding valueBinding = facesContext.getApplication()
						.createValueBinding("#{distributionViewBacker}");
				DistributionViewBacker dvb = (DistributionViewBacker) valueBinding
						.getValue(facesContext);
				file.setPath(dvb.getFolderPath());
			}
			file.setDistributionTime(new Timestamp(distributionTime.getTime()));
			if (visibleForAll)
				file.setVisibility(DocRights.EDIT_ASSIST | DocRights.READ_ALL);
			else if (!visibleForAll)
				file.setVisibility(DocRights.EDIT_ASSIST
						| DocRights.READ_ASSIST);
			try {
				if (!hasWritePermission(file)) {
					noPermission();
					return DocConstants.DOCUMENTEXPLORER;
				}
				distributionService.changeFile(file, old);
			} catch (NotAFolderException e) {
				handleNotAFolderException(e);
			} catch (PathNotFoundException e) {
				handlePathNotFoundException(e);
			} catch (ResourceAlreadyExistsException e) {
				handleResourceAlreadyExistsException(e);
			} catch (NotAFileException e) {
				handleNotAFileException(e);
			} catch (DocManagementException e) {
				handleDocManagementException(e);
			}
		} else if (link){
			Link editedLink = new LinkImpl();
			editedLink.setDistributionDate(new Timestamp(getDistributionTime().getTime()));
			editedLink.setMessage(file.getMessage());
			editedLink.setName(file.getName());
			editedLink.setPath(file.getPath());
			if (visibleForAll)
				editedLink.setVisibility(DocRights.EDIT_ASSIST | DocRights.READ_ALL);
			else if (!visibleForAll)
				editedLink.setVisibility(DocRights.EDIT_ASSIST
						| DocRights.READ_ASSIST);
			try {
				if (!hasWritePermission(editedLink)) {
					noPermission();
					return DocConstants.DOCUMENTEXPLORER;
				}
				distributionService.changeLink(editedLink);
			} catch (NotAFolderException e) {
				handleNotAFolderException(e);
			} catch (PathNotFoundException e) {
				handlePathNotFoundException(e);
			} catch (ResourceAlreadyExistsException e) {
				handleResourceAlreadyExistsException(e);
			} catch (NotAFileException e) {
				handleNotAFileException(e);
			} catch (DocManagementException e) {
				handleDocManagementException(e);
			}
		}*/
		return DocConstants.DOCUMENTEXPLORER;
	}

	public BigFile getFile() {
		if (file==null) file = new BigFileImpl();
		return file;
	}

	public void setFile(BigFile file) {
		this.file = file;
	}

	
	public boolean isVisibleForAll() {
		return (file.getVisibility()&DocRights.READ_ALL)>0;
	}
	
	public ExaminationService getExaminationService() {
		return examinationService;
	}

	public void setExaminationService(ExaminationService examinationService) {
		this.examinationService = examinationService;
	}
}