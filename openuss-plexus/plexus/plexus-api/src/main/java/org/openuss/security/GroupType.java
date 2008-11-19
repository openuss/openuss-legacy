package org.openuss.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Defines the different group types known by the system
 * </p>
 */
public class GroupType implements java.io.Serializable {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 5128510888639700225L;

	/**
	 * <p>
	 * Undefined usertype
	 * </p>
	 */
	public static final GroupType UNDEFINED = new GroupType(-1);

	/**
	 * <p>
	 * Groups that are created explicit by the user are called user defined.
	 * </p>
	 */
	public static final GroupType USERDEFINED = new GroupType(0);

	/**
	 * <p>
	 * Groups representing ROLES are marked as ROLE group type
	 * </p>
	 */
	public static final GroupType ROLE = new GroupType(1);

	/**
	 * <p>
	 * Automatically created groups with administrator rights.
	 * </p>
	 */
	public static final GroupType ADMINISTRATOR = new GroupType(2);

	/**
	 * <p>
	 * Automatically created groups with assistant rights.
	 * </p>
	 */
	public static final GroupType ASSISTANT = new GroupType(3);

	/**
	 * <p>
	 * Automatically created groups with tutor rights.
	 * </p>
	 */
	public static final GroupType TUTOR = new GroupType(4);

	/**
	 * <p>
	 * Automatically created groups with participants
	 * </p>
	 */
	public static final GroupType PARTICIPANT = new GroupType(5);

	private Integer value;

	private GroupType(Integer value) {
		this.value = value;
	}

	/**
	 * The default constructor allowing super classes to access it.
	 */
	protected GroupType() {
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() {
		return String.valueOf(value);
	}

	/**
	 * Creates an instance of GroupType from <code>value</code>.
	 * 
	 * @param value
	 *            the value to create the GroupType from.
	 */
	public static GroupType fromInteger(Integer value) {
		final GroupType typeValue = values.get(value);
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
		return (this == that) ? 0 : this.getValue().compareTo(((GroupType) that).getValue());
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
				|| (object instanceof GroupType && ((GroupType) object).getValue().equals(this.getValue()));
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
		return GroupType.fromInteger(this.value);
	}

	private static final Map<Integer, GroupType> values = new HashMap<Integer, GroupType>(7, 1);
	private static List<Integer> literals = new ArrayList<Integer>(7);
	private static List<String> names = new ArrayList<String>(7);

	/**
	 * Initializes the values.
	 */
	static {
		values.put(UNDEFINED.value, UNDEFINED);
		literals.add(UNDEFINED.value);
		names.add("UNDEFINED");
		values.put(USERDEFINED.value, USERDEFINED);
		literals.add(USERDEFINED.value);
		names.add("USERDEFINED");
		values.put(ROLE.value, ROLE);
		literals.add(ROLE.value);
		names.add("ROLE");
		values.put(ADMINISTRATOR.value, ADMINISTRATOR);
		literals.add(ADMINISTRATOR.value);
		names.add("ADMINISTRATOR");
		values.put(ASSISTANT.value, ASSISTANT);
		literals.add(ASSISTANT.value);
		names.add("ASSISTANT");
		values.put(TUTOR.value, TUTOR);
		literals.add(TUTOR.value);
		names.add("TUTOR");
		values.put(PARTICIPANT.value, PARTICIPANT);
		literals.add(PARTICIPANT.value);
		names.add("PARTICIPANT");
		literals = Collections.unmodifiableList(literals);
		names = Collections.unmodifiableList(names);
	}
}