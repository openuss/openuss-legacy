package org.openuss.web.docmanagement;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.myfaces.custom.tree2.TreeModel;
import org.apache.myfaces.custom.tree2.TreeModelBase;
import org.apache.myfaces.custom.tree2.TreeNodeBase;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.docmanagement.DistributionService;
import org.openuss.docmanagement.DocManagementException;
import org.openuss.docmanagement.DocRights;
import org.openuss.docmanagement.File;
import org.openuss.docmanagement.Folder;
import org.openuss.docmanagement.FolderImpl;
import org.openuss.docmanagement.Link;
import org.openuss.docmanagement.LinkImpl;
import org.openuss.docmanagement.NotAFileException;
import org.openuss.docmanagement.NotAFolderException;
import org.openuss.docmanagement.PathNotFoundException;
import org.openuss.docmanagement.Resource;
import org.openuss.docmanagement.ResourceAlreadyExistsException;
import org.openuss.docmanagement.DocConstants;
import org.openuss.docmanagement.SystemFolderException;

@Bean(name="folder2Folder", scope=Scope.SESSION)
@View
public class Folder2Folder extends AbstractDocPage{

	public static final Logger logger = Logger.getLogger(Folder2Folder.class);
	
	@Property(value="#{distributionService}")
	public DistributionService distributionService;

	/**
	 * source tree, which contains the files and folder of faculty
	 */
	public TreeModel sourceTree;
	
	/**
	 * target tree, which contains the files and folder of enrollment
	 */
	public TreeModel targetTree;
	
	/**
	 * Path of selected target node
	 */
	public String targetPath;
	
	/**
	 * path of selected source node
	 */
	public String sourcePath;
	
	
	/**
	 * getter method, which builds the source tree for tree2 component
	 * @return
	 */
	public TreeModel getSourceTree(){
		Folder folder = new FolderImpl();
		try {
			folder = distributionService.getFacultyFolder(faculty);
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
		//change model only if model has changed
		TreeModel tm = new TreeModelBase(folder2TreeNodeBase(folder, true));
		if (sourceTree!=null){
			if (!this.sourceTree.equals(tm)) this.sourceTree = tm;
		} else if(sourceTree==null) this.sourceTree = tm;
		return 	this.sourceTree;
	}
	
	/**
	 * getter method, which builds the target tree for tree2 component
	 * @return
	 */
	public TreeModel getTargetTree(){
		Folder folder = new FolderImpl();
		try {
			folder = distributionService.getMainFolder(enrollment);
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
		//change model only if model has changed
		TreeModel tm = new TreeModelBase(folder2TreeNodeBase(folder, false));
		if (targetTree!=null){
			if (!this.targetTree.equals(tm)) this.targetTree = tm;
		} else if(targetTree==null) this.targetTree = tm;
		return 	this.targetTree;
	}
	
	/**
	 *  converts Folder object to TreeNodeBase object 
	 * @param folder Folder object to be converted
	 * @return TreeNodeBase object as result of conversion
	 */
	@SuppressWarnings("unchecked")
	private TreeNodeBase folder2TreeNodeBase(Folder folder, boolean includeFiles){
		if (folder==null) return new TreeNodeBase();
		TreeNodeBase tn = new TreeNodeBase("folder", folder.getName(), folder.getPath(), (folder.getSubnodes()==null));
		Folder subFolder = null;
		File subFile = null;
		if (folder.getSubnodes()!=null){
			Collection<Resource> v = folder.getSubnodes();
			Iterator i = v.iterator();
			while (i.hasNext()){
				Object o = i.next();
				subFolder = null; 
				subFile = null;
				if (o instanceof Folder) {
					subFolder = (Folder) o;					
				}	
				if (o instanceof File) {
					subFile= (File) o;					
				}	

				if (subFolder!=null) {
					if (hasReadPermission(subFolder))
						tn.getChildren().add(folder2TreeNodeBase(subFolder, includeFiles));
				}
				if (includeFiles) if((subFile!=null)) {
					if (hasReadPermission(subFile))
						tn.getChildren().add(new TreeNodeBase("file", subFile.getName(), subFile.getPath(), true));
				}
			}
		}
		return tn;		
	}
	
	/**
	 * Action method, which links the selected resource from source to target
	 * @return
	 */
	public String link(){
		try {
			File file = distributionService.getFile(this.sourcePath);
			Folder folder = distributionService.getFolder(this.targetPath);
			Link link = new LinkImpl(); 
			link.setCreated(new Timestamp(System.currentTimeMillis()));
			link.setDistributionDate(new Timestamp(System.currentTimeMillis()));
			link.setMessage(file.getMessage());
			link.setName(file.getName());
			link.setPath(folder.getPath());
			link.setTarget(file);
			link.setVisibility(DocRights.READ_ALL|DocRights.EDIT_ASSIST);
			distributionService.addLink(link);
		} catch (NotAFileException e) {
			handleNotAFileException(e);
		} catch (NotAFolderException e) {
			handleNotAFolderException(e);
		} catch (PathNotFoundException e) {
			handlePathNotFoundException(e);
		} catch (ResourceAlreadyExistsException e) {
			handleResourceAlreadyExistsException(e);
		} catch (SystemFolderException e) {
			handleDocManagementException(e);		
		} catch (DocManagementException e) {
			handleDocManagementException(e);
		}				
		return DocConstants.FOLDERTOFOLDER;
	}

	/**
	 * Action method, which copies the selected resource from source to target
	 * @return
	 */
	public String copy(){
		try {
			File file = distributionService.getFile(this.sourcePath);
			if (!hasReadPermission(file)){
				noPermission();
				return DocConstants.FOLDERTOFOLDER;
			}
			Folder folder = distributionService.getFolder(this.targetPath);
			if (!hasWritePermission(folder)){
				noPermission();
				return DocConstants.FOLDERTOFOLDER;
			}

			distributionService.copyFile(file, folder);
		} catch (NotAFileException e) {
			handleNotAFileException(e);
		} catch (NotAFolderException e) {
			handleNotAFolderException(e);
		} catch (PathNotFoundException e) {
			handlePathNotFoundException(e);
		} catch (ResourceAlreadyExistsException e) {
			handleResourceAlreadyExistsException(e);
		} catch (SystemFolderException e) {
			handleDocManagementException(e);		
		} catch (DocManagementException e) {
			handleDocManagementException(e);
		}		
		return DocConstants.FOLDERTOFOLDER;
	}
	
	public DistributionService getDistributionService() {
		return distributionService;
	}
	
	public void setDistributionService(DistributionService distributionService) {
		this.distributionService = distributionService;
	}

	public String getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourceTree.getNodeById(sourcePath).getIdentifier();
	}

	public String getTargetPath() {
		return targetPath;
	}

	public void setTargetPath(String targetPath) {
		this.targetPath = targetTree.getNodeById(targetPath).getIdentifier();
	}
}