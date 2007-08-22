package org.openuss.lecture;

import java.sql.Blob;
import java.util.Collection;
import java.util.List;

import org.openuss.foundation.AbstractMockDao;
import org.openuss.security.Membership;

public class InstituteDaoMock extends AbstractMockDao<Institute> implements InstituteDao {

	public Institute create(boolean enabled, Membership membership, String name, String ownerName, String shortcut) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object create(int transform, boolean enabled, Membership membership, String name, String ownerName,
			String shortcut) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object create(int transform, String shortcut, String name, String description, String ownerName,
			String address, String postcode, String city, String country, String telephone, String telefax,
			String website, String email, String locale, String theme, Long imageId, boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public Institute create(String shortcut, String name, String description, String ownerName, String address,
			String postcode, String city, String country, String telephone, String telefax, String website,
			String email, String locale, String theme, Long imageId, boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByDepartmentAndEnabled(Department department, Boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByDepartmentAndEnabled(int transform, Department department, Boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByDepartmentAndEnabled(int transform, String queryString, Department department, Boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByDepartmentAndEnabled(String queryString, Department department, Boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByEnabled(Boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByEnabled(int transform, Boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByEnabled(int transform, String queryString, Boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByEnabled(String queryString, Boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object findByShortcut(int transform, String queryString, String shortcut) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object findByShortcut(int transform, String shortcut) {
		// TODO Auto-generated method stub
		return null;
	}

	public Institute findByShortcut(String queryString, String shortcut) {
		// TODO Auto-generated method stub
		return null;
	}

	public Institute findByShortcut(String shortcut) {
		// TODO Auto-generated method stub
		return null;
	}

	public void instituteInfoToEntity(InstituteInfo sourceVO, Institute targetEntity, boolean copyIfNull) {
		// TODO Auto-generated method stub
		
	}

	public Institute instituteInfoToEntity(InstituteInfo instituteInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	public void instituteInfoToEntityCollection(Collection instances) {
		// TODO Auto-generated method stub
		
	}

	public void instituteSecurityToEntity(InstituteSecurity sourceVO, Institute targetEntity, boolean copyIfNull) {
		// TODO Auto-generated method stub
		
	}

	public Institute instituteSecurityToEntity(InstituteSecurity instituteSecurity) {
		// TODO Auto-generated method stub
		return null;
	}

	public void instituteSecurityToEntityCollection(Collection instances) {
		// TODO Auto-generated method stub
		
	}

	public void toInstituteInfo(Institute sourceEntity, InstituteInfo targetVO) {
		// TODO Auto-generated method stub
		
	}

	public InstituteInfo toInstituteInfo(Institute entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public void toInstituteInfoCollection(Collection entities) {
		// TODO Auto-generated method stub
		
	}

	public void toInstituteSecurity(Institute sourceEntity, InstituteSecurity targetVO) {
		// TODO Auto-generated method stub
		
	}

	public InstituteSecurity toInstituteSecurity(Institute entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public void toInstituteSecurityCollection(Collection entities) {
		// TODO Auto-generated method stub
		
	}



}
