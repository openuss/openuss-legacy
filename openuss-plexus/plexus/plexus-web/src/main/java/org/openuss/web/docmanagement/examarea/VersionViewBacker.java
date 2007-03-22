package org.openuss.web.docmanagement.examarea;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.docmanagement.BigFile;
import org.openuss.docmanagement.BigFileImpl;
import org.openuss.docmanagement.DocConstants;
import org.openuss.docmanagement.DocManagementException;
import org.openuss.docmanagement.ExaminationService;
import org.openuss.docmanagement.File;
import org.openuss.docmanagement.FileImpl;
import org.openuss.docmanagement.NotAFileException;
import org.openuss.docmanagement.NotAFolderException;
import org.openuss.docmanagement.PathNotFoundException;
import org.openuss.docmanagement.ResourceAlreadyExistsException;
import org.openuss.web.docmanagement.AbstractEnrollmentDocPage;
import org.openuss.web.docmanagement.FileTableEntry;
import org.apache.log4j.Logger;


@Bean(name="versionViewBacker", scope=Scope.SESSION)
@View
public class VersionViewBacker extends AbstractEnrollmentDocPage{
	
	public static final Logger logger = Logger.getLogger(VersionViewBacker.class);
	
	@Property(value="#{examinationService}")
	public ExaminationService examinationService;

	@Property(value="#{examAreaViewBacker}")
	public ExamAreaViewBacker examAreaViewBacker;
	
	public String fileFacesPath;
	
	public ArrayList<FileTableEntry> data;
	
	/**
	 * @return ArrayList, which contains all files of current selected folder
	 *         for display in dataTable
	 */
	public ArrayList<FileTableEntry> getData() {
		ArrayList<FileTableEntry> al = new ArrayList<FileTableEntry>();
		FileTableEntry fte = examAreaViewBacker.getData().get(new Integer(examAreaViewBacker.getFileFacesPath()).intValue());
		try {
			List l = examinationService.getVersions(fileTableEntry2File(fte));
			Iterator i = l.iterator();
			while (i.hasNext()){
				al.add(file2FTE((File)i.next()));
			}
		} catch (NotAFolderException e) {
			handleNotAFolderException(e);
		} catch (PathNotFoundException e) {
			handlePathNotFoundException(e);
		} catch (ResourceAlreadyExistsException e) {
			handleResourceAlreadyExistsException(e);
		} catch (NotAFileException e) {
			handleNotAFileException(e);
		} catch (DocManagementException e) {
			handleDocManagementException(e);
		}
		this.data = al;
		return al;
	}

	/**
	 * convenience method to change a File to a filetableentry
	 * 
	 * @param r
	 * @return
	 */
	private FileTableEntry file2FTE(File f) {
		FileTableEntry fte = new FileTableEntry();
		fte.setCreated(f.getCreated());
		fte.setDistributionTime(f.getDistributionTime());
		fte.setId(f.getId());
		fte.setLastModification(f.getLastModification());
		fte.setLength(f.getLength());
		fte.setMessage(f.getMessage());
		fte.setMimeType(f.getMimeType());
		fte.setName(f.getName());
		fte.setPath(f.getPath());
		fte.setPredecessor(f.getPredecessor());
		fte.setVersion(f.getVersion());
		fte.setVisibility(f.getVisibility());
		fte.setOwner(f.getOwner());
		return fte;
	}
	
	/**
	 * convenience method, which converts a FileTableEntry object into an File
	 * object
	 * 
	 * @param fte
	 *            FileTableEntry
	 * @return
	 */
	private File fileTableEntry2File(FileTableEntry fte) {
		return new FileImpl(fte.getDistributionTime(), fte.getId(), fte
				.getLastModification(), fte.getLength(), fte.getMessage(), fte
				.getMimeType(), fte.getName(), fte.getPath(), fte
				.getPredecessor(), fte.getVersion(), fte.getVisibility(), fte.getOwner());
	}


	/**
	 * Action methods to trigger download
	 * 
	 * @return
	 */
	public String download() {

		FileTableEntry fte = this.data.get((new Integer(getFileFacesPath())).intValue());
		File file = fileTableEntry2File(fte);
		if (!hasReadPermission(file)) {
			noPermission();
			return DocConstants.EXAMEXPLORER;		
		}		
		BigFile bigFile = new BigFileImpl();
		try {
			bigFile = examinationService.getSubmission(file);			
		} catch (NotAFolderException e) {
			handleNotAFolderException(e);
		} catch (PathNotFoundException e) {
			handlePathNotFoundException(e);
		} catch (ResourceAlreadyExistsException e) {
			handleResourceAlreadyExistsException(e);
		} catch (NotAFileException e) {
			handleNotAFileException(e);
		} catch (DocManagementException e) {
			handleDocManagementException(e);
		}
		triggerDownload(bigFile);
		return DocConstants.EXAMEXPLORER;
	}

	private void triggerDownload(BigFile bigFile) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) context
				.getExternalContext().getResponse();
		int read = 0;
		byte[] bytes = new byte[1024];

		response.setContentType(bigFile.getMimeType());
		response.setHeader("Content-Disposition", "attachment;filename=\""
				+ bigFile.getName() + "\"");
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			while ((read = bigFile.getFile().read(bytes)) != -1) {
				os.write(bytes, 0, read);
			}
			os.flush();
			os.close();
		} catch (IOException e) {
			logger.error("IOException: ", e);
		}
		FacesContext.getCurrentInstance().responseComplete();
	}

	
	public ExaminationService getExaminationService() {
		return examinationService;
	}

	public void setExaminationService(ExaminationService examinationService) {
		this.examinationService = examinationService;
	}

	public ExamAreaViewBacker getExamAreaViewBacker() {
		return examAreaViewBacker;
	}

	public void setExamAreaViewBacker(ExamAreaViewBacker examAreaViewBacker) {
		this.examAreaViewBacker = examAreaViewBacker;
	}

	public String getFileFacesPath() {
		return fileFacesPath;
	}

	public void setFileFacesPath(String fileFacesPath) {
		this.fileFacesPath = fileFacesPath;
	}
}