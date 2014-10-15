package com.example.alexander.extrabraintesting;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.widget.Button;


/**
 * Created by Alexander on 2014-10-08.
 */
public class DayButton extends Button
{
    private StaticLayout dayLayout;

    public DayButton(Context context, String dayName)
    {
        super(context);

        setBackgroundColor(getResources().getColor(android.R.color.white));
        dayLayout = getTextLayout(dayName);
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

    private StaticLayout getTextLayout(String dayName)
    {
        // Create styled text
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(dayName);
        stringBuilder.setSpan(new RelativeSizeSpan(2f), 0, dayName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringBuilder.append("\n18 sep");

        // Draw text with smooth edges
        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

        // Create a text layout that won't be changed once created
        return new StaticLayout(stringBuilder, textPaint, 75, Layout.Alignment.ALIGN_CENTER, 1, 1, true);
    }
}
