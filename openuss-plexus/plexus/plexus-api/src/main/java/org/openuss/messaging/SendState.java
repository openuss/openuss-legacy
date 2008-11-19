package org.openuss.messaging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@author Ingo Dueppe 
 */
public class SendState implements java.io.Serializable {

	private static final long serialVersionUID = -712834625898000717L;

	public static final SendState TOSEND = new SendState(0);

	public static final SendState ERROR = new SendState(1);

	public static final SendState SEND = new SendState(2);

	private Integer value;

	private SendState(Integer value) {
		this.value = value;
	}

	protected SendState() {
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() {
		return String.valueOf(value);
	}

	/**
	 * Creates an instance of SendState from <code>value</code>.
	 * 
	 * @param value
	 *            the value to create the SendState from.
	 */
	public static SendState fromInteger(Integer value) {
		final SendState typeValue = values.get(value);
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
		return (this == that) ? 0 : this.getValue().compareTo(((SendState) that).getValue());
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
				|| (object instanceof SendState && ((SendState) object).getValue().equals(this.getValue()));
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
		return SendState.fromInteger(this.value);
	}

	private static final Map<Integer, SendState> values = new HashMap<Integer, SendState>(3, 1);
	private static List<Integer> literals = new ArrayList<Integer>(3);
	private static List<String> names = new ArrayList<String>(3);

	/**
	 * Initializes the values.
	 */
	static {
		values.put(TOSEND.value, TOSEND);
		literals.add(TOSEND.value);
		names.add("TOSEND");
		values.put(ERROR.value, ERROR);
		literals.add(ERROR.value);
		names.add("ERROR");
		values.put(SEND.value, SEND);
		literals.add(SEND.value);
		names.add("SEND");
		literals = Collections.unmodifiableList(literals);
		names = Collections.unmodifiableList(names);
	}
}