package org.openuss.lecture;

import java.util.ArrayList;
import java.util.List;

public class InstituteServiceMock implements InstituteService {

	public Long create(InstituteInfo instituteInfo, Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	public ApplicationInfo findApplicationByInstituteAndConfirmed(Long instituteId, boolean confirmed) {
		// TODO Auto-generated method stub
		return null;
	}

	public InstituteInfo findInstitute(Long instituteId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeCompleteInstituteTree(Long instituteId) {
		// TODO Auto-generated method stub
		
	}

	public List findInstitutesByDepartment(Long departmentId) {
		List institutes = new ArrayList();
		
		List validDepartments = new ArrayList();
		validDepartments.add(1101L);
		validDepartments.add(1102L);
		validDepartments.add(1103L);
		validDepartments.add(1201L);
		validDepartments.add(1202L);
		validDepartments.add(1301L);
		validDepartments.add(1302L);
		validDepartments.add(1401L);
		validDepartments.add(1402L);
		validDepartments.add(1501L);
		validDepartments.add(1502L);
		
		if(!validDepartments.contains(departmentId)){
			return institutes;
		}
		
		if( departmentId.longValue() % 2 == 0){
			InstituteInfo institute1 = new InstituteInfo();
			institute1.setId(10000L+departmentId.longValue());
			institute1.setName("Lehrstuhl 1");
			institute1.setShortcut("LS1");
			institute1.setEnabled(true);
			
			InstituteInfo institute2 = new InstituteInfo();
			institute2.setId(10000+departmentId.longValue());
			institute2.setName("Lehrstuhl 2");
			institute2.setShortcut("LS2");
			institute2.setEnabled(true);
			
			institutes.add(institute1);
			institutes.add(institute2);
		} else {
			InstituteInfo institute1 = new InstituteInfo();
			institute1.setId(10000L+departmentId.longValue());
			institute1.setName("Lehrstuhl A");
			institute1.setShortcut("LSA");
			institute1.setEnabled(true);
			
			InstituteInfo institute2 = new InstituteInfo();
			institute2.setId(10000L+departmentId.longValue());
			institute2.setName("Lehrstuhl B");
			institute2.setShortcut("LSB");
			institute2.setEnabled(true);
			
			institutes.add(institute1);
			institutes.add(institute2);
		}
		
		return institutes;
	}

	public List findInstitutesByDepartmentAndEnabled(Long departmentId,
			boolean enabled) {
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

	public boolean isNoneExistingOrganisationShortcutByInstitute (InstituteInfo self, String shortcut) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public List<InstituteInfo> findAllInstitutes (boolean enabledOnly) {
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
	
	public Long applyAtDepartment(Long instituteId, Long departmentId, Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findApplicationsByInstitute(Long instituteId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setInstituteStatus(Long instituteId, boolean status) {
		// TODO Auto-generated method stub
	}

	public ApplicationInfo findApplicationByInstitute(Long instituteId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setGroupOfMember(InstituteMember member, Long instituteId) {
		// TODO Auto-generated method stub
	}
	
	public void resendActivationCode(InstituteInfo instituteInfo, Long userId) {
		// TODO Auto-generated method stub
	}
	
}
