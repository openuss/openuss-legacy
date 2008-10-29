package org.openuss.registration;

/**
 * <p>
 * The RegistrationKey is generated during the registration process of a new
 * user. To verify the user email adress. After the registration forms were
 * completed a registration key is generated and send to the users email adress.
 * Within the email is a link to submit the generated registration key. After
 * the user has submitted the key the user account is activated.
 * </p>
 */
public interface RegistrationService {

	/**
     * 
     */
	public String generateActivationCode(org.openuss.security.UserInfo user)
			throws org.openuss.registration.RegistrationException;

	/**
     * 
     */
	public String generateInstituteActivationCode(org.openuss.lecture.Institute institute)
			throws org.openuss.registration.RegistrationException;

	/**
	 * <p>
	 * Register a new user. The new user is asigned to by default to
	 * <code>ROLE_USER</code> and optional to <code>ROLE_ASSISTANT</code>.
	 * </p>
	 * <p>
	 * 
	 * @param user
	 *            a user object
	 *            </p>
	 *            <p>
	 * @param assistant
	 *            wether or not to asign the assistant role to the user
	 *            </p>
	 *            <p>
	 * @return String the registrationCode
	 *         </p>
	 */
	public void registrateUser(org.openuss.security.UserInfo user)
			throws org.openuss.registration.RegistrationException;

	/**
	 * <p>
	 * Activates the user that is associated to the given registration code.
	 * After calling the messaged, the registration code gets invalid.
	 * </p>
	 * <p>
	 * 
	 * @param code
	 *            the registration code
	 *            </p>
	 *            <p>
	 * @throws RegistrationCodeNotFoundException
	 *             </p>
	 */
	public void activateUserByCode(String code) throws org.openuss.registration.RegistrationCodeNotFoundException,
			org.openuss.registration.RegistrationCodeExpiredException;

	/**
     * 
     */
	public java.sql.Timestamp getCreateTime(String activationCode)
			throws org.openuss.registration.RegistrationCodeNotFoundException;

	/**
     * 
     */
	public org.openuss.security.UserInfo loginUserByActivationCode(String activationCode)
			throws org.openuss.registration.RegistrationCodeNotFoundException,
			org.openuss.registration.RegistrationCodeExpiredException;

	/**
     * 
     */
	public void activateInstituteByCode(String code) throws org.openuss.registration.RegistrationCodeNotFoundException,
			org.openuss.registration.RegistrationCodeExpiredException,
			org.openuss.registration.RegistrationParentDepartmentDisabledException;

	/**
     * 
     */
	public void removeInstituteCodes(org.openuss.lecture.Institute institute);

	/**
     * 
     */
	public String generateUserDeleteCode(org.openuss.security.UserInfo user);

	/**
     * 
     */
	public void generateDeleteUserCommand(String code)
			throws org.openuss.registration.RegistrationCodeNotFoundException,
			org.openuss.registration.RegistrationCodeExpiredException;

}
