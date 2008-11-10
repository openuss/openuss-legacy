// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.dao.InvalidDataAccessResourceUsageException;

/**
 * @see org.openuss.security.User
 * @author Ingo Dueppe
 */
public class UserDaoImpl extends UserDaoBase {

	/**
	 * @see org.openuss.security.UserDao#getPassword(int, java.lang.Long)
	 */
	@Override
	public Object getPassword(final int transform, final Long id) {
		return this.getPassword(transform, "select password from org.openuss.security.User as user where user.id = ?",
				id);
	}

	/**
	 * @see org.openuss.security.UserDao#getPassword(int, java.lang.String,
	 *      java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getPassword(final int transform, final String queryString, final Long id) {
		try {
			Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setParameter(0, id);
			Set results = new LinkedHashSet(queryObject.list());
			Object result = null;
			if (results != null) {
				if (results.size() > 1) {
					throw new InvalidDataAccessResourceUsageException("More than one instance of 'java.lang.String"
							+ "' was found when executing query --> '" + queryString + "'");
				} else if (results.size() == 1) {
					result = results.iterator().next();
				}
			}
			return result;
		} catch (HibernateException ex) {
			throw super.convertHibernateAccessException(ex);
		}
	}

	public User userInfoToEntity(UserInfo userInfo) {
		User user = loadUserFromUserInfo(userInfo);
		userInfoToEntity(userInfo, user, false);
		return user;
	}

	private User loadUserFromUserInfo(UserInfo userInfo) {
		if (userInfo.getId() == null) {
			return User.Factory.newInstance();
		}
		User user = load(userInfo.getId());
		if (user == null) {
			user = User.Factory.newInstance();
		}
		return user;
	}

	@Override
	public void userInfoToEntity(UserInfo source, User target, boolean copyIfNull) {
		// prevent for override the password
		if (StringUtils.isNotBlank(target.getPassword())) {
			source.setPassword(target.getPassword());
		}
		if (copyIfNull || source.getUsername() != null) {
			target.setUsername(source.getUsername());
		}
		super.userInfoToEntity(source, target, copyIfNull);
		// delete user image id if needed
		target.setImageId(source.getImageId());		
	}

	@Override
	public void toUserInfo(User source, UserInfo target) {
		super.toUserInfo(source, target);
//		if (source.isCentralUser()) {
//			target.setUsername(SecurityDomainUtility.extractUsername(source.getUsername()));
//		} else {
//		}
		target.setUsername(source.getUsername());
		target.setDisplayName(source.getDisplayName());
		target.setCentralUser(source.isCentralUser());
		target.setDomainName(SecurityDomainUtility.extractDomain(source.getUsername()));
	}

}