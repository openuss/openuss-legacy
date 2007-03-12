package events;


/**
*
*/
public interface Student extends Person {
    /**
    *
    * @return The birthday as <code>java.util.Date</code>
    */
    public java.util.Date getBirthday();

    /**
      *
      * @param birthday The birthday to set
      */
    public void setBirthday(java.util.Date birthday);

    /**
    *
    * @return The abi as <code>float</code>
    */
    public float getAbi();

    /**
      *
      * @param abi The abi to set
      */
    public void setAbi(float abi);

    /**
    *
    * @return The age as <code>int</code>
    */
    public int getAge();

    /**
      *
      * @param age The age to set
      */
    public void setAge(int age);
}
