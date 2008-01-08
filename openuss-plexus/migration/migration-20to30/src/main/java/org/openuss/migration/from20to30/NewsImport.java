package org.openuss.migration.from20to30;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.hibernate.ScrollableResults;
import org.openuss.business.foundation.filebase.FileObject;
import org.openuss.documents.FileInfo;
import org.openuss.lecture.Course;
import org.openuss.lecture.Institute;
import org.openuss.migration.legacy.dao.LegacyDao;
import org.openuss.migration.legacy.domain.Enrollmentinformation2;
import org.openuss.migration.legacy.domain.Facultyinformation2;
import org.openuss.migration.legacy.domain.Filebase2;
import org.openuss.news.NewsCategory;
import org.openuss.news.NewsItemInfo;
import org.openuss.news.NewsService;
import org.openuss.news.PublisherType;

/**
 * Import NewsItems
 * 
 * @author Ingo Dueppe
 *
 */
public class NewsImport extends DefaultImport {
	
	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(NewsImport.class);
	
	/** LectureImport */
	private LectureImport lectureImport;
	
	/** NewsService */
	private NewsService newsService;
	
	public void perform() {
		importCourseInformations();
		importFacultyInformations();
	}
	
	private void importCourseInformations() {
		logger.debug("load course informations ...");
		ScrollableResults results = legacyDao.loadAllEnrollmentInformation();
		Enrollmentinformation2 info = null;
		while (results.next()) {
			evict(info);
			info = (Enrollmentinformation2) results.get()[0];
			Course course = lectureImport.getCourseByLegacyId(info.getEnrollmentpk());
			if (course == null) {
				logger.debug("skip news item - couldn't find course for news item...");
			} else {
				NewsItemInfo item = createNewsItem(info, course);
				newsService.saveNewsItem(item);
			}
		}
		results.close();
	}

	private void importFacultyInformations() {
		logger.debug("load faculty informations ...");
		ScrollableResults results = legacyDao.loadAllFacultyInformation();
		while (results.next()) {
			Facultyinformation2 info = (Facultyinformation2) results.get()[0];
			Institute institute = lectureImport.getInstituteByLegacyId(info.getFaculty().getId());
			if (institute == null) {
				logger.debug("Could' find instute for news item...");
			} else {
				NewsItemInfo item = createNewsItem(info, institute);
				newsService.saveNewsItem(item);
			}
		}
		results.close();
	}

	private NewsItemInfo createNewsItem(Enrollmentinformation2 info, Course course) {
		assert course != null;
		NewsItemInfo item = new NewsItemInfo();
		
		item.setAuthor(StringUtils.abbreviate(course.getName(),64));
		item.setPublishDate(info.getDdate());
		item.setTitle(StringUtils.abbreviate(course.getName(),250));
		item.setText(info.getTtext());
		
		item.setPublisherName(course.getName());
		item.setPublisherIdentifier(course.getId());
		item.setPublisherType(PublisherType.COURSE);
		item.setCategory(NewsCategory.COURSE);
		
		return item;
	}

	private NewsItemInfo createNewsItem(Facultyinformation2 info, Institute institute) {
		assert institute != null;
		NewsItemInfo item = new NewsItemInfo();
		
		item.setAuthor(info.getFaculty().getName());
		item.setPublishDate(info.getDdate());
		item.setExpireDate(DateUtils.addMonths(info.getDdate(),2));
		item.setText(StringUtils.trimToEmpty(info.getContent()));
		item.setTitle(StringUtils.trimToEmpty(info.getTtext()));
		
		item.setPublisherName(info.getFaculty().getName());
		item.setPublisherIdentifier(institute.getId());
		item.setPublisherType(PublisherType.INSTITUTE);
		item.setCategory(NewsCategory.GLOBAL);
		
		item.setAttachments(loadAttachments(info));
		return item;
	}
	
	private List<FileInfo> loadAttachments(Facultyinformation2 info) {
		if (StringUtils.isBlank(info.getFileid())) {
			return null;
		}
		Filebase2 fileBase = legacyDao.loadFileBase(info.getFileid());
		if (fileBase == null) {
			return null;
		}
		if (StringUtils.isBlank(info.getFfilename())) {
			logger.debug("skip attachment with no filename");
			return null;
		}

		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(fileBase.getBasefile());
			ObjectInputStream ois = new ObjectInputStream(bis);
			FileObject fileObject = (FileObject) ois.readObject();
			
			FileInfo fileInfo = new FileInfo();
			fileInfo.setCreated(info.getDdate());
			fileInfo.setFileName(info.getFfilename());
			fileInfo.setExtension(ImportUtil.extension(info.getFfilename()));
			fileInfo.setContentType("application/octet-stream");
			
			fileInfo.setFileSize(fileObject.getData().length);
			fileInfo.setInputStream(new ByteArrayInputStream(fileObject.getData()));
			
			List<FileInfo> attachments = new ArrayList<FileInfo>();
			attachments.add(fileInfo);
			
			return attachments;
		} catch (IOException e) {
			logger.error(e);
			return null;
		} catch (ClassNotFoundException e) {
			logger.error(e);
			return null;
		}
			
	}

	public LegacyDao getLegacyDao() {
		return legacyDao;
	}

	public LectureImport getLectureImport() {
		return lectureImport;
	}

	public void setLectureImport(LectureImport lectureImport) {
		this.lectureImport = lectureImport;
	}

	public NewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

	
	

}
