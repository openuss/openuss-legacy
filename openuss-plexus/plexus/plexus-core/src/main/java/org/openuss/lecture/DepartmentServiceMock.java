package org.openuss.lecture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DepartmentServiceMock implements DepartmentService {

	public Long create(DepartmentInfo departmentInfo, Long ownerId) {
		// TODO Auto-generated method stub
		return null;
	}

	public DepartmentInfo findDepartment(Long departmentId) {
		return this.getMockDepartments().get(departmentId);
	}

	public List findDepartmentsByUniversity(Long universityId) {
		Map<Long, DepartmentInfo> allDepartments = this.getMockDepartments();
		List departmentInfos = new ArrayList(); 
		
		if(universityId.equals(new Long(100))){
			departmentInfos.add(allDepartments.get(new Long(1101)));
			departmentInfos.add(allDepartments.get(new Long(1102)));
			departmentInfos.add(allDepartments.get(new Long(1103)));
		} else if(universityId.equals(new Long(101))){
			departmentInfos.add(allDepartments.get(new Long(1201)));
			departmentInfos.add(allDepartments.get(new Long(1201)));
		} else if(universityId.equals(new Long(102))){
			departmentInfos.add(allDepartments.get(new Long(1301)));
			departmentInfos.add(allDepartments.get(new Long(1302)));
		} else if(universityId.equals(new Long(103))){
			departmentInfos.add(allDepartments.get(new Long(1401)));
			departmentInfos.add(allDepartments.get(new Long(1402)));
		} else if(universityId.equals(new Long(104))){
			departmentInfos.add(allDepartments.get(new Long(1501)));
			departmentInfos.add(allDepartments.get(new Long(1502)));
		}
		
		return departmentInfos;
	}

	public List findDepartmentsByUniversityAndEnabled(Long universityId,
			Boolean enabled) {
		List departments = findDepartmentsByUniversity(universityId);
		List result = new ArrayList(); 
		DepartmentInfo tempDepartment;
		
		Iterator iterator = departments.iterator();
		while(iterator.hasNext()){
			tempDepartment = (DepartmentInfo) iterator.next();
			if(tempDepartment.getEnabled()){
				result.add(tempDepartment);
			}
		}
		
		return result;
	}

	public void removeDepartment(Long departmentId) {
		// TODO Auto-generated method stub

	}

	public void update(DepartmentInfo departmentInfo) {
		// TODO Auto-generated method stub

	}
	
	private Map<Long, DepartmentInfo> getMockDepartments(){
		DepartmentInfo departmentTemp;
		Map<Long, DepartmentInfo> departments = new HashMap<Long, DepartmentInfo>();
		
		// Uni 1
		departmentTemp = new DepartmentInfo();
		departmentTemp.setId((new Long(1101)));
		departmentTemp.setName("Fachbereich 3 (Jura)");
		departmentTemp.setShortcut("FB03");
		departmentTemp.setUniversityId(new Long(100));
		departmentTemp.setDepartmentType(org.openuss.lecture.DepartmentTypeEnum.OFFICIAL);
		departmentTemp.setEnabled(true);
		departments.put(departmentTemp.getId(), departmentTemp);
		
		departmentTemp = new DepartmentInfo();
		departmentTemp.setId((new Long(1102)));
		departmentTemp.setName("Fachbereich 4 (WiWi)");
		departmentTemp.setShortcut("FB04");
		departmentTemp.setUniversityId(new Long(100));
		departmentTemp.setDepartmentType(org.openuss.lecture.DepartmentTypeEnum.OFFICIAL);
		departmentTemp.setEnabled(true);
		departments.put(departmentTemp.getId(), departmentTemp);
		
		departmentTemp = new DepartmentInfo();
		departmentTemp.setId((new Long(1103)));
		departmentTemp.setName("Lerngruppen FB 4");
		departmentTemp.setShortcut("LG_FB04");
		departmentTemp.setUniversityId(new Long(100));
		departmentTemp.setDepartmentType(org.openuss.lecture.DepartmentTypeEnum.NONOFFICIAL);
		departmentTemp.setEnabled(true);
		departments.put(departmentTemp.getId(), departmentTemp);
		
		// Uni 2
		departmentTemp = new DepartmentInfo();
		departmentTemp.setId((new Long(1201)));
		departmentTemp.setName("Design");
		departmentTemp.setShortcut("Des");
		departmentTemp.setUniversityId(new Long(101));
		departmentTemp.setDepartmentType(org.openuss.lecture.DepartmentTypeEnum.OFFICIAL);
		departments.put(departmentTemp.getId(), departmentTemp);
		
		departmentTemp = new DepartmentInfo();
		departmentTemp.setId((new Long(1202)));
		departmentTemp.setName("Pflege");
		departmentTemp.setShortcut("Pfl");
		departmentTemp.setUniversityId(new Long(101));
		departmentTemp.setDepartmentType(org.openuss.lecture.DepartmentTypeEnum.OFFICIAL);
		departments.put(departmentTemp.getId(), departmentTemp);
		
		// Uni 3
		departmentTemp = new DepartmentInfo();
		departmentTemp.setId((new Long(1301)));
		departmentTemp.setName("Business");
		departmentTemp.setShortcut("Bus");
		departmentTemp.setUniversityId(new Long(102));
		departmentTemp.setDepartmentType(org.openuss.lecture.DepartmentTypeEnum.OFFICIAL);
		departments.put(departmentTemp.getId(), departmentTemp);
		
		departmentTemp = new DepartmentInfo();
		departmentTemp.setId((new Long(1302)));
		departmentTemp.setName("Law");
		departmentTemp.setShortcut("L");
		departmentTemp.setUniversityId(new Long(102));
		departmentTemp.setDepartmentType(org.openuss.lecture.DepartmentTypeEnum.OFFICIAL);
		departments.put(departmentTemp.getId(), departmentTemp);
		
		// Uni 4
		departmentTemp = new DepartmentInfo();
		departmentTemp.setId((new Long(1401)));
		departmentTemp.setName("Fremdsprachen");
		departmentTemp.setShortcut("FS");
		departmentTemp.setUniversityId(new Long(103));
		departmentTemp.setDepartmentType(org.openuss.lecture.DepartmentTypeEnum.OFFICIAL);
		departments.put(departmentTemp.getId(), departmentTemp);
		
		departmentTemp = new DepartmentInfo();
		departmentTemp.setId((new Long(1402)));
		departmentTemp.setName("Pesonalführung");
		departmentTemp.setShortcut("PF");
		departmentTemp.setUniversityId(new Long(103));
		departmentTemp.setDepartmentType(org.openuss.lecture.DepartmentTypeEnum.OFFICIAL);
		departments.put(departmentTemp.getId(), departmentTemp);
		
		// Uni 5
		departmentTemp = new DepartmentInfo();
		departmentTemp.setId((new Long(1501)));
		departmentTemp.setName("Fremdsprachen");
		departmentTemp.setShortcut("FS");
		departmentTemp.setUniversityId(new Long(104));
		departmentTemp.setDepartmentType(org.openuss.lecture.DepartmentTypeEnum.OFFICIAL);
		departments.put(departmentTemp.getId(), departmentTemp);
		
		departmentTemp = new DepartmentInfo();
		departmentTemp.setId((new Long(1502)));
		departmentTemp.setName("Management");
		departmentTemp.setShortcut("Mgm");
		departmentTemp.setUniversityId(new Long(104));
		departmentTemp.setDepartmentType(org.openuss.lecture.DepartmentTypeEnum.OFFICIAL);
		departments.put(departmentTemp.getId(), departmentTemp);
		
		return departments;
	}

	public void acceptApplication(Long applicationId, Long userId) {
		// TODO Auto-generated method stub
		
	}

	public void rejectApplication(Long applicationId) {
		// TODO Auto-generated method stub
		
	}

	public void signoffInstitute(Long instituteId) {
		// TODO Auto-generated method stub
		
	}

	public ApplicationInfo findApplication(Long applicationId) {
		// TODO Auto-generated method stub
		return null;
	}

}
