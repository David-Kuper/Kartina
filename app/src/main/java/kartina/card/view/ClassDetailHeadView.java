package kartina.card.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import kartina.R;
import kartina.adapter.ClassItemAdapter;
import kartina.card.IViewGroup;
import kartina.card.bean.CardBean;
import kartina.card.bean.ClassDetailHeadBean;
import kartina.util.DensityUtil;
/**
 * 课程详情头部
 */

/**
 * Created by David on 2016/11/7.
 */

public class ClassDetailHeadView extends IViewGroup<LinearLayout,ClassDetailHeadBean> {
    private TextView mClassName;
    private TextView mClassCredit;
    private TextView mClassHour;
    private TextView mClassNum;
    private TextView mClassCount;
    private TextView mNoteCount;
    private ImageView mAction;
    private TextView mDescribe;
    private OnActionClickListener onActionClickListener;

    private RecyclerView mClassItemList;
    private ClassItemAdapter classItemAdapter;

    private View view;
    private ClassDetailHeadBean data;

    public ClassDetailHeadView(Context context) {
        this(context, null);
    }

    public ClassDetailHeadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClassDetailHeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @Override
    protected LinearLayout initView(Context context) {
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.cardview_1007, null, false);
            mClassCredit = (TextView) view.findViewById(R.id.class_credit);
            mClassHour = (TextView) view.findViewById(R.id.class_hour);
            mClassItemList = (RecyclerView) view.findViewById(R.id.head_class_list);
            mClassName = (TextView) view.findViewById(R.id.class_name);
            mClassNum = (TextView) view.findViewById(R.id.class_num);
            mDescribe = (TextView) view.findViewById(R.id.describe);
            mAction = (ImageView) view.findViewById(R.id.action);
            mClassCount = (TextView) view.findViewById(R.id.class_count);
            mNoteCount = (TextView) view.findViewById(R.id.note_count);

            mAction.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (data.isActioned){
                        data.isActioned = false;
                        mAction.setImageResource(R.mipmap.focus_normal);
                    }else {
                        data.isActioned = true;
                        mAction.setImageResource(R.mipmap.focus_clicked);
                    }

                    if (onActionClickListener != null){
                        onActionClickListener.OnActionClick();
                    }
                }
            });

            mClassItemList.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        return (LinearLayout) view;
    }

    @Override
    protected void fillView(LinearLayout containerView) {
        FrameLayout.LayoutParams params = (LayoutParams) containerView.getLayoutParams();
        params.setMargins(0,0,0, DensityUtil.dp2px(getContext(),6));
    }



    public void fillViewWithData(ClassDetailHeadBean data) {
        if (data == null)
            return;
        this.data = data;
        mClassCredit.setText(data.classCredit);
        mClassHour.setText(data.classHour);
        mClassName.setText(data.className);
        mClassNum.setText(data.classNum);
        mDescribe.setText(data.describe);
        if (data.classItemList != null && data.classItemList.size() > 0){
            mClassCount.setText("( "+data.classItemList.size()+" )");
        }
        if (data.classItemList != null && data.classItemList.size() > 0) {
            if (classItemAdapter == null) {
                classItemAdapter = new ClassItemAdapter(getContext(), data.classItemList,true);
            } else {
                classItemAdapter.setData(data.classItemList,true);
            }
            mClassItemList.setAdapter(classItemAdapter);
        }
    }

    public void setOnActionClickListener(OnActionClickListener onActionClickListener) {
        if (onActionClickListener == null)
            return;
        this.onActionClickListener = onActionClickListener;
    }

    public interface OnActionClickListener{
        void OnActionClick();
    }

    public void setNoteCount(String noteCount){
        this.mNoteCount.setText("( "+noteCount+" )");
    }
}
