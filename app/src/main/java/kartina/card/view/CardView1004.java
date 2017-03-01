package kartina.card.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import kartina.R;
import kartina.card.bean.CardBean;
import kartina.card.bean.CardBean1004;
import kartina.view.ItemBottomView;
import kartina.view.ItemHeaderView;
import kartina.view.NoteBodyView;
/**
 * 首页帖子
 */

/**
 * Created by David on 2016/11/5.
 */

public class CardView1004 extends SuperCardView<CardBean<CardBean1004>> {
    private ItemHeaderView itemHeaderView;
    private ItemBottomView itemBottomView;
    private NoteBodyView noteBodyView;
    private CardBean1004 cardBean1004;

    private View view;

    public CardView1004(Context context) {
        super(context);
    }

    public CardView1004(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardView1004(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public View getView(Context context) {
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.cardview_1004, null, false);
            itemBottomView = (ItemBottomView) view.findViewById(R.id.item_bottom);
            itemHeaderView = (ItemHeaderView) view.findViewById(R.id.item_header);
            noteBodyView = (NoteBodyView) view.findViewById(R.id.note_body);
        }
        return view;
    }

    @Override
    public String getItemId() {
        if (cardBean1004 != null){
            return cardBean1004.itemId;
        }
        return null;
    }

    @Override
    protected void setData(CardBean<CardBean1004> data) {
        if (data == null)
            return;
        cardBean1004 = data.data;
        itemHeaderView.setData(cardBean1004.itemHeader);
        itemBottomView.setData(cardBean1004.itemBottom);
        noteBodyView.setData(cardBean1004.noteBody);
    }


}
