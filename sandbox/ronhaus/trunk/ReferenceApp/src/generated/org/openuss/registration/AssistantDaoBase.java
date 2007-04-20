package org.openuss.registration;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


public abstract class AssistantDaoBase extends HibernateDaoSupport
    implements AssistantDao {
    public java.io.Serializable create(Assistant assistant) {
        if (assistant == null) {
            throw new IllegalArgumentException(
                "Assistant.create - 'assistant' cannot be null");
        }

        java.io.Serializable id = getHibernateTemplate()
                                      .save(assistant);

        return id;
    }

    public Assistant read(java.lang.Long id) {
        Object o = getHibernateTemplate().load(AssistantImpl.class, id);

        if (o instanceof Assistant) {
            return (Assistant) o;
        } else {
            return null;
        }
    }

    public java.util.List<Assistant> readAll() {
        java.util.List values = getHibernateTemplate()
                                    .loadAll(AssistantImpl.class);

        java.util.List<Assistant> list = new java.util.ArrayList<Assistant>();

        for (java.util.Iterator i = values.iterator(); i.hasNext();) {
            Object o = i.next();

            if (o instanceof Assistant) {
                list.add((Assistant) o);
            }
        }

        return list;
    }

    public void update(Assistant assistant) {
        if (assistant == null) {
            throw new IllegalArgumentException(
                "Assistant.update - 'assistant' cannot be null");
        }

        getHibernateTemplate().update(assistant);
    }

    public void delete(Assistant assistant) {
        if (assistant == null) {
            throw new IllegalArgumentException(
                "Assistant.delete - 'assistant' cannot be null");
        }

        getHibernateTemplate().delete(assistant);
    }
}
