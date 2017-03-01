package kartina.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;

import kartina.R;
import kartina.activity.PublishActivity;
import kartina.adapter.KartinaFragmentPageAdapter;
import kartina.view.CommonStateView;


public class DiscoverFragment extends SuperFragment {
    private static final String TAG = "DiscoverFragment";
    private CommonStateView commonStateView;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Fragment[] fragments = {new ClassFragment(), new TalkFragment()};
    String[] titles = {"课程", "讨论"};
    KartinaFragmentPageAdapter adapter;


    @Override
    protected View getContentView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_discover, null, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mTabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        adapter = new KartinaFragmentPageAdapter(getChildFragmentManager(), getContext());
        getFragmentManager();

        KartinaFragmentPageAdapter.Configure configure = KartinaFragmentPageAdapter.Configure.Builder
                .getInstance()
                .setTitles(titles)
                .setFragments(fragments).create();
        try {
            adapter.init(configure);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager, false);
        initTabView(titles, adapter);
        Log.e("DiscoverFragment", "onViewCreated()");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        Log.e("DiscoverFragment", "onActivityCreated()");
    }

    private void init() {
        refreshTop();
    }

    private void initTabView(String[] titles, KartinaFragmentPageAdapter adapter) {
        for (int i = 0; i < titles.length; i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab != null)
                tab.setCustomView(adapter.getTabView(i));
        }
    }

    private void refreshTop() {
        loadData();
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
