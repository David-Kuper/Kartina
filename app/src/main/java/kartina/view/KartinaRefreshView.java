package kartina.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by David on 2016/11/25.
 */

public class KartinaRefreshView extends SwipeRefreshLayout {
    public KartinaRefreshView(Context context) {
        this(context,null);
    }

    public KartinaRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }
}
