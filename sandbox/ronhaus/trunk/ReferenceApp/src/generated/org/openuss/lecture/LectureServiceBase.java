package org.openuss.lecture;


/**
*
*/
public abstract class LectureServiceBase implements LectureService {
    /**
    * Dependency to org.openuss.lecture.CourseDao
    */
    private org.openuss.lecture.CourseDao courseDao;

    /**
    * Dependency to org.openuss.registration.AssistantDao
    */
    private org.openuss.registration.AssistantDao assistantDao;

    /**
    * Dependency to org.openuss.registration.StudentDao
    */
    private org.openuss.registration.StudentDao studentDao;

    public void setCourseDao(org.openuss.lecture.CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    protected org.openuss.lecture.CourseDao getCourseDao() {
        return this.courseDao;
    }

    public void setAssistantDao(
        org.openuss.registration.AssistantDao assistantDao) {
        this.assistantDao = assistantDao;
    }

    protected org.openuss.registration.AssistantDao getAssistantDao() {
        return this.assistantDao;
    }

    public void setStudentDao(org.openuss.registration.StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    protected org.openuss.registration.StudentDao getStudentDao() {
        return this.studentDao;
    }

    /**
    *
            * @param null
            * @param student
            * @param course
    */
    public void enrollStudentInCourse(
        org.openuss.registration.Student student,
        org.openuss.lecture.Course course) {
    }

    /**
    *
            * @param null
    */
    public java.util.Set<org.openuss.lecture.Course> getFullCourses() {
        return null;
    }

    /**
    *
            * @param null
            * @param student
    */
    public java.lang.Long persist(org.openuss.registration.Student student) {
        return null;
    }

    /**
    *
            * @param null
            * @param assistent
    */
    public java.lang.Long persist(org.openuss.registration.Assistant assistent) {
        return null;
    }

    /**
    *
            * @param null
            * @param course
    */
    public java.lang.Long persist(org.openuss.lecture.Course course) {
        return null;
    }

    /**
    *
            * @param null
            * @param mnr
    */
    public org.openuss.registration.Student readStudent(java.lang.Long mnr) {
        return null;
    }

    /**
    *
            * @param null
            * @param id
    */
    public org.openuss.registration.Assistant readAssistant(java.lang.Long id) {
        return null;
    }

    /**
    *
            * @param null
            * @param id
    */
    public org.openuss.lecture.Course readCourse(java.lang.Long id) {
        return null;
    }

    /**
    *
            * @param null
            * @param student
    */
    public void testTransactionManager(org.openuss.registration.Student student) {
    }
}
