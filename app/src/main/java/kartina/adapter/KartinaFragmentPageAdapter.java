package kartina.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import kartina.R;
import kartina.util.DensityUtil;

/**
 * Created by David on 2016/11/4.
 */

public class KartinaFragmentPageAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private Configure mConfigure;
    public KartinaFragmentPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    public void init(@NonNull Configure configure) throws Exception {
        mConfigure = configure;
        checkConfig(mConfigure);
    }

    private void checkConfig(Configure configure) throws Exception {
        if (configure == null)
            throw new NullPointerException("Configure can not be null!");
        if (configure.mTitles == null)
            throw new NullPointerException("configure.mTitles can not be null!");
        if (configure.mFragments == null)
            throw new NullPointerException("configure.mFragments can not be null!");
        if (configure.mTitles.length <= 0)
            throw new Exception("configure.mTitles.length must > 0!");
        if (configure.mFragments.length <= 0)
            throw new Exception("configure.mFragments.length must > 0!");
        if (configure.mFragments.length != configure.mTitles.length)
            throw new Exception("configure.mFragments.length must equal to configure.mTitles.length!");
    }

    @Override
    public Fragment getItem(int position) {
        return mConfigure.getFragments()[position];
    }

    @Override
    public int getCount() {
        return mConfigure.getFragments().length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mConfigure.mTitles[position];
    }

    public View getTabView(int position) {
        Log.e("KartinaFmPageAdapter","getTabView");
        View v = LayoutInflater.from(mContext).inflate(R.layout.custom_tab, null,false);
        TextView tv = (TextView) v.findViewById(R.id.title);
        tv.setText(mConfigure.mTitles[position]);
        if (mConfigure.mIcons != null && mConfigure.mIcons.length > 0){
            ImageView img = (ImageView) v.findViewById(R.id.icon);
            img.setVisibility(View.VISIBLE);
            img.setImageResource(mConfigure.mIcons[position]);
        }
        v.setLayoutParams(new ViewGroup.LayoutParams(DensityUtil.getWindowWidth((Activity) mContext)/mConfigure.mTitles.length, ViewGroup.LayoutParams.MATCH_PARENT));
        return v;
    }


    public static class Configure{
        private String[] mTitles = null;
        private int[] mIcons = null;
        private Fragment[] mFragments = null;
        protected Configure(){

        }
        public Fragment[] getFragments() {
            return mFragments;
        }

        public int[] getIcons() {
            return mIcons;
        }

        public String[] getTitles() {
            return mTitles;
        }

        public static class Builder{
            private String[] mTitles = null;
            private int[] mIcons = null;
            private Fragment[] mFragments = null;
            private Builder(){

            }
            public static Builder getInstance(){
                return new Builder();
            }
            public Builder setTitles(@NonNull String[] titles) {
                this.mTitles = titles;
                return this;
            }

            public Builder setIcons(@NonNull int[] icons) {
                this.mIcons = icons;
                return this;
            }
            public Builder setFragments(@NonNull Fragment[] fragments){
                mFragments = fragments;
                return this;
            }

            private void applyConfig(Configure configure){
                configure.mFragments = this.mFragments;
                configure.mIcons = this.mIcons;
                configure.mTitles = this.mTitles;
            }

            public Configure create(){
                Configure configure = new Configure();
                applyConfig(configure);
                return configure;
            }
        }
    }
}
