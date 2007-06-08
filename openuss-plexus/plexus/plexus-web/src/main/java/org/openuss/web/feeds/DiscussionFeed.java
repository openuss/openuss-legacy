// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.web.feeds;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.openuss.discussion.DiscussionService;
import org.openuss.discussion.ForumInfo;
import org.openuss.discussion.PostInfo;
import org.openuss.discussion.Topic;
import org.openuss.discussion.TopicInfo;
import org.openuss.lecture.Enrollment;
import org.openuss.lecture.EnrollmentInfo;
import org.openuss.lecture.EnrollmentService;
import org.openuss.system.SystemProperties;
import org.openuss.system.SystemService;

public class DiscussionFeed extends AbstractFeed{
	

	private transient EnrollmentService enrollmentService;
	
	private transient SystemService systemService;
	
	private transient DiscussionService discussionService;
	
	public static final Logger logger = Logger.getLogger(DiscussionFeed.class);

	@SuppressWarnings("unchecked")
	private FeedWrapper buildFeedArray(EnrollmentInfo enrollment) {
		final List entries = new ArrayList();
		ForumInfo forum = getDiscussionService().getForum(enrollment);
		
		List<Topic> topics = getDiscussionService().getTopics(forum);
		
		if (topics==null||topics.size()==0) return null;
		
		FeedWrapper feedWrapper = new FeedWrapper();
	    
		Iterator i = topics.iterator();
		Iterator j;
		TopicInfo topic;
		String link;
		List<PostInfo> posts;
		PostInfo post;
		Date lastEntry = new Date();
		while (i.hasNext()) {  
			topic = (TopicInfo) i.next();
			posts = getDiscussionService().getPosts(topic);
			j = posts.iterator();
			while(j.hasNext()){
				post = (PostInfo) j.next();
				if (lastEntry==null) lastEntry = post.getLastModification();
				if (post.getLastModification().after(lastEntry)) lastEntry = post.getLastModification();
				link = getSystemService().getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue()+"views/secured/discussion/discussionthread.faces?enrollment="+enrollment.getId()+"&topic="+topic.getId()+"#"+post.getId();
				this.addEntry(entries, post.getTitle(), link, post.getLastModification(), post.getText(), topic.getTitle(), post.getSubmitter());
			}
		}
		
		link = systemService.getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue()+"rss/secured/discussion.xml?enrollment="+enrollment.getId();
		
		feedWrapper.setWriter(this.convertToXml(enrollment.getName(), link, enrollment.getDescription(), systemService.getProperty(SystemProperties.COPYRIGHT).getValue(), entries));
		feedWrapper.setLastModified(lastEntry);
		return feedWrapper;
	}	
	
	/**
     * @see org.openuss.feed.FeedService#getRssFeedForEnrollment(org.openuss.lecture.EnrollmentInfo)
     */
    public FeedWrapper getFeed(Long enrollmentId)        
    {
    	if (enrollmentId==null||enrollmentId==0) return null;
    	Enrollment e = Enrollment.Factory.newInstance();
    	e.setId(enrollmentId);    	
    	EnrollmentInfo enrollment = getEnrollmentService().getEnrollmentInfo(getEnrollmentService().getEnrollment(e));
    	if (enrollment==null) return null;
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

	public DiscussionService getDiscussionService() {
		return discussionService;
	}

	public void setDiscussionService(DiscussionService discussionService) {
		this.discussionService = discussionService;
	}

}