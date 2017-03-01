package kartina.card.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import kartina.R;
import kartina.card.bean.CardBean;
import kartina.card.bean.CardBean1003;

/**
 * 搜索结果：帖子、话题
 */

/**
 * Created by David on 2016/11/5.
 */

public class CardView1003 extends SuperCardView<CardBean<CardBean1003>> {
    private View view;
    private ImageView avatarView;
    private TextView titleView;
    private TextView describeView;
    private TextView focusView;
    private TextView likeView;
    private TextView extraActionView;  //如果是话题则表示帖子数；如果是帖子则表示评论数
    private TextView tagView;   //打标（话题、帖子）
    CardBean1003 cardBean1003;

    public CardView1003(Context context) {
        super(context);
    }

    public CardView1003(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardView1003(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void setData(CardBean<CardBean1003> data) {
        if (data == null || data.data == null) {
            return;
        }
        cardBean1003 = data.data;
//        avatarView.setImageResource();
        titleView.setText(cardBean1003.title);
        describeView.setText(cardBean1003.describe);
        focusView.setText("关注 " + cardBean1003.focusNum);
        likeView.setText("赞 " + cardBean1003.likeNum);
        if (cardBean1003.type == 1) {//话题
            extraActionView.setText("帖子 " + cardBean1003.noteNum);
            tagView.setText("话题");
        } else {
            extraActionView.setText("评论" + cardBean1003.commentNum);
            tagView.setText("帖子");
        }
    }


    @Override
    public View getView(Context context) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.cardview_1003, null, false);
            avatarView = (ImageView) view.findViewById(R.id.avatar);
            titleView = (TextView) view.findViewById(R.id.title);
            describeView = (TextView) view.findViewById(R.id.describe);
            focusView = (TextView) view.findViewById(R.id.focus);
            likeView = (TextView) view.findViewById(R.id.like);
            extraActionView = (TextView) view.findViewById(R.id.extra_tag);
            tagView = (TextView) view.findViewById(R.id.tag);
        }
        return view;
    }

    @Override
    public String getItemId() {
        if (cardBean1003 != null)
            return cardBean1003.itemId;
        return null;
    }
}
