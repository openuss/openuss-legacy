package org.openuss.braincontest;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * <p>
 * Base Spring DAO Class: is able to create, update, remove, load, and find
 * objects of type <code>org.openuss.braincontest.Answer</code>.
 * </p>
 * 
 * @see org.openuss.braincontest.Answer
 */
public abstract class AnswerDaoBase extends HibernateDaoSupport implements AnswerDao {

	/**
	 * @see org.openuss.braincontest.AnswerDao#load(int, java.lang.Long)
	 */
	public java.lang.Object load(final int transform, final org.openuss.braincontest.AnswerPK answerPk) {
		if (answerPk == null) {
			throw new IllegalArgumentException("Answer.load - 'answerPk' can not be null");
		}

		final java.lang.Object entity = this.getHibernateTemplate().get(org.openuss.braincontest.AnswerImpl.class,
				answerPk);
		return transformEntity(transform, (org.openuss.braincontest.Answer) entity);
	}

	/**
	 * @see org.openuss.braincontest.AnswerDao#load(org.openuss.braincontest.AnswerPK)
	 */
	public org.openuss.braincontest.Answer load(org.openuss.braincontest.AnswerPK answerPk) {
		return (org.openuss.braincontest.Answer) this.load(TRANSFORM_NONE, answerPk);
	}

	/**
	 * @see org.openuss.braincontest.AnswerDao#loadAll()
	 */
	@SuppressWarnings( { "unchecked" })
	public java.util.Collection<org.openuss.braincontest.Answer> loadAll() {
		return this.loadAll(TRANSFORM_NONE);
	}

	/**
	 * @see org.openuss.braincontest.AnswerDao#loadAll(int)
	 */
	public java.util.Collection loadAll(final int transform) {
		final java.util.Collection results = this.getHibernateTemplate().loadAll(
				org.openuss.braincontest.AnswerImpl.class);
		this.transformEntities(transform, results);
		return results;
	}

	/**
	 * @see org.openuss.braincontest.AnswerDao#create(org.openuss.braincontest.Answer)
	 */
	public org.openuss.braincontest.Answer create(org.openuss.braincontest.Answer answer) {
		return (org.openuss.braincontest.Answer) this.create(TRANSFORM_NONE, answer);
	}

	/**
	 * @see org.openuss.braincontest.AnswerDao#create(int transform,
	 *      org.openuss.braincontest.Answer)
	 */
	public java.lang.Object create(final int transform, final org.openuss.braincontest.Answer answer) {
		if (answer == null) {
			throw new IllegalArgumentException("Answer.create - 'answer' can not be null");
		}
		this.getHibernateTemplate().save(answer);
		return this.transformEntity(transform, answer);
	}

	/**
	 * @see org.openuss.braincontest.AnswerDao#create(java.util.Collection<org.
	 *      openuss.braincontest.Answer>)
	 */
	@SuppressWarnings( { "unchecked" })
	public java.util.Collection<org.openuss.braincontest.Answer> create(
			final java.util.Collection<org.openuss.braincontest.Answer> entities) {
		return create(TRANSFORM_NONE, entities);
	}

	/**
	 * @see org.openuss.braincontest.AnswerDao#create(int,
	 *      java.util.Collection<org.openuss.braincontest.Answer>)
	 */
	public java.util.Collection create(final int transform,
			final java.util.Collection<org.openuss.braincontest.Answer> entities) {
		if (entities == null) {
			throw new IllegalArgumentException("Answer.create - 'entities' can not be null");
		}
		this.getHibernateTemplate().execute(new org.springframework.orm.hibernate3.HibernateCallback() {
			public java.lang.Object doInHibernate(org.hibernate.Session session)
					throws org.hibernate.HibernateException {
				for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();) {
					create(transform, (org.openuss.braincontest.Answer) entityIterator.next());
				}
				return null;
			}
		}, true);
		return entities;
	}

	/**
	 * @see org.openuss.braincontest.AnswerDao#update(org.openuss.braincontest.Answer)
	 */
	public void update(org.openuss.braincontest.Answer answer) {
		if (answer == null) {
			throw new IllegalArgumentException("Answer.update - 'answer' can not be null");
		}
		try {
			this.getHibernateTemplate().update(answer);
		} catch (org.springframework.dao.DataAccessException ex) {
			if (ex.getCause() instanceof org.hibernate.NonUniqueObjectException) {
				logger.debug("Catched NonUniqueObjectException " + ex.getCause().getMessage());
				getSession().merge(answer);
			} else {
				throw ex;
			}
		}
	}

	/**
	 * @see org.openuss.braincontest.AnswerDao#update(java.util.Collection<org.
	 *      openuss.braincontest.Answer>)
	 */
	public void update(final java.util.Collection<org.openuss.braincontest.Answer> entities) {
		if (entities == null) {
			throw new IllegalArgumentException("Answer.update - 'entities' can not be null");
		}
		this.getHibernateTemplate().execute(new org.springframework.orm.hibernate3.HibernateCallback() {
			public java.lang.Object doInHibernate(org.hibernate.Session session)
					throws org.hibernate.HibernateException {
				for (java.util.Iterator entityIterator = entities.iterator(); entityIterator.hasNext();) {
					update((org.openuss.braincontest.Answer) entityIterator.next());
				}
				return null;
			}
		}, true);
	}

	/**
	 * @see org.openuss.braincontest.AnswerDao#remove(org.openuss.braincontest.Answer)
	 */
	public void remove(org.openuss.braincontest.Answer answer) {
		if (answer == null) {
			throw new IllegalArgumentException("Answer.remove - 'answer' can not be null");
		}
		this.getHibernateTemplate().delete(answer);
	}

	/**
	 * @see org.openuss.braincontest.AnswerDao#remove(java.lang.Long)
	 */
	public void remove(org.openuss.braincontest.AnswerPK answerPk) {
		if (answerPk == null) {
			throw new IllegalArgumentException("Answer.remove - 'answerPk can not be null");
		}
		org.openuss.braincontest.Answer entity = this.load(answerPk);
		if (entity != null) {
			this.remove(entity);
		}
	}

	/**
	 * @see org.openuss.braincontest.AnswerDao#remove(java.util.Collection<org.
	 *      openuss.braincontest.Answer>)
	 */
	public void remove(java.util.Collection<org.openuss.braincontest.Answer> entities) {
		if (entities == null) {
			throw new IllegalArgumentException("Answer.remove - 'entities' can not be null");
		}
		this.getHibernateTemplate().deleteAll(entities);
	}

	/**
	 * @see org.openuss.braincontest.AnswerDao#findByContestAndSolver(java.lang.Long,
	 *      java.lang.Long)
	 */
	public org.openuss.braincontest.Answer findByContestAndSolver(java.lang.Long solverId, java.lang.Long contestId) {
		return (org.openuss.braincontest.Answer) this.findByContestAndSolver(TRANSFORM_NONE, solverId, contestId);
	}

	/**
	 * @see org.openuss.braincontest.AnswerDao#findByContestAndSolver(java.lang.String,
	 *      java.lang.Long, java.lang.Long)
	 */
	public org.openuss.braincontest.Answer findByContestAndSolver(final java.lang.String queryString,
			final java.lang.Long solverId, final java.lang.Long contestId) {
		return (org.openuss.braincontest.Answer) this.findByContestAndSolver(TRANSFORM_NONE, queryString, solverId,
				contestId);
	}

	/**
	 * @see org.openuss.braincontest.AnswerDao#findByContestAndSolver(int,
	 *      java.lang.Long, java.lang.Long)
	 */
	public java.lang.Object findByContestAndSolver(final int transform, final java.lang.Long solverId,
			final java.lang.Long contestId) {
		return this.findByContestAndSolver(transform,
				"from org.openuss.braincontest.Answer as answer where answer.solverId = ? and answer.contestId = ?",
				solverId, contestId);
	}

	/**
	 * @see org.openuss.braincontest.AnswerDao#findByContestAndSolver(int,
	 *      java.lang.String, java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public java.lang.Object findByContestAndSolver(final int transform, final java.lang.String queryString,
			final java.lang.Long solverId, final java.lang.Long contestId) {
		try {
			org.hibernate.Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setCacheable(true);
			queryObject.setParameter(0, solverId);
			queryObject.setParameter(1, contestId);
			java.util.Set results = new java.util.LinkedHashSet(queryObject.list());
			java.lang.Object result = null;
			if (results != null) {
				if (results.size() > 1) {
					throw new org.springframework.dao.InvalidDataAccessResourceUsageException(
							"More than one instance of 'org.openuss.braincontest.Answer"
									+ "' was found when executing query --> '" + queryString + "'");
				} else if (results.size() == 1) {
					result = results.iterator().next();
				}
			}
			result = transformEntity(transform, (org.openuss.braincontest.Answer) result);
			return result;
		} catch (org.hibernate.HibernateException ex) {
			throw super.convertHibernateAccessException(ex);
		}
	}

	/**
	 * @see org.openuss.braincontest.AnswerDao#findBySolver(java.lang.Long)
	 */
	public java.util.List findBySolver(final java.lang.Long solverId) {
		try {
			return this.handleFindBySolver(solverId);
		} catch (Throwable th) {
			throw new java.lang.RuntimeException(
					"Error performing 'org.openuss.braincontest.AnswerDao.findBySolver(java.lang.Long solverId)' --> "
							+ th, th);
		}
	}

	/**
	 * Performs the core logic for {@link #findBySolver(java.lang.Long)}
	 */
	protected abstract java.util.List handleFindBySolver(java.lang.Long solverId) throws java.lang.Exception;

	/**
	 * Allows transformation of entities into value objects (or something else
	 * for that matter), when the <code>transform</code> flag is set to one of
	 * the constants defined in <code>org.openuss.braincontest.AnswerDao</code>,
	 * please note that the {@link #TRANSFORM_NONE} constant denotes no
	 * transformation, so the entity itself will be returned. <p/> This method
	 * will return instances of these types:
	 * <ul>
	 * <li>{@link org.openuss.braincontest.Answer} - {@link #TRANSFORM_NONE}</li>
	 * <li>{@link org.openuss.braincontest.AnswerInfo} -
	 * {@link TRANSFORM_ANSWERINFO}</li>
	 * </ul>
	 * 
	 * If the integer argument value is unknown {@link #TRANSFORM_NONE} is
	 * assumed.
	 * 
	 * @param transform
	 *            one of the constants declared in
	 *            {@link org.openuss.braincontest.AnswerDao}
	 * @param entity
	 *            an entity that was found
	 * @return the transformed entity (i.e. new value object, etc)
	 * @see #transformEntities(int,java.util.Collection)
	 */
	protected java.lang.Object transformEntity(final int transform, final org.openuss.braincontest.Answer entity) {
		java.lang.Object target = null;
		if (entity != null) {
			switch (transform) {
			case TRANSFORM_ANSWERINFO:
				target = toAnswerInfo(entity);
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
	 * {@link #transformEntity(int,org.openuss.braincontest.Answer)} method.
	 * This method does not instantiate a new collection. <p/> This method is to
	 * be used internally only.
	 * 
	 * @param transform
	 *            one of the constants declared in
	 *            <code>org.openuss.braincontest.AnswerDao</code>
	 * @param entities
	 *            the collection of entities to transform
	 * @see #transformEntity(int,org.openuss.braincontest.Answer)
	 */
	protected void transformEntities(final int transform, final java.util.Collection entities) {
		switch (transform) {
		case TRANSFORM_ANSWERINFO:
			toAnswerInfoCollection(entities);
			break;
		case TRANSFORM_NONE: // fall-through
		default:
			// do nothing;
		}
	}

	/**
	 * @see org.openuss.braincontest.AnswerDao#toAnswerInfoCollection(java.util.Collection)
	 */
	public final void toAnswerInfoCollection(java.util.Collection entities) {
		if (entities != null) {
			org.apache.commons.collections.CollectionUtils.transform(entities, ANSWERINFO_TRANSFORMER);
		}
	}

	/**
	 * Default implementation for transforming the results of a report query
	 * into a value object. This implementation exists for convenience reasons
	 * only. It needs only be overridden in the {@link AnswerDaoImpl} class if
	 * you intend to use reporting queries.
	 * 
	 * @see org.openuss.braincontest.AnswerDao#toAnswerInfo(org.openuss.braincontest.Answer)
	 */
	protected org.openuss.braincontest.AnswerInfo toAnswerInfo(java.lang.Object[] row) {
		org.openuss.braincontest.AnswerInfo target = null;
		if (row != null) {
			final int numberOfObjects = row.length;
			for (int ctr = 0; ctr < numberOfObjects; ctr++) {
				final java.lang.Object object = row[ctr];
				if (object instanceof org.openuss.braincontest.Answer) {
					target = this.toAnswerInfo((org.openuss.braincontest.Answer) object);
					break;
				}
			}
		}
		return target;
	}

	/**
	 * This anonymous transformer is designed to transform entities or report
	 * query results (which result in an array of objects) to
	 * {@link org.openuss.braincontest.AnswerInfo} using the Jakarta
	 * Commons-Collections Transformation API.
	 */
	private org.apache.commons.collections.Transformer ANSWERINFO_TRANSFORMER = new org.apache.commons.collections.Transformer() {
		public java.lang.Object transform(java.lang.Object input) {
			java.lang.Object result = null;
			if (input instanceof org.openuss.braincontest.Answer) {
				result = toAnswerInfo((org.openuss.braincontest.Answer) input);
			} else if (input instanceof java.lang.Object[]) {
				result = toAnswerInfo((java.lang.Object[]) input);
			}
			return result;
		}
	};

	/**
	 * @see org.openuss.braincontest.AnswerDao#answerInfoToEntityCollection(java.util.Collection)
	 */
	public final void answerInfoToEntityCollection(java.util.Collection instances) {
		if (instances != null) {
			for (final java.util.Iterator iterator = instances.iterator(); iterator.hasNext();) {
				// - remove an objects that are null or not of the correct
				// instance
				if (!(iterator.next() instanceof org.openuss.braincontest.AnswerInfo)) {
					iterator.remove();
				}
			}
			org.apache.commons.collections.CollectionUtils.transform(instances, AnswerInfoToEntityTransformer);
		}
	}

	private final org.apache.commons.collections.Transformer AnswerInfoToEntityTransformer = new org.apache.commons.collections.Transformer() {
		public java.lang.Object transform(java.lang.Object input) {
			return answerInfoToEntity((org.openuss.braincontest.AnswerInfo) input);
		}
	};

	/**
	 * @see org.openuss.braincontest.AnswerDao#toAnswerInfo(org.openuss.braincontest.Answer,
	 *      org.openuss.braincontest.AnswerInfo)
	 */
	public void toAnswerInfo(org.openuss.braincontest.Answer source, org.openuss.braincontest.AnswerInfo target) {
		// TODO: if any VO attribute maps with identifier association ends, map
		// it in the impl class
		target.setAnsweredAt(source.getAnsweredAt());
	}

	/**
	 * @see org.openuss.braincontest.AnswerDao#toAnswerInfo(org.openuss.braincontest.Answer)
	 */
	public org.openuss.braincontest.AnswerInfo toAnswerInfo(final org.openuss.braincontest.Answer entity) {
		final org.openuss.braincontest.AnswerInfo target = new org.openuss.braincontest.AnswerInfo();
		this.toAnswerInfo(entity, target);
		return target;
	}

	/**
	 * @see org.openuss.braincontest.AnswerDao#answerInfoToEntity(org.openuss.braincontest.AnswerInfo,
	 *      org.openuss.braincontest.Answer)
	 */
	public void answerInfoToEntity(org.openuss.braincontest.AnswerInfo source, org.openuss.braincontest.Answer target,
			boolean copyIfNull) {
		// TODO: if any VO attribute maps with identifier association ends, map
		// it in the impl class
		if (target.getAnswerPk() == null) {
			target.setAnswerPk(new AnswerPK());
		}
		if (copyIfNull || source.getAnsweredAt() != null) {
			target.setAnsweredAt(source.getAnsweredAt());
		}
	}

}