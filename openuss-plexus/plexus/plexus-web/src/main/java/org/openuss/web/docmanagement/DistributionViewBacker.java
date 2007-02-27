package org.openuss.web.docmanagement;

import org.apache.myfaces.custom.tree2.TreeNode;
import org.openuss.docmanagement.DistributionService;

public class DistributionViewBacker{
	public DistributionService distributionService;
	
	//public TreeNode treeData;

	public TreeNode getTreeData(){
		return distributionService.getTreeData();		
	}
	
	public DistributionService getDistributionService() {
		return distributionService;
	}

	public void setDistributionService(DistributionService distributionService) {
		this.distributionService = distributionService;
	}
}