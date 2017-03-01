package kartina.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.ServerError;
import com.google.gson.JsonElement;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.kartina.service.model.AbstractResponse;

import java.util.ArrayList;
import java.util.List;

import kartina.R;
import kartina.activity.ClassActivity;
import kartina.activity.TalkActivity;
import kartina.activity.TeacherActivity;
import kartina.adapter.BannerPagerAdapter;
import kartina.card.CardAdapter;
import kartina.card.CardListView;
import kartina.card.DataManager.CardProcess;
import kartina.card.DataManager.RequestUtil;
import kartina.card.DataManager.ResponseParameter;
import kartina.card.DataManager.SuperService;
import kartina.card.ListViewController;
import kartina.card.ViewType.CardViewType;
import kartina.card.bean.CardBean;
import kartina.model.BannerBean;
import kartina.model.UIModel.HomeDo;
import kartina.model.UIModel.UserInfoDo;
import kartina.model.model.HomeModel;
import kartina.view.CircleIndicator;


/**
 * Created by David on 2016/10/31.
 */

public class SchoolFragment extends SuperFragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    View view;
    private ViewPager mBanner;
    private CircleIndicator mIndicator;
    private BannerPagerAdapter mBannerAdapter;
    private ListViewController mListViewController;
    private HomeDo homeDo;
    String uid;
    /**
     * 轮播图片自动切换时间
     */
    private static final int PHOTO_CHANGE_TIME = 3000;
    private static final int MSG_CHANGE_PHOTO = 10;
    private static final int MSG_BANNER_CHANGE = 11;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_CHANGE_PHOTO:
                    int index = (mBanner.getCurrentItem() + 1) % mBannerAdapter.getCount();
                    mBanner.setCurrentItem(index, true);
                    handler.sendEmptyMessageDelayed(MSG_CHANGE_PHOTO, PHOTO_CHANGE_TIME);
                    break;
                case MSG_BANNER_CHANGE:
                    break;
            }
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    protected View getContentView() {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_school, null, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        CardListView mCardListView = (CardListView) view.findViewById(R.id.listview_school);

        FrameLayout headcontainer = (FrameLayout) LayoutInflater.from(getContext()).inflate(R.layout.school_header, null);
        mBanner = (ViewPager) headcontainer.findViewById(R.id.viewpager);
        mIndicator = (CircleIndicator) headcontainer.findViewById(R.id.indicator);

        mBannerAdapter = new BannerPagerAdapter(getContext());
        mListViewController = new ListViewController(getActivity(), true);

        mBanner.setAdapter(mBannerAdapter);
        mIndicator.setViewPager(mBanner);

        mCardListView.addHeaderView(headcontainer);
        mListViewController.setView(mCardListView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        loadData();
    }

    private void setListener() {

        mBannerAdapter.setOnItemClickListener(new BannerPagerAdapter.OnItemClickListener() {
            @Override
            public void OnClick(BannerBean data, int index) {
                Toast.makeText(getContext(), "Clicked Banner " + index, Toast.LENGTH_SHORT).show();
            }
        });
        mListViewController.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0 && (swipeRefreshLayout == null || swipeRefreshLayout.getTop() == 0)) {
                    swipeRefreshLayout.setEnabled(true);
                } else {
                    swipeRefreshLayout.setEnabled(false);
                }
            }
        });
        mListViewController.setOnItemClickListener(new CardAdapter.CardAdapterListener() {
            @Override
            public void OnViewPosition(View view, int position, String itemId, CardViewType cardViewType) {
                Intent intent = null;
                if (cardViewType == CardViewType.CardViewType1001) {
                    intent = new Intent(getContext(), TeacherActivity.class);
                } else if (cardViewType == CardViewType.CardViewType1000) {
                    intent = new Intent(getContext(), ClassActivity.class);
                } else if (cardViewType == CardViewType.CardViewType1005) {
                    intent = new Intent(getContext(), TalkActivity.class);
                }
                if (intent != null) {
                    intent.putExtra("itemId", itemId);
                    startActivity(intent);
                }
            }
        });
    }

    private void init() {
        swipeRefreshLayout.setNestedScrollingEnabled(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                refreshTop();
            }
        });
        homeDo = new HomeDo();
        setListener();
    }

    private void refreshTop() {
        final List<BannerBean> bannerList = new ArrayList<>();
        BannerBean bannerBean;
        bannerBean = new BannerBean();
        bannerBean.setCoverRid(R.mipmap.banner1);
        bannerList.add(bannerBean);
        bannerBean = new BannerBean();
        bannerBean.setCoverRid(R.mipmap.banner2);
        bannerList.add(bannerBean);
        bannerBean = new BannerBean();
        bannerBean.setCoverRid(R.mipmap.banner3);
        bannerList.add(bannerBean);
        bannerBean = new BannerBean();
        bannerBean.setCoverRid(R.mipmap.banner4);
        bannerList.add(bannerBean);

        if (UserInfoDo.getInstance().getUserInfo() == null) {
            uid = new String();
        } else {
            uid = UserInfoDo.getInstance().getUid();
        }
        JsonElement uidJson = RequestUtil.getInstance().toJsonObject("userId", uid);
        homeDo.getHomeData(uidJson, new SuperService.CallBack<AbstractResponse<HomeModel>>() {

            @Override
            public void OnSucess(ResponseParameter<AbstractResponse<HomeModel>> response) {
                swipeRefreshLayout.setRefreshing(false);
                if (response.object.getSuccess().equals("1")) {
                    HomeModel homeModel = response.object.getData();
                    if (homeModel != null) {
                        homeDo.setHomeModel(homeModel);
                        List<BannerBean> bannerBeanList = homeModel.getBannerBeanList();
                        List<CardBean> cardBeanList = homeModel.getCardList();
                        if (bannerBeanList != null && bannerBeanList.size() > 0) {
                            mBannerAdapter.setData(bannerBeanList);
                        } else {
                            mBannerAdapter.setData(bannerList);
                        }
                        if (cardBeanList != null && cardBeanList.size() > 0) {
                            Log.e("SchoolFragment", "cardBeanList" + cardBeanList.get(0).getClass().getName());
                            //解析里面的泛型对象
                            CardProcess.parseCardList(cardBeanList, new CardProcess.CallBack<List<CardBean>>() {
                                @Override
                                public void parseCardSuccess(List<CardBean> list) {
                                    Log.e("SchoolFragment", "parseCardSuccess" + " currentThread = " + Thread.currentThread());
                                    mListViewController.setData(list, false);
                                }
                            }, getActivity());
                        }
                        handler.sendEmptyMessageDelayed(MSG_CHANGE_PHOTO, 5000);
                    }
                } else {
                    showLoading(VIEW_LOADFAILURE);
                    Log.e(this.getClass().getName(), "Failure Msg : " + RequestUtil.getInstance().toJsonString(response.object.getError()));
                }
            }

            @Override
            public <D> void OnError(ResponseParameter<D> response) {
                if (response.error instanceof NetworkError) {
                    showLoading(VIEW_WIFIFAILUER);
                } else if (response.error instanceof ServerError) {
                    showLoading(VIEW_SERVERERROR);
                } else {
                    showLoading(VIEW_LOADFAILURE);
                }
            }

            @Override
            public void OnProcess(ResponseParameter<AbstractResponse<HomeModel>> response, int curValue, int totalValue) {

            }
        });
    }

    @Override
    protected void loadData() {
        showLoading(VIEW_LOADING);
        final List<BannerBean> bannerList = new ArrayList<>();
        BannerBean bannerBean;
        bannerBean = new BannerBean();
        bannerBean.setCoverRid(R.mipmap.banner1);
        bannerList.add(bannerBean);
        bannerBean = new BannerBean();
        bannerBean.setCoverRid(R.mipmap.banner2);
        bannerList.add(bannerBean);
        bannerBean = new BannerBean();
        bannerBean.setCoverRid(R.mipmap.banner3);
        bannerList.add(bannerBean);
        bannerBean = new BannerBean();
        bannerBean.setCoverRid(R.mipmap.banner4);
        bannerList.add(bannerBean);
//
//        List<CardBean> cardList = new ArrayList<>();
//        //添加分割线
//        CardBean1008 cardBean1008;
//        cardBean1008 = new CardBean1008();
//        cardBean1008.groupName = "热门课程";
//        cardBean1008.groupNum = "10";
//        cardBean1008.showMore = true;
//        cardBean1008.moreType = CardView1008.MORE_CLASS;
//        CardBean cardBean;
//        cardBean = new CardBean<>();
//        cardBean.data = cardBean1008;
//        cardBean.cardViewType = CardViewType.CardViewType1008;
//        cardList.add(cardBean);
//
//        CardBean1000 cardBean1000;
//        for (int i = 0; i < 3; i++) {
//            cardBean1000 = new CardBean1000();
//            cardBean = new CardBean<>();
//            cardBean1000.academy = "计算机学院";
//            cardBean1000.name = "数据结构" + i;
//            cardBean1000.describe = "他是是临时决定六块腹肌网络科技方法阿萨大富翁发问阿多阿道夫";
//            cardBean1000.focusNum = 123;
//            cardBean1000.noteNum = 4213;
//            cardBean.cardViewType = CardViewType.CardViewType1000;
//            cardBean.data = cardBean1000;
//            cardList.add(cardBean);
//        }
//
//        cardBean1008 = new CardBean1008();
//        cardBean1008.groupName = "热门话题";
//        cardBean1008.groupNum = "10";
//        cardBean1008.moreType = CardView1008.MORE_TALK;
//        cardBean1008.showMore = true;
//        cardBean = new CardBean<CardBean1008>();
//        cardBean.cardViewType = CardViewType.CardViewType1008;
//        cardBean.data = cardBean1008;
//        cardList.add(cardBean);
//        for (int i = 0; i < 3; i++) {
//            ItemHeader itemHeader = new ItemHeader();
//            ItemBottom itemBottom = new ItemBottom();
//            NoteBody noteBody = new NoteBody();
//            CardBean1005 cardBean1005 = new CardBean1005();
//            cardBean = new CardBean<>();
//            cardBean.cardViewType = CardViewType.CardViewType1005;
//
//            itemBottom.comment_num = 321;
//            itemBottom.focus_num = 42;
//            itemBottom.praise_num = 212;
//            itemHeader.rid = R.mipmap.ic_launcher;
//            itemHeader.avarta = "http.jpg";
//            itemHeader.from_who = "王二";
//            itemHeader.time = "23:23";
//            itemHeader.origin = "话题圈";
//            noteBody.describe = "内容啊阿斯蒂芬了可使肌肤算法发送方认为" + i;
//            noteBody.title = "标题啊搜房网分阿斯蒂芬无非是发生反倒是访问阿斯蒂芬";
//
//            cardBean1005.mItemHeader = itemHeader;
//            cardBean1005.mItemBottom = itemBottom;
//            cardBean1005.noteBody = noteBody;
//
//            cardBean.data = cardBean1005;
//            cardList.add(cardBean);
//        }
//
//        //添加分割线
//        cardBean1008 = new CardBean1008();
//        cardBean1008.groupName = "热门老师";
//        cardBean1008.groupNum = "10";
//        cardBean1008.showMore = true;
//        cardBean1008.moreType = CardView1008.MORE_TEACHER;
//        cardBean = new CardBean<CardBean1008>();
//        cardBean.cardViewType = CardViewType.CardViewType1008;
//        cardBean.data = cardBean1008;
//        cardList.add(cardBean);
//
//        CardBean1001 cardBean1001;
//        for (int i = 0; i < 3; i++) {
//            cardBean1001 = new CardBean1001();
//            cardBean = new CardBean<>();
//            cardBean1001.academy = "计算机学院";
//            cardBean1001.avatar = null;
//            cardBean1001.commentNum = 213;
//            cardBean1001.focusNum = 312;
//            cardBean1001.isActioned = false;
//            cardBean1001.name = "王二思";
//            cardBean1001.noteNum = 123;
//            cardBean1001.praiseNum = 332;
//            cardBean1001.prosational = "教授";
//            cardBean1001.school = "四川大学";
//            cardBean1001.searchFiled = "图形图像研究";
//            cardBean.cardViewType = CardViewType.CardViewType1001;
//            cardBean.data = cardBean1001;
//            cardList.add(cardBean);
//        }
        if (UserInfoDo.getInstance().getUserInfo() == null) {
            uid = new String();
        } else {
            uid = UserInfoDo.getInstance().getUid();
        }
        JsonElement uidJson = RequestUtil.getInstance().toJsonObject("userId", uid);
        homeDo.getHomeData(uidJson, new SuperService.CallBack<AbstractResponse<HomeModel>>() {

            @Override
            public void OnSucess(ResponseParameter<AbstractResponse<HomeModel>> response) {
                showLoading(VIEW_CONTENT);
                if (response.object.getSuccess().equals("1")) {
                    HomeModel homeModel = response.object.getData();
                    Log.e("SchoolFragment", homeModel.getClass().getName());
                    if (homeModel != null) {
                        homeDo.setHomeModel(homeModel);
                        List<BannerBean> bannerBeanList = homeModel.getBannerBeanList();
                        List<CardBean> cardBeanList = homeModel.getCardList();
                        if (bannerBeanList != null && bannerBeanList.size() > 0) {
                            mBannerAdapter.setData(bannerBeanList);
                        } else {
                            mBannerAdapter.setData(bannerList);
                        }
                        if (cardBeanList != null && cardBeanList.size() > 0) {
                            Log.e("SchoolFragment", "cardBeanList" + cardBeanList.get(0).getClass().getName());
                            //解析里面的泛型对象
                            CardProcess.parseCardList(cardBeanList, new CardProcess.CallBack<List<CardBean>>() {
                                @Override
                                public void parseCardSuccess(List<CardBean> list) {
                                    Log.e("SchoolFragment", "parseCardSuccess" + " currentThread = " + Thread.currentThread());
                                    mListViewController.setData(list, false);
                                }
                            }, getActivity());
                        }
                        handler.sendEmptyMessageDelayed(MSG_CHANGE_PHOTO, 5000);
                    }
                } else {
                    showLoading(VIEW_LOADFAILURE);
                    Log.e(this.getClass().getName(), "Failure Msg : " + RequestUtil.getInstance().toJsonString(response.object.getError()));
                }
            }

            @Override
            public <D> void OnError(ResponseParameter<D> response) {
                if (response.error instanceof NetworkError) {
                    showLoading(VIEW_WIFIFAILUER);
                } else if (response.error instanceof ServerError) {
                    showLoading(VIEW_SERVERERROR);
                } else {
                    showLoading(VIEW_LOADFAILURE);
                }
            }

            @Override
            public void OnProcess(ResponseParameter<AbstractResponse<HomeModel>> response, int curValue, int totalValue) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
