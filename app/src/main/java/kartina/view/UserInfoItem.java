package kartina.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import kartina.R;

/**
 * Created by David on 2016/11/20.
 */

public class UserInfoItem extends FrameLayout {
    private TextView leftText;
    private TextView rightText;

    public UserInfoItem(Context context) {
        this(context, null);
    }

    public UserInfoItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UserInfoItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.UserInfoItem);
        int style = typedArray.getInt(R.styleable.UserInfoItem_UserInfoStyle,1);
        String leftText = typedArray.getString(R.styleable.UserInfoItem_leftText);
        String rightText = typedArray.getString(R.styleable.UserInfoItem_rightText);

        initView(style);

        setLeftText(leftText);
        setRightText(rightText);

        typedArray.recycle();
    }

    private void initView(int style) {
        View view = null;
        if (style == 1){
           view = LayoutInflater.from(getContext()).inflate(R.layout.userinfo_item1, null, false);
        }else{
            view = LayoutInflater.from(getContext()).inflate(R.layout.userinfo_item2, null, false);
        }
        leftText = (TextView) view.findViewById(R.id.left_text);
        rightText = (TextView) view.findViewById(R.id.right_text);
        addView(view);
    }

    public void setLeftText(String txt) {
        if (leftText == null)
            return;
        leftText.setText(txt);
    }

    public void setRightText(String txt) {
        if (rightText == null)
            return;
        rightText.setText(txt);
    }

    public String getRightContent(){
        if (rightText == null)
            return null;
        return rightText.getText().toString();
    }
}
