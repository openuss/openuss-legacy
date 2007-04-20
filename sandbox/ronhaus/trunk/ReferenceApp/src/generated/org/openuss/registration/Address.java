package org.openuss.registration;


/**
*
*/
public interface Address {
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
    * @return The street as <code>java.lang.String</code>
    */
    public java.lang.String getStreet();

    /**
      *
      * @param street The street to set
      */
    public void setStreet(java.lang.String street);

    /**
    *
    * @return The number as <code>java.lang.Integer</code>
    */
    public java.lang.Integer getNumber();

    /**
      *
      * @param number The number to set
      */
    public void setNumber(java.lang.Integer number);

    /**
    *
    * @return The zipCode as <code>java.lang.Long</code>
    */
    public java.lang.Long getZipCode();

    /**
      *
      * @param zipCode The zipCode to set
      */
    public void setZipCode(java.lang.Long zipCode);

    /**
    *
    * @return The country as <code>java.lang.String</code>
    */
    public java.lang.String getCountry();

    /**
      *
      * @param country The country to set
      */
    public void setCountry(java.lang.String country);
}
