package org.openuss.wiki;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>WikiSite</code>.
 * 
 * @see WikiSite
 */
public abstract class WikiSiteDaoBase extends HibernateDaoSupport implements WikiSiteDao {

	/**
	 * @see WikiSiteDao#load(int, java.lang.Long)
	 */
	public Object load(final int transform, final Long id) {
		if (id == null) {
			throw new IllegalArgumentException("WikiSite.load - 'id' can not be null");
		}

		final Object entity = this.getHibernateTemplate().get(WikiSiteImpl.class, id);
		return transformEntity(transform, (WikiSite) entity);
	}

	/**
	 * @see WikiSiteDao#load(Long)
	 */
	public WikiSite load(Long id) {
		return (WikiSite) this.load(TRANSFORM_NONE, id);
	}

	/**
	 * @see WikiSiteDao#loadAll()
	 */
	public Collection<WikiSite> loadAll() {
		return this.loadAll(TRANSFORM_NONE);
	}

	/**
	 * @see WikiSiteDao#loadAll(int)
	 */
	public Collection<WikiSite> loadAll(final int transform) {
		final Collection<WikiSite> results = this.getHibernateTemplate().loadAll(WikiSiteImpl.class);
		this.transformEntities(transform, results);
		return results;
	}

	/**
	 * @see WikiSiteDao#create(WikiSite)
	 */
	public WikiSite create(WikiSite wikiSite) {
		return (WikiSite) this.create(TRANSFORM_NONE, wikiSite);
	}

	/**
	 * @see WikiSiteDao#create(int transform, WikiSite)
	 */
	public Object create(final int transform, final WikiSite wikiSite) {
		if (wikiSite == null) {
			throw new IllegalArgumentException("WikiSite.create - 'wikiSite' can not be null");
		}
		this.getHibernateTemplate().save(wikiSite);
		return this.transformEntity(transform, wikiSite);
	}

	/**
	 * @see WikiSiteDao#create(Collection<org.openuss. wiki.WikiSite>)
	 */
	@SuppressWarnings( { "unchecked" })
	public Collection<WikiSite> create(final Collection<WikiSite> entities) {
		return create(TRANSFORM_NONE, entities);
	}

	/**
	 * @see WikiSiteDao#create(int, Collection<WikiSite>)
	 */
	public Collection create(final int transform, final Collection<WikiSite> entities) {
		if (entities == null) {
			throw new IllegalArgumentException("WikiSite.create - 'entities' can not be null");
		}
		this.getHibernateTemplate().execute(new org.springframework.orm.hibernate3.HibernateCallback() {
			public Object doInHibernate(org.hibernate.Session session) throws org.hibernate.HibernateException {
				for (Iterator entityIterator = entities.iterator(); entityIterator.hasNext();) {
					create(transform, (WikiSite) entityIterator.next());
				}
				return null;
			}
		}, true);
		return entities;
	}

	/**
	 * @see WikiSiteDao#update(WikiSite)
	 */
	public void update(WikiSite wikiSite) {
		if (wikiSite == null) {
			throw new IllegalArgumentException("WikiSite.update - 'wikiSite' can not be null");
		}
		try {
			this.getHibernateTemplate().update(wikiSite);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException " + ex.getCause().getMessage());
				getSession().merge(wikiSite);
			} else {
				throw ex;
			}
		}
	}

	/**
	 * @see WikiSiteDao#update(Collection<org.openuss. wiki.WikiSite>)
	 */
	public void update(final Collection<WikiSite> entities) {
		if (entities == null) {
			throw new IllegalArgumentException("WikiSite.update - 'entities' can not be null");
		}
		this.getHibernateTemplate().execute(new org.springframework.orm.hibernate3.HibernateCallback() {
			public Object doInHibernate(org.hibernate.Session session) throws org.hibernate.HibernateException {
				for (Iterator entityIterator = entities.iterator(); entityIterator.hasNext();) {
					update((WikiSite) entityIterator.next());
				}
				return null;
			}
		}, true);
	}

	/**
	 * @see WikiSiteDao#remove(WikiSite)
	 */
	public void remove(WikiSite wikiSite) {
		if (wikiSite == null) {
			throw new IllegalArgumentException("WikiSite.remove - 'wikiSite' can not be null");
		}
		this.getHibernateTemplate().delete(wikiSite);
	}

	/**
	 * @see WikiSiteDao#remove(Long)
	 */
	public void remove(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("WikiSite.remove - 'id can not be null");
		}
		WikiSite entity = this.load(id);
		if (entity != null) {
			this.remove(entity);
		}
	}

	/**
	 * @see WikiSiteDao#remove(Collection<org.openuss. wiki.WikiSite>)
	 */
	public void remove(Collection<WikiSite> entities) {
		if (entities == null) {
			throw new IllegalArgumentException("WikiSite.remove - 'entities' can not be null");
		}
		this.getHibernateTemplate().deleteAll(entities);
	}

	/**
	 * @see WikiSiteDao#findByDomainId(Long)
	 */
	public List findByDomainId(Long domainId) {
		return this.findByDomainId(TRANSFORM_NONE, domainId);
	}

	/**
	 * @see WikiSiteDao#findByDomainId(String, Long)
	 */
	public List findByDomainId(final String queryString, final Long domainId) {
		return this.findByDomainId(TRANSFORM_NONE, queryString, domainId);
	}

	/**
	 * @see WikiSiteDao#findByDomainId(int, Long)
	 */
	public List findByDomainId(final int transform, final Long domainId) {
		return this.findByDomainId(transform, "from org.openuss.wiki.WikiSite as wikiSite where wikiSite.domainId = ?", domainId);
	}

	/**
	 * @see WikiSiteDao#findByDomainId(int, String, Long)
	 */
	public List findByDomainId(final int transform, final String queryString, final Long domainId) {
		try {
			org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
			queryObject.setParameter(0, domainId);
			List results = queryObject.list();
			transformEntities(transform, results);
			return results;
		} catch (org.hibernate.HibernateException ex) {
			throw super.convertHibernateAccessException(ex);
		}
	}

	/**
	 * @see WikiSiteDao#findByDomainIdAndName(Long, String)
	 */
	public WikiSite findByDomainIdAndName(Long domainId, String name) {
		return (WikiSite) this.findByDomainIdAndName(TRANSFORM_NONE, domainId, name);
	}

	/**
	 * @see WikiSiteDao#findByDomainIdAndName(String, Long, String)
	 */
	public WikiSite findByDomainIdAndName(final String queryString, final Long domainId, final String name) {
		return (WikiSite) this.findByDomainIdAndName(TRANSFORM_NONE, queryString, domainId, name);
	}

	/**
	 * @see WikiSiteDao#findByDomainIdAndName(int, Long, String)
	 */
	public Object findByDomainIdAndName(final int transform, final Long domainId, final String name) {
		return this.findByDomainIdAndName(transform,
				"from org.openuss.wiki.WikiSite as wikiSite where wikiSite.domainId = ? and wikiSite.name = ?", domainId, name);
	}

	/**
	 * @see WikiSiteDao#findByDomainIdAndName(int, String, Long, String)
	 */
	@SuppressWarnings("unchecked")
	public Object findByDomainIdAndName(final int transform, final String queryString, final Long domainId,
			final String name) {
		try {
			org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
			queryObject.setParameter(0, domainId);
			queryObject.setParameter(1, name);
			Set results = new LinkedHashSet(queryObject.list());
			Object result = null;
			if (results != null) {
				if (results.size() > 1) {
					throw new org.springframework.dao.InvalidDataAccessResourceUsageException(
							"More than one instance of 'WikiSite" + "' was found when executing query --> '"
									+ queryString + "'");
				} else if (results.size() == 1) {
					result = results.iterator().next();
				}
			}
			result = transformEntity(transform, (WikiSite) result);
			return result;
		} catch (org.hibernate.HibernateException ex) {
			throw super.convertHibernateAccessException(ex);
		}
	}

	/**
	 * Allows transformation of entities into value objects (or something else
	 * for that matter), when the <code>transform</code> flag is set to one of
	 * the constants defined in <code>WikiSiteDao</code>, please note that the
	 * {@link #TRANSFORM_NONE} constant denotes no transformation, so the entity
	 * itself will be returned. <p/> This method will return instances of these
	 * types:
	 * <ul>
	 * <li>{@link WikiSite} - {@link #TRANSFORM_NONE}</li>
	 * <li>{@link WikiSiteInfo} - {@link TRANSFORM_WIKISITEINFO}</li>
	 * </ul>
	 * 
	 * If the integer argument value is unknown {@link #TRANSFORM_NONE} is
	 * assumed.
	 * 
	 * @param transform
	 *            one of the constants declared in {@link WikiSiteDao}
	 * @param entity
	 *            an entity that was found
	 * @return the transformed entity (i.e. new value object, etc)
	 * @see #transformEntities(int,Collection)
	 */
	protected Object transformEntity(final int transform, final WikiSite entity) {
		Object target = null;
		if (entity != null) {
			switch (transform) {
			case TRANSFORM_WIKISITEINFO:
				target = toWikiSiteInfo(entity);
				break;
			case TRANSFORM_NONE: // fall-through
			default:
				target = entity;
			}
		}
		return target;
	}

	/**
	 * Transforms a collection of entities using the
	 * {@link #transformEntity(int,WikiSite)} method. This method does not
	 * instantiate a new collection. <p/> This method is to be used internally
	 * only.
	 * 
	 * @param transform
	 *            one of the constants declared in <code>WikiSiteDao</code>
	 * @param entities
	 *            the collection of entities to transform
	 * @see #transformEntity(int,WikiSite)
	 */
	protected void transformEntities(final int transform, final Collection entities) {
		switch (transform) {
		case TRANSFORM_WIKISITEINFO:
			toWikiSiteInfoCollection(entities);
			break;
		case TRANSFORM_NONE: // fall-through
		default:
			// do nothing;
		}
	}

	/**
	 * @see WikiSiteDao#toWikiSiteInfoCollection(Collection)
	 */
	public final void toWikiSiteInfoCollection(Collection entities) {
		if (entities != null) {
			org.apache.commons.collections.CollectionUtils.transform(entities, WIKISITEINFO_TRANSFORMER);
		}
	}

	/**
	 * Default implementation for transforming the results of a report query
	 * into a value object. This implementation exists for convenience reasons
	 * only. It needs only be overridden in the {@link WikiSiteDaoImpl} class if
	 * you intend to use reporting queries.
	 * 
	 * @see WikiSiteDao#toWikiSiteInfo(WikiSite)
	 */
	protected WikiSiteInfo toWikiSiteInfo(Object[] row) {
		WikiSiteInfo target = null;
		if (row != null) {
			final int numberOfObjects = row.length;
			for (int ctr = 0; ctr < numberOfObjects; ctr++) {
				final Object object = row[ctr];
				if (object instanceof WikiSite) {
					target = this.toWikiSiteInfo((WikiSite) object);
					break;
				}
			}
		}
		return target;
	}

	/**
	 * This anonymous transformer is designed to transform entities or report
	 * query results (which result in an array of objects) to
	 * {@link WikiSiteInfo} using the Jakarta Commons-Collections Transformation
	 * API.
	 */
	private org.apache.commons.collections.Transformer WIKISITEINFO_TRANSFORMER = new org.apache.commons.collections.Transformer() {
		public Object transform(Object input) {
			Object result = null;
			if (input instanceof WikiSite) {
				result = toWikiSiteInfo((WikiSite) input);
			} else if (input instanceof Object[]) {
				result = toWikiSiteInfo((Object[]) input);
			}
			return result;
		}
	};

	/**
	 * @see WikiSiteDao#wikiSiteInfoToEntityCollection(Collection)
	 */
	public final void wikiSiteInfoToEntityCollection(Collection instances) {
		if (instances != null) {
			for (final Iterator iterator = instances.iterator(); iterator.hasNext();) {
				// - remove an objects that are null or not of the correct
				// instance
				if (!(iterator.next() instanceof WikiSiteInfo)) {
					iterator.remove();
				}
			}
			org.apache.commons.collections.CollectionUtils.transform(instances, WikiSiteInfoToEntityTransformer);
		}
	}

	private final org.apache.commons.collections.Transformer WikiSiteInfoToEntityTransformer = new org.apache.commons.collections.Transformer() {
		public Object transform(Object input) {
			return wikiSiteInfoToEntity((WikiSiteInfo) input);
		}
	};

	/**
	 * @see WikiSiteDao#toWikiSiteInfo(WikiSite, WikiSiteInfo)
	 */
	public void toWikiSiteInfo(WikiSite source, WikiSiteInfo target) {
		target.setId(source.getId());
		target.setDomainId(source.getDomainId());
		target.setName(source.getName());
		target.setDeleted((source.getDeleted() == null ? false : source.getDeleted().booleanValue()));
		target.setReadOnly((source.getReadOnly() == null ? false : source.getReadOnly().booleanValue()));
	}

	/**
	 * @see WikiSiteDao#toWikiSiteInfo(WikiSite)
	 */
	public WikiSiteInfo toWikiSiteInfo(final WikiSite entity) {
		final WikiSiteInfo target = new WikiSiteInfo();
		this.toWikiSiteInfo(entity, target);
		return target;
	}

	/**
	 * @see WikiSiteDao#wikiSiteInfoToEntity(WikiSiteInfo, WikiSite)
	 */
	public void wikiSiteInfoToEntity(WikiSiteInfo source, WikiSite target, boolean copyIfNull) {
		if (copyIfNull || source.getDomainId() != null) {
			target.setDomainId(source.getDomainId());
		}
		if (copyIfNull || source.getName() != null) {
			target.setName(source.getName());
		}
		target.setDeleted(new Boolean(source.isDeleted()));
		target.setReadOnly(new Boolean(source.isReadOnly()));
	}

}