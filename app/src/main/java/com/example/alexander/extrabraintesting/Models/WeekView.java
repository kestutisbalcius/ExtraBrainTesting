package com.example.alexander.extrabraintesting.Models;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.example.alexander.extrabraintesting.Models.DayButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Alexander on 2014-10-13.
 */
public class WeekView extends LinearLayout
{
    Context context;

    // XML-constructor
    public WeekView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;

        createButtons();
    }

    private ArrayList<Date> getWeekDates(Date... date)
    {
        GregorianCalendar calendar = new GregorianCalendar();

        // If a date argument is given...
        if (date.length == 1)
        {
            // use it to set the calendar
            calendar.setTime(date[0]);
        }

        calendar.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);

        ArrayList<Date> weekDates = new ArrayList<Date>(7);
        for (int day = 0; day < 7; day++)
        {
            weekDates.add(calendar.getTime());
            calendar.roll(Calendar.DAY_OF_MONTH,true);
        }

        return weekDates;
    }

    private void createButtons()
    {
        ArrayList<Date> weekDates = getWeekDates();
        DayButton dayButton;
        for (int day = 0; day < 7; day++)
        {
            dayButton = new DayButton(context, weekDates.get(day));
            addView(dayButton);

            // Set button layout in LinearLayout
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) dayButton.getLayoutParams();
            layoutParams.width = 0;
            layoutParams.weight = 1;
        }
    }
}
