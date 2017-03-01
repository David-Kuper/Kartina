package kartina.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import kartina.R;
import kartina.model.ClassItem;

/**
 * Created by David on 2016/11/8.
 */

public class AvatarView extends FrameLayout {
    private CircleImageView avatarView;
    private TextView fromView;
    private TextView timeView;

    private String avatar;
    private String name;

    public AvatarView(Context context) {
        this(context, null);
    }

    public AvatarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AvatarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.avatar_view, null, false);
        avatarView = (CircleImageView) view.findViewById(R.id.avatar);
        fromView = (TextView) view.findViewById(R.id.from_who);
        timeView = (TextView) view.findViewById(R.id.time);

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                 //进入个人主页
            }
        });
        addView(view);
    }

    public void setData(String avatar, String name) {
        if (avatar != null) {

        }else {
            avatarView.setImageResource(R.mipmap.ic_launcher);
        }
        fromView.setText(name);
    }

    public void setData(String avatar, String name,String time) {
        setData(avatar,name);
        if (time != null){
            timeView.setText("时间： "+time);
            timeView.setVisibility(VISIBLE);
        }
    }
}
