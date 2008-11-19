package org.openuss.messaging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ingo Dueppe 
 */
public class MailingStatus implements java.io.Serializable {

	private static final long serialVersionUID = 947461450693017273L;

	public static final MailingStatus PLANNED = new MailingStatus(1);

	public static final MailingStatus DRAFT = new MailingStatus(2);

	public static final MailingStatus INQUEUE = new MailingStatus(3);

	public static final MailingStatus SEND = new MailingStatus(4);

	public static final MailingStatus ERROR = new MailingStatus(5);

	private Integer value;

	private MailingStatus(Integer value) {
		this.value = value;
	}

	/**
	 * The default constructor allowing super classes to access it.
	 */
	protected MailingStatus() {
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() {
		return String.valueOf(value);
	}

	/**
	 * Creates an instance of MailingStatus from <code>value</code>.
	 * 
	 * @param value
	 *            the value to create the MailingStatus from.
	 */
	public static MailingStatus fromInteger(Integer value) {
		final MailingStatus typeValue = values.get(value);
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
		return (this == that) ? 0 : this.getValue().compareTo(((MailingStatus) that).getValue());
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
				|| (object instanceof MailingStatus && ((MailingStatus) object).getValue().equals(this.getValue()));
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
		return MailingStatus.fromInteger(this.value);
	}

	private static final Map<Integer, MailingStatus> values = new HashMap<Integer, MailingStatus>(5, 1);
	private static List<Integer> literals = new ArrayList<Integer>(5);
	private static List<String> names = new ArrayList<String>(5);

	/**
	 * Initializes the values.
	 */
	static {
		values.put(PLANNED.value, PLANNED);
		literals.add(PLANNED.value);
		names.add("PLANNED");
		values.put(DRAFT.value, DRAFT);
		literals.add(DRAFT.value);
		names.add("DRAFT");
		values.put(INQUEUE.value, INQUEUE);
		literals.add(INQUEUE.value);
		names.add("INQUEUE");
		values.put(SEND.value, SEND);
		literals.add(SEND.value);
		names.add("SEND");
		values.put(ERROR.value, ERROR);
		literals.add(ERROR.value);
		names.add("ERROR");
		literals = Collections.unmodifiableList(literals);
		names = Collections.unmodifiableList(names);
	}
}