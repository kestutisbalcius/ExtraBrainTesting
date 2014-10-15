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
    private int dayOfTheWeek;
    private String dayText;
    private TextPaint textPaint;
    private StaticLayout dayLayout;
    private SpannableStringBuilder stringBuilder;
    private String[] dayNames;

    public DayButton(Context context, String dayName)
    {
        super(context);

        setBackgroundColor(getResources().getColor(R.color.white));
        dayLayout = getTextLayout(dayName);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        // Draw canvas beneath top padding
        canvas.translate(0,getPaddingTop());
        dayLayout.draw(canvas);
        canvas.restore();
    }

    private StaticLayout getTextLayout(String dayName)
    {
        // Create styled text
        stringBuilder = new SpannableStringBuilder(dayName);
        stringBuilder.setSpan(new RelativeSizeSpan(2f), 0, dayName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringBuilder.append("\n18 sep");

        // Draw text with smooth edges
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

        // Create a text layout that won't be changed once created
        return new StaticLayout(stringBuilder, textPaint, 75, Layout.Alignment.ALIGN_CENTER, 1, 1, true);
    }






    /*
     * Code below only necessary for stand-alone buttons created with XML
     */

    //Construct from XML attributes, only for stand-alone buttons
    public DayButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.DayButton,
                0,0
        );

        try
        {
            dayOfTheWeek = a.getInteger(R.styleable.DayButton_weekday,5);
        }
        finally
        {
            a.recycle();
        }

        dayLayout = getTextLayout(getDayString(dayOfTheWeek));
    }

    // Only necessary for stand-alone buttons
    private String getDayString(int dayOfTheWeek)
    {
        dayNames = getResources().getStringArray(R.array.day_names);
        switch (dayOfTheWeek)
        {
            case 0: return dayNames[0];
            case 1: return dayNames[1];
            case 2: return dayNames[2];
            case 3: return dayNames[3];
            case 4: return dayNames[4];
            case 5: return dayNames[5];
            case 6: return dayNames[6];

            // Otherwise it's saturday
            default: return dayNames[5];
        }
    }



//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh)
//    {
//        super.onSizeChanged(w, h, oldw, oldh);
//        // Account for padding
//        float xpad = (float) (getPaddingLeft() + getPaddingRight());
//        float ypad = (float) (getPaddingTop() + getPaddingBottom());
//
//        float ww = (float) w - xpad;
//        float hh = (float) h - ypad;
//
//        // Figure out how big we can make the pie.
//        float diameter = Math.min(ww, hh);
//
//    }




//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
//    {
//        // Our desired minimum sizes including padding
//        int minWidth = getMinWidth() + getPaddingLeft() + getPaddingRight();
//        int minHeight = getMinHeight() + getPaddingTop() + getPaddingBottom();
//        // Create a final size based on minimum size and current parent size
//        int finalWidth = resolveSizeAndState(minWidth,widthMeasureSpec,1);
//        int finalHeight = resolveSizeAndState(minHeight,heightMeasureSpec,1);
//
//        setMeasuredDimension(finalWidth,finalHeight);
//    }


}
