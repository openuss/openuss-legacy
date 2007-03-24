package org.openuss.web.docmanagement;

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
import org.openuss.docmanagement.NotAFileException;
import org.openuss.docmanagement.NotAFolderException;
import org.openuss.docmanagement.PathNotFoundException;
import org.openuss.docmanagement.ResourceAlreadyExistsException;
import org.openuss.docmanagement.SystemFolderException;



@Bean(name="facultyFileController", scope=Scope.SESSION)
@View
public class FacultyFileController extends AbstractDocPage{

	@Property(value = "#{distributionService}")
	DistributionService distributionService;
	
	public BigFile file;
	
	public static final Logger logger = Logger.getLogger(FacultyFileController.class);
	
	public boolean old;
	
	public String save(){
		//visibility is only 0, if folder is a new folder
		old = (file.getVisibility()!=0);
		
		if (!old) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ValueBinding valueBinding = facesContext.getApplication().createValueBinding("#{facultyViewBacker}");
			FacultyViewBacker fvb = (FacultyViewBacker)valueBinding.getValue(facesContext);
			file.setPath(fvb.getFolderPath());
		}
		file.setDistributionTime(new Timestamp(System.currentTimeMillis()));
		file.setVisibility(DocRights.EDIT_ASSIST|DocRights.READ_ASSIST);
		try {
			if (!hasWritePermission(file)){
				noPermission();
				return DocConstants.FACULTY_EXPLORER;
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
		} catch (SystemFolderException e) {
			handleDocManagementException(e);		
		} catch (DocManagementException e) {
			handleDocManagementException(e);
		}	
		return DocConstants.FACULTY_EXPLORER;
	}

	public BigFile getFile() {
		if (file==null) file = new BigFileImpl();
		return file;
	}

	public void setFile(BigFile file) {
		this.file = file;
	}

	public DistributionService getDistributionService() {
		return distributionService;
	}

	public void setDistributionService(DistributionService distributionService) {
		this.distributionService = distributionService;
	}

	public boolean isVisibleForAll() {
		return (file.getVisibility()&DocRights.READ_ALL)>0;
	}

	public boolean isOld() {
		return old;
	}
	
	public void setOld(boolean old) {
		this.old = old;
	}

	public java.util.Date getDistributionTime() {		
		return file.getDistributionTime();
	}

}