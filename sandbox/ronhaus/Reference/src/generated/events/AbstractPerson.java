package events;


/**
*
*/
public abstract class AbstractPerson implements Person {
    private java.lang.Integer id;
    private java.lang.String name = "Ron";

    /**
    *
    * @return The id as <code>java.lang.Integer</code>
    */
    public java.lang.Integer getId() {
        return id;
    }

    /**
      *
      * @param id The id to set
      */
    public void setId(java.lang.Integer id) {
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
}
