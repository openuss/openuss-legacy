package org.openuss.security.ldap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 */
public class LdapServerType implements java.io.Serializable {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 273086666309161980L;

	/**
     * 
     */
	public static final LdapServerType OTHER = new LdapServerType(1);

	/**
     * 
     */
	public static final LdapServerType ACTIVE_DIRECTORY = new LdapServerType(2);

	private Integer value;

	private LdapServerType(Integer value) {
		this.value = value;
	}

	/**
	 * The default constructor allowing super classes to access it.
	 */
	protected LdapServerType() {
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() {
		return String.valueOf(value);
	}

	/**
	 * Creates an instance of LdapServerType from <code>value</code>.
	 * 
	 * @param value
	 *            the value to create the LdapServerType from.
	 */
	public static LdapServerType fromInteger(Integer value) {
		final LdapServerType typeValue = values.get(value);
		if (typeValue == null) {
			throw new IllegalArgumentException("invalid value '" + value + "', possible values are: " + literals);
		}
		return typeValue;
	}

	/**
	 * Gets the underlying value of this type safe enumeration.
	 * 
	 * @return the underlying value.
	 */
	public Integer getValue() {
		return this.value;
	}

	/**
	 * @see Comparable#compareTo(Object)
	 */
	public int compareTo(Object that) {
		return (this == that) ? 0 : this.getValue().compareTo(((LdapServerType) that).getValue());
	}

	/**
	 * Returns an unmodifiable list containing the literals that are known by
	 * this enumeration.
	 * 
	 * @return A List containing the actual literals defined by this
	 *         enumeration, this list can not be modified.
	 */
	public static List<Integer> literals() {
		return literals;
	}

	/**
	 * Returns an unmodifiable list containing the names of the literals that
	 * are known by this enumeration.
	 * 
	 * @return A List containing the actual names of the literals defined by
	 *         this enumeration, this list can not be modified.
	 */
	public static List<String> names() {
		return names;
	}

	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) {
		return (this == object)
				|| (object instanceof LdapServerType && ((LdapServerType) object).getValue().equals(this.getValue()));
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() {
		return this.getValue().hashCode();
	}

	/**
	 * This method allows the deserialization of an instance of this enumeration
	 * type to return the actual instance that will be the singleton for the JVM
	 * in which the current thread is running. <p/> Doing this will allow users
	 * to safely use the equality operator <code>==</code> for enumerations
	 * because a regular deserialized object is always a newly constructed
	 * instance and will therefore never be an existing reference; it is this
	 * <code>readResolve()</code> method which will intercept the
	 * deserialization process in order to return the proper singleton
	 * reference. <p/> This method is documented here: <a href=
	 * "http://java.sun.com/j2se/1.3/docs/guide/serialization/spec/input.doc6.html"
	 * >Java Object Serialization Specification</a>
	 */
	private Object readResolve() throws java.io.ObjectStreamException {
		return LdapServerType.fromInteger(this.value);
	}

	private static final Map<Integer, LdapServerType> values = new HashMap<Integer, LdapServerType>(2, 1);
	private static List<Integer> literals = new ArrayList<Integer>(2);
	private static List<String> names = new ArrayList<String>(2);

	/**
	 * Initializes the values.
	 */
	static {
		values.put(OTHER.value, OTHER);
		literals.add(OTHER.value);
		names.add("OTHER");
		values.put(ACTIVE_DIRECTORY.value, ACTIVE_DIRECTORY);
		literals.add(ACTIVE_DIRECTORY.value);
		names.add("ACTIVE_DIRECTORY");
		literals = Collections.unmodifiableList(literals);
		names = Collections.unmodifiableList(names);
	}
}