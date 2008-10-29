package org.openuss.viewtracking;

/**
 * @author Ingo Dueppe
 */
public interface DomainViewState {

	public org.openuss.viewtracking.DomainViewStatePK getDomainViewStatePk();

	public void setDomainViewStatePk(org.openuss.viewtracking.DomainViewStatePK domainViewStatePk);

	public org.openuss.viewtracking.ViewState getViewState();

	public void setViewState(org.openuss.viewtracking.ViewState viewState);

}