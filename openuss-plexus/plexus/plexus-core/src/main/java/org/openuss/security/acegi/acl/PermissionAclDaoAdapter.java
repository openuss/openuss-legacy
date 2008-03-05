package org.openuss.security.acegi.acl;

import java.util.List;

import org.acegisecurity.acl.basic.AclObjectIdentity;
import org.acegisecurity.acl.basic.BasicAclDao;
import org.acegisecurity.acl.basic.BasicAclEntry;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.openuss.security.Group; // FIXME PACKAGE CYCLE
import org.openuss.security.GroupType; // FIXME PACKAGE CYCLE
import org.openuss.security.acl.ObjectIdentity;
import org.openuss.security.acl.ObjectIdentityDao;
import org.openuss.security.acl.Permission;
import org.openuss.security.acl.PermissionDao;

/**
 * Adapter to connect the acegi dao layer with the hibernate permission dao objects. 
 * @author Ingo Dueppe
 */
public class PermissionAclDaoAdapter implements BasicAclDao {
	
    public static final String RECIPIENT_USED_FOR_INHERITENCE_MARKER = "___INHERITENCE_MARKER_ONLY___";
    
    private PermissionDao permissionDao;
	
	private ObjectIdentityDao objectIdentityDao;

	/**
	 * {@inheritDoc}
	 */
	public BasicAclEntry[] getAcls(AclObjectIdentity aclObjectIdentity) {
		if (aclObjectIdentity instanceof EntityObjectIdentity) {
			EntityObjectIdentity objIdentity = (EntityObjectIdentity) aclObjectIdentity;

			ObjectIdentity identity = objectIdentityDao.load(objIdentity.getIdentifier());
			if (identity == null) {
				return null; // unknown identity
			}

			// get permission entries of object identity
			List<BasicAclEntry> entries = getBasicAclPermissions(identity);
			
			if (entries.size() == 0) {
				// no permission entries found so set inheritence marker
				return new BasicAclEntry[] {createBasicAclEntry(identity)};
			} else {
				return (BasicAclEntry[]) entries.toArray(new BasicAclEntry[entries.size()]);
			} 
		}
		return null;
	}

	/**
	 * Create an inheritence marker of a known object identity
	 * @param identity
	 * @return BasicAclEntry
	 */
	private BasicAclEntry createBasicAclEntry(ObjectIdentity identity) {
		Permission permission = Permission.Factory.newInstance();
		permission.setAclObjectIdentity(identity);
		permission.setId(identity.getId());
		permission.setMask(0);
		permission.setRecipient(Group.Factory.newInstance(RECIPIENT_USED_FOR_INHERITENCE_MARKER,GroupType.UNDEFINED));
		
		return new AclPermissionAdapter(permission);
	}

	/**
	 * @param identifier
	 * @return List<BasicAclEntry>
	 */
	private List<BasicAclEntry> getBasicAclPermissions(ObjectIdentity objectIdentity) {
		List entries = permissionDao.findPermissions(objectIdentity);
		CollectionUtils.transform(entries, new AclPermissionAdapterTransformer());
		return entries; 
	}

	public PermissionDao getPermissionDao() {
		return permissionDao;
	}

	public void setPermissionDao(PermissionDao permissionDao) {
		this.permissionDao = permissionDao;
	}

	public ObjectIdentityDao getObjectIdentityDao() {
		return objectIdentityDao;
	}

	public void setObjectIdentityDao(ObjectIdentityDao objectIdentityDao) {
		this.objectIdentityDao = objectIdentityDao;
	}

	private static final class AclPermissionAdapterTransformer implements Transformer {
		public Object transform(Object input) {
			return new AclPermissionAdapter((Permission)input);
		}
	}
}
