package kartina.card;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


/**
 * Created by David on 2016/9/16.
 */

public abstract class IViewGroup<T extends View, D extends Object> extends FrameLayout {
    protected T containerView;

    public IViewGroup(Context context) {
        this(context, null);
    }

    public IViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setBackgroundColor(0X00000000);
        containerView = initView(context);
        if (containerView != null)
            addView(containerView);
    }

    protected abstract T initView(Context context);

    protected abstract void fillView(T containerView);
}
