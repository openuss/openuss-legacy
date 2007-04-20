package org.openuss.registration;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


public abstract class StudentDaoBase extends HibernateDaoSupport
    implements StudentDao {
    public java.io.Serializable create(Student student) {
        if (student == null) {
            throw new IllegalArgumentException(
                "Student.create - 'student' cannot be null");
        }

        java.io.Serializable id = getHibernateTemplate()
                                      .save(student);

        return id;
    }

    public Student read(java.lang.Long id) {
        Object o = getHibernateTemplate().load(StudentImpl.class, id);

        if (o instanceof Student) {
            return (Student) o;
        } else {
            return null;
        }
    }

    public java.util.List<Student> readAll() {
        java.util.List values = getHibernateTemplate().loadAll(StudentImpl.class);

        java.util.List<Student> list = new java.util.ArrayList<Student>();

        for (java.util.Iterator i = values.iterator(); i.hasNext();) {
            Object o = i.next();

            if (o instanceof Student) {
                list.add((Student) o);
            }
        }

        return list;
    }

    public void update(Student student) {
        if (student == null) {
            throw new IllegalArgumentException(
                "Student.update - 'student' cannot be null");
        }

        getHibernateTemplate().update(student);
    }

    public void delete(Student student) {
        if (student == null) {
            throw new IllegalArgumentException(
                "Student.delete - 'student' cannot be null");
        }

        getHibernateTemplate().delete(student);
    }
}
