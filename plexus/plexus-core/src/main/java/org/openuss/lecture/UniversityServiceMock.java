package org.openuss.lecture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UniversityServiceMock implements UniversityService {

	public Long createUniversity(UniversityInfo universityInfo, Long ownerId) {
		return null;
	}

	public Long createPeriod(PeriodInfo periodInfo) {
		return null;
	}

	public List<Period> findPeriodsByUniversityAndActivation(Long universityId,  boolean active) {
		return null;
	}

	@SuppressWarnings( { "unchecked" })
	public List findAllUniversities() {
		Map<Long, UniversityInfo> universities = this.getMockUniversities();
		return new ArrayList<UniversityInfo>(universities.values());
	}

	@SuppressWarnings( { "unchecked" })
	public java.util.List findUniversitiesByUser(Long userId) {
		return null;
	}
	
	public PeriodInfo findPeriod(Long periodId) {
		return this.getMockPeriods().get(periodId);
	}

	@SuppressWarnings( { "unchecked" })
	public List findPeriodsByUniversity(Long universityId) {
		Map<Long, PeriodInfo> allPeriods = this.getMockPeriods();
		List periodInfos = new ArrayList(); 
		
		if(universityId.equals(100L)){
			periodInfos.add(allPeriods.get(200000L));
			periodInfos.add(allPeriods.get(200001L));
		} else if(universityId.equals(101L)){
			periodInfos.add(allPeriods.get(200002L));
			periodInfos.add(allPeriods.get(200003L));
		} else if(universityId.equals(102L)){
			periodInfos.add(allPeriods.get(200004L));
			periodInfos.add(allPeriods.get(200005L));
			periodInfos.add(allPeriods.get(200006L));
		} else if(universityId.equals(103L)){
			periodInfos.add(allPeriods.get(200007L));
			periodInfos.add(allPeriods.get(200008L));
		} else if(universityId.equals(104L)){
			periodInfos.add(allPeriods.get(200009L));
			periodInfos.add(allPeriods.get(2000010L));
		}
		
		return periodInfos;
	}

	@SuppressWarnings( { "unchecked" })
	public List findUniversitiesByEnabled(boolean enabled) {
		Map<Long, UniversityInfo> universities = this.getMockUniversities();
		List result = new ArrayList();
		for (UniversityInfo university : universities.values()) {
			if (university.isEnabled() == enabled) {
				result.add(university);
			}
		}
		return result;
	}

	public UniversityInfo findUniversity(Long universityId) {
		return this.getMockUniversities().get(universityId);
	}
	
	@SuppressWarnings( { "unchecked" })
	public List findUniversitiesByType (UniversityType type) {
		return null;
	}

	public void removePeriod(Long periodId) {

	}

	public void removeUniversity(Long universityId) {

	}

	public void update(UniversityInfo universityInfo) {

	}

	public void update(PeriodInfo periodInfo) {

	}
	
	private Map<Long, UniversityInfo> getMockUniversities(){
		UniversityInfo universityTemp;
		Map<Long, UniversityInfo> universities = new HashMap<Long, UniversityInfo>();
		
		universityTemp = new UniversityInfo();
		universityTemp.setId(100L);
		universityTemp.setName("Universität Münster");
		universityTemp.setShortcut("WWU");
		universityTemp.setUniversityType(org.openuss.lecture.UniversityTypeEnum.UNIVERSITY);
		universityTemp.setEnabled(true);
		universities.put(universityTemp.getId(), universityTemp);
		
		universityTemp = new UniversityInfo();
		universityTemp.setId(101L);
		universityTemp.setName("FH Münster");
		universityTemp.setShortcut("FH MS");
		universityTemp.setUniversityType(org.openuss.lecture.UniversityTypeEnum.UNIVERSITY);
		universityTemp.setEnabled(true);
		universities.put(universityTemp.getId(), universityTemp);
		
		universityTemp = new UniversityInfo();
		universityTemp.setId(102L);
		universityTemp.setName("Ralph Lauren School of Business and Law");
		universityTemp.setShortcut("RLSBL");
		universityTemp.setUniversityType(org.openuss.lecture.UniversityTypeEnum.UNIVERSITY);
		universityTemp.setEnabled(true);
		universities.put(universityTemp.getId(), universityTemp);
		
		universityTemp = new UniversityInfo();
		universityTemp.setId(103L);
		universityTemp.setName("BMW AG");
		universityTemp.setShortcut("BMW");
		universityTemp.setUniversityType(org.openuss.lecture.UniversityTypeEnum.COMPANY);
		universityTemp.setEnabled(true);
		universities.put(universityTemp.getId(), universityTemp);
		
		universityTemp = new UniversityInfo();
		universityTemp.setId(104L);
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
		periodTemp.setUniversityId(100L);
		periods.put(periodTemp.getId(), periodTemp);
		
		periodTemp = new PeriodInfo();
		periodTemp.setId(200001L);
		periodTemp.setName("SS 2008");
		periodTemp.setUniversityId(100L);
		periods.put(periodTemp.getId(), periodTemp);
		
		periodTemp = new PeriodInfo();
		periodTemp.setId(200002L);
		periodTemp.setName("WS 2007/2008");
		periodTemp.setUniversityId(101L);
		periods.put(periodTemp.getId(), periodTemp);
		
		periodTemp = new PeriodInfo();
		periodTemp.setId(200003L);
		periodTemp.setName("SS 2008");
		periodTemp.setUniversityId(101L);
		periods.put(periodTemp.getId(), periodTemp);
		
		periodTemp = new PeriodInfo();
		periodTemp.setId(200004L);
		periodTemp.setName("Academic Year 2007/2008 - Trimester 1");
		periodTemp.setUniversityId(102L);
		periods.put(periodTemp.getId(), periodTemp);
		
		periodTemp = new PeriodInfo();
		periodTemp.setId(200005L);
		periodTemp.setName("Academic Year 2007/2008 - Trimester 2");
		periodTemp.setUniversityId(102L);
		periods.put(periodTemp.getId(), periodTemp);
		
		periodTemp = new PeriodInfo();
		periodTemp.setId(200006L);
		periodTemp.setName("Academic Year 2007/2008 - Trimester 3");
		periodTemp.setUniversityId(102L);
		periods.put(periodTemp.getId(), periodTemp);
		
		periodTemp = new PeriodInfo();
		periodTemp.setId(200007L);
		periodTemp.setName("2008 (1. Halbjahr)");
		periodTemp.setUniversityId(103L);
		periods.put(periodTemp.getId(), periodTemp);
		
		periodTemp = new PeriodInfo();
		periodTemp.setId(200008L);
		periodTemp.setName("2008 (2. Halbjahr)");
		periodTemp.setUniversityId(103L);
		periods.put(periodTemp.getId(), periodTemp);
		
		periodTemp = new PeriodInfo();
		periodTemp.setId(200009L);
		periodTemp.setName("2008 (1. Halbjahr)");
		periodTemp.setUniversityId(104L);
		periods.put(periodTemp.getId(), periodTemp);
		
		periodTemp = new PeriodInfo();
		periodTemp.setId(200010L);
		periodTemp.setName("2008 (2. Halbjahr)");
		periodTemp.setUniversityId(104L);
		periods.put(periodTemp.getId(), periodTemp);
		
		return periods;
	}

	public List<Period> findUniversitiesByMemberAndEnabled(Long userId, boolean enabled) {
		return null;
	}

	public List<Period> findUniversitiesByTypeAndEnabled(UniversityType universityType, boolean enabled) {
		return null;
	}
	
	public List<Period> findPeriodsByInstituteWithCoursesOrActive(InstituteInfo instituteInfo) {
		return null;
	}
	
	public void removeCompleteUniversityTree(Long universityId) {
	}

	public boolean isNoneExistingOrganisationShortcutByUniversity(UniversityInfo self, String shortcut) {
		return false;
	}
	
	public void setUniversityStatus(Long universityId, boolean status) {
	}
	
	public List<Period> findPeriodsByUniversityWithCourses(Long universityId) {
		return null;
	}
	
	public boolean isActivePeriod(Long periodId) {
		return false;
	}
	
	public void removePeriodAndCourses (Long periodId) {
	}
}
