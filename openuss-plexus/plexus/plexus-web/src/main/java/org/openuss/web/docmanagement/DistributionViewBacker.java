package org.openuss.web.docmanagement;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.myfaces.custom.tree2.TreeModel;
import org.apache.myfaces.custom.tree2.TreeModelBase;
import org.apache.myfaces.custom.tree2.TreeNodeBase;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.View;
import org.openuss.docmanagement.DistributionService;
import org.openuss.docmanagement.DistributionServiceImpl;
import org.openuss.docmanagement.File;
import org.openuss.docmanagement.FileImpl;
import org.openuss.docmanagement.Folder;
import org.openuss.docmanagement.Resource;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.web.Constants;

@Bean(name="distributionViewBacker", scope=Scope.REQUEST)
@View
public class DistributionViewBacker{

	@Property(value="#{distributionService}")
	public DistributionService distributionService;

	private static final Logger logger = Logger.getLogger(DistributionViewBacker.class);
	
	FileDataProvider data = new FileDataProvider();

	public TreeModel getTree(){
		DistributionServiceImpl dsi =  (DistributionServiceImpl) distributionService;
		try{
			dsi.buildTestStructure();
		} catch (Exception e){			
		}
		Folder folder = distributionService.getMainFolder(null);
		return new TreeModelBase(folder2TreeNodeBase(folder));		
	}
	
	private TreeNodeBase folder2TreeNodeBase(Folder folder){
		TreeNodeBase tn = new TreeNodeBase("folder", folder.getName(), folder.getId(), (folder.getSubnodes()==null));
		Folder f;
		if (folder.getSubnodes()!=null){
			Collection<Resource> v = folder.getSubnodes();
			Iterator i = v.iterator();
			while (i.hasNext()){
				//TODO test what instance i.next() is of
				f = (Folder) i.next();
				tn.getChildren().add(folder2TreeNodeBase(f));
			}
		}
		return tn;		
	}
	
	public TreeModel getTree2(){
		TreeNodeBase root = new TreeNodeBase("folder", "root", "0" , false);
		TreeNodeBase tn1 = new TreeNodeBase("folder", "Test123", "1" , true);
		TreeNodeBase tn2 = new TreeNodeBase("folder", "Sebastian", "2", false);
		TreeNodeBase tn2a = new TreeNodeBase("folder", "Roekens", "4", true);
		TreeNodeBase tn3 = new TreeNodeBase("folder", "Blubb", "3", true);
		tn2.getChildren().add(tn2a);
		root.getChildren().add(tn1);
		root.getChildren().add(tn2);
		root.getChildren().add(tn3);		
		return new TreeModelBase(root);
	}

	private class FileDataProvider extends AbstractPagedTable<File> {

		private DataPage<File> page; 
		
		@Override 
		public DataPage<File> getDataPage(int startRow, int pageSize) {		
			ArrayList<File> al = new ArrayList<File>();			
			File f1 = new FileImpl();
			f1.setName("TestFolder");
			f1.setDistributionTime(new Timestamp(System.currentTimeMillis()));
			File f2 = new FileImpl();
			f2.setName("TestFolder 2");
			f2.setDistributionTime(new Timestamp(System.currentTimeMillis()));
			File f3 = new FileImpl();
			f3.setName("TestFolder 3");
			f3.setDistributionTime(new Timestamp(System.currentTimeMillis()));
			page = new DataPage<File>(al.size(),0,al);
			return page;
		}
	}
	
	public String addTestStructure(){
		try{
			((DistributionServiceImpl)distributionService).buildTestStructure();
		} catch (Exception e){
			logger.error(e);
		}
		return Constants.SUCCESS;
	}
	
	
	public DistributionService getDistributionService() {
		return distributionService;
	}

	public void setDistributionService(DistributionService distributionService) {
		this.distributionService = distributionService;
	}

	public FileDataProvider getData() {
		return data;
	}

	public void setData(FileDataProvider data) {
		this.data = data;
	}
}