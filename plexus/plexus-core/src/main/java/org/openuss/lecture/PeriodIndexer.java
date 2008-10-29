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
* @author Kai Stettner
*/
public class PeriodIndexer extends DomainIndexer {

	private static final String SPACE = " ";

	private static final String DOMAINTYPE_VALUE = "Period";

	private static final Logger logger = Logger.getLogger(PeriodIndexer.class);

	private PeriodDao periodDao;
	
	public void create() {
		final Period period = getPeriod();
		if (period != null) {
			logger.debug("create new index for period "+period.getName()+" ("+period.getId()+")");
			getLuceneIndexTemplate().addDocument(new DocumentCreator() {
				public Document createDocument() throws Exception {
					Document document = new Document();
					setFields(period, document);
					return document;
				}
			});
		}
	}

	public void update() {
		final Period period = getPeriod();
		if (period != null) {
			logger.debug("update new index for period "+period.getName()+" ("+period.getId()+")");
			delete();
			create();
//			try {
//				Term instituteTerm = new Term(IDENTIFIER, String.valueOf(course.getId()));
//				getLuceneIndexTemplate().updateDocument(instituteTerm, new DocumentModifier() {
//					public Document updateDocument(Document document) throws Exception {
//						Document newDocument = new Document();
//						setFields(course, document);
//						return newDocument;
//					}
//				});
//			} catch (LuceneIndexAccessException ex) {
//				create();				
//			}
		}
	}

	private Period getPeriod() {
		Validate.notNull(getDomainObject(), "Parameter domainObject must not be null");
		Validate.notNull(getDomainObject().getId(), "Parameter domainObject.id must not be null");
		return periodDao.load(getDomainObject().getId());
	}

	private void setFields(final Period period, Document document) {
		document.add(new Field(IDENTIFIER, String.valueOf(period.getId()), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(DOMAINTYPE, DOMAINTYPE_VALUE, Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(MODIFIED, 
				DateTools.dateToString(new Date(), Resolution.MINUTE), Field.Store.YES,
				Field.Index.UN_TOKENIZED));
		document.add(new Field(CONTENT, content(period), Field.Store.YES, Field.Index.TOKENIZED));
		document.add(new Field(DETAILS, details(period), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(NAME, name(period), Field.Store.YES, Field.Index.UN_TOKENIZED));
	}
	
	private String name(final Period period) {
		return period.getName()+" ("+period.getDescription()+")";
	}
	
	private String details(final Period period) {
		StringBuilder details = new StringBuilder();
		details.append(StringUtils.trimToEmpty(period.getDescription()));
	
		return details.toString();
	}
	
	private String content(final Period period) {
		//TODO: Implement me correctly!
		
		StringBuilder content = new StringBuilder();
		content.append(period.getId()+SPACE);
		content.append(StringUtils.trimToEmpty(period.getName())+SPACE);
		content.append(StringUtils.trimToEmpty(period.getDescription())+SPACE);
		/*
		content.append(StringUtils.trimToEmpty(period.getInstitute().getAddress())+SPACE);
		content.append(StringUtils.trimToEmpty(period.getInstitute().getCity())+SPACE);
		content.append(StringUtils.trimToEmpty(period.getInstitute().getDescription())+SPACE);
		content.append(StringUtils.trimToEmpty(period.getInstitute().getLocale())+SPACE);
		content.append(StringUtils.trimToEmpty(period.getInstitute().getLand())+SPACE);
		content.append(StringUtils.trimToEmpty(period.getInstitute().getName())+SPACE);
		content.append(StringUtils.trimToEmpty(period.getInstitute().getOwnername())+SPACE);
		content.append(StringUtils.trimToEmpty(period.getInstitute().getPostcode())+SPACE);
		content.append(StringUtils.trimToEmpty(period.getInstitute().getShortcut())+SPACE);
		content.append(StringUtils.trimToEmpty(period.getInstitute().getTelefax())+SPACE);
		content.append(StringUtils.trimToEmpty(period.getInstitute().getTelephone())+SPACE);
		content.append(StringUtils.trimToEmpty(period.getInstitute().getTheme())+SPACE);
		content.append(StringUtils.trimToEmpty(period.getInstitute().getWebsite())+SPACE);
		*/
		return content.toString();
	}

	public PeriodDao getPeriodDao() {
		return periodDao;
	}

	public void setPeriodDao(PeriodDao departmentDao) {
		this.periodDao = departmentDao;
	}
}
