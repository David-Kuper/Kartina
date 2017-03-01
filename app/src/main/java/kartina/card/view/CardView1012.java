package kartina.card.view;
/**
 * 搜索结果：老师
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import kartina.R;
import kartina.card.bean.CardBean;
import kartina.card.bean.CardBean1012;
import kartina.view.CircleImageView;

/**
 * Created by David on 2016/11/17.
 */

public class CardView1012 extends SuperCardView<CardBean<CardBean1012>> {
    private View view;
    private CircleImageView avatarView;
    private TextView titleView;
    private TextView describeView;
    private TextView focusView;
    private TextView commentView;
    private TextView likeView;
    private TextView tagView;   //打标（话题、帖子）
    private CardBean1012 cardBean1012;

    public CardView1012(Context context) {
        super(context);
    }

    public CardView1012(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardView1012(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public View getView(Context context) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.cardview_1012, null, false);
            avatarView = (CircleImageView) view.findViewById(R.id.avatar);
            titleView = (TextView) view.findViewById(R.id.name);
            describeView = (TextView) view.findViewById(R.id.describe);
            focusView = (TextView) view.findViewById(R.id.focus);
            likeView = (TextView) view.findViewById(R.id.like);
            commentView = (TextView) view.findViewById(R.id.comment);
            tagView = (TextView) view.findViewById(R.id.tag);
        }
        return view;
    }

    @Override
    public String getItemId() {
        if (cardBean1012 != null)
            return cardBean1012.itemId;
        return null;
    }

    @Override
    protected void setData(CardBean<CardBean1012> data) {
        if (data == null || data.data == null) {
            return;
        }
        cardBean1012 = data.data;
//        avatarView.setImageResource();
        titleView.setText(cardBean1012.name);
        describeView.setText(cardBean1012.describe);
        focusView.setText("关注 " + cardBean1012.focusNum);
        likeView.setText("赞 " + cardBean1012.likeNum);
        commentView.setText("评论" + cardBean1012.commentNum);
        tagView.setText(cardBean1012.tag);
    }
}
