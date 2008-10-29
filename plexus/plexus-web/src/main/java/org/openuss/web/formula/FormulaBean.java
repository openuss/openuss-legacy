package org.openuss.web.formula;

import org.apache.log4j.Logger;

import java.io.Serializable;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;

@Bean(name="formulaBean",scope=Scope.SESSION)
public class FormulaBean implements Serializable {

	private static final Logger logger = Logger.getLogger(FormulaBean.class);
	
	private static final long serialVersionUID = -4651338730217725143L;
	
	private String formula = "";

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}
	
	public String save() {
		logger.debug(formula);
		return "Save";
	}

	public String cancel() {
		return "Cancel";
	}

}
