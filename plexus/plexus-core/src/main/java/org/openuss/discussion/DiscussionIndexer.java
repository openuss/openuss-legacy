package org.openuss.discussion;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.DateTools.Resolution;
import org.apache.lucene.index.Term;
import org.openuss.search.DomainIndexer;
import org.springmodules.lucene.index.core.DocumentCreator;

/**
 *
 * @author Ingo Dueppe
 * @author Peter Schuh
 * @author Tobias Brockmann
 */
public class DiscussionIndexer extends DomainIndexer {
	private static final String DOMAINTYPE_VALUE = "post";

	private static final Logger logger = Logger.getLogger(DiscussionIndexer.class);

	private PostDao postDao;
	
	public void create() {
		final Post post = getPost();
		if (post != null) {
			logger.debug("create new index entry for post "+post.getId());
			getLuceneIndexTemplate().addDocument(new DocumentCreator() {
				public Document createDocument() throws Exception {
					Document document = new Document();
					setFields(post, document);
					return document;
				}
			});
		}
	}

	public void update() {
		logger.debug("Starting method update");
		final Post post = getPost();
		if (post != null) {
			logger.debug("update index entry for post "+post.getId());
			delete();
			create();
		}
	}

	/**
	 * Delete the index entry of the domain object in discussion index
	 */
	@Override
	public void delete() {
		logger.debug("Method delete: delete index entry for post object");
		Validate.notNull(getDomainObject(),"Field domainObject must not be null");
		logger.debug("deleting post object ["+getDomainObject().getId()+"] from index");
		getLuceneIndexTemplate().deleteDocuments(new Term(IDENTIFIER, String.valueOf(getDomainObject().getId())));
	}
	
	private Post getPost() {
		Validate.notNull(getDomainObject(), "Parameter domainObject must not be null");
		Validate.notNull(getDomainObject().getId(), "Parameter domainObject.id must not be null");
		return postDao.load(getDomainObject().getId());
	}

	private void setFields(final Post post, Document document) {		 
		String postText = post.getText();
		document.add(new Field(IDENTIFIER, String.valueOf(post.getId()), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(POST_IDENTIFIER, String.valueOf(post.getId()), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(DOMAINTYPE, DOMAINTYPE_VALUE, Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(CREATED, 
				DateTools.dateToString(post.getCreated(), Resolution.MINUTE), Field.Store.YES,
				Field.Index.UN_TOKENIZED));
		document.add(new Field(MODIFIED, 
				DateTools.dateToString(post.getLastModification(), Resolution.MINUTE), Field.Store.YES,
				Field.Index.UN_TOKENIZED));
		document.add(new Field(POST_SUBMITTER_IDENTIFIER, String.valueOf(post.getSubmitter().getId()), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(POST_SUBMITTER_NAME, post.getSubmitterName(), Field.Store.YES, Field.Index.TOKENIZED));
		document.add(new Field(TOPIC_IDENTIFIER, String.valueOf(post.getTopic().getId()), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(FORUM_IDENTIFIER, String.valueOf(post.getTopic().getForum().getId()), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(POST_TITLE, post.getTitle(), Field.Store.YES, Field.Index.TOKENIZED));
		document.add(new Field(COURSE_IDENTIFIER, String.valueOf(post.getTopic().getForum().getDomainIdentifier()), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(CONTENT, postText, Field.Store.YES, Field.Index.TOKENIZED));
	}

	public PostDao getPostDao() {
		return postDao;
	}

	public void setPostDao(PostDao postDao) {
		this.postDao = postDao;
	}
}
