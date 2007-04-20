package org.openuss.lecture;


/**
*
*/
public interface LectureService {
    /**
    *
            * @param null
            * @param student
            * @param course
    */
    public void enrollStudentInCourse(
        org.openuss.registration.Student student,
        org.openuss.lecture.Course course);

    /**
    *
            * @param null
    */
    public java.util.Set<org.openuss.lecture.Course> getFullCourses();

    /**
    *
            * @param null
            * @param student
    */
    public java.lang.Long persist(org.openuss.registration.Student student);

    /**
    *
            * @param null
            * @param assistent
    */
    public java.lang.Long persist(org.openuss.registration.Assistant assistent);

    /**
    *
            * @param null
            * @param course
    */
    public java.lang.Long persist(org.openuss.lecture.Course course);

    /**
    *
            * @param null
            * @param mnr
    */
    public org.openuss.registration.Student readStudent(java.lang.Long mnr);

    /**
    *
            * @param null
            * @param id
    */
    public org.openuss.registration.Assistant readAssistant(java.lang.Long id);

    /**
    *
            * @param null
            * @param id
    */
    public org.openuss.lecture.Course readCourse(java.lang.Long id);

    /**
    *
            * @param null
            * @param student
    */
    public void testTransactionManager(org.openuss.registration.Student student);
}
