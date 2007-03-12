package events;


/**
*
*/
public interface Address {
    /**
    *
    * @return The id as <code>long</code>
    */
    public long getId();

    /**
      *
      * @param id The id to set
      */
    public void setId(long id);

    /**
    *
    * @return The plz as <code>int</code>
    */
    public int getPlz();

    /**
      *
      * @param plz The plz to set
      */
    public void setPlz(int plz);
}
