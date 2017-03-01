package kartina.card.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import kartina.R;

/**
 * Created by David on 2016/11/9.
 */

public class CheckMore extends FrameLayout {
    public CheckMore(Context context) {
        this(context,null);
    }

    public CheckMore(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CheckMore(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    public void initView(Context context) {
         View view = LayoutInflater.from(context).inflate(R.layout.check_more,this,false);
        addView(view);
    }


}
