package org.openuss.lecture;


/**
*
*/
public abstract class CourseBase implements Course {
    private java.lang.Long id;
    private java.lang.String name;
    private java.lang.Integer maxParticipants;
    private java.util.Set<org.openuss.registration.Student> participants = new java.util.HashSet<org.openuss.registration.Student>();
    private org.openuss.registration.Assistant lecturer;

    /**
    *
    * @return The id as <code>java.lang.Long</code>
    */
    public java.lang.Long getId() {
        return id;
    }

    /**
      *
      * @param id The id to set
      */
    public void setId(java.lang.Long id) {
        this.id = id;
    }

    /**
    *
    * @return The name as <code>java.lang.String</code>
    */
    public java.lang.String getName() {
        return name;
    }

    /**
      *
      * @param name The name to set
      */
    public void setName(java.lang.String name) {
        this.name = name;
    }

    /**
    *
    * @return The maxParticipants as <code>java.lang.Integer</code>
    */
    public java.lang.Integer getMaxParticipants() {
        return maxParticipants;
    }

    /**
      *
      * @param maxParticipants The maxParticipants to set
      */
    public void setMaxParticipants(java.lang.Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    /**
    *
    * @return The participants as <code>java.util.Set<org.openuss.registration.Student></code>
    */
    public java.util.Set<org.openuss.registration.Student> getParticipants() {
        return participants;
    }

    /**
      *
      * @param participants The participants to set
      */
    public void setParticipants(
        java.util.Set<org.openuss.registration.Student> participants) {
        this.participants = participants;
    }

    /**
    *
    * @return The lecturer as <code>org.openuss.registration.Assistant</code>
    */
    public org.openuss.registration.Assistant getLecturer() {
        return lecturer;
    }

    /**
      *
      * @param lecturer The lecturer to set
      */
    public void setLecturer(org.openuss.registration.Assistant lecturer) {
        this.lecturer = lecturer;
    }
}
