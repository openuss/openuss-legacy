package org.openuss.lecture.indexer;

import java.util.Date;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.DateTools.Resolution;
import org.apache.lucene.index.Term;
import org.openuss.lecture.Enrollment;
import org.openuss.lecture.EnrollmentDao;
import org.openuss.lecture.Faculty;
import org.openuss.search.DomainIndexCommand;
import org.springmodules.lucene.index.LuceneIndexAccessException;
import org.springmodules.lucene.index.core.DocumentCreator;
import org.springmodules.lucene.index.core.DocumentModifier;

/**
 * FIXME - implement everything
 * @author Ingo Dueppe
 */
public class EnrollmentIndexer extends DomainIndexCommand {

	private static final String DOMAINTYPE_VALUE = "ENROLLMENT";

	private static final Logger logger = Logger.getLogger(EnrollmentIndexer.class);

	private EnrollmentDao enrollmentDao;
	
	public void create() {
		final Faculty faculty = loadFaculty();
		if (faculty != null) {
			logger.debug("create new index for enrollment "+faculty.getName()+" ("+faculty.getId()+")");
			getLuceneIndexTemplate().addDocument(new DocumentCreator() {
				public Document createDocument() throws Exception {
					Document document = new Document();
					setFields(faculty, document);
					return document;
				}
			});
		}
	}

	public void update() {
		final Faculty faculty = loadFaculty();
		if (faculty != null) {
			logger.debug("update new index for enrollment "+faculty.getName()+" ("+faculty.getId()+")");
			try {
				Term facultyTerm = new Term(IDENTIFIER, String.valueOf(faculty.getId()));
				getLuceneIndexTemplate().updateDocument(facultyTerm, new DocumentModifier() {
					public Document updateDocument(Document document) throws Exception {
						Document newDocument = new Document();
						setFields(faculty, document);
						return newDocument;
					}
				});
			} catch (LuceneIndexAccessException ex) {
				create();				
			}
		}
	}

	private Faculty loadFaculty() {
		Validate.notNull(getDomainObject(), "Parameter domainObject must not be null");
		Validate.notNull(getDomainObject().getId(), "Parameter domainObject.id must not be null");
		Enrollment enrollment = enrollmentDao.load(getDomainObject().getId());
		return enrollment.getFaculty();
	}

	private void setFields(final Faculty faculty, Document document) {
		document.add(new Field(IDENTIFIER, String.valueOf(faculty.getId()), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(DOMAINTYPE, DOMAINTYPE_VALUE, Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(MODIFIED, 
				DateTools.dateToString(new Date(), Resolution.MINUTE), Field.Store.YES,
				Field.Index.UN_TOKENIZED));
		document.add(new Field(CONTENT, getFacultyContent(faculty), Field.Store.YES, Field.Index.TOKENIZED));
	}

	private String getFacultyContent(final Faculty faculty) {
		StringBuilder content = new StringBuilder();
		content.append(faculty.getId());
		content.append(" ");
		content.append(faculty.getName());
		content.append(" ");
		content.append(faculty.getDescription());
		content.append(" ");
		content.append(faculty.getOwnername());
		content.append(" ");
		content.append(faculty.getAddress());
		content.append(" ");
		content.append(faculty.getEmail());
		content.append(" ");
		content.append(faculty.getCity());
		content.append(" ");
		content.append(faculty.getPostcode());
		content.append(" ");
		content.append(faculty.getTelephone());
		content.append(" ");
		content.append(faculty.getTelefax());
		content.append(" ");
		content.append(faculty.getWebsite());
		return content.toString();
	}

	public EnrollmentDao getEnrollmentDao() {
		return enrollmentDao;
	}

	public void setEnrollmentDao(EnrollmentDao enrollmentDao) {
		this.enrollmentDao = enrollmentDao;
	}

}
