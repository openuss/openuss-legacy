package org.openuss.web.calendar;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.faces.event.ActionEvent;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.apache.myfaces.custom.schedule.model.DefaultScheduleEntry;
import org.apache.myfaces.custom.schedule.model.ScheduleModel;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;

/**
 * Backing Bean for the user calendar - month overview page (usercalendar.xhtml)
 * 
 * @author Thomas Jansing
 *
 */

@Bean(name = "views$secured$calendar$usercalendar", scope = Scope.REQUEST)
@View
public class UserCalendarPage extends AbstractCalendarPage {

	private static final Logger logger = Logger	.getLogger(UserCalendarPage.class);
	
	private ScheduleModel model;
    
    public ScheduleModel getModel()
    {
        return model;
    }

    public void setModel(ScheduleModel model)
    {
        this.model = model;
    }
    
    public void loadEntries(ActionEvent event)
    {	
    	logger.debug("Starting: loading Entries for calendar");
    	if (model == null)
            return;
    	Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(model.getSelectedDate());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 14);
       
        logger.debug("Starting: loaded model");
      DefaultScheduleEntry entry3 = new DefaultScheduleEntry();
      entry3.setId(RandomStringUtils.randomNumeric(32));
      calendar.add(Calendar.DATE, 1);
      calendar.set(Calendar.HOUR_OF_DAY, 9);
      calendar.set(Calendar.MINUTE, 0);
      calendar.set(Calendar.SECOND, 0);
      entry3.setStartTime(calendar.getTime());
      calendar.set(Calendar.HOUR_OF_DAY, 17);
      entry3.setEndTime(calendar.getTime());
      entry3.setTitle("Thoroughly test schedule component");
      model.addEntry(entry3);
        
        model.refresh();
    }
}