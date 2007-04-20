package org.openuss.registration;

public interface AddressDao {
    public java.io.Serializable create(Address address);

    public Address read(java.lang.Long id);

    public java.util.List<Address> readAll();

    public void update(Address address);

    public void delete(Address address);
}
