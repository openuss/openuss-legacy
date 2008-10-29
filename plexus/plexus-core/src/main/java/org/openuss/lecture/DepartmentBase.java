package org.openuss.lecture;

/**
 * 
 */
public abstract class DepartmentBase
    extends org.openuss.lecture.OrganisationImpl
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -4679919374589454976L;

    private org.openuss.lecture.DepartmentType departmentType = org.openuss.lecture.DepartmentType.NONOFFICIAL;

    /**
     * @see org.openuss.lecture.Department#getDepartmentType()
     */
    public org.openuss.lecture.DepartmentType getDepartmentType()
    {
        return this.departmentType;
    }

    /**
     * @see org.openuss.lecture.Department#setDepartmentType(org.openuss.lecture.DepartmentType departmentType)
     */
    public void setDepartmentType(org.openuss.lecture.DepartmentType departmentType)
    {
        this.departmentType = departmentType;
    }

    private boolean defaultDepartment = false;

    /**
     * @see org.openuss.lecture.Department#isDefaultDepartment()
     */
    public boolean isDefaultDepartment()
    {
        return this.defaultDepartment;
    }

    /**
     * @see org.openuss.lecture.Department#setDefaultDepartment(boolean defaultDepartment)
     */
    public void setDefaultDepartment(boolean defaultDepartment)
    {
        this.defaultDepartment = defaultDepartment;
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

    private java.util.List<org.openuss.lecture.Application> applications = new java.util.ArrayList<org.openuss.lecture.Application>();

    /**
     * 
     */
    public java.util.List<org.openuss.lecture.Application> getApplications()
    {
        return this.applications;
    }

    public void setApplications(java.util.List<org.openuss.lecture.Application> applications)
    {
        this.applications = applications;
    }

    /**
     * 
     */
    public abstract void add(org.openuss.lecture.Institute institute);

    /**
     * 
     */
    public abstract void remove(org.openuss.lecture.Institute institute);

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