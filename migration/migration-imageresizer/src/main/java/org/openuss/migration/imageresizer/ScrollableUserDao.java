package org.openuss.migration.imageresizer;

import java.sql.SQLException;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * 
 * User Scrollable Dao
 * 
 * @author Ingo Dueppe
 */
public class ScrollableUserDao extends HibernateDaoSupport {

	public ScrollableResults loadAllUsers() {
		return query("from org.openuss.security.UserImpl");
	}

	private ScrollableResults query(final String hql) {
		return (ScrollableResults) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setFetchSize(50);
				query.setCacheable(false);
				query.setFlushMode(FlushMode.MANUAL);
				return query.scroll(ScrollMode.FORWARD_ONLY);
			}
		});
	}

	public void evict(Object object) {
		if (object != null) {
			this.getSession().evict(object);
		}
	}

}
