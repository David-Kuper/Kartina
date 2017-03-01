package kartina.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.ServerError;
import com.google.gson.JsonElement;
import com.kartina.service.model.AbstractResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kartina.R;
import kartina.card.CardAdapter;
import kartina.card.CardListView;
import kartina.card.DataManager.CardProcess;
import kartina.card.DataManager.RequestUtil;
import kartina.card.DataManager.ResponseParameter;
import kartina.card.DataManager.SuperService;
import kartina.card.ListViewController;
import kartina.card.ViewType.CardViewType;
import kartina.card.bean.CardBean;
import kartina.card.bean.CardBean1006;
import kartina.card.bean.ClassDetailHeadBean;
import kartina.card.bean.CardBean1008;
import kartina.card.view.ClassDetailHeadView;
import kartina.model.ClassItem;
import kartina.model.NoteBody;
import kartina.model.ItemBottom;
import kartina.model.UIModel.DetailDo;
import kartina.model.UIModel.UserInfoDo;
import kartina.model.model.CourseDetailModel;
import kartina.model.model.GetCourseDetailModelRequest;
import kartina.msg.MessageCenter;
import kartina.msg.MessageConstant;
import kartina.util.Constants;

/**
 * Created by David on 2016/11/7.
 */

public class ClassActivity extends SuperActivity {
    private ClassDetailHeadView headView;
    private ClassDetailHeadBean headData;
    private ListViewController listViewController;
    private FloatingActionButton mFab;
    private DetailDo classDetail;
    private String itemId;
    private MessageCenter.Observer publishObserver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_class);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        itemId = intent.getStringExtra("itemId");
        if (title != null) {
            setTitle(title);
        } else {
            setTitle("课程详情");
        }
        init();
        refreshTop();
    }

    private void init() {
        publishObserver = new MessageCenter.Observer() {
            @Override
            public void onMessageCall(String msg, MessageCenter.Notification data) {
                if (msg.equals(MessageConstant.OBSERVER_PUBLISH)){
                    refreshNoteList();
                }
            }
        };
        MessageCenter.registerMsgObserver(MessageConstant.OBSERVER_PUBLISH,publishObserver);
        classDetail = new DetailDo();

        listViewController = new ListViewController(this, false);
        CardListView cardListView = (CardListView) findViewById(R.id.listview);
        headView = new ClassDetailHeadView(this);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "唤起发布页面", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(getBaseContext(), PublishActivity.class);
                intent.putExtra("title","发布课程帖子");
                intent.putExtra("publishType", Constants.PUBLISH_NOTE_FROM_CLASS);
                intent.putExtra("itemId",itemId);
                startActivity(intent);
            }
        });

        listViewController.setView(cardListView);
        listViewController.addHeaderView(headView);

        headView.setOnActionClickListener(new ClassDetailHeadView.OnActionClickListener() {
            @Override
            public void OnActionClick() {
                Toast.makeText(getApplicationContext(),"Action Click!",Toast.LENGTH_SHORT).show();
            }
        });
        listViewController.setOnItemClickListener(new CardAdapter.CardAdapterListener() {
            @Override
            public void OnViewPosition(View view, int position,String itemId,CardViewType cardViewType) {
                Intent intent = new Intent(getBaseContext(),NoteActivity.class);
                intent.putExtra("itemId",itemId);
                startActivity(intent);
            }
        });
        listViewController.setOnCheckMoreListener(new CardAdapter.CardAdapterMoreListener() {
            @Override
            public void OnChekMoreClicked(boolean hasNext) {
                Toast.makeText(getBaseContext(),"Click Check More!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refreshNoteList() {
        refreshTop();
    }

    private void refreshTop() {
        loadData();
    }
    @Override
    protected void loadData() {
//        ClassDetailHeadBean cardBean1007 = new ClassDetailHeadBean();
//        cardBean1007.classHour = "23";
//        cardBean1007.classCredit = "3";
//        cardBean1007.className = "代数";
//        cardBean1007.classNum = "1233112";
//
//        ClassItem classItem;
//        ArrayList<ClassItem> classItems = new ArrayList<>();
//        for (int i = 0; i < 4; i++) {
//            classItem = new ClassItem();
//            classItem.classId = "10";
//            classItem.classPlace = "江安耳机楼B302";
//            classItem.classTeacher = "宋玉宝";
//            classItem.classTime = "周二 5—9 节";
//            classItems.add(classItem);
//        }
//        cardBean1007.classItemList = classItems;
//
//        headData = new CardBean();
//        headData.data = cardBean1007;
//        headData.cardViewType = 1007;
//
//        headView.fillViewWithData(headData);
//
//        CardBean cardBean;
//        CardBean1006 cardBean1006;
//        ArrayList<CardBean> list = new ArrayList<>();
//
//        //初始化帖子列表
//        for (int i = 0; i < 10; i++) {
//            cardBean = new CardBean();
//            cardBean1006 = new CardBean1006();
//            ItemBottom itemBottom = new ItemBottom();
//            NoteBody noteBody = new NoteBody();
//            itemBottom.praise_num = 23;
//            itemBottom.focus_num = 23;
//            itemBottom.comment_num = 32;
//            noteBody.describe = "五个人为广大如果水电费我方式的分割若过人水电费国仁";
//            noteBody.title = "课程帖子标题"+i;
//
//            cardBean1006.userName = "张三";
//            cardBean1006.avatarUrl = null;
//            cardBean1006.time = "2014-12-21";
//            cardBean1006.itemBottom = itemBottom;
//            cardBean1006.noteBody = noteBody;
//
//            cardBean.cardViewType = 1006;
//            cardBean.data = cardBean1006;
//
//            list.add(cardBean);
//        }\
        showLoading(VIEW_LOADING);
        GetCourseDetailModelRequest request = new GetCourseDetailModelRequest();
        request.setCourseId(itemId);
        request.setRequestUserId(UserInfoDo.getInstance().getUid());
        JsonElement jsonElement = RequestUtil.getInstance().toJsonElement(request);
        classDetail.loadClassDetailInfo(jsonElement, new SuperService.CallBack<AbstractResponse<CourseDetailModel>>() {
            @Override
            public void OnSucess(ResponseParameter<AbstractResponse<CourseDetailModel>> response) {
                if (response.object.getSuccess().equals("1")){
                    showLoading(VIEW_CONTENT);
                    CourseDetailModel courseDetailModel = response.object.getData();
                    headData = courseDetailModel.getClassDetailHeadBean();

                    setTitle(headData.className);
                    headView.fillViewWithData(headData);
                    List<CardBean> cardList = courseDetailModel.getCardList();
                    headView.setNoteCount(String.valueOf(cardList.size()));
                    CardProcess.parseCardList(cardList, new CardProcess.CallBack<List<CardBean>>() {
                        @Override
                        public void parseCardSuccess(List<CardBean> list) {
                            listViewController.setData(list,true);
                        }
                    },ClassActivity.this);
                }else {
                    showLoading(VIEW_LOADFAILURE);
                    showToast(response.object.getError().getMsg());
                }
            }

            @Override
            public void OnProcess(ResponseParameter<AbstractResponse<CourseDetailModel>> response, int curValue, int totalValue) {

            }

            @Override
            public void OnError(ResponseParameter response) {
                if (response.error instanceof NetworkError){
                    showLoading(VIEW_WIFIFAILUER);
                }else if (response.error instanceof ServerError){
                    showLoading(VIEW_SERVERERROR);
                }else {
                    showLoading(VIEW_LOADFAILURE);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MessageCenter.unRegisterMsgObserver(MessageConstant.OBSERVER_PUBLISH,publishObserver);
    }
}
