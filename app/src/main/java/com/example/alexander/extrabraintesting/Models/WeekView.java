package com.example.alexander.extrabraintesting.Models;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class WeekView extends RadioGroup
{
    private final ArrayList<DayButton> dayButtons = new ArrayList<DayButton>(7);
    final Context context;

    // XML-constructor
    public WeekView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;

        setOrientation(HORIZONTAL);

        prepareDayButtons(getWeekDates(new Date()));
        populateWeekView(dayButtons);
    }

    private ArrayList<Date> getWeekDates(Date date)
    {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);

        ArrayList<Date> weekDates = new ArrayList<Date>(7);
        for (int day = 0; day < 7; day++)
        {
            weekDates.add(calendar.getTime());
            calendar.roll(Calendar.DAY_OF_MONTH,true);
        }

        return weekDates;
    }

    private void prepareDayButtons(ArrayList<Date> buttonDates)
    {
        for (Date day : buttonDates)
        {
            dayButtons.add(new DayButton(context, day));
        }
    }

    private void populateWeekView(ArrayList<DayButton> dayButtonList)
    {
        for (DayButton dayButton : dayButtonList)
        {
            addView(dayButton);
        }
    }

    public void nextWeek()
    {
        addWeeks(1);
    }
    public void previousWeek()
    {
        addWeeks(-1);
    }

    private void addWeeks(int weekCount)
    {
        GregorianCalendar calendar = new GregorianCalendar();
        for (DayButton dayButton : dayButtons)
        {
            calendar.setTime(dayButton.getDay());
            calendar.add(Calendar.WEEK_OF_YEAR, weekCount);
            dayButton.setDay(calendar.getTime());
        }
    }
}
