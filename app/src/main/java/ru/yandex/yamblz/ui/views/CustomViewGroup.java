package ru.yandex.yamblz.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by platon on 19.07.2016.
 */
public class CustomViewGroup extends ViewGroup
{
    public CustomViewGroup(Context context)
    {
        super(context);
    }

    public CustomViewGroup(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        int childCount = getChildCount();

        int prevChildRight = 0;
        int prevChildBottom = 0;

        for (int i = 0; i < childCount; i++)
        {
            final View child = getChildAt(i);
            child.layout(
                    prevChildRight,
                    prevChildBottom,
                    prevChildRight + child.getMeasuredWidth(),
                    prevChildBottom + child.getMeasuredHeight());

            prevChildRight += child.getMeasuredWidth();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);

        int maxWidth = 0;
        int childCount = getChildCount();

        View tmpChild = null;

        for (int i = 0; i < childCount; i++)
        {
            final View child = getChildAt(i);

            if (child.getVisibility() != GONE)
            {
                final LayoutParams lp = child.getLayoutParams();

                if (lp.width == LayoutParams.MATCH_PARENT)
                {
                    tmpChild = child;
                }
                else
                {
                    maxWidth += child.getMeasuredWidth();
                    measureChild(child, widthMeasureSpec, heightMeasureSpec);
                }
            }
        }

        if (tmpChild != null)
        {
            int childWidthSpec = MeasureSpec.makeMeasureSpec(parentWidth - maxWidth, MeasureSpec.EXACTLY);
            tmpChild.measure(childWidthSpec, heightMeasureSpec);
        }

        setMeasuredDimension(parentWidth, parentHeight);
    }
}
