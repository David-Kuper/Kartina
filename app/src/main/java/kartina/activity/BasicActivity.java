package kartina.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import java.util.HashMap;

import kartina.R;
import kartina.fragment.DiscoverFragment;
import kartina.fragment.PCenterFragment;
import kartina.fragment.SchoolFragment;
import kartina.util.AppUtils;
import kartina.view.KartinaActionBar;
import kartina.view.KartinaNavBar;
import kartina.view.CommonStateView;

public class BasicActivity extends SuperActivity implements KartinaNavBar.SelectAction {
    private static String TAG = "BasicActivity";
    private CommonStateView commonStateView;
    private KartinaNavBar navBar;
    private static HashMap<Integer, Fragment> fragmentList = new HashMap<Integer, Fragment>() {
        {
            put(KartinaNavBar.NAV1, new SchoolFragment());
            put(KartinaNavBar.NAV2, new DiscoverFragment());
            put(KartinaNavBar.NAV3, new PCenterFragment());
        }
    };
    private int mCurrentFmIndex = -1;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("BasicActivity","onNewIntent Coming");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_basic);
        init();
    }

    @Override
    protected void loadData() {

    }

    private void initFm(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container,fragmentList.get(KartinaNavBar.NAV1));
        transaction.commit();
        switchNav(KartinaNavBar.NAV1);
        mCurrentFmIndex = KartinaNavBar.NAV1;
    }
    private void setCurrentPage(int index) {
       if (mCurrentFmIndex != index) {
            switchNav(index);
            switchFragment(fragmentList.get(mCurrentFmIndex), fragmentList.get(index));
            mCurrentFmIndex = index;
        }
    }

    public void switchNav(int index){
        switch (index) {
            case KartinaNavBar.NAV1:
                navBar.selectNav(KartinaNavBar.NAV1);
                setActionBarStyle(KartinaActionBar.STYLE2);
                break;
            case KartinaNavBar.NAV2:
                navBar.selectNav(KartinaNavBar.NAV2);
                setActionBarStyle(KartinaActionBar.STYLE2);
                break;
            case KartinaNavBar.NAV3:
                navBar.selectNav(KartinaNavBar.NAV3);
                setActionBarStyle(KartinaActionBar.STYLE1);
                setTitle("个人中心");
                break;
            default:

        }
    }

    public void switchFragment(Fragment from, Fragment to) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!to.isAdded()) {    // 先判断是否被add过
            transaction.hide(from).add(R.id.fragment_container, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
        }
    }

    private void init() {
        navBar = (KartinaNavBar) findViewById(R.id.nav_bar_main);
        navBar.setSelection(this);
        initFm();
    }

    @Override
    public void selectNav(int index) {
        setCurrentPage(index);
    }
}
