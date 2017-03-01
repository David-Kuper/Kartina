package kartina.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import kartina.R;
import kartina.model.ItemHeader;

/**
 * Created by David on 2016/11/5.
 */

public class ItemHeaderView extends FrameLayout {
    private CircleImageView mImageView;
    private TextView mFrom_who;
    private TextView mOrigin;
    private TextView mTime;
    private Spinner mSpinner;
    private ItemHeader mItemHeader;

    public ItemHeaderView(Context context) {
        super(context);
        init(context);
    }

    public ItemHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ItemHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_header_layout, null, false);

        mImageView = (CircleImageView) view.findViewById(R.id.cover);
        mFrom_who = (TextView) view.findViewById(R.id.from_who);
        mOrigin = (TextView) view.findViewById(R.id.origin);
        mTime = (TextView) view.findViewById(R.id.time);
        mSpinner = (Spinner) view.findViewById(R.id.action);

        addView(view);
    }

    public void setData(ItemHeader itemHeader) {
        if (itemHeader == null)
            return;
        mItemHeader = itemHeader;
        if (itemHeader.avarta != null){
            mImageView.setVisibility(VISIBLE);
        }else if(itemHeader.rid > 0){
            mImageView.setImageResource(itemHeader.rid);
            mImageView.setVisibility(VISIBLE);
        }else {
            mImageView.setVisibility(VISIBLE);
        }
        mImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mFrom_who.setText(itemHeader.from_who);
        mFrom_who.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入个人主页

            }
        });
        mOrigin.setText(itemHeader.origin);
        mOrigin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入来源课程

            }
        });
        mTime.setText(itemHeader.time);
    }
}
