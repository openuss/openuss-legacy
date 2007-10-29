package org.openuss.commands;

import java.util.Collection;

import org.openuss.system.SystemProperty;
import org.openuss.system.SystemService;

/**
 * 
 * @author Ingo Dueppe
 */
public class MockServiceSystem implements SystemService {
	
	private Long instanceIdentity;

	
	
	public Collection<?> getProperties() {
		return null;
	}

	public SystemProperty getProperty(String name) {
		return null;
	}

	@SuppressWarnings("unchecked")
	public void persistProperties(Collection properties) {}

	public void persistProperty(SystemProperty property) {}

	public void setInstanceIdentity(Long instanceIdentity) {
		this.instanceIdentity = instanceIdentity;
	}

	public Long getInstanceIdentity() {
		return instanceIdentity;
	}

	public SystemProperty getProperty(Long id) {
		return null;
	}

}
