package org.openuss.web.discussion;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.discussion.PostInfo;
import org.openuss.discussion.TopicInfo;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.web.Constants;

@Bean(name = "views$secured$discussion$discussionthread", scope = Scope.REQUEST)
@View
public class DiscussionThreadPage extends AbstractDiscussionPage {

	private static final String DISCUSSION_READONLY = "discussion_readonly";

	private DiscussionThreadDataProvider data = new DiscussionThreadDataProvider();

	@Property(value = "#{" + Constants.DISCUSSION_THREADLENGTH + "}")
	public Integer length;

	@Property(value = "#{" + Constants.DISCUSSION_QUOTE_POST + "}")
	public PostInfo quoteFrom;
	
	public boolean topicWatchState;

	public boolean topicReadOnly;

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		if (isRedirected()) {
			return;
		}
		if (topic != null && topic.getId() != null) {
			topic = discussionService.getTopic(topic);
			setBean(Constants.DISCUSSION_TOPIC, topic);
		}
		if (topic == null || topic.getId() == null) {
			addError(i18n(Constants.DISCUSSION_THREAD_NOT_FOUND));
			redirect(Constants.DISCUSSION_MAIN);
			return;
		} else {
			// check if topic belongs to course
			if (!topic.getForumId().equals(forum.getId())) {
				addError(i18n(Constants.DISCUSSION_THREAD_NOT_FOUND));
				redirect(Constants.DISCUSSION_MAIN);
				return;
			}

			discussionService.addHit(topic);
			topicWatchState = discussionService.watchesTopic(topic);
			addPageCrumb();
		}
	}

	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setName(topic.getTitle());
		crumb.setHint(topic.getTitle());
		breadcrumbs.addCrumb(crumb);
	}

	private class DiscussionThreadDataProvider extends AbstractPagedTable<PostInfo> {

		private static final long serialVersionUID = -2279124328223684525L;

		private DataPage<PostInfo> page;

		@SuppressWarnings("unchecked")
		@Override
		public DataPage<PostInfo> getDataPage(int startRow, int pageSize) {
			if (page == null || page.getDatasetSize() == 0) {
				List<PostInfo> posts;
				if (!(topic == null || topic.getId() == null)) {
					posts = discussionService.getPosts(topic);
				} else {
					posts = new ArrayList<PostInfo>();
				}
				setBean(Constants.DISCUSSION_THREADLENGTH, posts.size());
				sort(posts);
				page = new DataPage<PostInfo>(posts.size(),0,posts);
			}
			return page;
		}
	}

	public String addPost() {
		if ((topic.isReadOnly() || getForum().isReadOnly()) && (!isAssistant())) {
			addError(i18n(DISCUSSION_READONLY));
			return Constants.FAILURE;
		}
		TopicInfo topic = discussionService.getTopic(this.topic);
		setBean(Constants.DISCUSSION_TOPIC, topic);
		PostInfo post = new PostInfo();
		String title = "Re: " + this.topic.getTitle();
		if (title.length() > 250) {
			title = StringUtils.abbreviate(title, 250);
		}
		post.setTitle(title);
		setBean(Constants.DISCUSSION_DISCUSSIONENTRY, post);
		return Constants.DISCUSSION_NEW;
	}

	public String removePost(){
		if ((topic.isReadOnly()||getForum().isReadOnly())&&(!isAssistant())){
			addError(i18n(DISCUSSION_READONLY));
			return Constants.FAILURE;
		}
		PostInfo postInfo = this.data.getRowData();
		discussionService.deletePost(postInfo);
		addMessage(i18n("discussion_post_deleted", postInfo.getTitle()));
		return Constants.DISCUSSION_MAIN;
	}

	public String editPost(){
		if ((topic.isReadOnly()||getForum().isReadOnly())&&(!isAssistant())){
			addError(i18n(DISCUSSION_READONLY));
			return Constants.FAILURE;
		}
		PostInfo postInfo = this.data.getRowData();
		postInfo = discussionService.getPost(postInfo);
		setBean(Constants.DISCUSSION_DISCUSSIONENTRY, postInfo);
		return Constants.DISCUSSION_NEW;
	}

	public String quote() {
		if ((topic.isReadOnly() || getForum().isReadOnly()) && (!isAssistant())) {
			addError(i18n(DISCUSSION_READONLY));
			return Constants.FAILURE;
		}
		TopicInfo topic = discussionService.getTopic(this.topic);
		setBean(Constants.DISCUSSION_TOPIC, topic);
		quoteFrom = this.data.getRowData();
		quoteFrom = discussionService.getPost(quoteFrom);
		setBean(Constants.DISCUSSION_QUOTE_POST, quoteFrom);
		setBean(Constants.DISCUSSION_DISCUSSIONENTRY, new PostInfo());
		return Constants.DISCUSSION_NEW;
	}

	public String changeWatchState() {
		if (discussionService.watchesTopic(topic)) {
			discussionService.removeTopicWatch(topic);
			addMessage(i18n("discussion_topic_unsubscribe_success"));
		} else if(!discussionService.watchesTopic(topic)){
			discussionService.addTopicWatch(topic);
			addMessage(i18n("discussion_topic_subscribe_success"));
		} 
		return Constants.SUCCESS;
	}

	public String changeReadOnly() {
		discussionService.changeEditState(topic);
		return Constants.SUCCESS;
	}

	public DiscussionThreadDataProvider getData() {
		return data;
	}

	public void setData(DiscussionThreadDataProvider data) {
		this.data = data;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public boolean isTopicWatchState() {
		return topicWatchState;
	}

	public void setTopicWatchState(boolean topicWatchState) {
		this.topicWatchState = topicWatchState;
	}

	public boolean isTopicReadOnly() {
		return topicReadOnly;
	}

	public void setTopicReadOnly(boolean topicReadOnly) {
		this.topicReadOnly = topicReadOnly;
	}

	public boolean isForumReadOnly() {
		if (courseInfo == null || courseInfo.getId() == null) {
			return false;
		}
		return getDiscussionService().getForum(courseInfo).isReadOnly();
	}

	public boolean isForumWatchState() {
		if (courseInfo == null || courseInfo.getId() == null) {
			return false;
		}
		return getDiscussionService().watchesForum(getDiscussionService().getForum(courseInfo));
	}

	public PostInfo getQuoteFrom() {
		return quoteFrom;
	}

	public void setQuoteFrom(PostInfo quoteFrom) {
		this.quoteFrom = quoteFrom;
	}

}
