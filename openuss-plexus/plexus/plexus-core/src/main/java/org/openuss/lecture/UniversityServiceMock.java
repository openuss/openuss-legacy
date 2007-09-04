package org.openuss.lecture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;

public class UniversityServiceMock implements UniversityService {

	public Long createUniversity(UniversityInfo universityInfo, Long ownerId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Long createPeriod(PeriodInfo periodInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findActivePeriodsByUniversity(Long universityId) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings( { "unchecked" })
	public List findAllUniversities() {
		Map<Long, UniversityInfo> universities = this.getMockUniversities();
		UniversityInfo tempUniversity;
		List result = new ArrayList();
		
		Iterator<Long> iterator = universities.keySet().iterator();
		while(iterator.hasNext()){
			Long key = iterator.next();
			tempUniversity = universities.get(key);
			result.add(tempUniversity);
		}

		return result;
	}

	@SuppressWarnings( { "unchecked" })
	public java.util.List findUniversitiesByUser(Long userId) {
		//TODO: Implement me!
		return null;
	}
	
	public PeriodInfo findPeriod(Long periodId) {
		return this.getMockPeriods().get(periodId);
	}

	@SuppressWarnings( { "unchecked" })
	public List findPeriodsByUniversityAndActivation(Long universityId, boolean active) {
		Map<Long, PeriodInfo> allPeriods = this.getMockPeriods();
		List periodInfos = new ArrayList(); 
		
		if(universityId.equals(new Long(100))){
			periodInfos.add(allPeriods.get(new Long(200000)));
			periodInfos.add(allPeriods.get(new Long(200001)));
		} else if(universityId.equals(new Long(101))){
			periodInfos.add(allPeriods.get(new Long(200002)));
			periodInfos.add(allPeriods.get(new Long(200003)));
		} else if(universityId.equals(new Long(102))){
			periodInfos.add(allPeriods.get(new Long(200004)));
			periodInfos.add(allPeriods.get(new Long(200005)));
			periodInfos.add(allPeriods.get(new Long(200006)));
		} else if(universityId.equals(new Long(103))){
			periodInfos.add(allPeriods.get(new Long(200007)));
			periodInfos.add(allPeriods.get(new Long(200008)));
		} else if(universityId.equals(new Long(104))){
			periodInfos.add(allPeriods.get(new Long(200009)));
			periodInfos.add(allPeriods.get(new Long(2000010)));
		}
		
		return periodInfos;
	}

	@SuppressWarnings( { "unchecked" })
	public List findUniversitiesByEnabled(boolean enabled) {
		Map<Long, UniversityInfo> universities = this.getMockUniversities();
		UniversityInfo tempUniversity;
		List result = new ArrayList();
		
		Iterator<Long> iterator = universities.keySet().iterator();
		while(iterator.hasNext()){
			Long key = iterator.next();
			tempUniversity = universities.get(key);
			if(tempUniversity.isEnabled() == enabled){
				result.add(tempUniversity);
			}
		}

		return result;
	}

	public UniversityInfo findUniversity(Long universityId) {
		return this.getMockUniversities().get(universityId);
	}
	
	@SuppressWarnings( { "unchecked" })
	public List findUniversitiesByType (UniversityType type) {
		// TODO Auto-generated method stub
		return null;
	}

	public void removePeriod(Long periodId) {
		// TODO Auto-generated method stub

	}

	public void removePeriodAndCourses(Long periodId) {
		// TODO Auto-generated method stub
		
	}

	public void removeUniversity(Long universityId) {
		// TODO Auto-generated method stub

	}

	public void update(UniversityInfo universityInfo) {
		// TODO Auto-generated method stub

	}

	public void update(PeriodInfo periodInfo) {
		// TODO Auto-generated method stub

	}
	
	private Map<Long, UniversityInfo> getMockUniversities(){
		UniversityInfo universityTemp;
		Map<Long, UniversityInfo> universities = new HashMap<Long, UniversityInfo>();
		
		universityTemp = new UniversityInfo();
		universityTemp.setId(new Long(100));
		universityTemp.setName("Universität Münster");
		universityTemp.setShortcut("WWU");
		universityTemp.setUniversityType(org.openuss.lecture.UniversityTypeEnum.UNIVERSITY);
		universityTemp.setEnabled(true);
		universities.put(universityTemp.getId(), universityTemp);
		
		universityTemp = new UniversityInfo();
		universityTemp.setId(new Long(101));
		universityTemp.setName("FH Münster");
		universityTemp.setShortcut("FH MS");
		universityTemp.setUniversityType(org.openuss.lecture.UniversityTypeEnum.UNIVERSITY);
		universityTemp.setEnabled(true);
		universities.put(universityTemp.getId(), universityTemp);
		
		universityTemp = new UniversityInfo();
		universityTemp.setId(new Long(102));
		universityTemp.setName("Ralph Lauren School of Business and Law");
		universityTemp.setShortcut("RLSBL");
		universityTemp.setUniversityType(org.openuss.lecture.UniversityTypeEnum.UNIVERSITY);
		universityTemp.setEnabled(true);
		universities.put(universityTemp.getId(), universityTemp);
		
		universityTemp = new UniversityInfo();
		universityTemp.setId(new Long(103));
		universityTemp.setName("BMW AG");
		universityTemp.setShortcut("BMW");
		universityTemp.setUniversityType(org.openuss.lecture.UniversityTypeEnum.COMPANY);
		universityTemp.setEnabled(true);
		universities.put(universityTemp.getId(), universityTemp);
		
		universityTemp = new UniversityInfo();
		universityTemp.setId(new Long(104));
		universityTemp.setName("Porsche AG");
		universityTemp.setShortcut("Por");
		universityTemp.setUniversityType(org.openuss.lecture.UniversityTypeEnum.COMPANY);
		universityTemp.setEnabled(false);
		universities.put(universityTemp.getId(), universityTemp);
		
		return universities;
	}
	
	private Map<Long, PeriodInfo> getMockPeriods(){
		
		PeriodInfo periodTemp;
		Map<Long, PeriodInfo> periods = new HashMap<Long, PeriodInfo>();
		
		periodTemp = new PeriodInfo();
		periodTemp.setId(periodTemp.getId());
		periodTemp.setName("WS 2007/2008");
		periodTemp.setUniversityId(new Long(100));
		periods.put(periodTemp.getId(), periodTemp);
		
		periodTemp = new PeriodInfo();
		periodTemp.setId(new Long(200001));
		periodTemp.setName("SS 2008");
		periodTemp.setUniversityId(new Long(100));
		periods.put(periodTemp.getId(), periodTemp);
		
		periodTemp = new PeriodInfo();
		periodTemp.setId(new Long(200002));
		periodTemp.setName("WS 2007/2008");
		periodTemp.setUniversityId(new Long(101));
		periods.put(periodTemp.getId(), periodTemp);
		
		periodTemp = new PeriodInfo();
		periodTemp.setId(new Long(200003));
		periodTemp.setName("SS 2008");
		periodTemp.setUniversityId(new Long(101));
		periods.put(periodTemp.getId(), periodTemp);
		
		periodTemp = new PeriodInfo();
		periodTemp.setId(new Long(200004));
		periodTemp.setName("Academic Year 2007/2008 - Trimester 1");
		periodTemp.setUniversityId(new Long(102));
		periods.put(periodTemp.getId(), periodTemp);
		
		periodTemp = new PeriodInfo();
		periodTemp.setId(new Long(200005));
		periodTemp.setName("Academic Year 2007/2008 - Trimester 2");
		periodTemp.setUniversityId(new Long(102));
		periods.put(periodTemp.getId(), periodTemp);
		
		periodTemp = new PeriodInfo();
		periodTemp.setId(new Long(200006));
		periodTemp.setName("Academic Year 2007/2008 - Trimester 3");
		periodTemp.setUniversityId(new Long(102));
		periods.put(periodTemp.getId(), periodTemp);
		
		periodTemp = new PeriodInfo();
		periodTemp.setId(new Long(200007));
		periodTemp.setName("2008 (1. Halbjahr)");
		periodTemp.setUniversityId(new Long(103));
		periods.put(periodTemp.getId(), periodTemp);
		
		periodTemp = new PeriodInfo();
		periodTemp.setId(new Long(200008));
		periodTemp.setName("2008 (2. Halbjahr)");
		periodTemp.setUniversityId(new Long(103));
		periods.put(periodTemp.getId(), periodTemp);
		
		periodTemp = new PeriodInfo();
		periodTemp.setId(new Long(200009));
		periodTemp.setName("2008 (1. Halbjahr)");
		periodTemp.setUniversityId(new Long(104));
		periods.put(periodTemp.getId(), periodTemp);
		
		periodTemp = new PeriodInfo();
		periodTemp.setId(new Long(200010));
		periodTemp.setName("2008 (2. Halbjahr)");
		periodTemp.setUniversityId(new Long(104));
		periods.put(periodTemp.getId(), periodTemp);
		
		return periods;
	}

	public List findUniversitiesByMemberAndEnabled(Long userId, boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findUniversitiesByTypeAndEnabled(UniversityType universityType, boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List findPeriodsByInstituteWithCoursesOrActive(InstituteInfo instituteInfo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void removeCompleteUniversityTree(Long universityId) {
		// TODO Auto-generated method stub
	}

	public boolean isNoneExistingUniversityShortcut(UniversityInfo self, String shortcut) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void setUniversityStatus(Long universityId, boolean status) {
		// TODO Auto-generated method stub
	}
	
	public List findPeriodsByUniversityWithCourses(Long universityId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean isActivePeriod(Long periodId) {
		// TODO Auto-generated method stub
		return false;
	}
}
