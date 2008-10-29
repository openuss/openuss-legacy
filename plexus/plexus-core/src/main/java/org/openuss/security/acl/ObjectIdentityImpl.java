package org.openuss.security.acl;

/**
 * @author Ingo Dueppe
 * @see org.openuss.security.acl.ObjectIdentity
 */
public class ObjectIdentityImpl extends ObjectIdentityBase implements ObjectIdentity {
	
	public static final long SYSTEM_OBJECT_IDENTITY = 0L;
	
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 161217105102033676L;

	public ObjectIdentityImpl() {
		// setAclClass("org.openuss.security.acl.EntityObjectIdentity");
	}

	public void addPermission(Permission permission) {
		getPermissions().add(permission);
		permission.getPermissionPk().setAclObjectIdentity(this);
	}

	public void removePermission(Permission permission) {
		if (permission != null) {
			getPermissions().remove(permission);
			permission.getPermissionPk().setAclObjectIdentity(null);
		}
	}

	@Override
	public ObjectIdentity getParent() {
		ObjectIdentity parent = super.getParent();
		if (getId() != SYSTEM_OBJECT_IDENTITY && parent == null) {
			parent = new ObjectIdentityImpl();
			parent.setId(SYSTEM_OBJECT_IDENTITY);
		}
		return parent;
	}

}