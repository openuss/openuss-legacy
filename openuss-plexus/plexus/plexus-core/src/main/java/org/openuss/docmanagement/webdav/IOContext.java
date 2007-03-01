package org.openuss.docmanagement.webdav;

public interface IOContext {
    public boolean hasStream();
    public void informCompleted(boolean success);
    public boolean isCompleted();
}
