package org.openuss.commands;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Ingo Dueppe
 * 
 */
public class CommandState implements Serializable {

	private static final long serialVersionUID = 5369750415994349294L;

	public static final CommandState EACH = new CommandState(0);

	public static final CommandState ONCE = new CommandState(1);

	public static final CommandState DONE = new CommandState(2);

	public static final CommandState ERROR = new CommandState(3);

	private Integer value;

	private CommandState(Integer value) {
		this.value = value;
	}

	/**
	 * The default constructor allowing super classes to access it.
	 */
	protected CommandState() {
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() {
		return String.valueOf(value);
	}

	/**
	 * Creates an instance of CommandState from <code>value</code>.
	 * 
	 * @param value
	 *            the value to create the CommandState from.
	 */
	public static CommandState fromInteger(Integer value) {
		final CommandState typeValue = values.get(value);
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
		return (this == that) ? 0 : this.getValue().compareTo(((CommandState) that).getValue());
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
				|| (object instanceof CommandState && ((CommandState) object).getValue().equals(this.getValue()));
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
		return CommandState.fromInteger(this.value);
	}

	private static final Map<Integer, CommandState> values = new HashMap<Integer, CommandState>(4, 1);
	private static List<Integer> literals = new ArrayList<Integer>(4);
	private static List<String> names = new ArrayList<String>(4);

	/**
	 * Initializes the values.
	 */
	static {
		values.put(EACH.value, EACH);
		literals.add(EACH.value);
		names.add("EACH");
		values.put(ONCE.value, ONCE);
		literals.add(ONCE.value);
		names.add("ONCE");
		values.put(DONE.value, DONE);
		literals.add(DONE.value);
		names.add("DONE");
		values.put(ERROR.value, ERROR);
		literals.add(ERROR.value);
		names.add("ERROR");
		literals = Collections.unmodifiableList(literals);
		names = Collections.unmodifiableList(names);
	}
}