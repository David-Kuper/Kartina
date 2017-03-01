package kartina.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import kartina.R;
import kartina.model.ClassItem;

/**
 * Created by David on 2016/11/7.
 */

public class ClassItemView extends FrameLayout {
    private TextView mClassId;
    private TextView mExtraTag;
    private TextView mExtraContent;
    private TextView mClassTime;
    private TextView mClassPlace;
    private ImageView mAction; //某个Action
    private TextView mName;
    private OnActionClickListener actionClickListener;
    private ClassItem classItem;


    public ClassItemView(Context context) {
        this(context, null);
    }

    public ClassItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClassItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.class_item, null, false);
        mClassTime = (TextView) view.findViewById(R.id.class_time);
        mClassId = (TextView) view.findViewById(R.id.class_id);
        mClassPlace = (TextView) view.findViewById(R.id.class_place);
        mExtraTag = (TextView) view.findViewById(R.id.extra_tag);
        mExtraContent = (TextView) view.findViewById(R.id.extra_content);
        mAction = (ImageView) view.findViewById(R.id.action);
        mName = (TextView) view.findViewById(R.id.name);

        mAction.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Action Clicked!", Toast.LENGTH_SHORT).show();
                if (classItem.isActioned) {
                    classItem.isActioned = false;
                    classItem.likeNum -= 1;
                    mAction.setImageResource(R.mipmap.like_normal);
                } else {
                    classItem.isActioned = true;
                    classItem.likeNum += 1;
                    mAction.setImageResource(R.mipmap.like_clicked);
                }
                if (actionClickListener != null) {
                    actionClickListener.onActionClick();
                }
            }
        });
        addView(view);
    }

    public void setActionClickListener(OnActionClickListener actionClickListener) {
        this.actionClickListener = actionClickListener;
    }

    public void setData(ClassItem classItem) {
        if (classItem == null)
            return;
        this.classItem = classItem;
        mName.setVisibility(GONE);

        mClassId.setText(classItem.classId);
        if (classItem.classTeacher != null) {
            mExtraTag.setText("任课老师：  ");
            mExtraContent.setText(classItem.classTeacher);
        } else {
            mExtraTag.setText("课程名：  ");
            mExtraContent.setText(classItem.className);
        }
        mClassPlace.setText(classItem.classPlace);
        mClassTime.setText(classItem.classTime);
    }

    public interface OnActionClickListener {
        void onActionClick();
    }
}
