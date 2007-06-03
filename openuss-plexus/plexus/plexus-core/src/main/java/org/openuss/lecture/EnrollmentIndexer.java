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
public class EnrollmentIndexer extends DomainIndexer {

	private static final String SPACE = " ";

	private static final String DOMAINTYPE_VALUE = "ENROLLMENT";

	private static final Logger logger = Logger.getLogger(EnrollmentIndexer.class);

	private EnrollmentDao enrollmentDao;
	
	public void create() {
		final Enrollment enrollment = getEnrollment();
		if (enrollment != null) {
			logger.debug("create new index for enrollment "+enrollment.getName()+" ("+enrollment.getId()+")");
			getLuceneIndexTemplate().addDocument(new DocumentCreator() {
				public Document createDocument() throws Exception {
					Document document = new Document();
					setFields(enrollment, document);
					return document;
				}
			});
		}
	}

	public void update() {
		final Enrollment enrollment = getEnrollment();
		if (enrollment != null) {
			logger.debug("update new index for enrollment "+enrollment.getName()+" ("+enrollment.getId()+")");
			delete();
			create();
//			try {
//				Term facultyTerm = new Term(IDENTIFIER, String.valueOf(enrollment.getId()));
//				getLuceneIndexTemplate().updateDocument(facultyTerm, new DocumentModifier() {
//					public Document updateDocument(Document document) throws Exception {
//						Document newDocument = new Document();
//						setFields(enrollment, document);
//						return newDocument;
//					}
//				});
//			} catch (LuceneIndexAccessException ex) {
//				create();				
//			}
		}
	}

	private Enrollment getEnrollment() {
		Validate.notNull(getDomainObject(), "Parameter domainObject must not be null");
		Validate.notNull(getDomainObject().getId(), "Parameter domainObject.id must not be null");
		return enrollmentDao.load(getDomainObject().getId());
	}

	private void setFields(final Enrollment enrollment, Document document) {
		document.add(new Field(IDENTIFIER, String.valueOf(enrollment.getId()), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(DOMAINTYPE, DOMAINTYPE_VALUE, Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(MODIFIED, 
				DateTools.dateToString(new Date(), Resolution.MINUTE), Field.Store.YES,
				Field.Index.UN_TOKENIZED));
		document.add(new Field(CONTENT, content(enrollment), Field.Store.YES, Field.Index.TOKENIZED));
		document.add(new Field(DETAILS, details(enrollment), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(NAME, name(enrollment), Field.Store.YES, Field.Index.UN_TOKENIZED));
	}
	
	private String name(final Enrollment enrollment) {
		return enrollment.getName()+" ("+enrollment.getShortcut()+") - "+enrollment.getPeriod().getName();
	}
	
	private String details(final Enrollment enrollment) {
		return enrollment.getFaculty().getName()+SPACE
				+enrollment.getFaculty().getOwnername()+SPACE
				+StringUtils.abbreviate(enrollment.getDescription(), 100);
	}

	private String content(final Enrollment enrollment) {
		StringBuilder content = new StringBuilder();
		content.append(enrollment.getId()+SPACE);
		content.append(StringUtils.trimToEmpty(enrollment.getName())+SPACE);
		content.append(StringUtils.trimToEmpty(enrollment.getShortcut())+SPACE);
		content.append(StringUtils.trimToEmpty(enrollment.getDescription())+SPACE);
		content.append(StringUtils.trimToEmpty(enrollment.getPeriod().getName())+SPACE);
		content.append(StringUtils.trimToEmpty(enrollment.getPeriod().getDescription())+SPACE);
		content.append(StringUtils.trimToEmpty(enrollment.getFaculty().getDescription())+SPACE);
		content.append(StringUtils.trimToEmpty(enrollment.getFaculty().getName())+SPACE);
		content.append(StringUtils.trimToEmpty(enrollment.getFaculty().getOwnername())+SPACE);
		content.append(StringUtils.trimToEmpty(enrollment.getFaculty().getAddress())+SPACE);
		return content.toString();
	}

	public EnrollmentDao getEnrollmentDao() {
		return enrollmentDao;
	}

	public void setEnrollmentDao(EnrollmentDao enrollmentDao) {
		this.enrollmentDao = enrollmentDao;
	}

}
