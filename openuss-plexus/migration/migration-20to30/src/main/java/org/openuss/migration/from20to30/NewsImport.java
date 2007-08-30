package org.openuss.migration.from20to30;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
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
public class NewsImport {
	
	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(NewsImport.class);
	
	/** LegacyDao */
	private LegacyDao legacyDao;
	
	/** LectureImport */
	private LectureImport lectureImport;
	
	/** NewsService */
	private NewsService newsService;
	
	public void importCourseInformations() {
		logger.debug("load course informations ...");
		List<Enrollmentinformation2> informations = legacyDao.loadAllEnrollmentInformation();
		for (Enrollmentinformation2 info : informations) {
			Course course = lectureImport.getCourseById(info.getEnrollmentpk());
			if (course == null) {
				logger.debug("skip news item - couldn't find course for news item...");
			} else {
				NewsItemInfo item = createNewsItem(info, course);
				newsService.saveNewsItem(item);
			}
		}
	}

	public void importFacultyInformations() {
		logger.debug("load faculty informations ...");
		List<Facultyinformation2> informations = legacyDao.loadAllFacultyInformation();
		
		for (Facultyinformation2 info : informations) {
			Institute institute = lectureImport.getInstituteById(info.getFaculty().getId());
			if (institute == null) {
				logger.debug("Could' find instute for news item...");
			} else {
				NewsItemInfo item = createNewsItem(info, institute);
				newsService.saveNewsItem(item);
			}
		}
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

	public void setLegacyDao(LegacyDao legacyDao) {
		this.legacyDao = legacyDao;
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
