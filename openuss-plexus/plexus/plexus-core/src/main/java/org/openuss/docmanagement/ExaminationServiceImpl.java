// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.docmanagement;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import org.openuss.lecture.Enrollment;
import org.apache.log4j.Logger;
/**
 * @see org.openuss.docmanagement.ExaminationService
 */
public class ExaminationServiceImpl
    extends org.openuss.docmanagement.ExaminationServiceBase
{

	public static final Logger logger = Logger.getLogger(ExaminationServiceImpl.class);
	

	public ExamAreaDao examAreaDao;
	
	public FolderDao folderDao;
	
	/**
	 * dao object to create and edit files, is injected by spring
	 */
	public FileDao fileDao;
	
	@Override
	protected void handleAddExamArea(Enrollment enrollment) throws Exception {		
		Folder folder = folderDao.getFolder(DocConstants.EXAMAREA);    		
		//add faculty main folder to distribution part of repository
		Folder enrollmentMain = new FolderImpl(enrollment.getShortcut(), enrollment.getId().toString(), folder.getPath(), null, DocRights.READ_ALL|DocRights.EDIT_ALL);
		//Set deadline now + 6 weeks
		try{
			folderDao.setFolder(enrollmentMain);
		} catch (ResourceAlreadyExistsException e){
		}
		examAreaDao.setDeadline(new Timestamp(System.currentTimeMillis()+ 1000*60*60*24*7*6), "/"+DocConstants.EXAMAREA+"/"+enrollment.getId().toString());
		logger.debug("main folder for enrollment added to repository");
	}

	@Override
	protected void handleAddSubmission(BigFile file) throws Exception {
		fileDao.setFile(file);		
	}

	@Override
	protected java.util.List handleGetAllSubmissions(ExamArea examArea) throws Exception {
		Iterator i = examArea.getSubnodes().iterator();
		List submissions = new ArrayList();
		Resource r;
		while (i.hasNext()){
			r = (Resource) i.next();
			if (r instanceof File){
				submissions.add((File)r);
			}			
		}
		return submissions;
	}

	@Override
	protected ExamArea handleGetExamArea(Enrollment enrollment) throws Exception {
		ExamArea examArea ;
		try{
			examArea = examAreaDao.getExamArea("/"+DocConstants.EXAMAREA+"/"+enrollment.getId().toString());
		}
		catch (PathNotFoundException e){
			handleAddExamArea(enrollment);
		}
		examArea = examAreaDao.getExamArea("/"+DocConstants.EXAMAREA+"/"+enrollment.getId().toString());
		return examArea;
	}

	@Override
	protected BigFile handleGetSubmission(File file) throws Exception {
		return fileDao.getFile(file);
	}

	@Override
	protected java.util.List handleGetVersions(File file) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void handleUpdateSubmission(BigFile file, File oldFile) throws Exception {
		fileDao.setFile(file);
	}

	public FileDao getFileDao() {
		return fileDao;
	}

	public void setFileDao(FileDao fileDao) {
		this.fileDao = fileDao;
	}


	@Override
	protected File handleGetSubmissionByUsername(String username, ExamArea examArea) throws Exception {
		List l = handleGetAllSubmissions(examArea);
		Iterator i = l.iterator();
		File f;
		while (i.hasNext()){
			f = (File)i.next();
			if (fileDao.getOwner(f).equals(username)) return f; 
		}
		return null;
	}

	public ExamAreaDao getExamAreaDao() {
		return examAreaDao;
	}

	public void setExamAreaDao(ExamAreaDao examAreaDao) {
		this.examAreaDao = examAreaDao;
	}

	@Override
	protected void handleSetDeadline(ExamArea examArea) throws Exception {
		examAreaDao.setDeadline(examArea.getDeadline(), examArea.getPath());
	}

	public FolderDao getFolderDao() {
		return folderDao;
	}

	public void setFolderDao(FolderDao folderDao) {
		this.folderDao = folderDao;
	}


}