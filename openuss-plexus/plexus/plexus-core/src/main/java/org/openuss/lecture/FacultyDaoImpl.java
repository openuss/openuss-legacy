// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.lecture;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openuss.security.Authority;
import org.openuss.security.Group;
import org.openuss.security.User;

/**
 * @see org.openuss.lecture.Faculty
 */
public class FacultyDaoImpl extends org.openuss.lecture.FacultyDaoBase {

	public Faculty facultyDetailsToEntity(FacultyDetails facultyDetails) {
		Faculty faculty = load(facultyDetails.getId());
		if (faculty == null) {
			faculty = Faculty.Factory.newInstance();
		}
		facultyDetailsToEntity(facultyDetails, faculty, false);
		return faculty;
	}

	public Faculty facultySecurityToEntity(FacultySecurity facultySecurity) {
		logger.error("facultySecurityToEntity is not supported!");
		return null;
	}

	@Override
	public void toFacultySecurity(Faculty faculty, FacultySecurity target) {
		Map<Long, FacultyMember> members = new HashMap<Long, FacultyMember>();
		List<FacultyGroup> groups = new ArrayList<FacultyGroup>();
		// analyse groups
		traverseGroups(faculty, members, groups);
		// analyse members that are not in any group yet.
		traverseAdditionalMembers(faculty, members);
		// add all found groups and members to facultysecurity
		target.setGroups(groups);
		target.setMembers(new ArrayList(members.values()));
	}

	private void traverseAdditionalMembers(Faculty faculty, Map<Long, FacultyMember> members) {
		for (Authority authority: faculty.getMembers()) {
			if (authority instanceof User && !members.containsKey(authority.getId())) {
				FacultyMember member = new FacultyMember();
				member.setGroups(new ArrayList<FacultyGroup>());
				toFacultyMember((User)authority, member, faculty);
				members.put(member.getId(), member);
			}
		}
	}

	private void traverseGroups(Faculty faculty, Map<Long, FacultyMember> members, Collection groups) {
		for (Group group : faculty.getGroups()) {
			FacultyGroup facultyGroup = groupToFacultyGroup(group);
			groups.add(facultyGroup);
			
			for (Authority authority : group.getMembers()) {
				if (authority instanceof User) {
					FacultyMember member = null;
					if (!members.containsKey(authority.getId())) {
						member = new FacultyMember();
						member.setGroups(new ArrayList<FacultyGroup>());
						toFacultyMember((User)authority, member, faculty);
						members.put(member.getId(), member);
					} else {
						member = members.get(authority.getId());
					}
					member.getGroups().add(facultyGroup);
					facultyGroup.getMembers().add(member);
				} else if (authority instanceof Group) {
					logger.error("group members are not supported yet and will be ignored!");
				}
			}
		}
	}
	
	private FacultyGroup groupToFacultyGroup(Group group) {
		FacultyGroup facultyGroup = new FacultyGroup();
		facultyGroup.setMembers(new ArrayList<FacultyMember>());
		toFacultyGroup(group, facultyGroup);
		return facultyGroup;
	}

	private void toFacultyGroup(Group group, FacultyGroup fg) {
		fg.setId(group.getId());
		fg.setLabel(group.getLabel());
		fg.setName(group.getName());
	}

	private void toFacultyMember(User user, FacultyMember member, Faculty faculty) {
		member.setId(user.getId());
		member.setUsername(user.getUsername());
		member.setFirstName(user.getContact().getFirstName());
		member.setLastName(user.getContact().getLastName());
		if (faculty != null) {
			member.setOwner((faculty.getOwner().equals(user)));
		}
	}

}