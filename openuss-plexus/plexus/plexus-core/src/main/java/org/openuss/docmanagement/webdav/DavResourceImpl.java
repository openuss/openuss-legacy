package org.openuss.docmanagement.webdav;

import java.io.IOException;
import java.util.List;

import org.apache.jackrabbit.webdav.DavException;
import org.apache.jackrabbit.webdav.DavResource;
import org.apache.jackrabbit.webdav.DavResourceFactory;
import org.apache.jackrabbit.webdav.DavResourceIterator;
import org.apache.jackrabbit.webdav.DavResourceLocator;
import org.apache.jackrabbit.webdav.DavSession;
import org.apache.jackrabbit.webdav.MultiStatusResponse;
import org.apache.jackrabbit.webdav.io.InputContext;
import org.apache.jackrabbit.webdav.io.OutputContext;
import org.apache.jackrabbit.webdav.lock.ActiveLock;
import org.apache.jackrabbit.webdav.lock.LockInfo;
import org.apache.jackrabbit.webdav.lock.LockManager;
import org.apache.jackrabbit.webdav.lock.Scope;
import org.apache.jackrabbit.webdav.lock.Type;
import org.apache.jackrabbit.webdav.property.DavProperty;
import org.apache.jackrabbit.webdav.property.DavPropertyName;
import org.apache.jackrabbit.webdav.property.DavPropertyNameSet;
import org.apache.jackrabbit.webdav.property.DavPropertySet;

public class DavResourceImpl implements DavResource {

	public void addLockManager(LockManager arg0) {
		// TODO Auto-generated method stub

	}

	public void addMember(DavResource arg0, InputContext arg1)
			throws DavException {
		// TODO Auto-generated method stub

	}

	public MultiStatusResponse alterProperties(List arg0) throws DavException {
		// TODO Auto-generated method stub
		return null;
	}

	public MultiStatusResponse alterProperties(DavPropertySet arg0,
			DavPropertyNameSet arg1) throws DavException {
		// TODO Auto-generated method stub
		return null;
	}

	public void copy(DavResource arg0, boolean arg1) throws DavException {
		// TODO Auto-generated method stub

	}

	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	public DavResource getCollection() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getComplianceClass() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}

	public DavResourceFactory getFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getHref() {
		// TODO Auto-generated method stub
		return null;
	}

	public DavResourceLocator getLocator() {
		// TODO Auto-generated method stub
		return null;
	}

	public ActiveLock getLock(Type arg0, Scope arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public ActiveLock[] getLocks() {
		// TODO Auto-generated method stub
		return null;
	}

	public DavResourceIterator getMembers() {
		// TODO Auto-generated method stub
		return null;
	}

	public long getModificationTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	public DavPropertySet getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	public DavProperty getProperty(DavPropertyName arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public DavPropertyName[] getPropertyNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getResourcePath() {
		// TODO Auto-generated method stub
		return null;
	}

	public DavSession getSession() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSupportedMethods() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasLock(Type arg0, Scope arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isCollection() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isLockable(Type arg0, Scope arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	public ActiveLock lock(LockInfo arg0) throws DavException {
		// TODO Auto-generated method stub
		return null;
	}

	public void move(DavResource arg0) throws DavException {
		// TODO Auto-generated method stub

	}

	public ActiveLock refreshLock(LockInfo arg0, String arg1)
			throws DavException {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeMember(DavResource arg0) throws DavException {
		// TODO Auto-generated method stub

	}

	public void removeProperty(DavPropertyName arg0) throws DavException {
		// TODO Auto-generated method stub

	}

	public void setProperty(DavProperty arg0) throws DavException {
		// TODO Auto-generated method stub

	}

	public void spool(OutputContext arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	public void unlock(String arg0) throws DavException {
		// TODO Auto-generated method stub

	}

}
