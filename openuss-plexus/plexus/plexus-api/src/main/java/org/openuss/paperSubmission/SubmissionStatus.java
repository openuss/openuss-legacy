package org.openuss.paperSubmission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Enumeration which identifies the PaperSubmission with the values:
 * IN_TIME
 * NOT_IN_TIME
 * NOT_SUBMITTED
 * 
 * @author Projektseminar WS 07/08, Team Collaboration
 */
public class SubmissionStatus implements java.io.Serializable {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -6630696293673084599L;

	public static final SubmissionStatus IN_TIME = new SubmissionStatus(1);

	public static final SubmissionStatus NOT_IN_TIME = new SubmissionStatus(2);

	public static final SubmissionStatus NOT_SUBMITTED = new SubmissionStatus(3);

	private Integer value;

	private SubmissionStatus(Integer value) {
		this.value = value;
	}

	/**
	 * The default constructor allowing super classes to access it.
	 */
	protected SubmissionStatus() {
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() {
		return String.valueOf(value);
	}

	/**
	 * Creates an instance of SubmissionStatus from <code>value</code>.
	 * 
	 * @param value
	 *            the value to create the SubmissionStatus from.
	 */
	public static SubmissionStatus fromInteger(Integer value) {
		final SubmissionStatus typeValue = values.get(value);
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
		return (this == that) ? 0 : this.getValue().compareTo(((SubmissionStatus) that).getValue());
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
				|| (object instanceof SubmissionStatus && ((SubmissionStatus) object).getValue()
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
		return SubmissionStatus.fromInteger(this.value);
	}

	private static final Map<Integer, SubmissionStatus> values = new HashMap<Integer, SubmissionStatus>(3, 1);
	private static List<Integer> literals = new ArrayList<Integer>(3);
	private static List<String> names = new ArrayList<String>(3);

	/**
	 * Initializes the values.
	 */
	static {
		values.put(IN_TIME.value, IN_TIME);
		literals.add(IN_TIME.value);
		names.add("IN_TIME");
		values.put(NOT_IN_TIME.value, NOT_IN_TIME);
		literals.add(NOT_IN_TIME.value);
		names.add("NOT_IN_TIME");
		values.put(NOT_SUBMITTED.value, NOT_SUBMITTED);
		literals.add(NOT_SUBMITTED.value);
		names.add("NOT_SUBMITTED");
		literals = Collections.unmodifiableList(literals);
		names = Collections.unmodifiableList(names);
	}
}