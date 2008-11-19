/**
 * <strong>OpenUSS - Plexus</strong>
 * The next generation e-learning plattform
 *
 * University of Muenster (c)
 * 
 * Developing Period 2005 - 2006
 */
package org.openuss.test.foundation;

import java.util.Locale;

/**
 * @author isarsu
 * @see http://sourceforge.net/users/isarsu/
 *
 * Creation date: 21.06.2006
 * 
 * Mock Class for the UI foundation component
 */
public class UserUIMock {
	
	private String firstName;
	private String lastName;
	private String title;
	private String email;
	private String username;
	private String password;
	private Locale locale;
	private String address;
	private String city;
	private String postcode;
	private String country;
	private String telephone;
	
	public UserUIMock() {
		this.firstName = "firstNameTest";
		this.lastName = "lastNameTest";
		this.title = "titleTest";
		this.email = "firstNameTest.lastNameTest@test.org";
		this.username = "userNameTest";
		this.password = "passwordTest";
		this.locale = new Locale("en", "US");
		this.address = "addressTest";
		this.city = "cityTest";
		this.postcode = "postcodeTest";
		this.country = "countryTest";
		this.telephone = "telephoneNumberTest";
		
	}
	/**
	 * @return Returns the address.
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address The address to set.
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return Returns the city.
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city The city to set.
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return Returns the country.
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country The country to set.
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return Returns the firstName.
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName The firstName to set.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return Returns the lastName.
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName The lastName to set.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return Returns the locale.
	 */
	public Locale getLocale() {
		return locale;
	}
	/**
	 * @param locale The locale to set.
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return Returns the postCode.
	 */
	public String getPostcode() {
		return postcode;
	}
	/**
	 * @param postCode The postCode to set.
	 */
	public void setPostcode(String postCode) {
		this.postcode = postCode;
	}
	/**
	 * @return Returns the telephoneNumber.
	 */
	public String getTelephone() {
		return telephone;
	}
	/**
	 * @param telephoneNumber The telephoneNumber to set.
	 */
	public void setTelephone(String telephoneNumber) {
		this.telephone = telephoneNumber;
	}
	/**
	 * @return Returns the userName.
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param userName The userName to set.
	 */
	public void setUsername(String userName) {
		this.username = userName;
	}

}