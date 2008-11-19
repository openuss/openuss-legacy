package org.openuss.security.acegi.acl;

import java.lang.reflect.InvocationTargetException;

import org.acegisecurity.acl.basic.AclObjectIdentity;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.log4j.Logger;
import org.openuss.framework.utilities.DomainObjectUtility;
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
	 * object instance. The passed object must provide a <code>Long getId()</code>
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
			identifier = identifierFromObject(object);
		} catch (IllegalAccessException iae) {
			logger.error(iae);
			throw iae;
		}
	}
	
	public EntityObjectIdentity(Long identifier) {
		super();
		this.identifier = identifier;
	}

	private Long identifierFromObject(Object object) throws IllegalAccessException, InvocationTargetException {
		if (object instanceof ObjectIdentity) {
			return ((ObjectIdentity) object).getId();
		} else {
			return DomainObjectUtility.identifierFromObject(object);
		}
	}

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
		if (identifier != null) {
			return identifier.intValue();
		} else {
			return super.hashCode();
		}
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
