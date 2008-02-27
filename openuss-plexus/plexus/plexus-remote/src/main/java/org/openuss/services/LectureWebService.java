package org.openuss.services;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import org.openuss.services.model.CourseBean;
import org.openuss.services.model.InstituteBean;
import org.openuss.services.model.Role;
import org.openuss.services.model.UserBean;


@WebService(targetNamespace="http://www.openuss.org/services", serviceName="OpenUSSService")
@SOAPBinding(style=Style.DOCUMENT,use=Use.LITERAL)
public interface LectureWebService {
	
	@WebMethod(action = "createCourse")
	public @WebResult(name="courseId") Long createCourse(@WebParam(name="course") CourseBean course) throws LectureLogicException;
	
	@WebMethod(action="updateCourse")
	public @WebResult(name="succcess") boolean updateCourse(@WebParam(name="course") CourseBean course) throws LectureLogicException; 

	@WebMethod(action = "getCourse")
	public @WebResult(name="courseId") CourseBean getCourse(@WebParam(name="courseId") long courseId) throws LectureLogicException;
	
	@WebMethod(action = "deleteCourse")
	public @WebResult(name="boolean") boolean deleteCourse(@WebParam(name="courseId") long courseId) throws LectureLogicException;
	
	@WebMethod(action = "assignCourseMember")
	public @WebResult(name="success") boolean assignCourseMember(@WebParam(name="courseId") long courseId, @WebParam(name="userId") long userId, @WebParam(name="role") Role role) throws LectureLogicException;
	
	@WebMethod(action = "removeCourseMember")
	public @WebResult(name="success") boolean removeCourseMember(@WebParam(name="courseId") long courseId, @WebParam(name="userId") long userId) throws LectureLogicException;
	
	@WebMethod(action = "isCourseMember")
	public @WebResult(name="roleType") Role isCourseMember(@WebParam(name="courseId") long courseId, @WebParam(name="userId") long userId) throws LectureLogicException;
	
	/**
	 * Create a new user account.
	 * @param user
	 * @return Identifier of the new user.
	 * @throws LectureLogicException
	 */
	@WebMethod(action = "createUser")
	public @WebResult(name="userId") Long createUser(@WebParam(name="user") UserBean user) throws LectureLogicException;
	
	@WebMethod(action = "updateUser")
	public @WebResult(name="success") boolean updateUser(@WebParam(name="user") UserBean user) throws LectureLogicException;

	@WebMethod(action = "deleteUser")
	public @WebResult(name="success") boolean deleteUser(@WebParam(name="userId") long userId) throws LectureLogicException;

	/**
	 * Find user identifier by unique username.
	 * @param username
	 * @return Long identifier of the user or null.
	 * @throws LectureLogicException
	 */
	@WebMethod(action = "findUser")
	public @WebResult(name="userId") Long findUser(@WebParam(name="username") String username) throws LectureLogicException;
	
	@WebMethod(action = "createInstitute")
	public @WebResult(name="instituteId") Long createInstitute(@WebParam(name="institute") InstituteBean institute) throws LectureLogicException;
	
	@WebMethod(action = "updateInstitute")
	public @WebResult(name="success") boolean updateInstitute(@WebParam(name="insitute") InstituteBean institute) throws LectureLogicException;
	
	@WebMethod(action = "getInstitute")
	public @WebResult(name="institute") InstituteBean getInstitute(@WebParam(name="instituteId") long instituteId) throws LectureLogicException;
	
	@WebMethod(action = "listInstitute")
	public @WebResult(name="instituteList") List<InstituteBean> listInstitute(@WebParam(name="departmentId") long departmentId) throws LectureLogicException;
	
	
}
