package kartina.card;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;


import java.util.List;



/**
 * Created by David on 2016/9/16.
 */

public abstract class IListViewGroup<T extends AbsListView,D extends List> extends IViewGroup<T,D> {
    public IListViewGroup(Context context) {
        super(context);
    }

    public IListViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IListViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void fillView(T containerView) {

    }

}
