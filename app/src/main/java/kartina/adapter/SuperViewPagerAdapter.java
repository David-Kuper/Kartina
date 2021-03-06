package kartina.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 滑动ViewPager
 * @author David
 *
 */
public class SuperViewPagerAdapter extends PagerAdapter {

	private List<View> views;

	public SuperViewPagerAdapter() {
	
	}

	public SuperViewPagerAdapter(List<View> views) {
		this.views = views;
	}

	@Override
	public int getCount() {
		return views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView(views.get(position));
	}

	@Override
	public Object instantiateItem(View container, int position) {
		((ViewPager) container).addView(views.get(position));
		return views.get(position);
	}

	/**
	 * 插入一个View到viewpager中
	 * 
	 * @param view
	 * @return
	 */
	public boolean insert(View view) {
		if (this.views == null) {
			this.views = new ArrayList<View>();
		}

		return this.views.add(view);
	}
}
