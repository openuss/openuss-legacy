package org.openuss.security.acegi.acl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.acegisecurity.acl.basic.AclObjectIdentity;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.log4j.Logger;
import org.openuss.security.acl.ObjectIdentity;
import org.springframework.util.Assert;

/**
 * EntityObjectIdentity
 * 
 * This class mainly depends on the fact that each domain object is identified
 * by a global unique key. Two domain objects must not have the same identifier
 * even if they are different types.
 * 
 * So the domain object should use GUID or a global sequence.
 * 
 * @author Ingo Dueppe
 */
public class EntityObjectIdentity implements AclObjectIdentity {

	private static final Logger logger = Logger.getLogger(EntityObjectIdentity.class);

	private static final long serialVersionUID = -4665214585721654008L;

	private Long identifier;


	/**
	 * Creates the <code>EntityObjectIdentity</code> based on the passed
	 * object instance. The passed object must provide a <code>Integer getId()</code>
	 * method, otherwise an exception will be thrown.
	 * 
	 * @param object
	 *            the domain object instance to create an identity for
	 * 
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 */
	public EntityObjectIdentity(Object object) throws IllegalAccessException, InvocationTargetException {
		try {
			Assert.notNull(object, "object cannot be null");
			
		
//			this.classname = (getPackageName(object.getClass().getName()) == null) ? ClassUtils.getShortName(object
//					.getClass()) : (getPackageName(object.getClass().getName()) + "." + ClassUtils.getShortName(object
//					.getClass()));
	
			final Class clazz = object.getClass();
			
			if (object instanceof Long) {
				identifier = (Long) object;
				return;
			}
			
			if (object instanceof ObjectIdentity) {
				identifier = ((ObjectIdentity) object).getId();
			}
	
			try {
				final Method method = clazz.getMethod("getId", new Class[] {});
				final Object result = method.invoke(object, new Object[] {});
				this.identifier = (Long) result;
			} catch (ClassCastException ex) {
				throw new IllegalArgumentException("Object of class '" + clazz
						+ "' does not provide the required Integer getId() method: " + object);
			} catch (NoSuchMethodException nsme) {
				throw new IllegalArgumentException("Object of class '" + clazz
						+ "' does not provide the required Integer getId() method: " + object);
			}
		} catch (IllegalAccessException th) {
			logger.error(th);
			throw th;
		}
	}

//	private String getPackageName(String className) {
//		Assert.hasLength(className, "class name must not be empty");
//		int lastDotIndex = className.lastIndexOf(".");
//		if (lastDotIndex == -1) {
//			return null;
//		}
//		return className.substring(0, lastDotIndex);
//	}

	public Long getIdentifier() {
		return identifier;
	}

	public void setIdentifier(final Long identifier) {
		this.identifier = identifier;
	}

	/**
	 * <strong> Important for caching</strong> {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(1330891477, -1591801705).append(identifier).hashCode();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof EntityObjectIdentity)) {
			return false;
		}
		if (this == object) {
			return true;
		}
		final EntityObjectIdentity rhs = (EntityObjectIdentity) object;
		return new EqualsBuilder().append(identifier, rhs.getIdentifier()).isEquals();
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		final StringBuilder stringBuffer = new StringBuilder();
		stringBuffer.append(this.getClass().getName()).append('[');
		stringBuffer.append("Identity: ").append(this.identifier).append(']');

		return stringBuffer.toString();
	}

}
