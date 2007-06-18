// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.web.feeds;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openuss.documents.DocumentService;
import org.openuss.documents.FileInfo;
import org.openuss.lecture.Enrollment;
import org.openuss.lecture.EnrollmentInfo;
import org.openuss.lecture.EnrollmentService;
import org.openuss.system.SystemProperties;
import org.openuss.system.SystemService;
import org.openuss.web.Constants;

public class DocumentsFeed extends AbstractFeed {

	private transient DocumentService documentService;
	
	private transient EnrollmentService enrollmentService;

	private transient SystemService systemService;


	private String viewUri; 
	
	public static final Logger logger = Logger.getLogger(DocumentsFeed.class);

	private FeedWrapper buildFeedArray(EnrollmentInfo enrollment) {
		final List entries = new ArrayList();
		FeedWrapper feedWrapper = new FeedWrapper();
		
		List folders = getDocumentService().getFolderEntries(enrollment, null);
		List<FileInfo> files = getDocumentService().allFileEntries(folders);
		
		String urlServer = getSystemService().getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue();
		
		if (files != null && files.size() > 0) {
			for(FileInfo entry : files) {
				String link = urlServer + "files/" + entry.getFileName() + "?"+Constants.REPOSITORY_FILE_ID+"="+entry.getId();
				this.addEntry(entries, enrollment.getName()+" - "+entry.getName(), link, entry.getCreated(), entry.getName()+"\n"+entry.getDescription(), enrollment.getName(),enrollment.getName());
			}
			
		}
		
		String link = systemService.getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue()+viewUri+"?enrollment=" + enrollment.getId();

		feedWrapper.setWriter(this.convertToXml(enrollment.getName(), link, enrollment.getDescription(), systemService
				.getProperty(SystemProperties.COPYRIGHT).getValue(), entries));

		return feedWrapper;
	}

	/**
	 * @see org.openuss.feed.FeedService#getRssFeedForEnrollment(org.openuss.lecture.EnrollmentInfo)
	 */
	public FeedWrapper getFeed(Long enrollmentId) {
		if (enrollmentId == null || enrollmentId == 0) {
			return null;
		}
		Enrollment e = Enrollment.Factory.newInstance();
		e.setId(enrollmentId);
		EnrollmentInfo enrollment = getEnrollmentService().getEnrollmentInfo(getEnrollmentService().getEnrollment(e));
		if (enrollment == null) {
			return null;
		}

		return buildFeedArray(enrollment);
	}

	public SystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	public EnrollmentService getEnrollmentService() {
		return enrollmentService;
	}

	public void setEnrollmentService(EnrollmentService enrollmentService) {
		this.enrollmentService = enrollmentService;
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