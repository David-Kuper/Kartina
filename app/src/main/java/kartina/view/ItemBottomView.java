package kartina.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import kartina.R;
import kartina.model.ItemBottom;

/**
 * Created by David on 2016/11/5.
 */

public class ItemBottomView extends FrameLayout {
    private TextView mFocus;
    private TextView mComment;
    private TextView mPraise;
    private ImageView mFocusImg;
    private ImageView mCommentImg;
    private ImageView mPraiseImg;
    private ItemBottom mItemBottom;
    private LinearLayout line1;
    private LinearLayout line2;
    private LinearLayout line3;

    public ItemBottomView(Context context) {
        super(context);
        init(context);
    }

    public ItemBottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ItemBottomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bottom_layout, this, false);
        line1 = (LinearLayout) view.findViewById(R.id.line1);
        line2 = (LinearLayout) view.findViewById(R.id.line2);
        line3 = (LinearLayout) view.findViewById(R.id.line3);

        mFocus = (TextView) view.findViewById(R.id.focus);
        mComment = (TextView) view.findViewById(R.id.comment);
        mPraise = (TextView) view.findViewById(R.id.praise);
        mFocusImg = (ImageView) view.findViewById(R.id.focus_img);
        mCommentImg = (ImageView) view.findViewById(R.id.comment_img);
        mPraiseImg = (ImageView) view.findViewById(R.id.praise_img);
        addView(view);
    }

    public void setData(ItemBottom itemBottom) {
        if (itemBottom == null)
            return;
        mItemBottom = itemBottom;
        if (mItemBottom.comment_num != -1){
            mComment.setText(""+mItemBottom.comment_num);
            line1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转到评论界面
                }
            });
            mComment.setText(""+mItemBottom.comment_num);
            line1.setVisibility(VISIBLE);
        }else {
            line1.setVisibility(GONE);
        }
        if (mItemBottom.focus_num != -1){
            mFocus.setText(""+mItemBottom.focus_num);
            line3.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemBottom.isFocused){
                        mItemBottom.isFocused = false;
                        mItemBottom.focus_num -= 1;
                        mFocusImg.setImageResource(R.mipmap.focus_normal);
                    }else {
                        mItemBottom.isFocused = true;
                        mItemBottom.focus_num += 1;
                        mFocusImg.setImageResource(R.mipmap.focus_clicked);
                    }
                    mFocus.setText(""+mItemBottom.focus_num);
                }
            });
            line3.setVisibility(VISIBLE);
        }else {
            line3.setVisibility(GONE);
        }
        if (mItemBottom.praise_num != -1){
            mPraise.setText(""+mItemBottom.praise_num);
            line2.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemBottom.isLiked){
                        mItemBottom.isLiked = false;
                        mItemBottom.praise_num -= 1;
                        mPraiseImg.setImageResource(R.mipmap.like_normal);
                    }else {
                        mItemBottom.isLiked = true;
                        mItemBottom.praise_num += 1;
                        mPraiseImg.setImageResource(R.mipmap.like_clicked);
                    }
                    mPraise.setText(""+mItemBottom.praise_num);
                }
            });
            line2.setVisibility(VISIBLE);
        }else {
            line2.setVisibility(GONE);
        }
    }
}
