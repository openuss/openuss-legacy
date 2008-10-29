package org.openuss.security;

/**
 * <p>
 * 
 * @author Ron Haus
 *         </p>
 *         <p>
 * @author Florian Dondorf
 *         </p>
 */
public interface MembershipService {

	/**
     * 
     */
	public void addMember(org.openuss.security.Membership membership, org.openuss.security.User user);

	/**
     * 
     */
	public void removeMember(org.openuss.security.Membership membership, org.openuss.security.User user);

	/**
     * 
     */
	public void addAspirant(org.openuss.security.Membership membership, org.openuss.security.User user);

	/**
     * 
     */
	public void acceptAspirant(org.openuss.security.Membership membership, org.openuss.security.User user);

	/**
     * 
     */
	public void rejectAspirant(org.openuss.security.Membership membership, org.openuss.security.User user);

	/**
     * 
     */
	public org.openuss.security.Group createGroup(org.openuss.security.Membership membership,
			org.openuss.security.Group group);

	/**
     * 
     */
	public void removeGroup(org.openuss.security.Membership membership, org.openuss.security.Group group);

	/**
	 * <p>
	 * Clears the Membership instance by removing its Groups and releases all
	 * its Members and Aspirants.
	 * </p>
	 * <p>
	 * 
	 * @param membership
	 *            Membership that is to be cleared
	 *            </p>
	 */
	public void clearMembership(org.openuss.security.Membership membership);

}
