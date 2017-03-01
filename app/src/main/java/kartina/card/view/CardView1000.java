package kartina.card.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import kartina.R;
import kartina.card.OnItemActionClickListener;
import kartina.card.ViewType.CardViewType;
import kartina.card.bean.CardBean;
import kartina.card.bean.CardBean1000;
import kartina.card.bean.CardBean1001;
/**
 * 首页课程卡片
 */

/**
 * Created by David on 2016/9/16.
 */

public class CardView1000 extends SuperCardView<CardBean<CardBean1000>> {
    private TextView nameView;
    private TextView describeView;
    private TextView academyView;
    private TextView noteNumView;
    private ImageView noteImage;
    private TextView focusNumView;
    private ImageView focusImag;
    private CardBean1000 cardBean1000;
    private OnItemActionClickListener onItemActionClickListener;

    View view;

    public CardView1000(Context context) {
        this(context,null);
    }

    public CardView1000(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CardView1000(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 将数据装填至View
     * @param data
     */
    @Override
    protected void setData(CardBean<CardBean1000> data) {
        if (data == null)
            return;
        cardBean1000 =  data.data;
        nameView.setText(cardBean1000.name);
        describeView.setText(cardBean1000.describe);
        academyView.setText(cardBean1000.academy);
        focusNumView.setText(""+cardBean1000.focusNum);
        noteNumView.setText(""+cardBean1000.noteNum);
    }




    @Override
    public View getView(Context context) {
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.cardview_1000, this, false);
            nameView = (TextView) view.findViewById(R.id.class_name);
            describeView = (TextView) view.findViewById(R.id.describe);
            academyView = (TextView) view.findViewById(R.id.academy);
            focusNumView = (TextView) view.findViewById(R.id.focus_num);
            focusImag = (ImageView) view.findViewById(R.id.focus_img);
            noteNumView = (TextView) view.findViewById(R.id.note_num);
            noteImage = (ImageView) view.findViewById(R.id.note_img);

            nameView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转到课程主页

                }
            });

            view.findViewById(R.id.focus_layout).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!cardBean1000.isFocused){
                        cardBean1000.isFocused = true;
                        cardBean1000.focusNum += 1;
                        focusImag.setImageResource(R.mipmap.focus_clicked);
                    }else {
                        cardBean1000.isFocused = false;
                        cardBean1000.focusNum -= 1;
                        focusImag.setImageResource(R.mipmap.focus_normal);
                    }
                    focusNumView.setText(""+cardBean1000.focusNum);
                }
            });
            view.findViewById(R.id.note_layout).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转到评论列表

                }
            });
        }
        return view;
    }

    @Override
    public String getItemId() {
        if (cardBean1000 != null){
            return cardBean1000.itemId;
        }
        return null;
    }

}
