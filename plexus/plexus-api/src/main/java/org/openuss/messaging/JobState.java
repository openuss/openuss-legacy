package org.openuss.messaging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ingo Dueppe 
 */
public class JobState implements java.io.Serializable {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -6889080896300128149L;

	/**
     * 
     */
	public static final JobState INQUEUE = new JobState(0);

	/**
     * 
     */
	public static final JobState DONE = new JobState(1);

	/**
     * 
     */
	public static final JobState ERROR = new JobState(2);

	private Integer value;

	private JobState(Integer value) {
		this.value = value;
	}

	/**
	 * The default constructor allowing super classes to access it.
	 */
	protected JobState() {
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() {
		return String.valueOf(value);
	}

	/**
	 * Creates an instance of JobState from <code>value</code>.
	 * 
	 * @param value
	 *            the value to create the JobState from.
	 */
	public static JobState fromInteger(Integer value) {
		final JobState typeValue = values.get(value);
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
		return (this == that) ? 0 : this.getValue().compareTo(((JobState) that).getValue());
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
				|| (object instanceof JobState && ((JobState) object).getValue().equals(this.getValue()));
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
		return JobState.fromInteger(this.value);
	}

	private static final Map<Integer, JobState> values = new HashMap<Integer, JobState>(3, 1);
	private static List<Integer> literals = new ArrayList<Integer>(3);
	private static List<String> names = new ArrayList<String>(3);

	/**
	 * Initializes the values.
	 */
	static {
		values.put(INQUEUE.value, INQUEUE);
		literals.add(INQUEUE.value);
		names.add("INQUEUE");
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