// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.web.feeds;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openuss.documents.DocumentService;
import org.openuss.documents.FileInfo;
import org.openuss.documents.FolderEntry;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseService;
import org.openuss.system.SystemProperties;
import org.openuss.system.SystemService;
import org.openuss.web.Constants;

import com.sun.syndication.feed.synd.SyndEntry;

public class DocumentsFeed extends AbstractFeed {

	private transient DocumentService documentService;
	
	private transient CourseService courseService;

	private transient SystemService systemService;

	private String viewUri; 
	
	public static final Logger logger = Logger.getLogger(DocumentsFeed.class);

	private FeedWrapper buildFeedArray(CourseInfo course) {
		final List<SyndEntry> entries = new ArrayList<SyndEntry>();
		FeedWrapper feedWrapper = new FeedWrapper();
		
		List<FolderEntry> folders = getDocumentService().getFolderEntries(course, null);
		List<FileInfo> files = getDocumentService().allFileEntries(folders);
		
		String urlServer = getSystemService().getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue();
		
		if (files != null && files.size() > 0) {
			for(FileInfo entry : files) {
				String link = urlServer + "/views/secured/documents/document.faces" + "?"+Constants.REPOSITORY_FILE_ID+"="+entry.getId()+"&"+Constants.COURSE+"="+course.getId();
				this.addEntry(entries, course.getName()+" - "+entry.getName(), link, entry.getCreated(), entry.getName()+"\n"+StringUtils.trimToEmpty(entry.getDescription()), course.getName(),course.getName());
			}
			
		}
		
		String link = systemService.getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue()+viewUri+"?course=" + course.getId();

		feedWrapper.setWriter(this.convertToXml("["+i18n("rss_documents", null, locale())+"] "+course.getName(), 
				link, course.getDescription(), 
				systemService.getProperty(SystemProperties.COPYRIGHT).getValue(), 
				entries));

		return feedWrapper;
	}

	/**
	 * @see org.openuss.feed.FeedService#getRssFeedForCourse(org.openuss.lecture.CourseInfo)
	 */
	public FeedWrapper getFeed(Long courseId) {
		if (courseId == null || courseId == 0) {
			return null;
		}
		CourseInfo course = getCourseService().findCourse(courseId);
		if (course == null) {
			return null;
		}

		return buildFeedArray(course);
	}

	public SystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	public CourseService getCourseService() {
		return courseService;
	}

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}

	public DocumentService getDocumentService() {
		return documentService;
	}

	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}

	public String getViewUri() {
		return viewUri;
	}

	public void setViewUri(String viewUri) {
		this.viewUri = viewUri;
	}

}