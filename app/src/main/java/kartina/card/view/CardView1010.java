package kartina.card.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import kartina.R;
import kartina.card.bean.CardBean;
import kartina.card.bean.CardBean1010;
import kartina.view.AvatarView;

/**
 * Created by David on 2016/11/9.
 */

public class CardView1010 extends SuperCardView<CardBean<CardBean1010>> {
    private AvatarView avatarView;
    private TextView contentView;
    private View view;
    private CardBean1010 cardBean1010;
    public CardView1010(Context context) {
        super(context);
    }

    public CardView1010(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardView1010(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public View getView(Context context) {
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.cardview_1010,null,false);
            avatarView = (AvatarView) view.findViewById(R.id.avatar_view);
            contentView = (TextView) view.findViewById(R.id.reply_content);
        }
        return view;
    }

    @Override
    public String getItemId() {
        if (cardBean1010 != null)
            return cardBean1010.itemId;
        return null;
    }

    @Override
    protected void setData(CardBean<CardBean1010> data) {
        if (data == null || data.data == null)
            return;
        cardBean1010 = data.data;
        avatarView.setData(data.data.avatarUrl, data.data.name,data.data.time);
        contentView.setText(data.data.content);
    }

}
