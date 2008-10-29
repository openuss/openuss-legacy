package org.openuss.lecture;

/**
 * A University is a special type of an Organisation.
 * It's the top level institution and contains Departments and
 * Periods.
 * @author Ron Haus
 * @author Florian Dondorf
 */
public abstract class UniversityBase
    extends org.openuss.lecture.OrganisationImpl
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -1938059924747399370L;

    private org.openuss.lecture.UniversityType universityType = org.openuss.lecture.UniversityType.MISC;

    /**
     * @see org.openuss.lecture.University#getUniversityType()
     */
    public org.openuss.lecture.UniversityType getUniversityType()
    {
        return this.universityType;
    }

    /**
     * @see org.openuss.lecture.University#setUniversityType(org.openuss.lecture.UniversityType universityType)
     */
    public void setUniversityType(org.openuss.lecture.UniversityType universityType)
    {
        this.universityType = universityType;
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

    private java.util.List<org.openuss.lecture.Period> periods = new java.util.ArrayList<org.openuss.lecture.Period>();

    /**
     * 
     */
    public java.util.List<org.openuss.lecture.Period> getPeriods()
    {
        return this.periods;
    }

    public void setPeriods(java.util.List<org.openuss.lecture.Period> periods)
    {
        this.periods = periods;
    }

    /**
     * 
     */
    public abstract void add(org.openuss.lecture.Department department);

    /**
     * 
     */
    public abstract void remove(org.openuss.lecture.Department department);

    /**
     * 
     */
    public abstract void add(org.openuss.lecture.Period period);

    /**
     * 
     */
    public abstract void remove(org.openuss.lecture.Period period);

    /**
     * 
     */
    public abstract java.util.List getActivePeriods();

    /**
     * <p>
     * Retrieve the first period of the university with the default
     * flag equal true.
     * </p>
     */
    public abstract org.openuss.lecture.Period getDefaultPeriod();

    /**
     * This entity does not have any identifiers
     * but since it extends the <code>org.openuss.lecture.OrganisationImpl</code> class
     * it will simply delegate the call up there.
     *
     * @see org.openuss.lecture.Organisation#equals(Object)
     */
    public boolean equals(Object object)
    {
        return super.equals(object);
    }

    /**
     * This entity does not have any identifiers
     * but since it extends the <code>org.openuss.lecture.OrganisationImpl</code> class
     * it will simply delegate the call up there.
     *
     * @see org.openuss.lecture.Organisation#hashCode()
     */
    public int hashCode()
    {
        return super.hashCode();
    }


}