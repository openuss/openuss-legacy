package org.openuss.registration;


/**
*
*/
public interface Assistant extends Person {
    /**
    *
    * @return The department as <code>java.lang.String</code>
    */
    public java.lang.String getDepartment();

    /**
      *
      * @param department The department to set
      */
    public void setDepartment(java.lang.String department);

    /**
    *
    * @return The lectures as <code>java.util.Set<org.openuss.lecture.Course></code>
    */
    public java.util.Set<org.openuss.lecture.Course> getLectures();

    /**
      *
      * @param lectures The lectures to set
      */
    public void setLectures(java.util.Set<org.openuss.lecture.Course> lectures);
}
