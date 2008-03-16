package org.openuss.web.security;

import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.web.migration.CentralUserData;

/**
 * Stores arbitrary information as strings. 
 * 
 * @author Peter Schuh
 *
 */
@Bean(name = "genericStringDataContainer", scope = Scope.REQUEST)
@View
public class  GenericStringDataContainer extends BaseBean {

	private static final Logger logger = Logger.getLogger(CentralUserData.class);

	List<String> data = new Vector<String>();
	
	public void addData(String newData) {
		if (newData != null)
			data.add(newData);
	}	

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}
	
}
