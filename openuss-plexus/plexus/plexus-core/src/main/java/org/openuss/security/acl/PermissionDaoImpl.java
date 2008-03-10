// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.acl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.hibernate.Query;
import org.openuss.security.acegi.acl.AclPermissionAdapter;

/**
 * @see org.openuss.security.acl.Permission
 */
public class PermissionDaoImpl extends PermissionDaoBase {

	@Override
	public List findPermissionsWithRecipient(final int transform, final ObjectIdentity objectIdentity) {
		return this.findPermissionsWithRecipient(transform,
				"select " +
				"	p.recipient.name as recipient," +
				"   p.mask as mask," +
				"   p.aclObjectIdentity.id as aclObjectIdentity," +
				"   p.aclObjectIdentity.parent.id as aclObjectParentIdentity" +
				" from org.openuss.security.acl.Permission as p where p.aclObjectIdentity = ?",
				objectIdentity);
	}

	/**
	 * @see org.openuss.security.acl.PermissionDao#findPermissionsWithRecipient(int,
	 *      java.lang.String, org.openuss.security.acl.ObjectIdentity)
	 */
	@Override
	public List findPermissionsWithRecipient(final int transform, final java.lang.String queryString, final ObjectIdentity objectIdentity) {
		try {
			Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setParameter(0, objectIdentity);
			queryObject.setReadOnly(true);
			List results = queryObject.list();
			CollectionUtils.transform(results, new AclPermissionTransformer());
			return results;
		} catch (org.hibernate.HibernateException ex) {
			throw super.convertHibernateAccessException(ex);
		}
	}
	
	private static final class AclPermissionTransformer implements Transformer {
		public Object transform(Object input) {
			Object[] objs = (Object[]) input;
			return new AclPermissionAdapter((String)objs[0],(Integer)objs[1],(Long)objs[2],(Long)objs[3]);
		}
	}


}