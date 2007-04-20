package org.openuss.lecture;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


public abstract class CourseDaoBase extends HibernateDaoSupport
    implements CourseDao {
    public java.io.Serializable create(Course course) {
        if (course == null) {
            throw new IllegalArgumentException(
                "Course.create - 'course' cannot be null");
        }

        java.io.Serializable id = getHibernateTemplate().save(course);

        return id;
    }

    public Course read(java.lang.Long id) {
        Object o = getHibernateTemplate().load(CourseImpl.class, id);

        if (o instanceof Course) {
            return (Course) o;
        } else {
            return null;
        }
    }

    public java.util.List<Course> readAll() {
        java.util.List values = getHibernateTemplate().loadAll(CourseImpl.class);

        java.util.List<Course> list = new java.util.ArrayList<Course>();

        for (java.util.Iterator i = values.iterator(); i.hasNext();) {
            Object o = i.next();

            if (o instanceof Course) {
                list.add((Course) o);
            }
        }

        return list;
    }

    public void update(Course course) {
        if (course == null) {
            throw new IllegalArgumentException(
                "Course.update - 'course' cannot be null");
        }

        getHibernateTemplate().update(course);
    }

    public void delete(Course course) {
        if (course == null) {
            throw new IllegalArgumentException(
                "Course.delete - 'course' cannot be null");
        }

        getHibernateTemplate().delete(course);
    }
}
