package org.openuss.web.docmanagement;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.View;
import org.openuss.docmanagement.DistributionService;
import org.openuss.docmanagement.DocRights;
import org.openuss.docmanagement.Folder;
import org.openuss.docmanagement.FolderImpl;
import org.openuss.docmanagement.DocConstants;
import org.openuss.docmanagement.PathNotFoundException;
import org.openuss.docmanagement.ResourceAlreadyExistsException;
import org.apache.log4j.Logger;

@Bean(name="folderController", scope=Scope.SESSION)
@View
public class FolderController{
	
	public Folder folder;
	
	public boolean visibleForAll;
	
	@Property(value = "#{distributionService}")
	public DistributionService distributionService;

	public static final Logger logger = Logger.getLogger(FolderController.class);
	
	public String save(){
		//visibility is only 0, if folder is a new folder
		boolean old = (folder.getVisibility()!=0);
		
		if (!old) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ValueBinding valueBinding = facesContext.getApplication().createValueBinding("#{distributionViewBacker}");
			DistributionViewBacker dvb = (DistributionViewBacker)valueBinding.getValue(facesContext);
			folder.setPath(dvb.getPath());
		}
		if (visibleForAll) folder.setVisibility(DocRights.EDIT_ASSIST|DocRights.READ_ALL);
		else if (!visibleForAll) folder.setVisibility(DocRights.EDIT_ASSIST|DocRights.READ_ASSIST);
		try {
			distributionService.changeFolder(folder, old);
		} catch (PathNotFoundException e) {
			logger.error("Path not found: ", e);
		} catch (ResourceAlreadyExistsException e) {
			logger.error("Resource already exists: ", e);
		}
		return DocConstants.DOCUMENTEXPLORER;
	}
	
	public Folder getFolder() {
		if (folder==null) folder = new FolderImpl();
		return folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
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

}