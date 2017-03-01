package kartina.card.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import kartina.R;
import kartina.card.bean.CardBean;
import kartina.card.bean.CardBean1001;
import kartina.model.ItemBottom;
import kartina.view.ItemBottomView;
/**
 * 首页老师卡片
 */

/**
 * Created by David on 2016/9/16.
 */

public class CardView1001 extends SuperCardView<CardBean<CardBean1001>> {
    private ImageView avatarView;
    private TextView nameView;
    private TextView academyView;
    private TextView prosationalTitleView;
    private TextView schoolView;
    private ImageView actionView;
    private TextView searchFiledView;
    private ItemBottomView itemBottomView;
    CardBean1001 cardBean1001;

    View view;

    public CardView1001(Context context) {
        this(context, null);
    }

    public CardView1001(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardView1001(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void setData(CardBean<CardBean1001> data) {
        if (data == null)
            return;
        cardBean1001 = data.data;
        //        avatarView.setImageResource();
        nameView.setText(cardBean1001.name);
        academyView.setText(cardBean1001.academy);
        prosationalTitleView.setText(cardBean1001.prosational);
        schoolView.setText(cardBean1001.school);
        searchFiledView.setText(cardBean1001.searchFiled);

        ItemBottom itemBottom = new ItemBottom();
        itemBottom.comment_num = cardBean1001.commentNum;
        itemBottom.praise_num = cardBean1001.praiseNum;
        itemBottom.focus_num = cardBean1001.focusNum;
        itemBottomView.setData(itemBottom);

        actionView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Clicked Action", Toast.LENGTH_SHORT).show();
                if (!cardBean1001.isActioned) {
                    cardBean1001.isActioned = true;
                    actionView.setImageResource(R.mipmap.focus_clicked);
                } else {
                    cardBean1001.isActioned = false;
                    actionView.setImageResource(R.mipmap.focus_normal);
                }
            }
        });
    }


    @Override
    public View getView(Context context) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.cardview_1001, null, false);
            avatarView = (ImageView) view.findViewById(R.id.avatar);
            nameView = (TextView) view.findViewById(R.id.name);
            academyView = (TextView) view.findViewById(R.id.academy);
            prosationalTitleView = (TextView) view.findViewById(R.id.prosationaltitle);
            schoolView = (TextView) view.findViewById(R.id.school);
            actionView = (ImageView) view.findViewById(R.id.action);
            searchFiledView = (TextView) view.findViewById(R.id.research_field);
            itemBottomView = (ItemBottomView) view.findViewById(R.id.item_bottom);
        }
        return view;
    }

    @Override
    public String getItemId() {
        if (cardBean1001 != null)
            return cardBean1001.itemId;
        return null;
    }
}
