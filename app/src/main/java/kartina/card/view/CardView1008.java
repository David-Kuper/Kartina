package kartina.card.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import kartina.R;
import kartina.card.bean.CardBean;
import kartina.card.bean.CardBean1008;
import kartina.util.DensityUtil;
/**
 * 组别栏
 */

/**
 * Created by David on 2016/11/7.
 */

public class CardView1008 extends SuperCardView<CardBean<CardBean1008>> {
    private TextView groupNumView;
    private TextView groupNameView;
    private TextView txtMoreView;
    private View view;
    private CardBean1008 cardBean1008;

    public static final int MORE_CLASS = 1;
    public static final int MORE_TALK = 2;
    public static final int MORE_TEACHER = 3;
    private OnCheckMoreClickListener onCheckMoreClickListener;

    public CardView1008(Context context) {
        super(context);
    }

    public CardView1008(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardView1008(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void setData(final CardBean<CardBean1008> data) {
        if (data == null || data.data == null)
            return;

        cardBean1008 = data.data;
        groupNameView.setText(data.data.groupName);
        groupNumView.setText("( " + data.data.groupNum + " )");
        if (data.data.showMore) {
            txtMoreView.setVisibility(VISIBLE);
            txtMoreView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onCheckMoreClickListener != null) {
                        onCheckMoreClickListener.OnCheckMore(data.data.groupName,data.data.moreType);
                    }
                }
            });
        } else {
            txtMoreView.setVisibility(GONE);
        }
    }

    @Override
    protected void fillView(CardView containerView) {
        containerView.setRadius(0);
        containerView.setCardElevation(DensityUtil.dp2px(getContext(), 2));
        FrameLayout.LayoutParams params = (LayoutParams) containerView.getLayoutParams();
        params.setMargins(0, DensityUtil.dp2px(getContext(), cardBean1008.marginTopOfDp), 0, DensityUtil.dp2px(getContext(), 2));
    }

    @Override
    public View getView(Context context) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.cardview_1008, null, false);
            groupNameView = (TextView) view.findViewById(R.id.group_name);
            groupNumView = (TextView) view.findViewById(R.id.group_num);
            txtMoreView = (TextView) view.findViewById(R.id.txt_more);
        }
        return view;
    }

    @Override
    public String getItemId() {
        if (cardBean1008 != null)
            return cardBean1008.itemId;
        return null;
    }

    public void setOnCheckMoreClickListener(OnCheckMoreClickListener onCheckMoreClickListener) {
        if (onCheckMoreClickListener == null)
            return;
        this.onCheckMoreClickListener = onCheckMoreClickListener;
    }

    public interface OnCheckMoreClickListener {
        void OnCheckMore(String title,int type);
    }
}
