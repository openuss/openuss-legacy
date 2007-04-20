package org.openuss.lecture;

public interface CourseDao {
    public java.io.Serializable create(Course course);

    public Course read(java.lang.Long id);

    public java.util.List<Course> readAll();

    public void update(Course course);

    public void delete(Course course);
}
