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
 * @author Kai Stettner
 * @author Malte Stockmann
 */
public class CourseIndexer extends DomainIndexer {

	private static final String SPACE = " ";

	private static final String DOMAINTYPE_VALUE = "course";

	private static final Logger logger = Logger.getLogger(CourseIndexer.class);

	private CourseDao courseDao;
	
	public void create() {
		final Course course = getCourse();
		if (course != null) {
			// course will not be indexed if it is not enabled
			if(!course.isEnabled()){
				logger.debug("method create: course is disabled - skip index "
						+"entry creation for course " + course.getName() 
						+ " (" + course.getId() + ")");
				return;
			}
			logger.debug("create new index entry for course "+course.getName()+" ("+course.getId()+")");
			getLuceneIndexTemplate().addDocument(new DocumentCreator() {
				public Document createDocument() throws Exception {
					Document document = new Document();
					setFields(course, document);
					return document;
				}
			});
		}
	}

	public void update() {
		logger.debug("Starting method update");
		final Course course = getCourse();
		if (course != null) {
			logger.debug("update index entry for course "+course.getName()+" ("+course.getId()+")");
			// update doesn't work properly, so delete and create does the same
			delete();
			create();
		}
	}

	private Course getCourse() {
		Validate.notNull(getDomainObject(), "Parameter domainObject must not be null");
		Validate.notNull(getDomainObject().getId(), "Parameter domainObject.id must not be null");
		return courseDao.load(getDomainObject().getId());
	}

	private void setFields(final Course course, Document document) {
		boolean isOfficial; 
		if(course.getCourseType().getInstitute().getDepartment().getDepartmentType().equals(DepartmentType.OFFICIAL)){
			isOfficial = true;
		} else {
			isOfficial = false;
		}
		
		document.add(new Field(IDENTIFIER, String.valueOf(course.getId()), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(DOMAINTYPE, DOMAINTYPE_VALUE, Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(MODIFIED, 
				DateTools.dateToString(new Date(), Resolution.MINUTE), Field.Store.YES,
				Field.Index.UN_TOKENIZED));
		document.add(new Field(CONTENT, content(course), Field.Store.YES, Field.Index.TOKENIZED));
		document.add(new Field(DETAILS, details(course), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(NAME, name(course), Field.Store.YES, Field.Index.UN_TOKENIZED));
		
		document.add(new Field(COURSE_TYPE_IDENTIFIER, String.valueOf(course.getCourseType().getId()), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(INSTITUTE_IDENTIFIER, String.valueOf(String.valueOf(course.getCourseType().getInstitute().getId())), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(DEPARTMENT_IDENTIFIER, String.valueOf(course.getCourseType().getInstitute().getDepartment().getId()), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(UNIVERSITY_IDENTIFIER, String.valueOf(course.getCourseType().getInstitute().getDepartment().getUniversity().getId()), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(PERIOD_IDENTIFIER, String.valueOf(course.getPeriod().getId()), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(OFFICIAL_FLAG, String.valueOf(isOfficial), Field.Store.YES, Field.Index.UN_TOKENIZED));
	}
	
	private String name(final Course course) {
		return course.getName()+" ("+course.getShortcut()+") - "+course.getPeriod().getName();
	}
	
	private String details(final Course course) {
		return course.getCourseType().getInstitute().getName()+SPACE
				+course.getCourseType().getInstitute().getOwnerName()+SPACE
				+StringUtils.abbreviate(course.getDescription(), 100);
	}

	private String content(final Course course) {
		StringBuilder content = new StringBuilder();
		content.append(course.getId()+SPACE);
		content.append(StringUtils.trimToEmpty(course.getName())+SPACE);
		content.append(StringUtils.trimToEmpty(course.getShortcut())+SPACE);
		content.append(StringUtils.trimToEmpty(course.getDescription())+SPACE);
		content.append(StringUtils.trimToEmpty(course.getPeriod().getName())+SPACE);
		content.append(StringUtils.trimToEmpty(course.getPeriod().getDescription())+SPACE);
		return content.toString();
	}

	public CourseDao getCourseDao() {
		return courseDao;
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

}
