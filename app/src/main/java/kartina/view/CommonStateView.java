package kartina.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import kartina.R;

/**
 * 公共状态页面
 */


/**
 * Created by David on 2016/10/31.
 */

public class CommonStateView extends FrameLayout {
    //空内容
    public static final int CONTENT_EMPTY = 0;
    //网络链接失败
    public static final int NETWORK_ERROR = 1;
    //服务器异常
    public static final int SERVER_ERROR = 2;

    private ImageView mCommonImg;
    private TextView  mCommonText;
    private Button    mCommonBtn;


    public CommonStateView(Context context) {
        this(context,null);
    }

    public CommonStateView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CommonStateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.commone_state_view,null,false);
        mCommonBtn = (Button) view.findViewById(R.id.common_state_reloadBtn);
        mCommonImg = (ImageView) view.findViewById(R.id.common_state_img);
        mCommonText = (TextView) view.findViewById(R.id.common_state_text);
        addView(view);
    }

    public void setStateView(int code){
        switch (code){
            case CommonStateView.CONTENT_EMPTY:

                break;
            case CommonStateView.NETWORK_ERROR:

                break;
            case CommonStateView.SERVER_ERROR:

                break;
            default:

        }
    }

}
