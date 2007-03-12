package events;


/**
*
*/
public abstract class AbstractAddress implements Address {
    private long id;
    private int plz;

    /**
    *
    * @return The id as <code>long</code>
    */
    public long getId() {
        return id;
    }

    /**
      *
      * @param id The id to set
      */
    public void setId(long id) {
        this.id = id;
    }

    /**
    *
    * @return The plz as <code>int</code>
    */
    public int getPlz() {
        return plz;
    }

    /**
      *
      * @param plz The plz to set
      */
    public void setPlz(int plz) {
        this.plz = plz;
    }
}
