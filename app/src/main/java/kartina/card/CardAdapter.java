package kartina.card;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import kartina.activity.ItemResultActivity;
import kartina.card.ViewType.CardViewType;
import kartina.card.bean.CardBean;
import kartina.card.view.CardView1008;
import kartina.card.view.CheckMore;
import kartina.card.view.SuperCardView;


/**
 * Created by David on 2016/9/16.
 */

public class CardAdapter extends BaseAdapter {
    private static final String TAG = "CardAdapter";
    private Context context;
    private List<CardBean> list = new ArrayList<>();
    private boolean hashNext = false;
    private CardAdapterListener cardAdapterListener;
    private CardAdapterMoreListener cardAdapterMoreListener;
    private Map<Class, List<SuperCardView>> preViewListMap = new HashMap<>();

    public CardAdapter(Context context, List<CardBean> list,boolean hashNext) {
        this.context = context;
        this.list = list;
        this.hashNext = hashNext;
    }

    public CardAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CardBean> list,boolean hashNext) {
        if (null == list || list.size() <= 0)
            return;
        this.list = list;
        this.hashNext = hashNext;
        notifyDataSetChanged();
    }

    public void addData(List<CardBean> list,boolean hashNext) {
        if (null == list)
            return;
        this.list.addAll(list);
        this.hashNext = hashNext;
        notifyDataSetChanged();
    }

    public void addData(CardBean cardBean) {
        if (null == list || list.size() == 0)
            return;
        this.list.add(cardBean);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (hashNext && list.size() > 0)
            return list.size() + 1;
        else
            return list.size();
    }

    @Override
    public Object getItem(int i) {
        if (i < list.size()) {
            return list.get(i);
        } else if (hashNext){
            CardBean checkMore = new CardBean<>();
            checkMore.data = new Object();
            checkMore.cardViewType = CardViewType.CardViewType1002.type;
            return checkMore;
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    //TODO 重要知识点

    /**
     * 该方法在getView之前调用，从缓存中寻到对应的View
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position < list.size()) {
            return CardViewType.getCardViewByType(list.get(position).cardViewType).type;
        } else {
            return CardViewType.CardViewType1002.type;
        }
    }

    //TODO

    /**
     * 该方法只在setAdapter的时候调用
     *
     * @return
     */
    @Override
    public int getViewTypeCount() {
//        Log.e(TAG, "getViewTypeCount");
        if (CardViewType.values().length <= 0) {
            return 1;
        }
        return CardViewType.values().length;
    }

    /**
     * 预加载
     *
     * @param list
     */
    public void setPreViewListMap(List<CardBean> list) {
//        Log.e(TAG, "setPreViewListMap");
        if (list == null) {
            return;
        }
        CardBean cardBean;
        List<SuperCardView> viewList;
        for (int i = 0; i < list.size(); i++) {
            cardBean = list.get(i);
            CardViewType cardViewType = CardViewType.getCardViewByType(cardBean.cardViewType);
            if (preViewListMap.containsKey(cardViewType.viewClazz)) {
                viewList = preViewListMap.get(cardViewType.viewClazz);
                if (viewList.size() < cardViewType.PreNum) {
                    //反射注册一个View
                    SuperCardView cardView = getSuperCardView(cardViewType.viewClazz.getName(), context);
                    if (cardView != null)
                        viewList.add(cardView);
                }
            } else if (cardViewType.PreNum > 0) {
                viewList = new ArrayList<>();
                SuperCardView cardView = getSuperCardView(cardViewType.viewClazz.getName(), context);
                if (cardView != null)
                    viewList.add(cardView);
                preViewListMap.put(cardViewType.viewClazz, viewList);
            }
        }
    }

    private SuperCardView getSuperCardView(String className, Context context) {
//        Log.e(TAG, "getSuperCardView");
        //反射注册一个View
        Object cardView = null;
        try {
            Class clazz = Class.forName(className);
            cardView = clazz.getConstructor(Context.class).newInstance(context);
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return (SuperCardView) cardView;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
//        Log.e(TAG, "getView");
        final CardBean item = (CardBean) getItem(position);
        final CardViewType cardViewType = CardViewType.getCardViewByType(item.cardViewType);
        if (view instanceof SuperCardView) {
            ((SuperCardView) view).fillViewWithData(item);
        } else if (view == null) {
            if (getItemViewType(position) != CardViewType.CardViewType1002.type) {
                List<SuperCardView> views = preViewListMap.get(cardViewType.viewClazz);
                if (views != null && views.size() > 0) {
                    view = (View) preViewListMap.get(cardViewType.viewClazz).get(0);
                    views.remove(0);
                } else {
                    //重新inflate一个
                    view = (SuperCardView) getSuperCardView(cardViewType.viewClazz.getName(), context);
                }
                if (view != null){
                    ((SuperCardView) view).fillViewWithData(item);
                }
            } else {
                view = new CheckMore(context);
            }
        } else {
            //其他View类型
        }

        if (getItemViewType(position) == CardViewType.CardViewType1008.type) {
            ((CardView1008) view).setOnCheckMoreClickListener(new CardView1008.OnCheckMoreClickListener() {
                @Override
                public void OnCheckMore(String groupName,int type) {
                    Intent intent = new Intent(context, ItemResultActivity.class);
                    intent.putExtra("title", groupName);
                    intent.putExtra("moreType",type );
                    context.startActivity(intent);
                }
            });
        }

        final View finalView = view;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardAdapterListener != null && position < list.size()) {
                    Log.e("CardAdapter","itemId = "+((SuperCardView) finalView).getItemId());
                    cardAdapterListener.OnViewPosition(finalView, position,((SuperCardView) finalView).getItemId(),cardViewType);
                }
                if (cardAdapterMoreListener != null && position == list.size()) {
                    if (hashNext){

                    }else {
                        Toast.makeText(context, "没有更多了", Toast.LENGTH_SHORT).show();
                    }
                    cardAdapterMoreListener.OnChekMoreClicked(hashNext);
                }
            }
        });
        return view;
    }

    public void setCardAdapterListener(CardAdapterListener cardAdapterListener) {
        this.cardAdapterListener = cardAdapterListener;
    }

    public void setCardAdapterMoreListener(CardAdapterMoreListener cardAdapterMoreListener) {
        this.cardAdapterMoreListener = cardAdapterMoreListener;
    }

    public interface CardAdapterListener {
        void OnViewPosition(View view, int position,String itemId,CardViewType cardViewType);
    }

    public interface CardAdapterMoreListener {
        void OnChekMoreClicked(boolean hasNext);
    }
}
