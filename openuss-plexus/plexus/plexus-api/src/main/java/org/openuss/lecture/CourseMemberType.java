package org.openuss.lecture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ingo Dueppe 
 */
public class CourseMemberType implements java.io.Serializable {

	private static final long serialVersionUID = 4665136019911562969L;

	public static final CourseMemberType ASSISTANT = new CourseMemberType(0);

	public static final CourseMemberType PARTICIPANT = new CourseMemberType(1);

	public static final CourseMemberType ASPIRANT = new CourseMemberType(2);

	private Integer value;

	private CourseMemberType(Integer value) {
		this.value = value;
	}

	/**
	 * The default constructor allowing super classes to access it.
	 */
	protected CourseMemberType() {
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() {
		return String.valueOf(value);
	}

	/**
	 * Creates an instance of CourseMemberType from <code>value</code>.
	 * 
	 * @param value
	 *            the value to create the CourseMemberType from.
	 */
	public static CourseMemberType fromInteger(Integer value) {
		final CourseMemberType typeValue = values.get(value);
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
		return (this == that) ? 0 : this.getValue().compareTo(((CourseMemberType) that).getValue());
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
				|| (object instanceof CourseMemberType && ((CourseMemberType) object).getValue()
						.equals(this.getValue()));
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
		return CourseMemberType.fromInteger(this.value);
	}

	private static final Map<Integer, CourseMemberType> values = new HashMap<Integer, CourseMemberType>(3, 1);
	private static List<Integer> literals = new ArrayList<Integer>(3);
	private static List<String> names = new ArrayList<String>(3);

	/**
	 * Initializes the values.
	 */
	static {
		values.put(ASSISTANT.value, ASSISTANT);
		literals.add(ASSISTANT.value);
		names.add("ASSISTANT");
		values.put(PARTICIPANT.value, PARTICIPANT);
		literals.add(PARTICIPANT.value);
		names.add("PARTICIPANT");
		values.put(ASPIRANT.value, ASPIRANT);
		literals.add(ASPIRANT.value);
		names.add("ASPIRANT");
		literals = Collections.unmodifiableList(literals);
		names = Collections.unmodifiableList(names);
	}
}