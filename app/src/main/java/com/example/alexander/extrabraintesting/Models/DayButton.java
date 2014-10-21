package com.example.alexander.extrabraintesting.Models;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.style.RelativeSizeSpan;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DayButton extends Button
{
    private StaticLayout dayLayout;
    public DayButton(Context context, Date dayDate)
    {
        super(context);
        setBackgroundColor(getResources().getColor(android.R.color.white));
        dayLayout = getTextLayout(dayDate);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        // Draw canvas beneath top padding
        canvas.translate(0, getPaddingTop());
        dayLayout.draw(canvas);
        canvas.restore();
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
        stringBuilder.append("\n" + dayOfMonth);

        // Draw text with smooth edges
        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

        // Create a text layout that won't be changed once created
        return new StaticLayout(stringBuilder, textPaint, 75, Layout.Alignment.ALIGN_CENTER, 1, 1, true);
    }
}
