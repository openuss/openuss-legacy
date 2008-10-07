package org.openuss.lecture;

import java.util.ArrayList;
import java.util.List;

import org.openuss.security.User;

public class InstituteServiceMock implements InstituteService {

	public Long create(InstituteInfo instituteInfo, Long userId) {
		return null;
	}

	public ApplicationInfo findApplicationByInstituteAndConfirmed(Long instituteId, boolean confirmed) {
		return null;
	}

	public InstituteInfo findInstitute(Long instituteId) {
		return null;
	}

	public void removeCompleteInstituteTree(Long instituteId) {
		
	}

	public List<InstituteInfo> findInstitutesByDepartment(Long departmentId) {
		List<InstituteInfo> institutes = new ArrayList<InstituteInfo>();
		
		List<Long> validDepartments = new ArrayList<Long>();
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

	@SuppressWarnings("unchecked")
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

	}

	public void update(InstituteInfo instituteInfo) {

	}

	public Long applyAtDepartment(Long instituteId, Long departmentId) {
		return null;
	}

	public void removeUnconfirmedApplication(Long applicationId) {
		
	}

	public boolean isNoneExistingOrganisationShortcutByInstitute (InstituteInfo self, String shortcut) {
		return false;
	}
	
	public List<InstituteInfo> findAllInstitutes (boolean enabledOnly) {
		return null;
	}
	
	public InstituteSecurity getInstituteSecurity(Long instituteId) {
		return null;
	}
	
	public Long applyAtDepartment(ApplicationInfo applicationInfo) {
		return null;
	}
	
	public Long applyAtDepartment(Long instituteId, Long departmentId, Long userId) {
		return null;
	}

	public List<Application> findApplicationsByInstitute(Long instituteId) {
		return null;
	}

	public void setInstituteStatus(Long instituteId, boolean status) {
	}

	public ApplicationInfo findApplicationByInstitute(Long instituteId) {
		return null;
	}
	
	public void setGroupOfMember(InstituteMember member, Long instituteId) {
	}
	
	public void resendActivationCode(InstituteInfo instituteInfo, Long userId) {
	}

	public void removeUserDependencies(User user) {
	}
	
}
