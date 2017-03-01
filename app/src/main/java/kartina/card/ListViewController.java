package kartina.card;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.List;

import kartina.card.bean.CardBean;


/**
 * Created by David on 2016/9/16.
 */

public class ListViewController extends IViewController<CardListView,List<CardBean>> {
    private CardAdapter cardAdapter;
    private CardListView cardListView;
    private Context context;
    CardAdapter.CardAdapterListener adapterListener;
    CardAdapter.CardAdapterMoreListener adapterMoreListener;
    private boolean openPreViewList = false;
    public  ListViewController(@NonNull Activity context, boolean openPreViewList){
        this.context = context;
        this.openPreViewList = openPreViewList;
    }
    @Override
    public void setData(List<CardBean> data,boolean hasNext) {
        if (cardListView == null)
            throw new NullPointerException("CardListView can not be null if you want setData for it!");

        if (data == null )
            return;
        if (cardAdapter == null) {
            cardAdapter = new CardAdapter(context, data,hasNext);
        }else {
            cardAdapter.setData(data,hasNext);
        }
        /**
         * 最好使用异步任务
         */
        if (openPreViewList) {
            cardAdapter.setPreViewListMap(data);
        }
        if (adapterMoreListener != null)
            cardListView.setOnCheckMoreListener(adapterMoreListener);
        if (adapterListener != null)
            cardListView.setOnItemClickListener(adapterListener);
        setAdapter(cardAdapter);
    }

    public void addHeaderView(View view){
        if (view == null)
            return;
        cardListView.addHeaderView(view);
    }

    @Override
    public void addData(List<CardBean> data,boolean hasNext) {
        if (data == null || cardAdapter == null)
            return;
        cardAdapter.addData(data,hasNext);
    }

    @Override
    public void setView(CardListView view) {
        if (view == null)
            return;
        cardListView = view;
    }

    public void setOnItemClickListener(CardAdapter.CardAdapterListener adapterListener){
        if (adapterListener == null)
            return;
        if (cardAdapter == null){
            this.adapterListener = adapterListener;
        }else {
            cardAdapter.setCardAdapterListener(adapterListener);
        }
    }

    public void setOnCheckMoreListener(CardAdapter.CardAdapterMoreListener adapterMoreListener){
        if (adapterMoreListener == null)
            return;
        if (cardAdapter == null){
            this.adapterMoreListener = adapterMoreListener;
        }else {
             cardAdapter.setCardAdapterMoreListener(adapterMoreListener);
        }

    }

    public void addData(CardBean data) {
        if (data == null || cardAdapter == null)
            return;
        cardAdapter.addData(data);
    }

    public void setOnScrollListener(AbsListView.OnScrollListener onScrollListener){
        if (onScrollListener == null)
            return;
        cardListView.setOnScrollListener(onScrollListener);
    }

    private void setAdapter(CardAdapter adapter){
        if (cardListView == null)
            throw new NullPointerException("CardListView can not be null if you want setAdapter for it!");
        if (adapter == null)
            return;
        cardAdapter = adapter;
        cardListView.setAdapter(adapter);
    }

    public void setPreViewListState(boolean state){
        openPreViewList = state;
    }

    @Deprecated
    public void setMode(PullToRefreshBase.Mode mode){
        if (cardListView == null)
            return;
        cardListView.setMode(mode);
    }
}
