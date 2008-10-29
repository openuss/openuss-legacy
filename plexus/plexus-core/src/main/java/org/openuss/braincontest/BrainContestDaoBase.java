package org.openuss.braincontest;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.braincontest.BrainContest</code>.
 * </p>
 * 
 * @see org.openuss.braincontest.BrainContest
 */
public abstract class BrainContestDaoBase extends HibernateDaoSupport implements BrainContestDao {

	/**
	 * @see org.openuss.braincontest.BrainContestDao#load(int, java.lang.Long)
	 */
	public java.lang.Object load(final int transform, final java.lang.Long id) {
		if (id == null) {
			throw new IllegalArgumentException("BrainContest.load - 'id' can not be null");
		}

		final java.lang.Object entity = this.getHibernateTemplate().get(
				org.openuss.braincontest.BrainContestImpl.class, id);
		return transformEntity(transform, (org.openuss.braincontest.BrainContest) entity);
	}

	/**
	 * @see org.openuss.braincontest.BrainContestDao#load(java.lang.Long)
	 */
	public org.openuss.braincontest.BrainContest load(java.lang.Long id) {
		return (org.openuss.braincontest.BrainContest) this.load(TRANSFORM_NONE, id);
	}

	/**
	 * @see org.openuss.braincontest.BrainContestDao#loadAll()
	 */
	@SuppressWarnings( { "unchecked" })
	public java.util.Collection<org.openuss.braincontest.BrainContest> loadAll() {
		return this.loadAll(TRANSFORM_NONE);
	}

	/**
	 * @see org.openuss.braincontest.BrainContestDao#loadAll(int)
	 */
	public java.util.Collection loadAll(final int transform) {
		final java.util.Collection results = this.getHibernateTemplate().loadAll(
				org.openuss.braincontest.BrainContestImpl.class);
		this.transformEntities(transform, results);
		return results;
	}

	/**
	 * @see org.openuss.braincontest.BrainContestDao#create(org.openuss.braincontest.BrainContest)
	 */
	public org.openuss.braincontest.BrainContest create(org.openuss.braincontest.BrainContest brainContest) {
		return (org.openuss.braincontest.BrainContest) this.create(TRANSFORM_NONE, brainContest);
	}

	/**
	 * @see org.openuss.braincontest.BrainContestDao#create(int transform,
	 *      org.openuss.braincontest.BrainContest)
	 */
	public java.lang.Object create(final int transform, final org.openuss.braincontest.BrainContest brainContest) {
		if (brainContest == null) {
			throw new IllegalArgumentException("BrainContest.create - 'brainContest' can not be null");
		}
		this.getHibernateTemplate().save(brainContest);
		return this.transformEntity(transform, brainContest);
	}

	/**
	 * @see 
	 *      org.openuss.braincontest.BrainContestDao#create(java.util.Collection<
	 *      org.openuss.braincontest.BrainContest>)
	 */
	@SuppressWarnings( { "unchecked" })
	public java.util.Collection<org.openuss.braincontest.BrainContest> create(
			final java.util.Collection<org.openuss.braincontest.BrainContest> entities) {
		return create(TRANSFORM_NONE, entities);
	}

	/**
	 * @see org.openuss.braincontest.BrainContestDao#create(int,
	 *      java.util.Collection<org.openuss.braincontest.BrainContest>)
	 */
	public java.util.Collection create(final int transform,
			final java.util.Collection<org.openuss.braincontest.BrainContest> entities) {
		if (entities == null) {
			throw new IllegalArgumentException("BrainContest.create - 'entities' can not be null");
		}
		this.getHibernateTemplate().execute(new org.springframework.orm.hibernate3.HibernateCallback() {
			public java.lang.Object doInHibernate(org.hibernate.Session session)
					throws org.hibernate.HibernateException {
				for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();) {
					create(transform, (org.openuss.braincontest.BrainContest) entityIterator.next());
				}
				return null;
			}
		}, true);
		return entities;
	}

	/**
	 * @see org.openuss.braincontest.BrainContestDao#update(org.openuss.braincontest.BrainContest)
	 */
	public void update(org.openuss.braincontest.BrainContest brainContest) {
		if (brainContest == null) {
			throw new IllegalArgumentException("BrainContest.update - 'brainContest' can not be null");
		}
		try {
			this.getHibernateTemplate().update(brainContest);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException " + ex.getCause().getMessage());
				getSession().merge(brainContest);
			} else {
				throw ex;
			}
		}
	}

	/**
	 * @see 
	 *      org.openuss.braincontest.BrainContestDao#update(java.util.Collection<
	 *      org.openuss.braincontest.BrainContest>)
	 */
	public void update(final java.util.Collection<org.openuss.braincontest.BrainContest> entities) {
		if (entities == null) {
			throw new IllegalArgumentException("BrainContest.update - 'entities' can not be null");
		}
		this.getHibernateTemplate().execute(new org.springframework.orm.hibernate3.HibernateCallback() {
			public java.lang.Object doInHibernate(org.hibernate.Session session)
					throws org.hibernate.HibernateException {
				for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();) {
					update((org.openuss.braincontest.BrainContest) entityIterator.next());
				}
				return null;
			}
		}, true);
	}

	/**
	 * @see org.openuss.braincontest.BrainContestDao#remove(org.openuss.braincontest.BrainContest)
	 */
	public void remove(org.openuss.braincontest.BrainContest brainContest) {
		if (brainContest == null) {
			throw new IllegalArgumentException("BrainContest.remove - 'brainContest' can not be null");
		}
		this.getHibernateTemplate().delete(brainContest);
	}

	/**
	 * @see org.openuss.braincontest.BrainContestDao#remove(java.lang.Long)
	 */
	public void remove(java.lang.Long id) {
		if (id == null) {
			throw new IllegalArgumentException("BrainContest.remove - 'id can not be null");
		}
		org.openuss.braincontest.BrainContest entity = this.load(id);
		if (entity != null) {
			this.remove(entity);
		}
	}

	/**
	 * @see 
	 *      org.openuss.braincontest.BrainContestDao#remove(java.util.Collection<
	 *      org.openuss.braincontest.BrainContest>)
	 */
	public void remove(java.util.Collection<org.openuss.braincontest.BrainContest> entities) {
		if (entities == null) {
			throw new IllegalArgumentException("BrainContest.remove - 'entities' can not be null");
		}
		this.getHibernateTemplate().deleteAll(entities);
	}

	/**
	 * @see org.openuss.braincontest.BrainContestDao#findByDomainObject(java.lang.Long)
	 */
	public java.util.List findByDomainObject(java.lang.Long domainIdentifier) {
		return this.findByDomainObject(TRANSFORM_NONE, domainIdentifier);
	}

	/**
	 * @see org.openuss.braincontest.BrainContestDao#findByDomainObject(java.lang.String,
	 *      java.lang.Long)
	 */
	public java.util.List findByDomainObject(final java.lang.String queryString, final java.lang.Long domainIdentifier) {
		return this.findByDomainObject(TRANSFORM_NONE, queryString, domainIdentifier);
	}

	/**
	 * @see org.openuss.braincontest.BrainContestDao#findByDomainObject(int,
	 *      java.lang.Long)
	 */
	public java.util.List findByDomainObject(final int transform, final java.lang.Long domainIdentifier) {
		return this.findByDomainObject(transform,
				"from org.openuss.braincontest.BrainContest as brainContest where brainContest.domainIdentifier = ?",
				domainIdentifier);
	}

	/**
	 * @see org.openuss.braincontest.BrainContestDao#findByDomainObject(int,
	 *      java.lang.String, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public java.util.List findByDomainObject(final int transform, final java.lang.String queryString,
			final java.lang.Long domainIdentifier) {
		try {
			org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
			queryObject.setParameter(0, domainIdentifier);
			java.util.List results = queryObject.list();
			transformEntities(transform, results);
			return results;
		} catch (org.hibernate.HibernateException ex) {
			throw super.convertHibernateAccessException(ex);
		}
	}

	/**
	 * Allows transformation of entities into value objects (or something else
	 * for that matter), when the <code>transform</code> flag is set to one of
	 * the constants defined in
	 * <code>org.openuss.braincontest.BrainContestDao</code>, please note that
	 * the {@link #TRANSFORM_NONE} constant denotes no transformation, so the
	 * entity itself will be returned. <p/> This method will return instances of
	 * these types:
	 * <ul>
	 * <li>{@link org.openuss.braincontest.BrainContest} -
	 * {@link #TRANSFORM_NONE}</li>
	 * <li>{@link org.openuss.braincontest.BrainContestInfo} -
	 * {@link TRANSFORM_BRAINCONTESTINFO}</li>
	 * </ul>
	 * 
	 * If the integer argument value is unknown {@link #TRANSFORM_NONE} is
	 * assumed.
	 * 
	 * @param transform
	 *            one of the constants declared in
	 *            {@link org.openuss.braincontest.BrainContestDao}
	 * @param entity
	 *            an entity that was found
	 * @return the transformed entity (i.e. new value object, etc)
	 * @see #transformEntities(int,java.util.Collection)
	 */
	protected java.lang.Object transformEntity(final int transform, final org.openuss.braincontest.BrainContest entity) {
		java.lang.Object target = null;
		if (entity != null) {
			switch (transform) {
			case TRANSFORM_BRAINCONTESTINFO:
				target = toBrainContestInfo(entity);
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
	 * {@link #transformEntity(int,org.openuss.braincontest.BrainContest)}
	 * method. This method does not instantiate a new collection. <p/> This
	 * method is to be used internally only.
	 * 
	 * @param transform
	 *            one of the constants declared in
	 *            <code>org.openuss.braincontest.BrainContestDao</code>
	 * @param entities
	 *            the collection of entities to transform
	 * @see #transformEntity(int,org.openuss.braincontest.BrainContest)
	 */
	protected void transformEntities(final int transform, final java.util.Collection entities) {
		switch (transform) {
		case TRANSFORM_BRAINCONTESTINFO:
			toBrainContestInfoCollection(entities);
			break;
		case TRANSFORM_NONE: // fall-through
		default:
			// do nothing;
		}
	}

	/**
	 * @see org.openuss.braincontest.BrainContestDao#toBrainContestInfoCollection(java.util.Collection)
	 */
	public final void toBrainContestInfoCollection(java.util.Collection entities) {
		if (entities != null) {
			org.apache.commons.collections.CollectionUtils.transform(entities, BRAINCONTESTINFO_TRANSFORMER);
		}
	}

	/**
	 * Default implementation for transforming the results of a report query
	 * into a value object. This implementation exists for convenience reasons
	 * only. It needs only be overridden in the {@link BrainContestDaoImpl}
	 * class if you intend to use reporting queries.
	 * 
	 * @see org.openuss.braincontest.BrainContestDao#toBrainContestInfo(org.openuss.braincontest.BrainContest)
	 */
	protected org.openuss.braincontest.BrainContestInfo toBrainContestInfo(java.lang.Object[] row) {
		org.openuss.braincontest.BrainContestInfo target = null;
		if (row != null) {
			final int numberOfObjects = row.length;
			for (int ctr = 0; ctr < numberOfObjects; ctr++) {
				final java.lang.Object object = row[ctr];
				if (object instanceof org.openuss.braincontest.BrainContest) {
					target = this.toBrainContestInfo((org.openuss.braincontest.BrainContest) object);
					break;
				}
			}
		}
		return target;
	}

	/**
	 * This anonymous transformer is designed to transform entities or report
	 * query results (which result in an array of objects) to
	 * {@link org.openuss.braincontest.BrainContestInfo} using the Jakarta
	 * Commons-Collections Transformation API.
	 */
	private org.apache.commons.collections.Transformer BRAINCONTESTINFO_TRANSFORMER = new org.apache.commons.collections.Transformer() {
		public java.lang.Object transform(java.lang.Object input) {
			java.lang.Object result = null;
			if (input instanceof org.openuss.braincontest.BrainContest) {
				result = toBrainContestInfo((org.openuss.braincontest.BrainContest) input);
			} else if (input instanceof java.lang.Object[]) {
				result = toBrainContestInfo((java.lang.Object[]) input);
			}
			return result;
		}
	};

	/**
	 * @see org.openuss.braincontest.BrainContestDao#brainContestInfoToEntityCollection(java.util.Collection)
	 */
	public final void brainContestInfoToEntityCollection(java.util.Collection instances) {
		if (instances != null) {
			for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();) {
				// - remove an objects that are null or not of the correct
				// instance
				if (!(iterator.next() instanceof org.openuss.braincontest.BrainContestInfo)) {
					iterator.remove();
				}
			}
			org.apache.commons.collections.CollectionUtils.transform(instances, BrainContestInfoToEntityTransformer);
		}
	}

	private final org.apache.commons.collections.Transformer BrainContestInfoToEntityTransformer = new org.apache.commons.collections.Transformer() {
		public java.lang.Object transform(java.lang.Object input) {
			return brainContestInfoToEntity((org.openuss.braincontest.BrainContestInfo) input);
		}
	};

	/**
	 * @see org.openuss.braincontest.BrainContestDao#toBrainContestInfo(org.openuss.braincontest.BrainContest,
	 *      org.openuss.braincontest.BrainContestInfo)
	 */
	public void toBrainContestInfo(org.openuss.braincontest.BrainContest source,
			org.openuss.braincontest.BrainContestInfo target) {
		target.setId(source.getId());
		target.setDomainIdentifier(source.getDomainIdentifier());
		target.setReleaseDate(source.getReleaseDate());
		target.setDescription(source.getDescription());
		target.setTitle(source.getTitle());
		// No conversion for target.answers (can't convert
		// source.getAnswers():org.openuss.braincontest.Answer to
		// java.lang.Integer)
		target.setTries(source.getTries());
		target.setSolution(source.getSolution());
	}

	/**
	 * @see org.openuss.braincontest.BrainContestDao#toBrainContestInfo(org.openuss.braincontest.BrainContest)
	 */
	public org.openuss.braincontest.BrainContestInfo toBrainContestInfo(
			final org.openuss.braincontest.BrainContest entity) {
		final org.openuss.braincontest.BrainContestInfo target = new org.openuss.braincontest.BrainContestInfo();
		this.toBrainContestInfo(entity, target);
		return target;
	}

	/**
	 * @see org.openuss.braincontest.BrainContestDao#brainContestInfoToEntity(org.openuss.braincontest.BrainContestInfo,
	 *      org.openuss.braincontest.BrainContest)
	 */
	public void brainContestInfoToEntity(org.openuss.braincontest.BrainContestInfo source,
			org.openuss.braincontest.BrainContest target, boolean copyIfNull) {
		if (copyIfNull || source.getDomainIdentifier() != null) {
			target.setDomainIdentifier(source.getDomainIdentifier());
		}
		if (copyIfNull || source.getReleaseDate() != null) {
			target.setReleaseDate(source.getReleaseDate());
		}
		if (copyIfNull || source.getDescription() != null) {
			target.setDescription(source.getDescription());
		}
		if (copyIfNull || source.getTitle() != null) {
			target.setTitle(source.getTitle());
		}
		if (copyIfNull || source.getTries() != null) {
			target.setTries(source.getTries());
		}
		if (copyIfNull || source.getSolution() != null) {
			target.setSolution(source.getSolution());
		}
	}

}