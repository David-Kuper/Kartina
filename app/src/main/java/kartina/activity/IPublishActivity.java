package kartina.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;

import kartina.R;
import kartina.com.astuetz.PagerSlidingTabStrip;
import kartina.fragment.IPublishCommentFragment;
import kartina.fragment.IPublishNoteFragment;
import kartina.fragment.IPublishTalkFragment;

public class IPublishActivity extends SuperActivity {
    private PagerSlidingTabStrip tabLayout;
    private ViewPager viewPager;
    private MyPagerAdapter adapter;

    private Drawable oldBackground = null;
    private int currentColor = 0xFF666666;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_ijoin);
        setTitle("我发布的");
        init();
        loadData();
    }

    @Override
    protected void loadData() {

    }

    private void init(){
        
        tabLayout = (PagerSlidingTabStrip) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new MyPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);

        tabLayout = (PagerSlidingTabStrip) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        viewPager.setPageMargin(pageMargin);

        tabLayout.setViewPager(viewPager);

    }
    public class MyPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = { "话题","帖子","评论"};
        public MyPagerAdapter(FragmentManager fm, Fragment[] fragments) {
            super(fm);
        }

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0){
               return IPublishTalkFragment.getInstance();
            }else if(position == 1){
               return IPublishNoteFragment.getInstance();
            }else {
                return IPublishCommentFragment.getInstance();
            }
        }
    }
}
