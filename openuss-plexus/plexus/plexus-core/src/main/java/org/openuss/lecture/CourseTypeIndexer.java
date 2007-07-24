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
public class CourseTypeIndexer extends DomainIndexer {

	private static final String SPACE = " ";

	private static final String DOMAINTYPE_VALUE = "CourseType";

	private static final Logger logger = Logger.getLogger(CourseTypeIndexer.class);

	private CourseTypeDao courseTypeDao;
	
	public void create() {
		final CourseType courseType = getCourseType();
		if (courseType != null) {
			logger.debug("create new index for courseType "+courseType.getName()+" ("+courseType.getId()+")");
			getLuceneIndexTemplate().addDocument(new DocumentCreator() {
				public Document createDocument() throws Exception {
					Document document = new Document();
					setFields(courseType, document);
					return document;
				}
			});
		}
	}

	public void update() {
		final CourseType courseType = getCourseType();
		if (courseType != null) {
			logger.debug("update new index forcourseType "+courseType.getName()+" ("+courseType.getId()+")");
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

	private CourseType getCourseType() {
		Validate.notNull(getDomainObject(), "Parameter domainObject must not be null");
		Validate.notNull(getDomainObject().getId(), "Parameter domainObject.id must not be null");
		return courseTypeDao.load(getDomainObject().getId());
	}

	private void setFields(final CourseType courseType, Document document) {
		document.add(new Field(IDENTIFIER, String.valueOf(courseType.getId()), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(DOMAINTYPE, DOMAINTYPE_VALUE, Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(MODIFIED, 
				DateTools.dateToString(new Date(), Resolution.MINUTE), Field.Store.YES,
				Field.Index.UN_TOKENIZED));
		document.add(new Field(CONTENT, content(courseType), Field.Store.YES, Field.Index.TOKENIZED));
		document.add(new Field(DETAILS, details(courseType), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(NAME, name(courseType), Field.Store.YES, Field.Index.UN_TOKENIZED));
	}
	
	private String name(final CourseType courseType) {
		return courseType.getName()+" ("+courseType.getShortcut()+")";
	}
	
	private String details(final CourseType courseType) {
		StringBuilder details = new StringBuilder();
		details.append(StringUtils.trimToEmpty(courseType.getDescription()));
	
		return details.toString();
	}
	
	private String content(final CourseType courseType) {
		StringBuilder content = new StringBuilder();
		content.append(courseType.getId()+SPACE);
		content.append(StringUtils.trimToEmpty(courseType.getName())+SPACE);
		content.append(StringUtils.trimToEmpty(courseType.getShortcut())+SPACE);
		content.append(StringUtils.trimToEmpty(courseType.getDescription())+SPACE);
		content.append(StringUtils.trimToEmpty(courseType.getInstitute().getAddress())+SPACE);
		content.append(StringUtils.trimToEmpty(courseType.getInstitute().getCity())+SPACE);
		content.append(StringUtils.trimToEmpty(courseType.getInstitute().getDescription())+SPACE);
		content.append(StringUtils.trimToEmpty(courseType.getInstitute().getLocale())+SPACE);
		content.append(StringUtils.trimToEmpty(courseType.getInstitute().getLand())+SPACE);
		content.append(StringUtils.trimToEmpty(courseType.getInstitute().getName())+SPACE);
		content.append(StringUtils.trimToEmpty(courseType.getInstitute().getOwnername())+SPACE);
		content.append(StringUtils.trimToEmpty(courseType.getInstitute().getPostcode())+SPACE);
		content.append(StringUtils.trimToEmpty(courseType.getInstitute().getShortcut())+SPACE);
		content.append(StringUtils.trimToEmpty(courseType.getInstitute().getTelefax())+SPACE);
		content.append(StringUtils.trimToEmpty(courseType.getInstitute().getTelephone())+SPACE);
		content.append(StringUtils.trimToEmpty(courseType.getInstitute().getTheme())+SPACE);
		content.append(StringUtils.trimToEmpty(courseType.getInstitute().getWebsite())+SPACE);
		
		return content.toString();
	}

	public CourseTypeDao getCourseTypeDao() {
		return courseTypeDao;
	}

	public void setCourseTypeDao(CourseTypeDao departmentDao) {
		this.courseTypeDao = departmentDao;
	}
}
