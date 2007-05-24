package org.openuss.lecture;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.DateTools.Resolution;
import org.apache.lucene.index.Term;
import org.openuss.lecture.Faculty;
import org.openuss.lecture.FacultyDao;
import org.openuss.search.DomainIndexer;
import org.springmodules.lucene.index.LuceneIndexAccessException;
import org.springmodules.lucene.index.core.DocumentCreator;
import org.springmodules.lucene.index.core.DocumentModifier;

/**
 * 
 * @author Ingo Dueppe
 */
public class FacultyIndexer extends DomainIndexer {

	private static final String DOMAINTYPE_VALUE = "FACULTY";

	private static final Logger logger = Logger.getLogger(FacultyIndexer.class);

	private FacultyDao facultyDao;

	public void create() {
		final Faculty faculty = getFaculty();
		if (faculty != null) {
			logger.debug("create new index for faculty " + faculty.getName() + " (" + faculty.getId() + ")");
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
		final Faculty faculty = getFaculty();
		if (faculty != null) {
			logger.debug("update new index for faculty " + faculty.getName() + " (" + faculty.getId() + ")");
			Term facultyTerm = new Term(IDENTIFIER, String.valueOf(faculty.getId()));
			try {
				getLuceneIndexTemplate().updateDocument(facultyTerm, new DocumentModifier() {
					public Document updateDocument(Document document) throws Exception {
						Document newDocument = new Document();
						setFields(faculty, document);
						return newDocument;
					}
				});
			} catch (LuceneIndexAccessException ex) {
				logger.warn("exception during update, trying to create...");
				create();
			}
		}
	}

	private Faculty getFaculty() {
		Validate.notNull(getDomainObject(), "Parameter domainObject must not be null");
		Validate.notNull(getDomainObject().getId(), "Parameter domainObject.id must not be null");
		return facultyDao.load(getDomainObject().getId());
	}

	private void setFields(final Faculty faculty, Document document) {
		document.add(new Field(IDENTIFIER, String.valueOf(faculty.getId()), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(DOMAINTYPE, DOMAINTYPE_VALUE, Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(MODIFIED, DateTools.dateToString(new Date(), Resolution.MINUTE), Field.Store.YES,
				Field.Index.UN_TOKENIZED));
		document.add(new Field(CONTENT, content(faculty), Field.Store.YES, Field.Index.TOKENIZED));
		
		document.add(new Field(NAME, name(faculty), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(DETAILS, details(faculty), Field.Store.YES, Field.Index.UN_TOKENIZED));
	}
	
	private String name(final Faculty faculty) {
		return faculty.getName()+" ("+faculty.getShortcut()+")";
	}
	
	private String details(final Faculty faculty) {
		StringBuilder details = new StringBuilder();
		details.append(StringUtils.trimToEmpty(faculty.getOwnername()));
		details.append(StringUtils.trimToEmpty(faculty.getAddress()));
		return details.toString();
	}

	private String content(final Faculty faculty) {
		StringBuilder content = new StringBuilder();
		content.append(faculty.getId());
		content.append(" ");
		content.append(faculty.getShortcut());
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

	public FacultyDao getFacultyDao() {
		return facultyDao;
	}

	public void setFacultyDao(FacultyDao facultyDao) {
		this.facultyDao = facultyDao;
	}
}
