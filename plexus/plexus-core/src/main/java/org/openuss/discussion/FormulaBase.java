package org.openuss.discussion;

/**
 * @author Ingo Dueppe
 */
public abstract class FormulaBase implements Formula, java.io.Serializable {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -7577954679775829433L;

	private java.lang.Long id;

	/**
	 * @see org.openuss.discussion.Formula#getId()
	 */
	public java.lang.Long getId() {
		return this.id;
	}

	/**
	 * @see org.openuss.discussion.Formula#setId(java.lang.Long id)
	 */
	public void setId(java.lang.Long id) {
		this.id = id;
	}

	private java.lang.String formula;

	/**
	 * @see org.openuss.discussion.Formula#getFormula()
	 */
	public java.lang.String getFormula() {
		return this.formula;
	}

	/**
	 * @see org.openuss.discussion.Formula#setFormula(java.lang.String formula)
	 */
	public void setFormula(java.lang.String formula) {
		this.formula = formula;
	}

	/**
	 * Returns <code>true</code> if the argument is an Formula instance and all
	 * identifiers for this entity equal the identifiers of the argument entity.
	 * Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof Formula)) {
			return false;
		}
		final Formula that = (Formula) object;
		if (this.id == null || that.getId() == null || !this.id.equals(that.getId())) {
			return false;
		}
		return true;
	}

	/**
	 * Returns a hash code based on this entity's identifiers.
	 */
	public int hashCode() {
		int hashCode = 0;
		hashCode = 29 * hashCode + (id == null ? 0 : id.hashCode());
		return hashCode;
	}

}