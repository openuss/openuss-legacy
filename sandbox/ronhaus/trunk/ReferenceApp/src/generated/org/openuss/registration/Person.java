package org.openuss.registration;


/**
*
*/
public interface Person {
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
    * @return The firstname as <code>java.lang.String</code>
    */
    public java.lang.String getFirstname();

    /**
      *
      * @param firstname The firstname to set
      */
    public void setFirstname(java.lang.String firstname);

    /**
    *
    * @return The lastname as <code>java.lang.String</code>
    */
    public java.lang.String getLastname();

    /**
      *
      * @param lastname The lastname to set
      */
    public void setLastname(java.lang.String lastname);

    /**
    *
    * @return The registration as <code>java.util.Date</code>
    */
    public java.util.Date getRegistration();

    /**
      *
      * @param registration The registration to set
      */
    public void setRegistration(java.util.Date registration);

    /**
    *
    * @return The address as <code>org.openuss.registration.Address</code>
    */
    public org.openuss.registration.Address getAddress();

    /**
      *
      * @param address The address to set
      */
    public void setAddress(org.openuss.registration.Address address);
}
