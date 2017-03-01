package kartina.card.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import kartina.R;
import kartina.adapter.ClassItemAdapter;
import kartina.card.IViewGroup;
import kartina.card.bean.CardBean;
import kartina.card.bean.TeacherHeadBean;

/**
 * Created by David on 2016/11/9.
 */

public class TeacherHeadView extends IViewGroup<CardView,TeacherHeadBean> {
    private ImageView avatarView;
    private TextView nameView;
    private TextView academyView;
    private TextView prosationalTitleView;
    private TextView schoolView;
    private ImageView actionView;
    private TextView searchFiledView;
    private TextView describeView;
    private RecyclerView teachListView;
    private TextView commentView;
    private TeacherHeadBean cardBean1011;
    private ClassItemAdapter classItemAdapter;
    private View view;
    public TeacherHeadView(Context context) {
        super(context);
    }

    public TeacherHeadView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TeacherHeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected CardView initView(Context context) {
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.cardview_1011, null, false);
            avatarView = (ImageView) view.findViewById(R.id.avatar);
            nameView = (TextView) view.findViewById(R.id.name);
            academyView = (TextView) view.findViewById(R.id.academy);
            prosationalTitleView = (TextView) view.findViewById(R.id.prosationaltitle);
            schoolView = (TextView) view.findViewById(R.id.school);
            actionView = (ImageView) view.findViewById(R.id.action);
            searchFiledView = (TextView) view.findViewById(R.id.research_field);
            describeView = (TextView) view.findViewById(R.id.describe);
            teachListView = (RecyclerView) view.findViewById(R.id.teach_list);
            commentView = (TextView) view.findViewById(R.id.comment_num);
            actionView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),"Clicked Action",Toast.LENGTH_SHORT).show();
                    if (cardBean1011!= null ){
                        if (cardBean1011.isActioned){
                            cardBean1011.isActioned = false;
                            actionView.setImageResource(R.mipmap.focus_normal);
                        }else  {
                            cardBean1011.isActioned = true;
                            actionView.setImageResource(R.mipmap.focus_clicked);
                        }
                    }
                }
            });
        }
        return (CardView) view;
    }

    @Override
    protected void fillView(CardView containerView) {
    }

    public void fillViewWithData(TeacherHeadBean data) {
         if (data == null )
             return;
         cardBean1011 = data;
        //avatarView.setImageResource();
        nameView.setText(cardBean1011.name);
        academyView.setText(cardBean1011.academy);
        prosationalTitleView.setText(cardBean1011.prosational);
        schoolView.setText(cardBean1011.school);
        describeView.setText(cardBean1011.describe);
        commentView.setText(" "+cardBean1011.commentNum);
        searchFiledView.setText(cardBean1011.searchFiled);
        if (cardBean1011.teachList != null && cardBean1011.teachList.size() > 0) {
            if (classItemAdapter == null) {
                classItemAdapter = new ClassItemAdapter(getContext(), cardBean1011.teachList,true);
                teachListView.setAdapter(classItemAdapter);
            } else {
                classItemAdapter.setData(cardBean1011.teachList,true);
            }
        }

    }
}
