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
* @author Malte Stockmann
*/
public class DepartmentIndexer extends DomainIndexer {
	
	private static final String SPACE = " ";
	private static final String NEWLINE = "<br/>";

	private static final String DOMAINTYPE_VALUE = "department";

	private static final Logger logger = Logger.getLogger(DepartmentIndexer.class);

	private DepartmentDao departmentDao;
	
	public void create() {
		final Department department = getDepartment();
		if (department != null) {
			logger.debug("create new index entry for department "+department.getName()+" ("+department.getId()+")");
			getLuceneIndexTemplate().addDocument(new DocumentCreator() {
				public Document createDocument() throws Exception {
					Document document = new Document();
					setFields(department, document);
					return document;
				}
			});
		}
	}

	public void update() {
		final Department department = getDepartment();
		if (department != null) {
			logger.debug("update index entry for department "+department.getName()+" ("+department.getId()+")");
			delete();
			create();
		}
	}

	private Department getDepartment() {
		Validate.notNull(getDomainObject(), "Parameter domainObject must not be null");
		Validate.notNull(getDomainObject().getId(), "Parameter domainObject.id must not be null");
		return departmentDao.load(getDomainObject().getId());
	}

	private void setFields(final Department department, Document document) {
		boolean isOfficial; 
		if(department.getDepartmentType().equals(DepartmentType.OFFICIAL)){
			isOfficial = true;
		} else {
			isOfficial = false;
		}
		
		document.add(new Field(IDENTIFIER, String.valueOf(department.getId()), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(DOMAINTYPE, DOMAINTYPE_VALUE, Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(MODIFIED, 
				DateTools.dateToString(new Date(), Resolution.MINUTE), Field.Store.YES,
				Field.Index.UN_TOKENIZED));
		document.add(new Field(CONTENT, content(department), Field.Store.YES, Field.Index.TOKENIZED));
		document.add(new Field(DETAILS, details(department), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(NAME, name(department), Field.Store.YES, Field.Index.TOKENIZED));
		
		document.add(new Field(COURSE_TYPE_IDENTIFIER, "", Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(INSTITUTE_IDENTIFIER, "", Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(DEPARTMENT_IDENTIFIER, String.valueOf(department.getId()), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(UNIVERSITY_IDENTIFIER, String.valueOf(department.getUniversity().getId()), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(PERIOD_IDENTIFIER, "", Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(OFFICIAL_FLAG, String.valueOf(isOfficial), Field.Store.YES, Field.Index.UN_TOKENIZED));
	}
	
	private String name(final Department department) {
		return department.getName()+" ("+department.getShortcut()+")";
	}
	
	private String details(final Department department) {
		
		StringBuilder details = new StringBuilder();
		
		details.append(StringUtils.trimToEmpty(department.getUniversity().getName()+SPACE));
		if(department.getDepartmentType().equals(DepartmentType.OFFICIAL)){
			details.append(" (offizieller Fachbereich)"+NEWLINE);
		} else {
			details.append(" (inoffizieller Fachbereich)"+NEWLINE);
		}
		
		if(!StringUtils.isEmpty(department.getDescription()))
			details.append(NEWLINE + StringUtils.trimToEmpty(StringUtils.abbreviate(department.getDescription(), 200)));
		
		return details.toString();
	}
	
	private String content(final Department department) {
		StringBuilder content = new StringBuilder();
		content.append(department.getId()+SPACE);
		content.append(StringUtils.trimToEmpty(department.getName())+SPACE);
		content.append(StringUtils.trimToEmpty(department.getShortcut())+SPACE);
		content.append(StringUtils.trimToEmpty(department.getDescription())+SPACE);
//		content.append(StringUtils.trimToEmpty(department.getUniversity().getDescription())+SPACE);
//		content.append(StringUtils.trimToEmpty(department.getUniversity().getName())+SPACE);
//		content.append(StringUtils.trimToEmpty(department.getUniversity().getShortcut())+SPACE);

		return content.toString();
	}

	public DepartmentDao getDepartmentDao() {
		return departmentDao;
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

}
