package org.openuss.registration;


/**
*
*/
public abstract class StudentBase extends PersonBase implements Student {
    private java.lang.Long mnr;
    private java.util.Date enrollment;
    private java.util.Set<java.lang.String> mainFields = new java.util.HashSet<java.lang.String>();
    private java.util.Set<org.openuss.lecture.Course> courses = new java.util.HashSet<org.openuss.lecture.Course>();

    /**
    *
    * @return The mnr as <code>java.lang.Long</code>
    */
    public java.lang.Long getMnr() {
        return mnr;
    }

    /**
      *
      * @param mnr The mnr to set
      */
    public void setMnr(java.lang.Long mnr) {
        this.mnr = mnr;
    }

    /**
    *
    * @return The enrollment as <code>java.util.Date</code>
    */
    public java.util.Date getEnrollment() {
        return enrollment;
    }

    /**
      *
      * @param enrollment The enrollment to set
      */
    public void setEnrollment(java.util.Date enrollment) {
        this.enrollment = enrollment;
    }

    /**
    *
    * @return The mainFields as <code>java.util.Set<java.lang.String></code>
    */
    public java.util.Set<java.lang.String> getMainFields() {
        return mainFields;
    }

    /**
      *
      * @param mainFields The mainFields to set
      */
    public void setMainFields(java.util.Set<java.lang.String> mainFields) {
        this.mainFields = mainFields;
    }

    /**
    *
    * @return The courses as <code>java.util.Set<org.openuss.lecture.Course></code>
    */
    public java.util.Set<org.openuss.lecture.Course> getCourses() {
        return courses;
    }

    /**
      *
      * @param courses The courses to set
      */
    public void setCourses(java.util.Set<org.openuss.lecture.Course> courses) {
        this.courses = courses;
    }
}
