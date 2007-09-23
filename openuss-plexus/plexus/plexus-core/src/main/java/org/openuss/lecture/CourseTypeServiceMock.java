package org.openuss.lecture;

import java.util.ArrayList;
import java.util.List;

public class CourseTypeServiceMock implements CourseTypeService {

	public Long create(CourseTypeInfo courseTypeInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	public CourseTypeInfo findCourseType(Long courseTypeId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findCourseTypesByInstitute(Long instituteId) {
		List courseTypes = new ArrayList();
		
		if(instituteId.longValue() % 2 == 0){
			CourseTypeInfo courseType1 = new CourseTypeInfo();
			courseType1.setId(new Long(100000+instituteId.longValue()));
			courseType1.setName("Grundlagen");
			courseType1.setShortcut("Grdl");
			
			CourseTypeInfo courseType2 = new CourseTypeInfo();
			courseType2.setId(new Long(100000+instituteId.longValue()));
			courseType2.setName("Ausgewählte Kapitel");
			courseType2.setShortcut("AK");
			
			courseTypes.add(courseType1);
			courseTypes.add(courseType2);
		} else {
			CourseTypeInfo courseType1 = new CourseTypeInfo();
			courseType1.setId(new Long(100000+instituteId.longValue()));
			courseType1.setName("Einführung");
			courseType1.setShortcut("Ein");
			
			CourseTypeInfo courseType2 = new CourseTypeInfo();
			courseType2.setId(new Long(100000+instituteId.longValue()));
			courseType2.setName("Vertiefung");
			courseType2.setShortcut("Ver");
			
			courseTypes.add(courseType1);
			courseTypes.add(courseType2);
		}
			
		return courseTypes;
	}

	public void removeCourseType(Long courseTypeId) {
		// TODO Auto-generated method stub

	}

	public void update(CourseTypeInfo courseTypeInfo) {
		// TODO Auto-generated method stub

	}
	
	public boolean isNoneExistingCourseTypeShortcut (CourseTypeInfo self, String shortcut) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isNoneExistingCourseTypeName (CourseTypeInfo self, String name) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void registerListener (LectureListener listener) {
		// TODO Auto-generated method stub
		
	}
}
