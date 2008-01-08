package org.openuss.foundation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.support.PropertyComparator;

/**
 * @author Ingo Dueppe
 *
 * @param <T>
 */
public abstract class AbstractMockDao<T extends DomainObject> {

	protected Map<Long, T> store = new HashMap<Long, T>();
	
	private static long id = 1;
	
	protected long nextId() {
		synchronized(AbstractMockDao.class) {
			return id++;
		}
	}
	
	public T create(T entity) {
		if (entity.getId() == null) {
			entity.setId(nextId());
		}
		store.put(entity.getId(), entity);
		
		return entity;
	}

	public Object create(int transform, T entity) {
		return create(entity);
	}

	public Collection<T> create(Collection<T> entities) {
		for (T entity : entities) {
			create(entity);
		}
		return entities;
	}
	
	public Collection<?> create(int transform, Collection<T> entities) {
		return create(entities);
	}

	public T load(Long id) {
		return store.get(id);
	}

	public Object load(int transform, Long id) {
		return load(id);
	}

	public Collection<T> loadAll() {
		List<T> result = new ArrayList<T>(store.values());
		Collections.sort(result, new PropertyComparator("id",true,true));
		return result;
	}

	public Collection<?> loadAll(int transform) {
		return loadAll();
	}

	public void remove(T T) {
		remove(T.getId());
	}

	public void remove(Long id) {
		store.remove(id);
	}

	public void remove(Collection<T> entities) {
		for(T command : entities) {
			remove(command);
		}
	}

	public void update(T entity) {
		if (store.containsKey(entity.getId())) {
			store.remove(entity.getId());
			store.put(entity.getId(), entity);
		} 
	}

	public void update(Collection<T> entities) {
		create(entities);
	}

	public void reset() {
		store.clear();
	}
	

	
}