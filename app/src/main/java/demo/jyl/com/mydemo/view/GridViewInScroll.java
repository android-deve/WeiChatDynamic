package demo.jyl.com.mydemo.view;

/**
 * @author Jack Boy
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class GridViewInScroll extends GridView {

    public boolean hasScrollBar = true;

    /**
     * @param context
     */
    public GridViewInScroll(Context context) {
        this(context, null);
    }

    public GridViewInScroll(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public GridViewInScroll(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = heightMeasureSpec;
        if (hasScrollBar) {
            expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

}