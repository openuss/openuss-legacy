package org.openuss.commands;

import org.apache.commons.lang.Validate;
import org.openuss.foundation.AbstractMockDao;

/**
 * 
 * @author Ingo Dueppe
 */
public class MockLastProcessedCommandDao extends AbstractMockDao<LastProcessedCommand> implements LastProcessedCommandDao {

	@Override
	public LastProcessedCommand create(LastProcessedCommand entity) {
		Validate.notNull(entity,"LastProcessedCommand cannot be null");
		Validate.notNull(entity.getId(),"LastProcessedCommand must provide an Id.");;
		return super.create(entity);
	}
	
	


}
