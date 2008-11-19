package org.openuss.lecture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ingo Dueppe  
 */
public class AccessType implements java.io.Serializable {

	private static final long serialVersionUID = -3211187938222074943L;

	public static final AccessType ANONYMOUS = new AccessType(0);

	/**
	 * The course can be accessed from every user of OpenUSS.
	 */
	public static final AccessType OPEN = new AccessType(1);

	/**
	 * The course is closed, only members of the institute can access it.
	 */
	public static final AccessType CLOSED = new AccessType(2);

	/**
	 * The user needs a password to access the course
	 */
	public static final AccessType PASSWORD = new AccessType(3);

	/**
	 * The user must be accepted by one of the assistants to access the course
	 */
	public static final AccessType APPLICATION = new AccessType(4);

	private Integer value;

	private AccessType(Integer value) {
		this.value = value;
	}

	/**
	 * The default constructor allowing super classes to access it.
	 */
	protected AccessType() {
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() {
		return String.valueOf(value);
	}

	/**
	 * Creates an instance of AccessType from <code>value</code>.
	 * 
	 * @param value
	 *            the value to create the AccessType from.
	 */
	public static AccessType fromInteger(Integer value) {
		final AccessType typeValue = values.get(value);
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
		return (this == that) ? 0 : this.getValue().compareTo(((AccessType) that).getValue());
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
				|| (object instanceof AccessType && ((AccessType) object).getValue().equals(this.getValue()));
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
		return AccessType.fromInteger(this.value);
	}

	private static final Map<Integer, AccessType> values = new HashMap<Integer, AccessType>(5, 1);
	private static List<Integer> literals = new ArrayList<Integer>(5);
	private static List<String> names = new ArrayList<String>(5);

	/**
	 * Initializes the values.
	 */
	static {
		values.put(ANONYMOUS.value, ANONYMOUS);
		literals.add(ANONYMOUS.value);
		names.add("ANONYMOUS");
		values.put(OPEN.value, OPEN);
		literals.add(OPEN.value);
		names.add("OPEN");
		values.put(CLOSED.value, CLOSED);
		literals.add(CLOSED.value);
		names.add("CLOSED");
		values.put(PASSWORD.value, PASSWORD);
		literals.add(PASSWORD.value);
		names.add("PASSWORD");
		values.put(APPLICATION.value, APPLICATION);
		literals.add(APPLICATION.value);
		names.add("APPLICATION");
		literals = Collections.unmodifiableList(literals);
		names = Collections.unmodifiableList(names);
	}
}