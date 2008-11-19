package org.openuss.viewtracking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 */
public class ViewState implements java.io.Serializable {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -8892383795463748196L;

	/**
     * 
     */
	public static final ViewState NEW = new ViewState(0);

	/**
     * 
     */
	public static final ViewState READ = new ViewState(1);

	/**
     * 
     */
	public static final ViewState MODIFIED = new ViewState(2);

	private Integer value;

	private ViewState(Integer value) {
		this.value = value;
	}

	/**
	 * The default constructor allowing super classes to access it.
	 */
	protected ViewState() {
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() {
		return String.valueOf(value);
	}

	/**
	 * Creates an instance of ViewState from <code>value</code>.
	 * 
	 * @param value
	 *            the value to create the ViewState from.
	 */
	public static ViewState fromInteger(Integer value) {
		final ViewState typeValue = values.get(value);
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
		return (this == that) ? 0 : this.getValue().compareTo(((ViewState) that).getValue());
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
				|| (object instanceof ViewState && ((ViewState) object).getValue().equals(this.getValue()));
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
		return ViewState.fromInteger(this.value);
	}

	private static final Map<Integer, ViewState> values = new HashMap<Integer, ViewState>(3, 1);
	private static List<Integer> literals = new ArrayList<Integer>(3);
	private static List<String> names = new ArrayList<String>(3);

	/**
	 * Initializes the values.
	 */
	static {
		values.put(NEW.value, NEW);
		literals.add(NEW.value);
		names.add("NEW");
		values.put(READ.value, READ);
		literals.add(READ.value);
		names.add("READ");
		values.put(MODIFIED.value, MODIFIED);
		literals.add(MODIFIED.value);
		names.add("MODIFIED");
		literals = Collections.unmodifiableList(literals);
		names = Collections.unmodifiableList(names);
	}
}