package org.openuss.web.docmanagement;

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
import org.openuss.docmanagement.DocRights;
import org.openuss.docmanagement.File;
import org.openuss.docmanagement.FileImpl;
import org.openuss.docmanagement.PathNotFoundException;
import org.openuss.docmanagement.ResourceAlreadyExistsException;



@Bean(name="fileController", scope=Scope.SESSION)
@View
public class FileController{
	
	@Property(value = "#{distributionService}")
	DistributionService distributionService;
	
	public BigFile file;
	
	public boolean visibleForAll;
	
	public static final Logger logger = Logger.getLogger(FileController.class);
	
	public boolean old;
	

	public String save(){
		//visibility is only 0, if folder is a new folder
		old = (file.getVisibility()!=0);
		
		if (!old) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ValueBinding valueBinding = facesContext.getApplication().createValueBinding("#{distributionViewBacker}");
			DistributionViewBacker dvb = (DistributionViewBacker)valueBinding.getValue(facesContext);
			file.setPath(dvb.getPath());
		}
		if (visibleForAll) file.setVisibility(DocRights.EDIT_ASSIST|DocRights.READ_ALL);
		else if (!visibleForAll) file.setVisibility(DocRights.EDIT_ASSIST|DocRights.READ_ASSIST);
		try {
			distributionService.changeFile(file, old);
		} catch (PathNotFoundException e) {
			logger.error("Path not found: ", e);
		} catch (ResourceAlreadyExistsException e) {
			logger.error("Resource already exists: ", e);
		}
		return DocConstants.DOCUMENTEXPLORER;
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
		return visibleForAll;
	}

	public void setVisibleForAll(boolean visibleForAll) {
		this.visibleForAll = visibleForAll;
	}

	public boolean isOld() {
		return old;
	}
	
	public void setOld(boolean old) {
		this.old = old;
	}
}