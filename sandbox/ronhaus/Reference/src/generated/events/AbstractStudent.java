package events;


/**
*
*/
public abstract class AbstractStudent extends AbstractPerson implements Student {
    private java.util.Date birthday;
    private float abi = 1.7f;
    private int age = 20;

    /**
    *
    * @return The birthday as <code>java.util.Date</code>
    */
    public java.util.Date getBirthday() {
        return birthday;
    }

    /**
      *
      * @param birthday The birthday to set
      */
    public void setBirthday(java.util.Date birthday) {
        this.birthday = birthday;
    }

    /**
    *
    * @return The abi as <code>float</code>
    */
    public float getAbi() {
        return abi;
    }

    /**
      *
      * @param abi The abi to set
      */
    public void setAbi(float abi) {
        this.abi = abi;
    }

    /**
    *
    * @return The age as <code>int</code>
    */
    public int getAge() {
        return age;
    }

    /**
      *
      * @param age The age to set
      */
    public void setAge(int age) {
        this.age = age;
    }
}
