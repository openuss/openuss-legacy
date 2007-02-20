package org.openuss.web.docmanagement;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.docmanagement.DistributionService;

@Bean(name = "views$public$testBean", scope = Scope.REQUEST)
@View
public class TestBean{
	
	@Property(value = "#{distributionService}")
	public DistributionService distributionService;
	
	public String startMe(){
		distributionService.addFacultyFolder(null);
		return "desktop";
	}

	public DistributionService getDistributionService() {
		return distributionService;
	}

	public void setDistributionService(DistributionService distributionService) {
		this.distributionService = distributionService;
	}
}