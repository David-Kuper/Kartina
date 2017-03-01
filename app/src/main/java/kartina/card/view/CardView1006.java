package kartina.card.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import kartina.R;
import kartina.card.bean.CardBean;
import kartina.card.bean.CardBean1006;
import kartina.view.AvatarView;
import kartina.view.ItemBottomView;
import kartina.view.NoteBodyView;

/**
 * 课程下帖子卡片
 */

/**
 * Created by David on 2016/11/5.
 */

public class CardView1006 extends SuperCardView<CardBean<CardBean1006>> {
    private AvatarView userView;
    private ItemBottomView mItemBottomView;
    private NoteBodyView noteBodyView;
    private CardBean1006 cardBean1006;
    private View view;

    public CardView1006(Context context) {
        super(context);
    }

    public CardView1006(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardView1006(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void setData(CardBean<CardBean1006> data) {
        if (data == null) {
            return;
        }
        cardBean1006 = data.data;
        userView.setData(data.data.avatarUrl,data.data.userName,data.data.time);
        mItemBottomView.setData(data.data.itemBottom);
        noteBodyView.setData(data.data.noteBody);
    }



    @Override
    public View getView(Context context) {
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.cardview_1006, null, false);
            userView = (AvatarView) view.findViewById(R.id.user);
            mItemBottomView = (ItemBottomView) view.findViewById(R.id.item_bottom);
            noteBodyView = (NoteBodyView) view.findViewById(R.id.note_body);
        }
        return view;
    }

    @Override
    public String getItemId() {
        if (cardBean1006 != null)
            return cardBean1006.itemId;
        return null;
    }
}
