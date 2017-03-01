package kartina.card.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.io.Serializable;

import kartina.R;
import kartina.card.IViewGroup;
import kartina.card.ViewType.CardViewType;
import kartina.card.bean.CardBean;
import kartina.util.DensityUtil;


/**
 * Created by David on 2016/9/16.
 */

public abstract class SuperCardView<D> extends IViewGroup<CardView, D> {
    CardView cardView;
    String itemId;
    public SuperCardView(Context context) {
        super(context);
    }

    public SuperCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SuperCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void fillViewWithData(D data) {
        if (null != data) {
            setData(data);
            itemId = getItemId();
            fillView(containerView);
        }
    }

    @Override
    protected CardView initView(Context context) {
        View view = getView(context);
        if (view != null) {
            if (cardView == null)
                cardView = getCardView(context);
            cardView.addView(view);
        }
        return cardView;
    }

    private CardView getCardView(Context context){
        if (cardView == null){
            cardView = new CardView(context);
            cardView.setCardElevation(DensityUtil.dp2px(context,6));
            cardView.setContentPadding(5,5,5,5);
            cardView.setBackground(getResources().getDrawable(R.drawable.text_click_selector));
            cardView.setRadius(DensityUtil.dp2px(context,5));
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(5,0,5,DensityUtil.dp2px(context,8));
            cardView.setLayoutParams(params);
        }
        return cardView;
    }

    @Override
    protected void fillView(CardView containerView) {

    }


    public abstract View getView(Context context);
    public abstract String getItemId();

    /**
     * 将数据装填至View
     *
     * @param data
     */
    protected abstract void setData(D data);
}
