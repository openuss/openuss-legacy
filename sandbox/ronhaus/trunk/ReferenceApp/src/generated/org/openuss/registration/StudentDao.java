package org.openuss.registration;

public interface StudentDao {
    public java.io.Serializable create(Student student);

    public Student read(java.lang.Long id);

    public java.util.List<Student> readAll();

    public void update(Student student);

    public void delete(Student student);
}
