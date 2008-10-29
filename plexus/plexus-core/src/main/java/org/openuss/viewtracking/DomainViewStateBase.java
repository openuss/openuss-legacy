package org.openuss.viewtracking;

/**
 * @author Ingo Dueppe
 */
public abstract class DomainViewStateBase implements DomainViewState, java.io.Serializable {

	private static final long serialVersionUID = 5902500691971537682L;

	private org.openuss.viewtracking.DomainViewStatePK domainViewStatePk;

	public org.openuss.viewtracking.DomainViewStatePK getDomainViewStatePk() {
		return this.domainViewStatePk;
	}

	public void setDomainViewStatePk(org.openuss.viewtracking.DomainViewStatePK domainViewStatePk) {
		this.domainViewStatePk = domainViewStatePk;
	}

	private org.openuss.viewtracking.ViewState viewState;

	/**
	 * @see org.openuss.viewtracking.DomainViewState#getViewState()
	 */
	public org.openuss.viewtracking.ViewState getViewState() {
		return this.viewState;
	}

	/**
	 * @see org.openuss.viewtracking.DomainViewState#setViewState(org.openuss.viewtracking.ViewState
	 *      viewState)
	 */
	public void setViewState(org.openuss.viewtracking.ViewState viewState) {
		this.viewState = viewState;
	}

	/**
	 * Returns <code>true</code> if the argument is an DomainViewState instance
	 * and all identifiers for this entity equal the identifiers of the argument
	 * entity. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof DomainViewState)) {
			return false;
		}
		final DomainViewState that = (DomainViewState) object;
		if (this.domainViewStatePk == null || that.getDomainViewStatePk() == null
				|| !this.domainViewStatePk.equals(that.getDomainViewStatePk())) {
			return false;
		}
		return true;
	}

	/**
	 * Returns a hash code based on this entity's identifiers.
	 */
	public int hashCode() {
		int hashCode = 0;
		hashCode = 29 * hashCode + (domainViewStatePk == null ? 0 : domainViewStatePk.hashCode());

		return hashCode;
	}

}