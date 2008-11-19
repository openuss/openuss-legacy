package org.openuss.lecture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DepartmentServiceMock implements DepartmentService {

	public Long create(DepartmentInfo departmentInfo, Long ownerId) {
		return null;
	}

	public DepartmentInfo findDepartment(Long departmentId) {
		return this.getMockDepartments().get(departmentId);
	}


	public void removeCompleteDepartmentTree(Long departmentId) {
		
	}

	public List<Department> findDepartmentsByUniversityAndTypeAndEnabled(Long universityId, DepartmentType type, boolean enabled) {
		return null;
	}

	@SuppressWarnings( { "unchecked" })
	public List findDepartmentsByUniversity(Long universityId) {
		Map<Long, DepartmentInfo> allDepartments = this.getMockDepartments();
		List departmentInfos = new ArrayList(); 
		
		if(universityId.equals(100L)){
			departmentInfos.add(allDepartments.get(1101L));
			departmentInfos.add(allDepartments.get(1102L));
			departmentInfos.add(allDepartments.get(1103L));
		} else if(universityId.equals(101L)){
			departmentInfos.add(allDepartments.get(1201L));
			departmentInfos.add(allDepartments.get(1201L));
		} else if(universityId.equals(102L)){
			departmentInfos.add(allDepartments.get(1301L));
			departmentInfos.add(allDepartments.get(1302L));
		} else if(universityId.equals(103L)){
			departmentInfos.add(allDepartments.get(1401L));
			departmentInfos.add(allDepartments.get(1402L));
		} else if(universityId.equals(104L)){
			departmentInfos.add(allDepartments.get(1501L));
			departmentInfos.add(allDepartments.get(1502L));
		}
		
		return departmentInfos;
	}

	@SuppressWarnings( { "unchecked" })
	public List findDepartmentsByUniversityAndEnabled(Long universityId,
			boolean enabled) {
		List departments = findDepartmentsByUniversity(universityId);
		List result = new ArrayList(); 
		DepartmentInfo tempDepartment;
		
		Iterator iterator = departments.iterator();
		while(iterator.hasNext()){
			tempDepartment = (DepartmentInfo) iterator.next();
			if(tempDepartment.isEnabled()){
				result.add(tempDepartment);
			}
		}
		
		return result;
	}

	public void removeDepartment(Long departmentId) {

	}

	public void update(DepartmentInfo departmentInfo) {

	}
	
	private Map<Long, DepartmentInfo> getMockDepartments(){
		DepartmentInfo departmentTemp;
		Map<Long, DepartmentInfo> departments = new HashMap<Long, DepartmentInfo>();
		
		// Uni 1
		departmentTemp = new DepartmentInfo();
		departmentTemp.setId(1101L);
		departmentTemp.setName("Fachbereich 3 (Jura)");
		departmentTemp.setShortcut("FB03");
		departmentTemp.setUniversityId(100L);
		departmentTemp.setDepartmentType(org.openuss.lecture.DepartmentTypeEnum.OFFICIAL);
		departmentTemp.setEnabled(true);
		departments.put(departmentTemp.getId(), departmentTemp);
		
		departmentTemp = new DepartmentInfo();
		departmentTemp.setId(1102L);
		departmentTemp.setName("Fachbereich 4 (WiWi)");
		departmentTemp.setShortcut("FB04");
		departmentTemp.setUniversityId(100L);
		departmentTemp.setDepartmentType(org.openuss.lecture.DepartmentTypeEnum.OFFICIAL);
		departmentTemp.setEnabled(true);
		departments.put(departmentTemp.getId(), departmentTemp);
		
		departmentTemp = new DepartmentInfo();
		departmentTemp.setId(1103L);
		departmentTemp.setName("Lerngruppen FB 4");
		departmentTemp.setShortcut("LG_FB04");
		departmentTemp.setUniversityId(100L);
		departmentTemp.setDepartmentType(org.openuss.lecture.DepartmentTypeEnum.NONOFFICIAL);
		departmentTemp.setEnabled(true);
		departments.put(departmentTemp.getId(), departmentTemp);
		
		// Uni 2
		departmentTemp = new DepartmentInfo();
		departmentTemp.setId(1201L);
		departmentTemp.setName("Design");
		departmentTemp.setShortcut("Des");
		departmentTemp.setUniversityId(101L);
		departmentTemp.setDepartmentType(org.openuss.lecture.DepartmentTypeEnum.OFFICIAL);
		departments.put(departmentTemp.getId(), departmentTemp);
		
		departmentTemp = new DepartmentInfo();
		departmentTemp.setId(1202L);
		departmentTemp.setName("Pflege");
		departmentTemp.setShortcut("Pfl");
		departmentTemp.setUniversityId(101L);
		departmentTemp.setDepartmentType(org.openuss.lecture.DepartmentTypeEnum.OFFICIAL);
		departments.put(departmentTemp.getId(), departmentTemp);
		
		// Uni 3
		departmentTemp = new DepartmentInfo();
		departmentTemp.setId(1301L);
		departmentTemp.setName("Business");
		departmentTemp.setShortcut("Bus");
		departmentTemp.setUniversityId(102L);
		departmentTemp.setDepartmentType(org.openuss.lecture.DepartmentTypeEnum.OFFICIAL);
		departments.put(departmentTemp.getId(), departmentTemp);
		
		departmentTemp = new DepartmentInfo();
		departmentTemp.setId(1302L);
		departmentTemp.setName("Law");
		departmentTemp.setShortcut("L");
		departmentTemp.setUniversityId(102L);
		departmentTemp.setDepartmentType(org.openuss.lecture.DepartmentTypeEnum.OFFICIAL);
		departments.put(departmentTemp.getId(), departmentTemp);
		
		// Uni 4
		departmentTemp = new DepartmentInfo();
		departmentTemp.setId(1401L);
		departmentTemp.setName("Fremdsprachen");
		departmentTemp.setShortcut("FS");
		departmentTemp.setUniversityId(103L);
		departmentTemp.setDepartmentType(org.openuss.lecture.DepartmentTypeEnum.OFFICIAL);
		departments.put(departmentTemp.getId(), departmentTemp);
		
		departmentTemp = new DepartmentInfo();
		departmentTemp.setId(1402L);
		departmentTemp.setName("Pesonalführung");
		departmentTemp.setShortcut("PF");
		departmentTemp.setUniversityId(103L);
		departmentTemp.setDepartmentType(org.openuss.lecture.DepartmentTypeEnum.OFFICIAL);
		departments.put(departmentTemp.getId(), departmentTemp);
		
		// Uni 5
		departmentTemp = new DepartmentInfo();
		departmentTemp.setId(1501L);
		departmentTemp.setName("Fremdsprachen");
		departmentTemp.setShortcut("FS");
		departmentTemp.setUniversityId(104L);
		departmentTemp.setDepartmentType(org.openuss.lecture.DepartmentTypeEnum.OFFICIAL);
		departments.put(departmentTemp.getId(), departmentTemp);
		
		departmentTemp = new DepartmentInfo();
		departmentTemp.setId(1502L);
		departmentTemp.setName("Management");
		departmentTemp.setShortcut("Mgm");
		departmentTemp.setUniversityId(104L);
		departmentTemp.setDepartmentType(org.openuss.lecture.DepartmentTypeEnum.OFFICIAL);
		departments.put(departmentTemp.getId(), departmentTemp);
		
		return departments;
	}

	public void acceptApplication(Long applicationId, Long userId) {
		
	}

	public void rejectApplication(Long applicationId) {
		
	}

	public void signoffInstitute(Long instituteId) {
	}

	public ApplicationInfo findApplication(Long applicationId) {
		return null;
	}
	
	public List<Department> findDepartmentsByType(DepartmentType type) {
		return null;
	}

	public List<Department> findDepartmentsByUniversityAndType(Long universityId, DepartmentType departmentType) {
		return null;
	}

	public boolean isNoneExistingOrganisationShortcutByDepartment(DepartmentInfo self, String shortcut) {
		return false;
	}
	
	public void setDepartmentStatus(Long departmentId, boolean status) {
	}
	
	public List<Department> findApplicationsByDepartment(Long departmentId) {
		return null;
	}

	public List<Department> findApplicationsByDepartmentAndConfirmed(Long departmentId, boolean confirmed) {
		return null;
	}
	
	public List<Department> findOpenApplicationsByDepartment(Long departmentId) {
		return null;
	}
	
	
}
