package org.openuss.migration.from20to30;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openuss.documents.DocumentApplicationException;
import org.openuss.documents.DocumentService;
import org.openuss.documents.FileInfo;
import org.openuss.documents.FolderInfo;
import org.openuss.lecture.Course;
import org.openuss.migration.legacy.dao.LegacyDao;
import org.openuss.migration.legacy.domain.Enrollment2;
import org.openuss.migration.legacy.domain.Lecture2;
import org.openuss.migration.legacy.domain.Lecturefile2;

/**
 * Class to import course documents from legacy lecture tables.
 * 
 * @author Ingo Dueppe
 * 
 */
public class DocumentImport {

	private static final Logger logger = Logger.getLogger(DocumentImport.class);

	/** LegacyDao */
	private LegacyDao legacyDao;

	/** DocumentService */
	private DocumentService documentService;

	/** LectureImport */
	private LectureImport lectureImport;

	/** SessionFactory */
	private SessionFactory legacySessionFactory;
	
	private Session session;

	public void perform() {
		logger.info("loading lecture structure....");
		
		session = legacySessionFactory.getCurrentSession();

		ScrollableResults results = legacyDao.loadAllLectures();
		Lecture2 lecture = null;
		while (results.next()) {
			if (lecture != null) {
				session.evict(lecture);
			}
			lecture = (Lecture2) results.get()[0];

			Enrollment2 enrollment = lecture.getEnrollment();
			Course course = lectureImport.getCourseByLegacyId(enrollment.getId());
			if (course != null) {
				importFolder(lecture, course);
			} else {
				logger.debug("skip lecture - couldn't find according course " + lecture.getTitle());
			}
		}
		results.close();
	}

	private void importFolder(Lecture2 lecture, Course course) {
		FolderInfo root = documentService.getFolder(course);
		FolderInfo folder = new FolderInfo();
		folder.setName(lecture.getTitle());
		folder.setCreated(lecture.getDdate());
		if (!createFolder(root, folder)) {
			logger.error("Couldn't create folder with name "+folder.getName());
			return;
		} 
		logger.debug("loading files for folder " + folder.getPath() + "/" + folder.getName());
		for (Lecturefile2 lectureFile : lecture.getLecturefiles()) {
			if (lectureFile != null && StringUtils.isNotBlank(lectureFile.getLectureFileId())) {
				try {
					logger.debug("process " + lectureFile.getLecturefilename());
					FileInfo fileInfo = new FileInfo();
					fileInfo.setFileName(lectureFile.getLecturefilename());
					fileInfo.setCreated(lectureFile.getDdate());
					fileInfo.setExtension(ImportUtil.extension(lectureFile.getLecturefilename()));
					fileInfo.setContentType("application/octet-stream");

					byte[] data = legacyDao.loadLectureFileData(lectureFile.getLectureFileId());
					if (data != null) {
						ByteArrayInputStream bis = new ByteArrayInputStream(data);
						fileInfo.setFileSize(data.length);
						bis.close();
						
						fileInfo.setInputStream(bis);
						saveFile(folder, fileInfo);
						
						fileInfo.getInputStream().close();
					}
				} catch (IOException e) {
					logger.error(e);
				} 
			}
		}
	}

	private boolean createFolder(FolderInfo root, FolderInfo folder) {
		int check = 0;
		String folderName = folder.getName();
		while (check < 100) {
			try {
				documentService.createFolder(folder, root);
				return true;
			} catch (DocumentApplicationException dae) {
				check++;
				folder.setName(folderName+"_"+check);
			}
		}
		return false;
	}

	private void saveFile(FolderInfo folder, FileInfo fileInfo) {
		int check = 0;
		String name = fileInfo.getName();
		String fileName = fileInfo.getFileName();
		while (check < 10) {
			try {
				documentService.createFileEntry(fileInfo, folder);
				check = 10;
			} catch (DocumentApplicationException dae) {
				check++;
				fileInfo.setFileName(fileName+"_"+check);
				fileInfo.setName(name+"_"+check);
				logger.debug("renamed file name to "+fileInfo.getFileName());
			}
		}
	}

	public LegacyDao getLegacyDao() {
		return legacyDao;
	}

	public void setLegacyDao(LegacyDao legacyDao) {
		this.legacyDao = legacyDao;
	}

	public DocumentService getDocumentService() {
		return documentService;
	}

	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}

	public LectureImport getLectureImport() {
		return lectureImport;
	}

	public void setLectureImport(LectureImport lectureImport) {
		this.lectureImport = lectureImport;
	}

	public SessionFactory getLegacySessionFactory() {
		return legacySessionFactory;
	}

	public void setLegacySessionFactory(SessionFactory legacySessionFactory) {
		this.legacySessionFactory = legacySessionFactory;
	}

}
