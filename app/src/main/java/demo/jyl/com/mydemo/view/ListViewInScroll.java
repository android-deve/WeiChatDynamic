package demo.jyl.com.mydemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @author Jack Boy
 */
public class ListViewInScroll extends ListView {
    public boolean hasScrollBar = true;

    public ListViewInScroll(Context context) {
        super(context);
    }

    public ListViewInScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewInScroll(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
