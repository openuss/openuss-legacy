package org.openuss.registration;


/**
*
*/
public abstract class AddressBase implements Address {
    private java.lang.Long id;
    private java.lang.String street;
    private java.lang.Integer number;
    private java.lang.Long zipCode;
    private java.lang.String country = "Germany";

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
    * @return The street as <code>java.lang.String</code>
    */
    public java.lang.String getStreet() {
        return street;
    }

    /**
      *
      * @param street The street to set
      */
    public void setStreet(java.lang.String street) {
        this.street = street;
    }

    /**
    *
    * @return The number as <code>java.lang.Integer</code>
    */
    public java.lang.Integer getNumber() {
        return number;
    }

    /**
      *
      * @param number The number to set
      */
    public void setNumber(java.lang.Integer number) {
        this.number = number;
    }

    /**
    *
    * @return The zipCode as <code>java.lang.Long</code>
    */
    public java.lang.Long getZipCode() {
        return zipCode;
    }

    /**
      *
      * @param zipCode The zipCode to set
      */
    public void setZipCode(java.lang.Long zipCode) {
        this.zipCode = zipCode;
    }

    /**
    *
    * @return The country as <code>java.lang.String</code>
    */
    public java.lang.String getCountry() {
        return country;
    }

    /**
      *
      * @param country The country to set
      */
    public void setCountry(java.lang.String country) {
        this.country = country;
    }
}
