package org.openuss.registration;


/**
*
*/
public abstract class PersonBase implements Person {
    private java.lang.Long id;
    private java.lang.String firstname;
    private java.lang.String lastname;
    private java.util.Date registration;
    private org.openuss.registration.Address address;

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
    * @return The firstname as <code>java.lang.String</code>
    */
    public java.lang.String getFirstname() {
        return firstname;
    }

    /**
      *
      * @param firstname The firstname to set
      */
    public void setFirstname(java.lang.String firstname) {
        this.firstname = firstname;
    }

    /**
    *
    * @return The lastname as <code>java.lang.String</code>
    */
    public java.lang.String getLastname() {
        return lastname;
    }

    /**
      *
      * @param lastname The lastname to set
      */
    public void setLastname(java.lang.String lastname) {
        this.lastname = lastname;
    }

    /**
    *
    * @return The registration as <code>java.util.Date</code>
    */
    public java.util.Date getRegistration() {
        return registration;
    }

    /**
      *
      * @param registration The registration to set
      */
    public void setRegistration(java.util.Date registration) {
        this.registration = registration;
    }

    /**
    *
    * @return The address as <code>org.openuss.registration.Address</code>
    */
    public org.openuss.registration.Address getAddress() {
        return address;
    }

    /**
      *
      * @param address The address to set
      */
    public void setAddress(org.openuss.registration.Address address) {
        this.address = address;
    }
}
