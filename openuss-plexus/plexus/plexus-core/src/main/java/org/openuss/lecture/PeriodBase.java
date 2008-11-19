package org.openuss.lecture;

/**
 * <p>
 * A period is a arbitary time frame like a semester, or trimester.
 * </p>
 */
public abstract class PeriodBase
    implements Period, java.io.Serializable
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = 4333528450075839736L;

    private java.lang.Long id;

    /**
     * @see org.openuss.lecture.Period#getId()
     */
    public java.lang.Long getId()
    {
        return this.id;
    }

    /**
     * @see org.openuss.lecture.Period#setId(java.lang.Long id)
     */
    public void setId(java.lang.Long id)
    {
        this.id = id;
    }

    private java.lang.String name;

    /**
     * @see org.openuss.lecture.Period#getName()
     */
    public java.lang.String getName()
    {
        return this.name;
    }

    /**
     * @see org.openuss.lecture.Period#setName(java.lang.String name)
     */
    public void setName(java.lang.String name)
    {
        this.name = name;
    }

    private java.lang.String description;

    /**
     * @see org.openuss.lecture.Period#getDescription()
     */
    public java.lang.String getDescription()
    {
        return this.description;
    }

    /**
     * @see org.openuss.lecture.Period#setDescription(java.lang.String description)
     */
    public void setDescription(java.lang.String description)
    {
        this.description = description;
    }

    private java.util.Date startdate;

    /**
     * @see org.openuss.lecture.Period#getStartdate()
     */
    public java.util.Date getStartdate()
    {
        return this.startdate;
    }

    /**
     * @see org.openuss.lecture.Period#setStartdate(java.util.Date startdate)
     */
    public void setStartdate(java.util.Date startdate)
    {
        this.startdate = startdate;
    }

    private java.util.Date enddate;

    /**
     * @see org.openuss.lecture.Period#getEnddate()
     */
    public java.util.Date getEnddate()
    {
        return this.enddate;
    }

    /**
     * @see org.openuss.lecture.Period#setEnddate(java.util.Date enddate)
     */
    public void setEnddate(java.util.Date enddate)
    {
        this.enddate = enddate;
    }

    private boolean defaultPeriod = false;

    /**
     * @see org.openuss.lecture.Period#isDefaultPeriod()
     */
    public boolean isDefaultPeriod()
    {
        return this.defaultPeriod;
    }

    /**
     * @see org.openuss.lecture.Period#setDefaultPeriod(boolean defaultPeriod)
     */
    public void setDefaultPeriod(boolean defaultPeriod)
    {
        this.defaultPeriod = defaultPeriod;
    }

    private java.util.List<org.openuss.lecture.Course> courses = new java.util.ArrayList<org.openuss.lecture.Course>();

    /**
     * 
     */
    public java.util.List<org.openuss.lecture.Course> getCourses()
    {
        return this.courses;
    }

    public void setCourses(java.util.List<org.openuss.lecture.Course> courses)
    {
        this.courses = courses;
    }

    private org.openuss.lecture.University university;

    /**
     * 
     */
    public org.openuss.lecture.University getUniversity()
    {
        return this.university;
    }

    public void setUniversity(org.openuss.lecture.University university)
    {
        this.university = university;
    }

    /**
     * 
     */
    public abstract void add(org.openuss.lecture.Course course);

    /**
     * 
     */
    public abstract void remove(org.openuss.lecture.Course course);

    /**
     * 
     */
    public abstract boolean isActive();

    /**
     * Returns <code>true</code> if the argument is an Period instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof Period))
        {
            return false;
        }
        final Period that = (Period)object;
        if (this.id == null || that.getId() == null || !this.id.equals(that.getId()))
        {
            return false;
        }
        return true;
    }

    /**
     * Returns a hash code based on this entity's identifiers.
     */
    public int hashCode()
    {
        int hashCode = 0;
        hashCode = 29 * hashCode + (id == null ? 0 : id.hashCode());

        return hashCode;
    }


}