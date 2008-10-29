// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.web.feeds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.openuss.discussion.DiscussionService;
import org.openuss.discussion.ForumInfo;
import org.openuss.discussion.PostInfo;
import org.openuss.discussion.Topic;
import org.openuss.discussion.TopicInfo;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseService;
import org.openuss.system.SystemProperties;
import org.openuss.system.SystemService;

public class DiscussionFeed extends AbstractFeed {

	private transient CourseService courseService;

	private transient SystemService systemService;

	private transient DiscussionService discussionService;

	public static final Logger logger = Logger.getLogger(DiscussionFeed.class);

	@SuppressWarnings("unchecked")
	private FeedWrapper buildFeedArray(CourseInfo course) {
		final List entries = new ArrayList();
		ForumInfo forum = getDiscussionService().getForum(course);
		FeedWrapper feedWrapper = new FeedWrapper();
		String link;

		List<Topic> topics = getDiscussionService().getTopics(forum);

		if (topics != null && !topics.isEmpty()) {
			Collections.reverse(topics);
			Iterator i = topics.iterator();
			Iterator j;
			TopicInfo topic;
			List<PostInfo> posts;
			PostInfo post;
			Date lastEntry = new Date();
			while (i.hasNext()) {
				topic = (TopicInfo) i.next();
				posts = getDiscussionService().getPosts(topic);
				Collections.reverse(posts);
				j = posts.iterator();
				while (j.hasNext()) {
					post = (PostInfo) j.next();
					if (lastEntry == null) {
						lastEntry = post.getLastModification();
					}
					if (post.getLastModification().after(lastEntry)) {
						lastEntry = post.getLastModification();
					}
					link = getSystemService().getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue()
							+ "/views/secured/discussion/discussionthread.faces?course=" + course.getId() + "&topic="
							+ topic.getId() + "#" + post.getId();
					this.addEntry(entries, post.getTitle(), link, post.getLastModification(), post.getText(), topic
							.getTitle(), post.getSubmitter());
				}
			}
			feedWrapper.setLastModified(lastEntry);
		}

		link = systemService.getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue()
				+ "/rss/secured/discussion.xml?course=" + course.getId();

		feedWrapper.setWriter(this.convertToXml("["
				+ i18n("rss_discussion", null, locale()) + "] "
				+ course.getName(), link, course.getDescription(), systemService
				.getProperty(SystemProperties.COPYRIGHT).getValue(), entries));
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
		} else {
			return buildFeedArray(course);
		}
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

	public DiscussionService getDiscussionService() {
		return discussionService;
	}

	public void setDiscussionService(DiscussionService discussionService) {
		this.discussionService = discussionService;
	}

}