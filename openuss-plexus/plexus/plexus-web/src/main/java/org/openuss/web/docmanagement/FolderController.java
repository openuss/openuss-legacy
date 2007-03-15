package org.openuss.web.docmanagement;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.View;
import org.openuss.docmanagement.DistributionService;
import org.openuss.docmanagement.Folder;
import org.openuss.docmanagement.FolderImpl;

@Bean(name="folderController", scope=Scope.SESSION)
@View
public class FolderController{
	
	public Folder folder;
	
	@Property(value = "#{distributionService}")
	public DistributionService distributionService;

	
	public String save(){
		return "XXX";
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
}