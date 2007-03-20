package org.openuss.web.docmanagement;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.docmanagement.DistributionService;
import org.openuss.docmanagement.DistributionServiceImpl;
import org.openuss.docmanagement.DocConstants;
import org.apache.log4j.Logger;

@Bean(name="adminViewBacker", scope=Scope.SESSION)
@View
public class AdminViewBacker{
	
	public static final Logger logger = Logger.getLogger(AdminViewBacker.class);
	
	@Property(value="#{distributionService}")
	public DistributionService distributionService;

	/**
	 * only for testing purposes 
	 * @return
	 */	
	public String addTestStructure(){
		try{
			((DistributionServiceImpl)distributionService).buildTestStructure();
		} catch (Exception e){
			logger.error(e);
		}
		return DocConstants.DOCUMENTEXPLORER;
	}

	
	public String clearRepository(){
		try{
			((DistributionServiceImpl)distributionService).clearRepository();
		} catch (Exception e){
			logger.error(e);
		}
		return DocConstants.DOCUMENTEXPLORER;
	}

	public String buildMainRepositoryStructure(){
		try{
			((DistributionServiceImpl)distributionService).buildMainRepositoryStructure();
		} catch (Exception e){
			logger.error(e);
		}
		return DocConstants.DOCUMENTEXPLORER;
	}


	public DistributionService getDistributionService() {
		return distributionService;
	}


	public void setDistributionService(DistributionService distributionService) {
		this.distributionService = distributionService;
	}
	
	

}