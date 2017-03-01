package kartina.card.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import kartina.R;
import kartina.activity.ClassActivity;
import kartina.card.bean.CardBean;
import kartina.card.bean.CardBean1005;
import kartina.view.ItemBottomView;
import kartina.view.ItemHeaderView;
import kartina.view.NoteBodyView;
/**
 * 话题卡片
 */

/**
 * Created by David on 2016/11/5.
 */

public class CardView1005 extends SuperCardView<CardBean<CardBean1005>> {
    private View view;
    private ItemHeaderView mItemHeaderView;
    private ItemBottomView mItemBottomView;
    private NoteBodyView noteBodyView;
    private CardBean1005 mCardBean;

    public CardView1005(Context context) {
        super(context);
    }

    public CardView1005(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardView1005(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void setData(CardBean<CardBean1005> data) {
        if (data == null || data.data == null)
            return;

        mCardBean = data.data;
        mItemBottomView.setData(mCardBean.mItemBottom);
        mItemHeaderView.setData(mCardBean.mItemHeader);
        noteBodyView.setData(mCardBean.noteBody);
    }


    @Override
    public View getView(Context context) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.cardview_1005, null, false);
            mItemHeaderView = (ItemHeaderView) view.findViewById(R.id.item_header);
            mItemBottomView = (ItemBottomView) view.findViewById(R.id.item_bottom);
            noteBodyView = (NoteBodyView) view.findViewById(R.id.note_body);
        }
        return view;
    }

    @Override
    public String getItemId() {
        if (mCardBean != null) {
            return mCardBean.itemId;
        }
        return null;
    }

}
