package org.openuss.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openuss.services.model.CourseBean;
import org.openuss.services.model.InstituteBean;
import org.openuss.services.model.Role;
import org.openuss.services.model.UserBean;

/**
 * Mockup Object for Web-Service-Testing
 * @author Ingo Dueppe
 *
 */
public class LectureWebServiceMock implements LectureWebService {
	
	private Map<Long,CourseBean> courses = new HashMap<Long,CourseBean>();

	private Map<Long,UserBean> usersById = new HashMap<Long,UserBean>();
	
	private Map<String, Long> usersByName = new HashMap<String, Long>();
	
	private Map<Long, InstituteBean> institutes = new HashMap<Long, InstituteBean>();
	
	private Map<Long, Map<Long, Role>> members = new HashMap<Long, Map<Long,Role>>();
	
	public LectureWebServiceMock() {
		CourseBean course = new CourseBean();
		course.setId(nextId());
		course.setInstituteId(1L);
		course.setName("name");
		course.setShortcut("shortcut");
		course.setDescription("description");
		storeCourse(course);
	}
	
	@Override
	public CourseBean getCourse(long courseId) throws LectureLogicException {
		if (!courses.containsKey(courseId)) {
			throw new LectureLogicException("Course with id not found!");
		} else {
			return courses.get(courseId);
		}
	}

	@Override
	public Long createCourse(CourseBean course) throws LectureLogicException {
		if (course.getId() != null) {
			throw new LectureLogicException("Course Id must be empty!");
		}
		course.setId(nextId());
		storeCourse(course);
		return course.getId();
	}
	
	@Override
	public Long createUser(UserBean user) throws LectureLogicException {
		if (user == null) {
			throw new LectureLogicException("User must not be null!");
		} 
		
		if (usersByName.containsKey(user.getUsername())) {
			throw new LectureLogicException("Username already exists.");
		}
		user.setId(nextId());
		storeUser(user);
		return user.getId();
	}

	private void storeUser(UserBean user) {
		usersById.put(user.getId(), user);
		usersByName.put(user.getUsername(), user.getId());
	}


	@Override
	public boolean deleteUser(long userId) throws LectureLogicException {
		UserBean user = usersById.get(userId);
		if (user != null) {
			usersById.remove(userId);
			usersByName.remove(user.getUsername());
			return true;
		} else {
			throw new LectureLogicException("User not found!");
		}
	}

	@Override
	public Long findUser(String username) throws LectureLogicException {
		return usersByName.get(username);
	}

	@Override
	public boolean assignCourseMember(long courseId, long userId, Role role) throws LectureLogicException {
		CourseBean course = loadCourse(courseId);
		UserBean user = loadUser(userId);
		
		if (role != Role.PARTICIPANT && role != Role.ASSISTANT) {
			throw new LectureLogicException("User role must be PARTICIPANT or ASSISTANT");
		}

		Map<Long, Role> roles = loadRoles(course);
		roles.put(user.getId(), role);
		
		return true;
	}

	private Map<Long, Role> loadRoles(CourseBean course) {
		Map<Long, Role> roles = members.get(course.getId());
		if (roles == null) {
			roles = new HashMap<Long, Role>();
			members.put(course.getId(), roles);
		}
		return roles;
	}

	private UserBean loadUser(long userId) throws LectureLogicException {
		UserBean user = usersById.get(userId);
		if (user == null) {
			throw new LectureLogicException("User not found");
		}
		return user;
	}

	private CourseBean loadCourse(long courseId) throws LectureLogicException {
		CourseBean course = courses.get(courseId);
		if (course == null) {
			throw new LectureLogicException("course is null");
		}
		return course;
	}
	
	@Override
	public Role isCourseMember(long courseId, long userId) throws LectureLogicException {
		return loadRole(loadUser(userId), loadRoles(loadCourse(courseId)));
	}

	private Role loadRole(UserBean user, Map<Long, Role> roles) {
		Role role = roles.get(user.getId());
		if (role == null) {
			role = Role.NONE;
		}
		return role;
	}

	@Override
	public boolean removeCourseMember(long courseId, long userId) throws LectureLogicException {
		Map<Long, Role> roles = loadRoles(loadCourse(courseId));
		Role role = roles.remove(userId);
		return role != null;
	}

	@Override
	public boolean updateCourse(CourseBean course) throws LectureLogicException {
		if (course == null || course.getId() == null) {
			throw new LectureLogicException("Course Identifier is not defined!");
		}
		if (!courses.containsKey(course.getId())) {
			throw new LectureLogicException("Course not found!");
		}
		storeCourse(course);
		return true;
	}

	@Override
	public boolean updateUser(UserBean user) throws LectureLogicException {
		if (user == null || user.getId() == null) {
			throw new LectureLogicException("User id must be specifed!");
		}
		storeUser(user);
		return true;
	}

	@Override
	public Long createInstitute(InstituteBean institute) throws LectureLogicException {
		institute.setId(nextId());
		storeInstitute(institute);
		return institute.getId();
	}

	private void storeInstitute(InstituteBean institute) {
		institutes.put(institute.getId(), institute);
	}

	@Override
	public InstituteBean getInstitute(long instituteId) throws LectureLogicException {
		return institutes.get(instituteId);
	}

	@Override
	public boolean updateInstitute(InstituteBean institute) throws LectureLogicException {
		storeInstitute(institute);
		return true;
	}


	private void storeCourse(CourseBean course) {
		courses.put(course.getId(), course);
	}

	private static long id = 0;
	
	private static synchronized long nextId() {
		return ++id;
	}

	@Override
	public boolean deleteCourse(long courseId) throws LectureLogicException {
		return courses.remove(courseId) == null;
	}

	@Override
	public List<InstituteBean> listInstitute(long departmentId) throws LectureLogicException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
