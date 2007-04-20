package org.openuss.registration;


/**
*
*/
public interface Student extends Person {
    /**
    *
    * @return The mnr as <code>java.lang.Long</code>
    */
    public java.lang.Long getMnr();

    /**
      *
      * @param mnr The mnr to set
      */
    public void setMnr(java.lang.Long mnr);

    /**
    *
    * @return The enrollment as <code>java.util.Date</code>
    */
    public java.util.Date getEnrollment();

    /**
      *
      * @param enrollment The enrollment to set
      */
    public void setEnrollment(java.util.Date enrollment);

    /**
    *
    * @return The mainFields as <code>java.util.Set<java.lang.String></code>
    */
    public java.util.Set<java.lang.String> getMainFields();

    /**
      *
      * @param mainFields The mainFields to set
      */
    public void setMainFields(java.util.Set<java.lang.String> mainFields);

    /**
    *
    * @return The courses as <code>java.util.Set<org.openuss.lecture.Course></code>
    */
    public java.util.Set<org.openuss.lecture.Course> getCourses();

    /**
      *
      * @param courses The courses to set
      */
    public void setCourses(java.util.Set<org.openuss.lecture.Course> courses);
}
