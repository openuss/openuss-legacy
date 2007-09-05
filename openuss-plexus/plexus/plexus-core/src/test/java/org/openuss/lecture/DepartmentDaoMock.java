package org.openuss.lecture;

import java.util.Collection;
import java.util.List;

import org.openuss.foundation.AbstractMockDao;
import org.openuss.security.Membership;

public class DepartmentDaoMock extends AbstractMockDao<Department> implements DepartmentDao {

	public Department create(boolean defaultDepartment, DepartmentType departmentType, boolean enabled,
			Membership membership, String name, String ownerName, String shortcut) {
		// TODO Auto-generated method stub
		return null;
	}

	public Department create(DepartmentType departmentType, boolean defaultDepartment, String shortcut, String name,
			String description, String ownerName, String address, String postcode, String city, String country,
			String telephone, String telefax, String website, String email, String locale, String theme, Long imageId,
			boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object create(int transform, boolean defaultDepartment, DepartmentType departmentType, boolean enabled,
			Membership membership, String name, String ownerName, String shortcut) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object create(int transform, DepartmentType departmentType, boolean defaultDepartment, String shortcut,
			String name, String description, String ownerName, String address, String postcode, String city,
			String country, String telephone, String telefax, String website, String email, String locale,
			String theme, Long imageId, boolean enabled) {
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

	public List findByEnabled(boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByEnabled(int transform, boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByEnabled(int transform, String queryString, boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByEnabled(String queryString, boolean enabled) {
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

	public Department findByShortcut(String queryString, String shortcut) {
		// TODO Auto-generated method stub
		return null;
	}

	public Department findByShortcut(String shortcut) {
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

	public Object findByUniversityAndDefault(int transform, String queryString, University university,
			boolean defaultDepartment) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object findByUniversityAndDefault(int transform, University university, boolean defaultDepartment) {
		// TODO Auto-generated method stub
		return null;
	}

	public Department findByUniversityAndDefault(String queryString, University university, boolean defaultDepartment) {
		// TODO Auto-generated method stub
		return null;
	}

	public Department findByUniversityAndDefault(University university, boolean defaultDepartment) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByUniversityAndEnabled(int transform, String queryString, University university, boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByUniversityAndEnabled(int transform, University university, boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByUniversityAndEnabled(String queryString, University university, boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByUniversityAndEnabled(University university, boolean enabled) {
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

	public List findByUniversityAndTypeAndEnabled(int transform, String queryString, University university,
			DepartmentType departmentType, boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByUniversityAndTypeAndEnabled(int transform, University university, DepartmentType departmentType,
			boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByUniversityAndTypeAndEnabled(String queryString, University university,
			DepartmentType departmentType, boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByUniversityAndTypeAndEnabled(University university, DepartmentType departmentType, boolean enabled) {
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



	
}
