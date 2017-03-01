package kartina.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import kartina.R;

/**
 * Created by David on 2016/11/16.
 */

public class CircleTextView extends FrameLayout {
    private TextView textView;
    public CircleTextView(Context context) {
        this(context,null);
    }

    public CircleTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void initView(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.hot_tag_text,this,false);
        textView = (TextView) view.findViewById(R.id.round_txt);
        addView(view);
    }

    public void setTag(String tag) {
        if (textView == null)
            return;
        textView.setText(tag);
    }
}
