package com.example.alexander.extrabraintesting.Helper;

import com.example.alexander.extrabraintesting.Models.PagerDay;
import com.example.alexander.extrabraintesting.Models.TimeEntry;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by devHaris on 2014-11-15.
 */
public class DateHelper {
    private Calendar todayCalendar;
    private List<PagerDay> pagerDates;


    public DateHelper(){
        todayCalendar = new GregorianCalendar();
        todayCalendar.setTime(new Date());
        pagerDates = new ArrayList<PagerDay>();
    }

    public List<PagerDay> CalculateDayFromToday(ArrayList<TimeEntry> timeEntries){

        Calendar startDate = GregorianCalendar.getInstance();
        startDate.setTime(todayCalendar.getTime());
        startDate.add(Calendar.DATE, -50);

        Calendar futureDate = GregorianCalendar.getInstance();
        futureDate.setTime(todayCalendar.getTime());
        futureDate.add(Calendar.DATE, 50);


        while (startDate.getTime().before(futureDate.getTime()))
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = dateFormat.format(startDate.getTime());
            PagerDay result = new PagerDay();

            for(TimeEntry entry : timeEntries){
                if(date.equals(entry.getDay()) && result.getTimeEntries() == null){
                    result = new PagerDay(startDate.getTime(), new ArrayList<TimeEntry>());
                    result.getTimeEntries().add(entry);
                }
                else if(date.equals(entry.getDay())){
                    result.getTimeEntries().add(entry);
                }
                else if(!date.equals(entry.getDay()) && result.getTimeEntries() == null){
                    result = new PagerDay(startDate.getTime(), new ArrayList<TimeEntry>());
                }

            }

            pagerDates.add(result);
            startDate.add(Calendar.DATE, 1);
        }

        return pagerDates;
    }

    public static String isOneDayNear(Date date){
        Calendar compareDate = Calendar.getInstance(); // today
        compareDate.setTime(new Date());

        Calendar isToday = Calendar.getInstance();
        isToday.setTime(date); // Today

        if (compareDate.get(Calendar.YEAR) == isToday.get(Calendar.YEAR) && compareDate.get(Calendar.DAY_OF_YEAR) == isToday.get(Calendar.DAY_OF_YEAR))
            return "Today";

        Calendar yesterday = Calendar.getInstance(); // today
        yesterday.add(Calendar.DATE, -1); // Yesterday

        if(compareDate.get(Calendar.YEAR) == yesterday.get(Calendar.YEAR) && compareDate.get(Calendar.DAY_OF_YEAR) == yesterday.get(Calendar.DAY_OF_YEAR))
            return "Yesterday";

        Calendar tomorrow = Calendar.getInstance(); // today
        tomorrow.add(Calendar.DATE, 1); // Tomorrow

        if(compareDate.get(Calendar.YEAR) == tomorrow.get(Calendar.YEAR) && compareDate.get(Calendar.DAY_OF_YEAR) == tomorrow.get(Calendar.DAY_OF_YEAR))
            return "Tomorrow";

        return "";
    }
}
