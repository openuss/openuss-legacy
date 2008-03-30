package org.openuss.lecture;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

/**
 * This Methods generate a mapping between to collections of periods.
 * @author Ingo Dueppe
 */
public class PeriodMapping {

	public static Map<Period, Period> generate(Collection<Period> fromPeriods, Collection<Period> toPeriods) {
		Map<Period, Period> mappings = new HashMap<Period, Period>();

		Period defaultPeriod = (Period) CollectionUtils.find(toPeriods, new Predicate() {
			public boolean evaluate(Object object) {
				return ((Period) object).isDefaultPeriod();
			}
		});
		
		
		for (Period source : fromPeriods) {
			Period match = defaultPeriod;
			long distance = Long.MAX_VALUE;
			for (Period target : toPeriods) {
				if (isDuring(source, target) && distance > distance(source, target)) {
					match = target;
				}
			}
			mappings.put(source, match);
		}
		return mappings;
	}

	/**
	 * Checks if the candidate periods is during the period.
	 * @param candidate - period
	 * @param period - period
	 * @return true - if the candidate is during the period 
	 */
	private static boolean isDuring(Period candidate, Period period) {
		return !period.getStartdate().after(candidate.getStartdate()) && !period.getEnddate().before(candidate.getEnddate());
	}

	
	private static long distance(Period candidate, Period period) {
		return period.getStartdate().getTime() - candidate.getStartdate().getTime() + candidate.getEnddate().getTime() - period.getEnddate().getTime();
	}
}
