package org.openuss.lecture;

import org.apache.log4j.Logger;
import org.openuss.search.IndexerApplicationException;
import org.openuss.search.IndexerService;

/**
 * Aspect for CourseType Indexing 
 * @author Ingo Dueppe
 * @author Kai Stettner
 */
public class CourseTypeIndexingAspect {

	private static final Logger logger = Logger.getLogger(CourseTypeIndexingAspect.class);

	private IndexerService indexerService;
	
	/**
	 * Create index entry of a course type.
	 * @param courseTypeInfo
	 */
	public void createCourseTypeIndex(CourseTypeInfo courseTypeInfo) {
		logger.debug("Starting method createCourseTypeIndex");
		try {
			logger.debug("method createCourseTypeIndex: createIndex");
			indexerService.createIndex(courseTypeInfo);
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}
	
	/**
	 * Update course type index.
	 * @param courseTypeInfo
	 */
	public void updateCourseTypeIndex(CourseTypeInfo courseTypeInfo) {
		logger.debug("Starting method updateCourseTypeIndex");
		try {
			logger.debug("method updateCourseTypeIndex: updateIndex");
			indexerService.updateIndex(courseTypeInfo);
			/*for(Course course:institute.getCourses()) {
				if (course.getAccessType() != AccessType.CLOSED) {
					indexerService.updateIndex(course);
				}
				
			}*/
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}

	/**
	 * Delete course type from lecture index.
	 * @param courseTypeId
	 */
	public void deleteCourseTypeIndex(Long courseTypeId) {
		logger.debug("Starting method deleteCourseTypeIndex");
		try {
			logger.debug("method deleteCourseTypeIndex: deleteIndex");
			CourseType courseType = CourseType.Factory.newInstance();
			courseType.setId(courseTypeId);
			indexerService.deleteIndex(courseType);
		} catch (IndexerApplicationException e) {
			logger.error(e);
		}
	}
	
	public IndexerService getIndexerService() {
		return indexerService;
	}

	public void setIndexerService(IndexerService indexerService) {
		this.indexerService = indexerService;
	}
}
