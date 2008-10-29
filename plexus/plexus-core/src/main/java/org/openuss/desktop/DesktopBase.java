package org.openuss.desktop;

/**
 * 
 */
public abstract class DesktopBase
    implements Desktop, java.io.Serializable
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -9060711448888140684L;

    private java.lang.Long id;

    /**
     * @see org.openuss.desktop.Desktop#getId()
     */
    public java.lang.Long getId()
    {
        return this.id;
    }

    /**
     * @see org.openuss.desktop.Desktop#setId(java.lang.Long id)
     */
    public void setId(java.lang.Long id)
    {
        this.id = id;
    }

    private org.openuss.security.User user;

    /**
     * 
     */
    public org.openuss.security.User getUser()
    {
        return this.user;
    }

    public void setUser(org.openuss.security.User user)
    {
        this.user = user;
    }

    private java.util.List<org.openuss.lecture.Institute> institutes = new java.util.ArrayList<org.openuss.lecture.Institute>();

    /**
     * 
     */
    public java.util.List<org.openuss.lecture.Institute> getInstitutes()
    {
        return this.institutes;
    }

    public void setInstitutes(java.util.List<org.openuss.lecture.Institute> institutes)
    {
        this.institutes = institutes;
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

    private java.util.List<org.openuss.lecture.CourseType> courseTypes = new java.util.ArrayList<org.openuss.lecture.CourseType>();

    /**
     * 
     */
    public java.util.List<org.openuss.lecture.CourseType> getCourseTypes()
    {
        return this.courseTypes;
    }

    public void setCourseTypes(java.util.List<org.openuss.lecture.CourseType> courseTypes)
    {
        this.courseTypes = courseTypes;
    }

    private java.util.List<org.openuss.lecture.University> universities = new java.util.ArrayList<org.openuss.lecture.University>();

    /**
     * 
     */
    public java.util.List<org.openuss.lecture.University> getUniversities()
    {
        return this.universities;
    }

    public void setUniversities(java.util.List<org.openuss.lecture.University> universities)
    {
        this.universities = universities;
    }

    private java.util.List<org.openuss.lecture.Department> departments = new java.util.ArrayList<org.openuss.lecture.Department>();

    /**
     * 
     */
    public java.util.List<org.openuss.lecture.Department> getDepartments()
    {
        return this.departments;
    }

    public void setDepartments(java.util.List<org.openuss.lecture.Department> departments)
    {
        this.departments = departments;
    }

    /**
     * 
     */
    public abstract void linkInstitute(org.openuss.lecture.Institute institute);

    /**
     * 
     */
    public abstract void unlinkInstitute(org.openuss.lecture.Institute institute);

    /**
     * 
     */
    public abstract void linkCourseType(org.openuss.lecture.CourseType courseType);

    /**
     * 
     */
    public abstract void unlinkCourseType(org.openuss.lecture.CourseType courseType);

    /**
     * 
     */
    public abstract void linkCourse(org.openuss.lecture.Course course);

    /**
     * 
     */
    public abstract void unlinkCourse(org.openuss.lecture.Course course);

    /**
     * Returns <code>true</code> if the argument is an Desktop instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof Desktop))
        {
            return false;
        }
        final Desktop that = (Desktop)object;
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