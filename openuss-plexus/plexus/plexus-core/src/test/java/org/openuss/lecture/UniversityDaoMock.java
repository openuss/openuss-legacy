package org.openuss.lecture;

import java.util.Collection;
import java.util.List;

import org.openuss.foundation.AbstractMockDao;
import org.openuss.security.Membership;

public class UniversityDaoMock extends AbstractMockDao<University> implements UniversityDao {

	public University create(boolean enabled, Membership membership, String name, String ownerName, String shortcut,
			UniversityType universityType) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object create(int transform, boolean enabled, Membership membership, String name, String ownerName,
			String shortcut, UniversityType universityType) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object create(int transform, UniversityType universityType, String shortcut, String name,
			String description, String ownerName, String address, String postcode, String city, String country,
			String telephone, String telefax, String website, String email, String locale, String theme, Long imageId,
			boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public University create(UniversityType universityType, String shortcut, String name, String description,
			String ownerName, String address, String postcode, String city, String country, String telephone,
			String telefax, String website, String email, String locale, String theme, Long imageId, boolean enabled) {
		// TODO Auto-generated method stub
		return null;
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

	public University findByShortcut(String queryString, String shortcut) {
		// TODO Auto-generated method stub
		return null;
	}

	public University findByShortcut(String shortcut) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByTypeAndEnabled(int transform, String queryString, UniversityType universityType, boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByTypeAndEnabled(int transform, UniversityType universityType, boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByTypeAndEnabled(String queryString, UniversityType universityType, boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByTypeAndEnabled(UniversityType universityType, boolean enabled) {
		// TODO Auto-generated method stub
		return null;
	}

	public void toUniversityInfo(University sourceEntity, UniversityInfo targetVO) {
		// TODO Auto-generated method stub
		
	}

	public UniversityInfo toUniversityInfo(University entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public void toUniversityInfoCollection(Collection entities) {
		// TODO Auto-generated method stub
		
	}

	public void universityInfoToEntity(UniversityInfo sourceVO, University targetEntity, boolean copyIfNull) {
		// TODO Auto-generated method stub
		
	}

	public University universityInfoToEntity(UniversityInfo universityInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	public void universityInfoToEntityCollection(Collection instances) {
		// TODO Auto-generated method stub
		
	}


}
