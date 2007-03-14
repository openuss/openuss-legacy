package org.openuss.web.docmanagement;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.View;
import org.openuss.docmanagement.DistributionService;
import org.openuss.docmanagement.File;
import org.openuss.docmanagement.FileImpl;

@Bean(name="fileController", scope=Scope.REQUEST)
@View
public class FileController{
	
	@Property(value = "#{distributionService}")
	DistributionService distributionService;
	
	public File file;
	
	public String save(){
		return "XXX";
	}

	public File getFile() {
		if (file==null) file = new FileImpl();
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public DistributionService getDistributionService() {
		return distributionService;
	}

	public void setDistributionService(DistributionService distributionService) {
		this.distributionService = distributionService;
	}
}