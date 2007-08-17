package org.openuss.lecture;

import java.sql.Blob;
import java.util.Collection;
import java.util.List;

import org.openuss.foundation.AbstractMockDao;
import org.openuss.security.Membership;

public class DepartmentDaoMock extends AbstractMockDao<Department> implements DepartmentDao {

	public Department create(DepartmentType departmentType, Boolean enabled, Membership membership, String name, String ownerName, String shortcut) {
		// TODO Auto-generated method stub
		return null;
	}

	public Department create(DepartmentType departmentType, String shortcut, String name, String description, 
			String ownerName, String address, String postcode, String city, String country, String telephone, 
			String telefax, String website, String email, String locale, String theme, Long imageId, Boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object create(int transform, DepartmentType departmentType, Boolean enabled, Membership membership, 
			String name, String ownerName, String shortcut) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object create(int transform, DepartmentType departmentType, String shortcut, String name, String description, 
			String ownerName, String address, String postcode, String city, String country, String telephone, 
			String telefax, String website, String email, String locale, String theme, Long imageId, Boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public void departmentInfoToEntity(DepartmentInfo sourceVO, Department targetEntity, boolean copyIfNull) {
		// TODO Auto-generated method stub
		
	}

	public Department departmentInfoToEntity(DepartmentInfo departmentInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	public void departmentInfoToEntityCollection(Collection instances) {
		// TODO Auto-generated method stub
		
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

	public List findByType(DepartmentType type) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List findByType(int transform, DepartmentType type) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List findByType(int transform, String queryString, DepartmentType type) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List findByType(String queryString, DepartmentType type) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List findByUniversityAndEnabled(int transform, String queryString, University university, Boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByUniversityAndEnabled(int transform, University university, Boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByUniversityAndEnabled(String queryString, University university, Boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByUniversityAndEnabled(University university, Boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List findByUniversity(int transform, String queryString, University university) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByUniversity(int transform, University university) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByUniversity(String queryString, University university) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByUniversity(University university) {
		// TODO Auto-generated method stub
		return null;
	}

	public void toDepartmentInfo(Department sourceEntity, DepartmentInfo targetVO) {
		// TODO Auto-generated method stub
		
	}

	public DepartmentInfo toDepartmentInfo(Department entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public void toDepartmentInfoCollection(Collection entities) {
		// TODO Auto-generated method stub
		
	}

	public Department create(DepartmentType departmentType, String shortcut, String name, String description,
			String ownerName, String address, String postcode, String city, String country, String telephone,
			String telefax, String website, String email, String locale, String theme, Blob logo, Boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object create(int transform, DepartmentType departmentType, String shortcut, String name,
			String description, String ownerName, String address, String postcode, String city, String country,
			String telephone, String telefax, String website, String email, String locale, String theme, Blob logo,
			Boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByUniversityAndType(int transform, String queryString, University university,
			DepartmentType departmentType) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByUniversityAndType(int transform, University university, DepartmentType departmentType) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByUniversityAndType(String queryString, University university, DepartmentType departmentType) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByUniversityAndType(University university, DepartmentType departmentType) {
		// TODO Auto-generated method stub
		return null;
	}

}
