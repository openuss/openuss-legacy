package org.openuss.lecture;

import java.util.ArrayList;
import java.util.List;

public class InstituteServiceMock implements InstituteService {

	public Long create(InstituteInfo instituteInfo, Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	public InstituteInfo findInstitute(Long instituteId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findInstitutesByDepartment(Long departmentId) {
		List institutes = new ArrayList();
		
		List validDepartments = new ArrayList();
		validDepartments.add(new Long(1101));
		validDepartments.add(new Long(1102));
		validDepartments.add(new Long(1103));
		validDepartments.add(new Long(1201));
		validDepartments.add(new Long(1202));
		validDepartments.add(new Long(1301));
		validDepartments.add(new Long(1302));
		validDepartments.add(new Long(1401));
		validDepartments.add(new Long(1402));
		validDepartments.add(new Long(1501));
		validDepartments.add(new Long(1502));
		
		if(!validDepartments.contains(departmentId)){
			return institutes;
		}
		
		if( departmentId.longValue() % 2 == 0){
			InstituteInfo institute1 = new InstituteInfo();
			institute1.setId(new Long(10000+departmentId.longValue()));
			institute1.setName("Lehrstuhl 1");
			institute1.setShortcut("LS1");
			institute1.setEnabled(true);
			
			InstituteInfo institute2 = new InstituteInfo();
			institute2.setId(new Long(10000+departmentId.longValue()));
			institute2.setName("Lehrstuhl 2");
			institute2.setShortcut("LS2");
			institute2.setEnabled(true);
			
			institutes.add(institute1);
			institutes.add(institute2);
		} else {
			InstituteInfo institute1 = new InstituteInfo();
			institute1.setId(new Long(10000+departmentId.longValue()));
			institute1.setName("Lehrstuhl A");
			institute1.setShortcut("LSA");
			institute1.setEnabled(true);
			
			InstituteInfo institute2 = new InstituteInfo();
			institute2.setId(new Long(10000+departmentId.longValue()));
			institute2.setName("Lehrstuhl B");
			institute2.setShortcut("LSB");
			institute2.setEnabled(true);
			
			institutes.add(institute1);
			institutes.add(institute2);
		}
		
		return institutes;
	}

	public List findInstitutesByDepartmentAndEnabled(Long departmentId,
			Boolean enabled) {
		// for testing purposes: return the same data as method findInstitutesByDepartment
		// (all institutes there are enabled)
		if( enabled ){
			return this.findInstitutesByDepartment(departmentId);
		} else {
			return new ArrayList();
		}
		
	}

	public void removeInstitute(Long instituteId) {
		// TODO Auto-generated method stub

	}

	public void update(InstituteInfo instituteInfo) {
		// TODO Auto-generated method stub

	}

	public Long applyAtDepartment(Long instituteId, Long departmentId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeUnconfirmedApplication(Long applicationId) {
		// TODO Auto-generated method stub
		
	}

	public boolean isNoneExistingInstituteShortcut (InstituteInfo self, String shortcut) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public List findInstitutesByEnabled (Boolean enabledOnly) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public InstituteSecurity getInstituteSecurity(Long instituteId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Long applyAtDepartment(ApplicationInfo applicationInfo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setInstituteStatus(Long instituteId, Boolean status) {
		// TODO Auto-generated method stub
	}
}
