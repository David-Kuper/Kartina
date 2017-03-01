package kartina.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import kartina.R;
import kartina.model.NoteBody;
import kartina.util.DensityUtil;

/**
 * Created by David on 2016/11/7.
 */

public class NoteBodyView extends FrameLayout {
    private TextView titleView;
    private TextView describeView;
    public NoteBodyView(Context context) {
        this(context,null);
    }

    public NoteBodyView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NoteBodyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.note_body,null);
        titleView = (TextView) view.findViewById(R.id.title);
        describeView = (TextView) view.findViewById(R.id.describe);
        addView(view);
    }

    public void setTitleSize(float size){
        if (titleView == null)
            return;
        titleView.setTextSize(size);
    }

    public void setContentSize(float size){
        if (describeView == null)
            return;
        describeView.setTextSize(size);
    }

    public void setData(NoteBody noteBody){
        if (noteBody == null)
            return;
        titleView.setText(noteBody.title);
        describeView.setText(noteBody.describe);
    }
}
