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
import org.openuss.docmanagement.Link;
import org.openuss.docmanagement.LinkImpl;
import org.openuss.docmanagement.NotAFileException;
import org.openuss.docmanagement.NotAFolderException;
import org.openuss.docmanagement.PathNotFoundException;
import org.openuss.docmanagement.ResourceAlreadyExistsException;
import org.openuss.docmanagement.SystemFolderException;



@Bean(name="fileController", scope=Scope.SESSION)
@View
public class FileController extends AbstractDocPage{
	
	@Property(value = "#{distributionService}")
	DistributionService distributionService;
	
	public BigFile file;
	
	public boolean visibleForAll;
	
	public static final Logger logger = Logger.getLogger(FileController.class);
	
	public boolean old;
	
	public boolean link;
	
	public Link linkToEdit;
	
	public java.util.Date distributionTime;
	

	public String save(){
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
			} catch (SystemFolderException e) {
				handleDocManagementException(e);		
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
			} catch (SystemFolderException e) {
				handleDocManagementException(e);		
			} catch (DocManagementException e) {
				handleDocManagementException(e);
			}
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
		return (file.getVisibility()&DocRights.READ_ALL)>0;
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

	public java.util.Date getDistributionTime() {		
		return file.getDistributionTime();
	}

	public void setDistributionTime(java.util.Date distributionTime) {
		this.distributionTime = distributionTime;
	}

	public boolean isLink() {
		return link;
	}

	public void setLink(boolean link) {
		this.link = link;
	}

	public Link getLinkToEdit() {
		return linkToEdit;
	}

	public void setLinkToEdit(Link linkToEdit) {
		this.linkToEdit = linkToEdit;
	}
}