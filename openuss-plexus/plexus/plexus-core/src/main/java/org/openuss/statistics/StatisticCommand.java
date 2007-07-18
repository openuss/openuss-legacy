package org.openuss.statistics;

import org.openuss.commands.AbstractDomainCommand;
import org.openuss.commands.DomainCommand;

public class StatisticCommand extends AbstractDomainCommand implements DomainCommand{ 
	
	private SystemStatisticDao systemStatisticDao;
	
	public void execute() throws Exception {
		getSystemStatisticDao().current();
	}
	
	public SystemStatisticDao getSystemStatisticDao() {
		return systemStatisticDao;
	}
	
	public void setSystemStatisticDao(SystemStatisticDao systemStatisticDao) {
		this.systemStatisticDao = systemStatisticDao;
	}

}

