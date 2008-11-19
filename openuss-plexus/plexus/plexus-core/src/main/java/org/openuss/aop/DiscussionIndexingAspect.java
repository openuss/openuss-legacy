package org.openuss.aop;

import org.apache.log4j.Logger;
import org.openuss.discussion.Post;
import org.openuss.discussion.PostDao;
import org.openuss.discussion.PostImpl;
import org.openuss.discussion.PostInfo;
import org.openuss.search.IndexerApplicationException;
import org.openuss.search.IndexerService;

/** Aspect for the indexing of posts.
 * 
 * @author Ingo Dueppe
 * @author Peter Schuh
 * @author Tobias Brockmann
 */
public class DiscussionIndexingAspect {

	private static final Logger logger = Logger.getLogger(DiscussionIndexingAspect.class);

	private IndexerService indexerService;
	
	private PostDao postDao;
	
	private Post post;

	/**
	 * Creates index entry for a post
	 * 
	 * @param postInfo
	 */
	public void createPostIndex(PostInfo postInfo) {
		logger.info("Starting method createPostIndex");
		try {
			post = postDao.postInfoToEntity(postInfo);
			indexerService.createIndex(post);
			
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}

	/**
	 * Updates post index entry.
	 * 
	 * @param postInfo
	 */
	public void updatePostIndex(PostInfo postInfo) {
		logger.info("Starting method updatePostIndex");
		try {
			post = postDao.postInfoToEntity(postInfo);
			indexerService.updateIndex(post);
				
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}
	
	/**
	 * Deletes post from discussion index.
	 * @param postInfo
	 */
	public void deletePostIndex(PostInfo postInfo) {
		logger.info("Starting method deletePostIndex with parameter postInfo");
		deletePostIndexById(postInfo.getId());
	}
	
	/**
	 * Deletes post from discussion index.
	 * @param postId
	 */
	public void deletePostIndexById(Long postId) {
		logger.info("Starting method deletePostIndex");
		try {
			Post post = new PostImpl();
			post.setId(postId);
			indexerService.deleteIndex(post);
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}

	/* getter and setter */
	public IndexerService getIndexerService() {
		return indexerService;
	}

	public void setIndexerService(IndexerService indexerService) {
		this.indexerService = indexerService;
	}

	public PostDao getPostDao() {
		return postDao;
	}

	public void setPostDao(PostDao postDao) {
		this.postDao = postDao;
	}
}
