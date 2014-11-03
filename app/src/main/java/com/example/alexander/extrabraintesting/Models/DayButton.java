package com.example.alexander.extrabraintesting.Models;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.StateListDrawable;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.format.DateUtils;
import android.text.style.RelativeSizeSpan;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.alexander.extrabraintesting.R;

import java.text.SimpleDateFormat;
import java.util.Date;

class DayButton extends RadioButton
{
    private StaticLayout dayLayout;
    private int lastlineDescent;
    private Date day;

    public DayButton(Context context, Date day)
    {
        super(context);

        setDay(day);

        // Removes the radioButton image (setting null doesn't suffice for that)
        setButtonDrawable(new StateListDrawable());
        // Assign background color depending on checked state
        setBackgroundResource(R.drawable.daybutton_states);
        // Each button should fill up equal space
        setLayoutParams(new RadioGroup.LayoutParams(0, 55, 1));
    }

    private boolean isToday()
    {
        return DateUtils.isToday(day.getTime());
    }

    public void setDay(Date day)
    {
        this.day = day;
        // Update the textLayout with the Date
        dayLayout = getTextLayout(day);
    }

    public Date getDay()
    {
        return day;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        dayLayout.draw(canvas);
    }

    private StaticLayout getTextLayout(Date date)
    {
        SimpleDateFormat dayOfWeekFormat = new SimpleDateFormat("c");
        SimpleDateFormat dayOfMonthFormat = new SimpleDateFormat("d MMM");

        String dayName = dayOfWeekFormat.format(date);
        String dayOfMonth = dayOfMonthFormat.format(date);

        // Create styled text
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(dayName);
        stringBuilder.setSpan(new RelativeSizeSpan(2f), 0, dayName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringBuilder.append("\n");
        stringBuilder.append(dayOfMonth);

        // Draw text with smooth edges
        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        if (isToday()) { textPaint.setColor(Color.BLUE); }

        // Create a text layout that won't be changed once created
        return new StaticLayout(stringBuilder, textPaint, 75, Layout.Alignment.ALIGN_CENTER, 1, 1, true);

    }
}
