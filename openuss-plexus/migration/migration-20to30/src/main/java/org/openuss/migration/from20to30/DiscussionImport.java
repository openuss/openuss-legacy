package org.openuss.migration.from20to30;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.ScrollableResults;
import org.openuss.discussion.Forum;
import org.openuss.discussion.ForumDao;
import org.openuss.discussion.ForumWatch;
import org.openuss.discussion.ForumWatchDao;
import org.openuss.discussion.Post;
import org.openuss.discussion.PostDao;
import org.openuss.discussion.Topic;
import org.openuss.discussion.TopicDao;
import org.openuss.documents.DocumentService;
import org.openuss.documents.FileInfo;
import org.openuss.foundation.DomainObject;
import org.openuss.migration.legacy.domain.Discussionitem2;
import org.openuss.migration.legacy.domain.Discussionwatch2;
import org.openuss.security.User;
import org.openuss.security.UserDao;
import org.openuss.security.acl.ObjectIdentity;
import org.openuss.security.acl.ObjectIdentityDao;

/**
 * This Service migrate data from openuss 2.0 to openuss-plexus 3.0
 * 
 * @author Ingo Dueppe
 * 
 */
public class DiscussionImport extends DefaultImport {

	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(DiscussionImport.class);

	/** UserImport */
	private UserImport userImport;

	/** ForumDao */
	private ForumDao forumDao;

	/** UserDao */
	private UserDao userDao;

	/** TopicDao */
	private TopicDao topicDao;

	/** PostDao */
	private PostDao postDao;

	/** ForumWatchDao */
	private ForumWatchDao forumWatchDao;

	/** Unknown User */
	private User unknownUser;

	/** ObjectIdentityDao */
	private ObjectIdentityDao objectIdentityDao;
	
	/** DocumentService */
	private DocumentService documentService;

	/** ObjectIdentities */
	private Collection<ObjectIdentity> objectIdentities = new ArrayList<ObjectIdentity>();
	
	/** Course Forum Map */
	private Map<Long,Forum> course2Forum = new HashMap<Long,Forum>();

	private int count = 0;

	public void perform() {
		logger.info("initializing...");
		initializeUser();
		logger.info("parsing discussions...");
		importItems();
		logger.info("parsing watch entries...");
		importWatches();
		logger.info("persisting object identities...");
		persistObjectIdentities();
		logger.info("clearing data...");
//		clearingData();
	}

//	private void clearingData() {
//		objectIdentities = null;
//		domain2Forum = null;
//		legacy2Forum = null;
//	}

	private void persistObjectIdentities() {
		objectIdentityDao.create(objectIdentities);
	}

	private void importWatches() {
		ScrollableResults results = legacyDao.loadAllDiscussionWatches();
		Discussionwatch2 watch = null;
		while (results.next()) {
			evict(watch);
			watch = (Discussionwatch2) results.get()[0];
			
			Long courseId = identifierDao.getId(watch.getDiscussionitem());
			Forum forum = course2Forum.get(courseId);
//			Forum forum = forumDao.findByDomainIdentifier(courseId); 
			if (forum == null) {
				logger.debug("skip watch entry for discussion item "+watch.getDiscussionitem());
			} else {
				User submitter = userImport.loadUserByLegacyId(watch.getSubmitter());
				if (submitter != null) {
					logger.info("create forum watch for user "+submitter.getDisplayName() );
					ForumWatch forumWatch = ForumWatch.Factory.newInstance();
					forumWatch.setForum(forum);
					forumWatch.setUser(submitter);
					forumWatchDao.create(forumWatch);
				} else {
					logger.info("submitter is null" );
				}
			}
		}
	}

	private void importItems() {
		ScrollableResults results = legacyDao.loadTopDiscussionItems();
		Discussionitem2 item = null;
		while (results.next()) {
			evict(item);
			item = (Discussionitem2) results.get()[0];
			Long courseId = identifierDao.getId(item.getEnrollment());
			if (courseId == null) {
				logger.debug("skip discussion item " + item.getId() + " because course doesn't exists.");
			} else {
				ObjectIdentity courseOI = objectIdentityDao.load(courseId);

				Forum forum = loadForum(courseId);
				
				User submitter = loadSubmitter(item);

				Topic topic = Topic.Factory.newInstance(0, false, submitter);
				topicDao.create(topic);
				ObjectIdentity topicOI = saveObjectIdentity(courseOI, topic);
				forum.addTopic(topic);

				createRecursivePost(item, topic, topicOI);
				topicDao.update(topic);
				progress();
			}
		}
	}

	private void initializeUser() {
		unknownUser = userDao.load(-11L);
	}

	private Forum loadForum(Long courseId) {
		Forum forum = course2Forum.get(courseId);
//		Forum forum = forumDao.findByDomainIdentifier(courseId);
		if (forum == null) {
			logger.debug("creating forum for course " + courseId);
			forum = Forum.Factory.newInstance(courseId, false);
			forumDao.create(forum);
			course2Forum.put(courseId, forum);
		} else {
			logger.debug("found forum "+forum.getDomainIdentifier());
		}
		return forum;
	}

	private ObjectIdentity saveObjectIdentity(ObjectIdentity parentObjectIdentity, DomainObject domainObject) {
		if (domainObject.getId() == null) {
			throw new RuntimeException("DomainObject Id is null!");
		}
		ObjectIdentity objectIdentity = ImportUtil.createObjectIdentity(domainObject.getId(), parentObjectIdentity);
		objectIdentityDao.create(objectIdentity);
		return objectIdentity;
	}

	private void progress() {
		count++;
		if (count % 200 == 0) {
			logger.debug("processed " + count);
		}
	}

	private void createRecursivePost(Discussionitem2 discussionItem, Topic topic, ObjectIdentity topicObjectIdentity) {
		logger.debug("parsing " + discussionItem.getId());

		Post post = Post.Factory.newInstance();

		post.setTitle(discussionItem.getSubject());
		post.setText(discussionItem.getTtext());
		post.setCreated(discussionItem.getDdate());
		post.setIp("legacy-import");
		post.setSubmitter(loadSubmitter(discussionItem));
		if (discussionItem.getFormula() != null) {
			try {
				post.setFormulaString(discussionItem.getFormula().getText());
			} catch (Exception ex) {
				logger.trace("skip formula ");
			}
		}
		topic.addPost(post);
		postDao.create(post);
		saveObjectIdentity(topicObjectIdentity, post);
		importAttachment(discussionItem, post);

		for (Discussionitem2 item : discussionItem.getDiscussionitems()) {
			createRecursivePost(item, topic, topicObjectIdentity);
		}
	}

	private void importAttachment(Discussionitem2 discussionItem, Post post) {
		if (StringUtils.isNotBlank(discussionItem.getDiscussionfilename())) {
			try {
				FileInfo fileInfo = new FileInfo();
				fileInfo.setContentType("application/octet-stream");
				fileInfo.setFileName(discussionItem.getDiscussionfilename());
				byte[] data = legacyDao.loadDiscussionFileData(discussionItem.getDiscussionfilebase());
				if (data != null && data.length > 0) {
					fileInfo.setFileSize(data.length);
					fileInfo.setInputStream(new ByteArrayInputStream(data));
					fileInfo.setCreated(post.getCreated());
					List<FileInfo> fileInfos = new ArrayList<FileInfo>();
					fileInfos.add(fileInfo);
					documentService.diffSave(post, fileInfos);
				} else {
					logger.debug("skip attachment due to zero data!");
				}
			} catch (Exception ex) {
				logger.trace("skip attachment due to an error...");
			}
		}
	}

	private User loadSubmitter(Discussionitem2 item) {
		User submitter = userImport.loadUserByLegacyId(item.getSubmitterpk());
		if (submitter == null) {
			submitter = unknownUser;
		}
		return submitter;
	}

	public void setForumDao(ForumDao forumDao) {
		this.forumDao = forumDao;
	}

	public void setUserImport(UserImport userImport) {
		this.userImport = userImport;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setTopicDao(TopicDao topicDao) {
		this.topicDao = topicDao;
	}

	public void setObjectIdentityDao(ObjectIdentityDao objectIdentityDao) {
		this.objectIdentityDao = objectIdentityDao;
	}

	public void setPostDao(PostDao postDao) {
		this.postDao = postDao;
	}

	public void setForumWatchDao(ForumWatchDao forumWatchDao) {
		this.forumWatchDao = forumWatchDao;
	}

	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}

}
