package org.openuss.web.system;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.model.PagedDataModel;
import org.openuss.system.SystemProperty;

@Bean(name="propertyList", scope=Scope.SESSION)
@View
public class PropertyList extends PagedDataModel<SystemProperty> {
	
	private static final long serialVersionUID = 2076461147275349227L;
	
	private boolean editable;

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}
}
