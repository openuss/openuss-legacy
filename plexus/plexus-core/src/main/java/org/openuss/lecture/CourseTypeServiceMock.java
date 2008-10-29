package org.openuss.lecture;

import java.util.ArrayList;
import java.util.List;

// FIXME Tests - Mockup objects must not exist in production code.
public class CourseTypeServiceMock implements CourseTypeService {

	public Long create(CourseTypeInfo courseTypeInfo) {
		return null;
	}

	public CourseTypeInfo findCourseType(Long courseTypeId) {
		return null;
	}

	public List<CourseTypeInfo> findCourseTypesByInstitute(Long instituteId) {
		List<CourseTypeInfo> courseTypes = new ArrayList<CourseTypeInfo>();
		
		if(instituteId.longValue() % 2 == 0){
			CourseTypeInfo courseType1 = new CourseTypeInfo();
			courseType1.setId(100000L+instituteId);
			courseType1.setName("Grundlagen");
			courseType1.setShortcut("Grdl");
			
			CourseTypeInfo courseType2 = new CourseTypeInfo();
			courseType2.setId(100000L+instituteId);
			courseType2.setName("Ausgewählte Kapitel");
			courseType2.setShortcut("AK");
			
			courseTypes.add(courseType1);
			courseTypes.add(courseType2);
		} else {
			CourseTypeInfo courseType1 = new CourseTypeInfo();
			courseType1.setId(100000L+instituteId);
			courseType1.setName("Einführung");
			courseType1.setShortcut("Ein");
			
			CourseTypeInfo courseType2 = new CourseTypeInfo();
			courseType2.setId(100000L+instituteId);
			courseType2.setName("Vertiefung");
			courseType2.setShortcut("Ver");
			
			courseTypes.add(courseType1);
			courseTypes.add(courseType2);
		}
			
		return courseTypes;
	}

	public void removeCourseType(Long courseTypeId) {
	}

	public void update(CourseTypeInfo courseTypeInfo) {
	}
	
	public boolean isNoneExistingCourseTypeShortcut (CourseTypeInfo self, String shortcut) {
		return false;
	}

	public boolean isNoneExistingCourseTypeName (CourseTypeInfo self, String name) {
		return false;
	}
	
}
