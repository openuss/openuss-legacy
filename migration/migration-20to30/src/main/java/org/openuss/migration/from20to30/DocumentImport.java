package org.openuss.migration.from20to30;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.openuss.business.extension.lecture.file.filedescription.lecturefilebase.LectureFileObject;
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
	
	public void importDocuments() {
		logger.info("loading lecture structure....");
		List<Lecture2> lectures = legacyDao.loadAllLectures();

		for (Lecture2 lecture : lectures) {
			Enrollment2 enrollment = lecture.getEnrollment();
			Course course = lectureImport.getCourseByLegacyId(enrollment.getId());
			if (course != null) {
				try {
					FolderInfo root = documentService.getFolder(course);
					FolderInfo folder = new FolderInfo();
					folder.setName(lecture.getTitle());
					folder.setCreated(lecture.getDdate());
					documentService.createFolder(folder, root);
					logger.debug("loading files for folder "+folder.getPath()+"/"+folder.getName());
					for (Lecturefile2 lectureFile : lecture.getLecturefiles() ) {
						if (lectureFile != null && lectureFile.getLecturefilebase() != null) {
							try {
								logger.debug("process "+lectureFile.getLecturefilename());
								FileInfo fileInfo = new FileInfo();
								fileInfo.setFileName(lectureFile.getLecturefilename());
								fileInfo.setCreated(lectureFile.getDdate());
								fileInfo.setExtension(ImportUtil.extension(lectureFile.getLecturefilename()));
								fileInfo.setContentType("application/octet-stream");
	
								ByteArrayInputStream bis = new ByteArrayInputStream(lectureFile.getLecturefilebase().getBasefile());
								ObjectInputStream ois = new ObjectInputStream(bis);
								LectureFileObject fileObject = (LectureFileObject) ois.readObject();
								fileInfo.setFileSize(fileObject.getData().length);
								fileInfo.setInputStream(new ByteArrayInputStream(fileObject.getData()));
	
								documentService.createFileEntry(fileInfo, folder);
							} catch (IOException e) {
								logger.error(e);
							} catch (ClassNotFoundException e) {
								logger.error(e);
							} catch (Exception e) {
								logger.error(e);
							}
						}
					}
				} catch (DocumentApplicationException e) {
					logger.debug(e);
				}
			} else {
				logger.debug("skip lecture - couldn't find according course "+lecture.getTitle());
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


}
