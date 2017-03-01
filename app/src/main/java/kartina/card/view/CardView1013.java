package kartina.card.view;
/**
 * 搜索结果：课程
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import kartina.R;
import kartina.card.bean.CardBean;
import kartina.card.bean.CardBean1013;

/**
 * Created by David on 2016/11/17.
 */

public class CardView1013 extends SuperCardView<CardBean<CardBean1013>> {
    View view;
    private TextView mClassId;
    private TextView mClassName;
    private TextView mTeacherName;
    private TextView mExtraTag;
    private TextView mClassTime;
    private TextView mClassPlace;
    private ImageView mAction; //某个Action
    private TextView mName;
    private OnActionClickListener actionClickListener;
    private CardBean1013 cardBean1013;

    public CardView1013(Context context) {
        super(context);
    }

    @Override
    public View getView(Context context) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.cardview_1013, null, false);
            mClassTime = (TextView) view.findViewById(R.id.class_time);
            mClassId = (TextView) view.findViewById(R.id.class_id);
            mClassPlace = (TextView) view.findViewById(R.id.class_place);
            mExtraTag = (TextView) view.findViewById(R.id.extra_tag);
            mClassName = (TextView) view.findViewById(R.id.name);
            mTeacherName = (TextView) view.findViewById(R.id.extra_content);
            mAction = (ImageView) view.findViewById(R.id.action);
            mName = (TextView) view.findViewById(R.id.name);

            mAction.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Action Clicked!", Toast.LENGTH_SHORT).show();
                    if (cardBean1013.isActioned) {
                        cardBean1013.isActioned = false;
                        cardBean1013.likeNum -= 1;
                        mAction.setImageResource(R.mipmap.like_normal);
                    } else {
                        cardBean1013.isActioned = true;
                        cardBean1013.likeNum += 1;
                        mAction.setImageResource(R.mipmap.like_clicked);
                    }
                    if (actionClickListener != null) {
                        actionClickListener.onActionClick();
                    }
                }
            });
        }
        return view;
    }

    @Override
    public String getItemId() {
        if (cardBean1013 != null)
            return cardBean1013.itemId;
        return null;
    }

    @Override
    protected void setData(CardBean<CardBean1013> data) {
        if (data == null || data.data == null)
            return;
        cardBean1013 = data.data;
        if (cardBean1013.className != null && !cardBean1013.className.trim().equals("")) {
            mName.setText(cardBean1013.className);
            mName.setVisibility(VISIBLE);
        } else {
            mName.setVisibility(GONE);
        }

        mClassId.setText(cardBean1013.classId);
        mExtraTag.setText("任课老师：  ");
        mTeacherName.setText(cardBean1013.classTeacher);
        mClassName.setText(cardBean1013.className);
        mClassPlace.setText(cardBean1013.classPlace);
        mClassTime.setText(cardBean1013.classTime);
    }
    public interface OnActionClickListener{
        void onActionClick();
    }
}
