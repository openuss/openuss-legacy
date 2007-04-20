package org.openuss.registration;

public interface AssistantDao {
    public java.io.Serializable create(Assistant assistant);

    public Assistant read(java.lang.Long id);

    public java.util.List<Assistant> readAll();

    public void update(Assistant assistant);

    public void delete(Assistant assistant);
}
