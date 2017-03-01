package kartina.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kartina.model.BannerBean;
import kartina.view.BannerView;

public class BannerPagerAdapter extends PagerAdapter {

    private Context mContext;
    private final List<BannerBean> mList = new ArrayList<>();
    private final SparseArray<FrameLayout> mHolderArray = new SparseArray<>();
    private OnItemClickListener onItemClickListener;

    public BannerPagerAdapter(Context context,ArrayList<BannerBean> list) {
        if (list != null)
           mList.addAll(list);
    }
    public BannerPagerAdapter(Context context) {
        mContext = context;
    }
    public void setData(List<BannerBean> list){
        if (mList == null || list == null)
            return;
        mList.clear();
        synchronized (mList){
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView(mHolderArray.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        BannerView bannerView = null;
        if (mHolderArray.get(position) == null){
            bannerView = new BannerView(view.getContext());
            bannerView.setBannerCover(mList.get(position).getCoverRid());
            bannerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null){
                        onItemClickListener.OnClick(mList.get(position),position);
                    }
                }
            });
            mHolderArray.put(position, bannerView);
        }else {
            bannerView = (BannerView) mHolderArray.get(position);
        }
        view.addView(bannerView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        return bannerView;
    }

    @Override
    public int getItemPosition(Object object) {
        int index = mHolderArray.indexOfValue((FrameLayout) object);
        if ( index != -1){
            return index;
        }else {
            return POSITION_NONE;
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void OnClick(BannerBean data,int index);
    }

    public void addItem(BannerBean bannerBean) {
        if (!mList.contains(bannerBean)){
            mList.add(bannerBean);
            notifyDataSetChanged();
        }
    }

    public void removeItem(BannerBean bannerBean) {
        if (mList.contains(bannerBean)) {
            mList.remove(bannerBean);
            notifyDataSetChanged();
        }
    }
}