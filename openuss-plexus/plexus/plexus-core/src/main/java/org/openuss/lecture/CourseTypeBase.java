package org.openuss.lecture;

/**
 * <p>
 * The courseType
 * </p>
 */
public abstract class CourseTypeBase
    implements CourseType, java.io.Serializable
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = 8060633415304382432L;

    private java.lang.Long id;

    /**
     * @see org.openuss.lecture.CourseType#getId()
     */
    public java.lang.Long getId()
    {
        return this.id;
    }

    /**
     * @see org.openuss.lecture.CourseType#setId(java.lang.Long id)
     */
    public void setId(java.lang.Long id)
    {
        this.id = id;
    }

    private java.lang.String name;

    /**
     * @see org.openuss.lecture.CourseType#getName()
     */
    public java.lang.String getName()
    {
        return this.name;
    }

    /**
     * @see org.openuss.lecture.CourseType#setName(java.lang.String name)
     */
    public void setName(java.lang.String name)
    {
        this.name = name;
    }

    private java.lang.String shortcut;

    /**
     * @see org.openuss.lecture.CourseType#getShortcut()
     */
    public java.lang.String getShortcut()
    {
        return this.shortcut;
    }

    /**
     * @see org.openuss.lecture.CourseType#setShortcut(java.lang.String shortcut)
     */
    public void setShortcut(java.lang.String shortcut)
    {
        this.shortcut = shortcut;
    }

    private java.lang.String description;

    /**
     * @see org.openuss.lecture.CourseType#getDescription()
     */
    public java.lang.String getDescription()
    {
        return this.description;
    }

    /**
     * @see org.openuss.lecture.CourseType#setDescription(java.lang.String description)
     */
    public void setDescription(java.lang.String description)
    {
        this.description = description;
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

    private org.openuss.lecture.Institute institute;

    /**
     * 
     */
    public org.openuss.lecture.Institute getInstitute()
    {
        return this.institute;
    }

    public void setInstitute(org.openuss.lecture.Institute institute)
    {
        this.institute = institute;
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
     * Returns <code>true</code> if the argument is an CourseType instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof CourseType))
        {
            return false;
        }
        final CourseType that = (CourseType)object;
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