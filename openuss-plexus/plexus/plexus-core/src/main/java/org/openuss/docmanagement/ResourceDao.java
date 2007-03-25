package org.openuss.docmanagement;

import org.apache.log4j.Logger;

public abstract class ResourceDao {
	
	public static final Logger daoLogger = Logger.getLogger(ResourceDao.class);

	public ResourceDao(){		
	}
	
	public boolean systemFolder(String path){
		String tempString = "";
		if (!path.startsWith("/")) path = "/"+ path;
		
		boolean testBool = false;
		try{
			// folder is system folder,		
			// if path = /area
				if (path.equals("/"+DocConstants.DISTRIBUTION)){
					daoLogger.debug("Path: "+path + " Systemfolder: true");
					return true;
				}
				if (path.equals("/"+DocConstants.EXAMAREA)) {
					daoLogger.debug("Path: "+path + " Systemfolder: true");
					return true;
				}
				if (path.equals("/"+DocConstants.WORKINGPLACE)) {
					daoLogger.debug("Path: "+path + " Systemfolder: true");
					return true;		
				}
			// if path = /area/id
				tempString =path.substring(1);
				//take out area
				tempString = tempString.substring(tempString.indexOf("/")+1);
				if (tempString.indexOf("/")==-1) {
					daoLogger.debug("Path: "+path + " Systemfolder: true");
					return true;
				}
			// if path = /area/id/trash
				tempString =path.substring(1);
				//cut of area code
				tempString = tempString.substring(tempString.indexOf("/")+1);
				//test if path ends with trash folder name
				testBool = tempString.endsWith(DocConstants.TRASH_NAME);
				//cut of last path folder
				tempString = tempString.substring(0, tempString.lastIndexOf("/"));
				//test if testString contains any more /
				if ((tempString.indexOf("/")==-1)&&testBool) {
					daoLogger.debug("Path: "+path + " Systemfolder: true");
					return true;
				}
			// if path = /area/id/trash/*
				tempString =path.substring(1);
				//take out area
				tempString = tempString.substring(tempString.indexOf("/")+1);
				//take out id
				tempString = tempString.substring(tempString.indexOf("/")+1);
				if (tempString.startsWith(DocConstants.TRASH_NAME+"/")) {
					daoLogger.debug("Path: "+path + " Systemfolder: true");
					return true;
				}
		} catch (Exception e){
			//if path malformed return true
			daoLogger.debug("Path: "+path + " Systemfolder: true");
			return true;
		}
		daoLogger.debug("Path: "+path + " Systemfolder: false");
		return false;
	}

}
