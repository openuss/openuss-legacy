package org.openuss.web.search;

import java.util.ArrayList;
import java.util.List;

import org.openuss.lecture.CourseType;
import org.openuss.lecture.CourseTypeImpl;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.PeriodInfo;
import org.openuss.lecture.UniversityInfo;

public class ExtendedSearchStub {
	
	public List findAllUniversities() throws Exception{
		
		UniversityInfo university1 = new UniversityInfo();
		university1.setId(new Long(100));
		university1.setName("Universität Münster");
		university1.setShortcut("WWU");
		university1.setUniversityType(org.openuss.lecture.UniversityTypeEnum.UNIVERSITY);
		
		UniversityInfo university2 = new UniversityInfo();
		university2.setId(new Long(101));
		university2.setName("FH Münster");
		university2.setShortcut("FH MS");
		university2.setUniversityType(org.openuss.lecture.UniversityTypeEnum.UNIVERSITY);
		
		UniversityInfo university3 = new UniversityInfo();
		university3.setId(new Long(102));
		university3.setName("Ralph Lauren School of Business and Law");
		university3.setShortcut("RLSBL");
		university3.setUniversityType(org.openuss.lecture.UniversityTypeEnum.UNIVERSITY);
		
		UniversityInfo university4 = new UniversityInfo();
		university4.setId(new Long(103));
		university4.setName("BMW AG");
		university4.setShortcut("BMW");
		university4.setUniversityType(org.openuss.lecture.UniversityTypeEnum.COMPANY);
		
		List universityInfos = new ArrayList();
		universityInfos.add(university1);
		universityInfos.add(university2);
		universityInfos.add(university3);
		universityInfos.add(university4);
		return universityInfos;
	}
	
	public List findDepartmentsByUniversity(Long universityId) throws Exception {
		List departmentInfos = new ArrayList();
		
		if(universityId.equals(new Long(100))){
			DepartmentInfo department1 = new DepartmentInfo();
			department1.setId((new Long(1000+universityId.longValue()+1)));
			department1.setName("Fachbereich 3 (Jura)");
			department1.setShortcut("FB03");
			department1.setUniversityId(new Long(100));
			department1.setDepartmentType(org.openuss.lecture.DepartmentTypeEnum.OFFICIAL);
			
			DepartmentInfo department2 = new DepartmentInfo();
			department2.setId((new Long(1000+universityId.longValue()+2)));
			department2.setName("Fachbereich 4 (WiWi)");
			department2.setShortcut("FB04");
			department2.setUniversityId(new Long(100));
			department2.setDepartmentType(org.openuss.lecture.DepartmentTypeEnum.OFFICIAL);
			
			DepartmentInfo department3 = new DepartmentInfo();
			department3.setId((new Long(1000+universityId.longValue()+3)));
			department3.setName("Lerngruppen FB 4");
			department3.setShortcut("LG_FB04");
			department3.setUniversityId(new Long(100));
			department3.setDepartmentType(org.openuss.lecture.DepartmentTypeEnum.NONOFFICIAL);
			
			departmentInfos.add(department1);
			departmentInfos.add(department2);
			departmentInfos.add(department3);
		} else if(universityId.equals(new Long(101))){
			DepartmentInfo department1 = new DepartmentInfo();
			department1.setId((new Long(1000+universityId.longValue()+1)));
			department1.setName("Design");
			department1.setShortcut("Des");
			department1.setUniversityId(new Long(101));
			department1.setDepartmentType(org.openuss.lecture.DepartmentTypeEnum.OFFICIAL);
			
			DepartmentInfo department2 = new DepartmentInfo();
			department2.setId((new Long(1000+universityId.longValue()+2)));
			department2.setName("Pflege");
			department2.setShortcut("Pfl");
			department2.setUniversityId(new Long(101));
			department2.setDepartmentType(org.openuss.lecture.DepartmentTypeEnum.OFFICIAL);
			
			departmentInfos.add(department1);
			departmentInfos.add(department2);
		} else if(universityId.equals(new Long(102))){
			DepartmentInfo department1 = new DepartmentInfo();
			department1.setId((new Long(1000+universityId.longValue()+1)));
			department1.setName("Business");
			department1.setShortcut("Bus");
			department1.setUniversityId(new Long(102));
			department1.setDepartmentType(org.openuss.lecture.DepartmentTypeEnum.OFFICIAL);
			
			DepartmentInfo department2 = new DepartmentInfo();
			department2.setId((new Long(1000+universityId.longValue()+2)));
			department2.setName("Law");
			department2.setShortcut("L");
			department2.setUniversityId(new Long(102));
			department2.setDepartmentType(org.openuss.lecture.DepartmentTypeEnum.OFFICIAL);
			
			departmentInfos.add(department1);
			departmentInfos.add(department2);
		} else if(universityId.equals(new Long(103))){
			DepartmentInfo department1 = new DepartmentInfo();
			department1.setId((new Long(1000+universityId.longValue()+1)));
			department1.setName("Fremdsprachen");
			department1.setShortcut("FS");
			department1.setUniversityId(new Long(103));
			department1.setDepartmentType(org.openuss.lecture.DepartmentTypeEnum.OFFICIAL);
			
			DepartmentInfo department2 = new DepartmentInfo();
			department2.setId((new Long(1000+universityId.longValue()+2)));
			department2.setName("Pesonalführung");
			department2.setShortcut("PF");
			department2.setUniversityId(new Long(103));
			department2.setDepartmentType(org.openuss.lecture.DepartmentTypeEnum.OFFICIAL);
			
			departmentInfos.add(department1);
			departmentInfos.add(department2);
		}
		
		return departmentInfos;
	}
	
	public List findInstitutesByDepartment(Long departmentId) throws Exception {
		List instituteDetails = new ArrayList();
		
		if( departmentId.longValue() % 2 == 0){
			InstituteInfo institute1 = new InstituteInfo();
			institute1.setId(new Long(10000+departmentId.longValue()));
			institute1.setName("Lehrstuhl 1");
			institute1.setShortcut("LS1");
			
			InstituteInfo institute2 = new InstituteInfo();
			institute2.setId(new Long(10000+departmentId.longValue()));
			institute2.setName("Lehrstuhl 2");
			institute2.setShortcut("LS2");
			
			instituteDetails.add(institute1);
			instituteDetails.add(institute2);
		} else {
			InstituteInfo institute1 = new InstituteInfo();
			institute1.setId(new Long(10000+departmentId.longValue()));
			institute1.setName("Lehrstuhl A");
			institute1.setShortcut("LSA");
			
			InstituteInfo institute2 = new InstituteInfo();
			institute2.setId(new Long(10000+departmentId.longValue()));
			institute2.setName("Lehrstuhl B");
			institute2.setShortcut("LSB");
			
			instituteDetails.add(institute1);
			instituteDetails.add(institute2);
		}
			
		return instituteDetails;
	}
	
	public List findCourseTypeByInstitute(Long instituteId) throws Exception {
		List courseTypes = new ArrayList();
		
		if( instituteId.longValue() % 2 == 0){
			CourseType courseType1 = new CourseTypeImpl();
			courseType1.setId(new Long(100000+instituteId.longValue()));
			courseType1.setName("Grundlagen");
			courseType1.setShortcut("Grdl");
			
			CourseType courseType2 = new CourseTypeImpl();
			courseType2.setId(new Long(100000+instituteId.longValue()));
			courseType2.setName("Ausgewählte Kapitel");
			courseType2.setShortcut("AK");
			
			courseTypes.add(courseType1);
			courseTypes.add(courseType2);
		} else {
			CourseType courseType1 = new CourseTypeImpl();
			courseType1.setId(new Long(100000+instituteId.longValue()));
			courseType1.setName("Einführung");
			courseType1.setShortcut("Ein");
			
			CourseType courseType2 = new CourseTypeImpl();
			courseType2.setId(new Long(100000+instituteId.longValue()));
			courseType2.setName("Vertiefung");
			courseType2.setShortcut("VEr");
			
			courseTypes.add(courseType1);
			courseTypes.add(courseType2);
		}
			
		return courseTypes;
	}
	
	public List findPeriodsByUniversity(Long universityId) throws Exception {
		List periodInfos = new ArrayList(); 
		
		if(universityId.equals(new Long(100))){
			PeriodInfo period1 = new PeriodInfo();
			period1.setId(new Long(200000));
			period1.setName("WS 2007/2008");
			period1.setUniversityId(universityId);
			
			PeriodInfo period2 = new PeriodInfo();
			period2.setId(new Long(200001));
			period2.setName("SS 2008");
			period2.setUniversityId(universityId);
			
			periodInfos.add(period1);
			periodInfos.add(period2);
		} else if(universityId.equals(new Long(101))){
			PeriodInfo period1 = new PeriodInfo();
			period1.setId(new Long(200002));
			period1.setName("WS 2007/2008");
			period1.setUniversityId(universityId);
			
			PeriodInfo period2 = new PeriodInfo();
			period2.setId(new Long(200003));
			period2.setName("SS 2008");
			period2.setUniversityId(universityId);
			
			periodInfos.add(period1);
			periodInfos.add(period2);
		} else if(universityId.equals(new Long(102))){
			PeriodInfo period1 = new PeriodInfo();
			period1.setId(new Long(200004));
			period1.setName("Academic Year 2007/2008 - Trimester 1");
			period1.setUniversityId(universityId);
			
			PeriodInfo period2 = new PeriodInfo();
			period2.setId(new Long(200005));
			period2.setName("Academic Year 2007/2008 - Trimester 2");
			period2.setUniversityId(universityId);
			
			PeriodInfo period3 = new PeriodInfo();
			period3.setId(new Long(200006));
			period3.setName("Academic Year 2007/2008 - Trimester 3");
			period3.setUniversityId(universityId);
			
			periodInfos.add(period1);
			periodInfos.add(period2);
			periodInfos.add(period3);
		} else {
			PeriodInfo period1 = new PeriodInfo();
			period1.setId(new Long(200007));
			period1.setName("2008 (1. Halbjahr)");
			period1.setUniversityId(universityId);
			
			PeriodInfo period2 = new PeriodInfo();
			period2.setId(new Long(200008));
			period2.setName("2008 (2. Halbjahr)");
			period2.setUniversityId(universityId);
			
			periodInfos.add(period1);
			periodInfos.add(period2);
		}
		
		return periodInfos;
	}
	
	
	
	
	
	
}
