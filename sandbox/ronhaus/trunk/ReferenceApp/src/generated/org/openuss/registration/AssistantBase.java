package org.openuss.registration;


/**
*
*/
public abstract class AssistantBase extends PersonBase implements Assistant {
    private java.lang.String department;
    private java.util.Set<org.openuss.lecture.Course> lectures = new java.util.HashSet<org.openuss.lecture.Course>();

    /**
    *
    * @return The department as <code>java.lang.String</code>
    */
    public java.lang.String getDepartment() {
        return department;
    }

    /**
      *
      * @param department The department to set
      */
    public void setDepartment(java.lang.String department) {
        this.department = department;
    }

    /**
    *
    * @return The lectures as <code>java.util.Set<org.openuss.lecture.Course></code>
    */
    public java.util.Set<org.openuss.lecture.Course> getLectures() {
        return lectures;
    }

    /**
      *
      * @param lectures The lectures to set
      */
    public void setLectures(java.util.Set<org.openuss.lecture.Course> lectures) {
        this.lectures = lectures;
    }
}
