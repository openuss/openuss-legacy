// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security;


/**
 * @see org.openuss.security.User
 * @author Ingo Dueppe
 */
public class UserDaoImpl extends org.openuss.security.UserDaoBase {
	
	@Override
	public Object create(int transform, User user) {
		// garantee that each username is lowercase
		user.setUsername(user.getUsername().toLowerCase());
		return super.create(transform, user);
	}

	/**
	 * @see org.openuss.security.UserDao#getPassword(int, java.lang.Long)
	 */
	@Override
	public java.lang.Object getPassword(final int transform, final java.lang.Long id) {
		return this.getPassword(transform, "select password from org.openuss.security.User as user where user.id = ?",	id);
	}

	/**
	 * @see org.openuss.security.UserDao#getPassword(int, java.lang.String,
	 *      java.lang.Long)
	 */
	@Override
	public java.lang.Object getPassword(final int transform, final java.lang.String queryString, final java.lang.Long id) {
		try {
			org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setParameter(0, id);
			java.util.Set results = new java.util.LinkedHashSet(queryObject.list());
			java.lang.Object result = null;
			if (results != null) {
				if (results.size() > 1) {
					throw new org.springframework.dao.InvalidDataAccessResourceUsageException(
							"More than one instance of 'java.lang.String" + "' was found when executing query --> '"
									+ queryString + "'");
				} else if (results.size() == 1) {
					result = results.iterator().next();
				}
			}
			return result;
		} catch (org.hibernate.HibernateException ex) {
			throw super.convertHibernateAccessException(ex);
		}
	}

	public User userInfoToEntity(UserInfo userInfo) {
		User user = loadUserFromUserInfo(userInfo);
		userInfoToEntity(userInfo, user, false);
		return user;
	}

	private User loadUserFromUserInfo(UserInfo userInfo) {
		User user = load(userInfo.getId());
		if (user == null) {
			user = User.Factory.newInstance();
		}
		return user;
	}

	@Override
	public void toUserInfo(org.openuss.security.User source, org.openuss.security.UserInfo target) {
		super.toUserInfo(source, target);
		// do not provide the users password hash code
		target.setPassword("[PROTECTED]");
		target.setFirstName(source.getFirstName());
		target.setLastName(source.getLastName());
	}

}