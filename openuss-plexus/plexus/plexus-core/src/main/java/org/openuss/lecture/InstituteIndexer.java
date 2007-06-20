package org.openuss.lecture;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.DateTools.Resolution;
import org.openuss.search.DomainIndexer;
import org.springmodules.lucene.index.core.DocumentCreator;

/**
 * 
 * @author Ingo Dueppe
 */
public class InstituteIndexer extends DomainIndexer {

	private static final String DOMAINTYPE_VALUE = "INSTITUTE";

	private static final Logger logger = Logger.getLogger(InstituteIndexer.class);

	private InstituteDao instituteDao;

	public void create() {
		final Institute institute = getInstitute();
		if (institute != null) {
			logger.debug("create new index for institute " + institute.getName() + " (" + institute.getId() + ")");
			getLuceneIndexTemplate().addDocument(new DocumentCreator() {
				public Document createDocument() throws Exception {
					Document document = new Document();
					setFields(institute, document);
					return document;
				}
			});
		}
	}

	public void update() {
		final Institute institute = getInstitute();
		if (institute != null) {
			logger.debug("update new index for institute " + institute.getName() + " (" + institute.getId() + ")");
			// update doesn't work properly, so deleting and create does the same.
			delete();
			create();
			//	Term instituteTerm = new Term(IDENTIFIER, String.valueOf(institute.getId().longValue()));
			//	try {
			//		getLuceneIndexTemplate().updateDocument(instituteTerm, new DocumentModifier() {
			//			public Document updateDocument(Document document) throws Exception {
			//				Document newDocument = new Document();
			//				setFields(institute, document);
			//				return newDocument;
			//			}
			//		});
			//	} catch (LuceneIndexAccessException ex) {
			//		logger.debug("Exception during update, trying to create...",ex);
			//		create();
			//	}
		}
	}

	private Institute getInstitute() {
		Validate.notNull(getDomainObject(), "Parameter domainObject must not be null");
		Validate.notNull(getDomainObject().getId(), "Parameter domainObject.id must not be null");
		return instituteDao.load(getDomainObject().getId());
	}

	private void setFields(final Institute institute, Document document) {
		document.add(new Field(IDENTIFIER, String.valueOf(institute.getId().longValue()), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(DOMAINTYPE, DOMAINTYPE_VALUE, Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(MODIFIED, DateTools.dateToString(new Date(), Resolution.MINUTE), Field.Store.YES,
				Field.Index.UN_TOKENIZED));
		document.add(new Field(CONTENT, content(institute), Field.Store.YES, Field.Index.TOKENIZED));
		
		document.add(new Field(NAME, name(institute), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(DETAILS, details(institute), Field.Store.YES, Field.Index.UN_TOKENIZED));
	}
	
	private String name(final Institute institute) {
		return institute.getName()+" ("+institute.getShortcut()+")";
	}
	
	private String details(final Institute institute) {
		StringBuilder details = new StringBuilder();
		details.append(StringUtils.trimToEmpty(institute.getOwnername()));
		details.append(StringUtils.trimToEmpty(institute.getAddress()));
		return details.toString();
	}

	private String content(final Institute institute) {
		StringBuilder content = new StringBuilder();
		content.append(institute.getId());
		content.append(" ");
		content.append(institute.getShortcut());
		content.append(" ");
		content.append(institute.getName());
		content.append(" ");
		content.append(institute.getDescription());
		content.append(" ");
		content.append(institute.getOwnername());
		content.append(" ");
		content.append(institute.getAddress());
		content.append(" ");
		content.append(institute.getEmail());
		content.append(" ");
		content.append(institute.getCity());
		content.append(" ");
		content.append(institute.getPostcode());
		content.append(" ");
		content.append(institute.getTelephone());
		content.append(" ");
		content.append(institute.getTelefax());
		content.append(" ");
		content.append(institute.getWebsite());
		return content.toString();
	}

	public InstituteDao getInstituteDao() {
		return instituteDao;
	}

	public void setInstituteDao(InstituteDao instituteDao) {
		this.instituteDao = instituteDao;
	}
}
