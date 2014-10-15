package com.example.alexander.extrabraintesting;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;

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

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.WeekView, 0, 0);
        try
        {
            // Get day names given in XML-attribute
            CharSequence[] dayNames = a.getTextArray(R.styleable.WeekView_android_entries);
            if (dayNames != null)
            {
                createButtons(dayNames);
            }
        }
        finally
        {
            a.recycle();
        }
    }

    private void createButtons(CharSequence[] dayNames)
    {
        DayButton dayButton;
        for (int day = 0; day < 7; day++)
        {
            dayButton = new DayButton(context, dayNames[day].toString());
            addView(dayButton);

            // Set Layout parameters
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) dayButton.getLayoutParams();
            layoutParams.width = 0;
            layoutParams.weight = 1;
        }
    }
}
