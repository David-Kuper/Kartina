package kartina.card;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;
import java.util.Map;

import kartina.R;
import kartina.card.bean.CardBean;


/**
 * Created by David on 2016/9/16.
 */

public class CardListView extends IListViewGroup<ListView, List<CardBean>> {
    private String TAG = "CardListView";
    private CardAdapter mAdapter;
    private ListView listView;
    CardAdapter.CardAdapterListener adapterListener;
    CardAdapter.CardAdapterMoreListener adapterMoreListener;
    private PullToRefreshBase.Mode mPullMode = PullToRefreshBase.Mode.DISABLED;

    public CardListView(Context context) {
        this(context, null);
    }

    public CardListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public CardListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected ListView initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.listview,null,false);
        listView = (ListView) view;
        return listView;
    }

    public void setAdapter(CardAdapter adapter){
        if (adapter == null || listView == null)
            return;
        mAdapter = adapter;
        listView.setAdapter(mAdapter);
        if (adapterListener != null)
            mAdapter.setCardAdapterListener(adapterListener);
        if (adapterMoreListener != null)
            mAdapter.setCardAdapterMoreListener(adapterMoreListener);
    }
    public void addHeaderView(@NonNull View view){
        if (listView == null)
            return;
        listView.addHeaderView(view);
    }

    public void setOnItemClickListener(CardAdapter.CardAdapterListener adapterListener){
        if (adapterListener == null)
            return;
        this.adapterListener = adapterListener;
    }

    public void setOnCheckMoreListener(CardAdapter.CardAdapterMoreListener adapterMoreListener){
        if (adapterMoreListener == null)
            return;
        this.adapterMoreListener = adapterMoreListener;
    }
    public void setOnScrollListener(AbsListView.OnScrollListener onScrollListener){
        if (onScrollListener == null)
            return;
        listView.setOnScrollListener(onScrollListener);
    }
    @Deprecated
    public void setMode(PullToRefreshBase.Mode mode){
        if (mPullMode == mode || listView == null)
            return;

        mPullMode = mode;
//        mPullRefreshListView.setMode(mode);
    }

}
