package org.openuss.lecture;


/**
*
*/
public interface Course {
    /**
    *
    * @return The id as <code>java.lang.Long</code>
    */
    public java.lang.Long getId();

    /**
      *
      * @param id The id to set
      */
    public void setId(java.lang.Long id);

    /**
    *
    * @return The name as <code>java.lang.String</code>
    */
    public java.lang.String getName();

    /**
      *
      * @param name The name to set
      */
    public void setName(java.lang.String name);

    /**
    *
    * @return The maxParticipants as <code>java.lang.Integer</code>
    */
    public java.lang.Integer getMaxParticipants();

    /**
      *
      * @param maxParticipants The maxParticipants to set
      */
    public void setMaxParticipants(java.lang.Integer maxParticipants);

    /**
    *
    * @return The participants as <code>java.util.Set<org.openuss.registration.Student></code>
    */
    public java.util.Set<org.openuss.registration.Student> getParticipants();

    /**
      *
      * @param participants The participants to set
      */
    public void setParticipants(
        java.util.Set<org.openuss.registration.Student> participants);

    /**
    *
    * @return The lecturer as <code>org.openuss.registration.Assistant</code>
    */
    public org.openuss.registration.Assistant getLecturer();

    /**
      *
      * @param lecturer The lecturer to set
      */
    public void setLecturer(org.openuss.registration.Assistant lecturer);
}
