package org.openuss.registration;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


public abstract class AddressDaoBase extends HibernateDaoSupport
    implements AddressDao {
    public java.io.Serializable create(Address address) {
        if (address == null) {
            throw new IllegalArgumentException(
                "Address.create - 'address' cannot be null");
        }

        java.io.Serializable id = getHibernateTemplate()
                                      .save(address);

        return id;
    }

    public Address read(java.lang.Long id) {
        Object o = getHibernateTemplate().load(AddressImpl.class, id);

        if (o instanceof Address) {
            return (Address) o;
        } else {
            return null;
        }
    }

    public java.util.List<Address> readAll() {
        java.util.List values = getHibernateTemplate().loadAll(AddressImpl.class);

        java.util.List<Address> list = new java.util.ArrayList<Address>();

        for (java.util.Iterator i = values.iterator(); i.hasNext();) {
            Object o = i.next();

            if (o instanceof Address) {
                list.add((Address) o);
            }
        }

        return list;
    }

    public void update(Address address) {
        if (address == null) {
            throw new IllegalArgumentException(
                "Address.update - 'address' cannot be null");
        }

        getHibernateTemplate().update(address);
    }

    public void delete(Address address) {
        if (address == null) {
            throw new IllegalArgumentException(
                "Address.delete - 'address' cannot be null");
        }

        getHibernateTemplate().delete(address);
    }
}
