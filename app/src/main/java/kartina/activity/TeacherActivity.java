package kartina.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.ServerError;
import com.google.gson.JsonElement;
import com.kartina.constant.CommentType;
import com.kartina.service.model.AbstractResponse;
import com.kartina.service.model.comment.AddCommentRequest;
import com.kartina.service.model.comment.AddCommentResponse;

import java.util.ArrayList;
import java.util.List;

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
import kartina.card.bean.CardBean1010;
import kartina.card.bean.TeacherHeadBean;
import kartina.card.view.TeacherHeadView;
import kartina.model.ClassItem;
import kartina.model.UIModel.DetailDo;
import kartina.model.UIModel.UserActionDo;
import kartina.model.UIModel.UserInfoDo;
import kartina.model.model.GetTeacherDetailModelRequest;
import kartina.model.model.TeacherDetailModel;
import kartina.msg.MessageCenter;
import kartina.msg.MessageConstant;
import kartina.util.AppUtils;
import kartina.view.ChatView;

public class TeacherActivity extends SuperActivity {
    private TeacherHeadBean headData;
    private TeacherHeadView headView;
    private ChatView chatView;
    private ListViewController listViewController;
    private String itemId;
    private DetailDo detailDo;
    private UserActionDo userActionDo;
    private MessageCenter.Observer commentObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_teacher);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        itemId = intent.getStringExtra("itemId");
        if (title != null) {
            setTitle(title);
        } else {
            setTitle("教师");
        }
        init();

        refreshTop();
    }

    private void init() {
        userActionDo = new UserActionDo();
        detailDo = new DetailDo();
        commentObserver = new MessageCenter.Observer() {
            @Override
            public void onMessageCall(String msg, MessageCenter.Notification data) {

            }
        };
        MessageCenter.registerMsgObserver(MessageConstant.OBSERVER_COMMENT,commentObserver);

        listViewController = new ListViewController(this, false);
        CardListView cardListView = (CardListView) findViewById(R.id.listview);
        headView = new TeacherHeadView(this);
        chatView = (ChatView) findViewById(R.id.chat_view);

        listViewController.setView(cardListView);
        listViewController.addHeaderView(headView);

        chatView.setChatViewListener(new ChatView.ChatViewListener() {
            @Override
            public void OnSendMsg(String content) {
                if (AppUtils.isLogin(TeacherActivity.this)) {
                    AddCommentRequest request = new AddCommentRequest();
                    request.setRequestUserId(UserInfoDo.getInstance().getUid());
                    request.setContent(content);
                    request.setCommentType(CommentType.User.getValue());
                    request.setUserName(UserInfoDo.getInstance().getUserNick());
                    request.setItemId(itemId);
                    JsonElement requestBody = RequestUtil.getInstance().toJsonElement(request);
                    userActionDo.publishComment(requestBody, new SuperService.CallBack<AbstractResponse<AddCommentResponse>>() {
                        @Override
                        public void OnSucess(ResponseParameter<AbstractResponse<AddCommentResponse>> response) {
                            if (response.object.getSuccess().equals("1")){
                                showToast("评论成功");
                                refreshCommentList();
                            }else {
                                showToast("评论失败\n"+response.object.getError().getMsg());
                            }
                        }

                        @Override
                        public <D> void OnError(ResponseParameter<D> response) {

                        }

                        @Override
                        public void OnProcess(ResponseParameter<AbstractResponse<AddCommentResponse>> response, int curValue, int totalValue) {

                        }
                    });
                }
            }

            @Override
            public void OnSendSuccess() {

            }

            @Override
            public void OnSendFailed() {

            }
        });
        listViewController.setOnCheckMoreListener(new CardAdapter.CardAdapterMoreListener() {
            @Override
            public void OnChekMoreClicked(boolean hasNext) {
                Toast.makeText(getBaseContext(), "Click Check More!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refreshCommentList() {
        refreshTop();
    }

    private void refreshTop() {
        loadData();
    }

    @Override
    protected void loadData() {

//        headData = new CardBean();
//        TeacherHeadBean cardBean1011 = new TeacherHeadBean();
//        cardBean1011.academy = "计算机学院";
//        cardBean1011.avatar = null;
//        cardBean1011.commentNum = 30;
//        cardBean1011.describe = "为人师表哈啊哈哈哈哈哈";
//        cardBean1011.school = "中科院";
//        cardBean1011.isActioned = false;
//        cardBean1011.name = "王二娃";
//        cardBean1011.prosational = "教授";
//
//        ClassItem classItem;
//        ArrayList<ClassItem> classItems = new ArrayList<>();
//        for (int i = 0; i < 4; i++) {
//            classItem = new ClassItem();
//            classItem.classId = "10";
//            classItem.classPlace = "江安耳机楼B302";
//            classItem.className = "高等代数" + i;
//            classItem.classTime = "周二 5—9 节";
//            classItems.add(classItem);
//        }
//        cardBean1011.teachList = classItems;
//
//        headData.data = cardBean1011;
//        headData.cardViewType = 1011;
//        headView = new TeacherHeadView(this);
//        headView.fillViewWithData(headData);
//
//
//        CardBean cardBean;
//        CardBean1010 CardBean1010;
//        ArrayList<CardBean> list = new ArrayList<>();
//        //初始化回复列表
//        for (int i = 0; i < 10; i++) {
//            cardBean = new CardBean();
//            CardBean1010 = new CardBean1010();
//
//            CardBean1010.avatarUrl = null;
//            CardBean1010.name = "李四" + i;
//            CardBean1010.content = "垃圾上岛咖啡哇阿斯蒂芬为阿斯蒂芬发给我镂收到了咖啡碱撒地方阿里山的考卷发空结构";
//            CardBean1010.time = "2013-03-21";
//
//            cardBean.cardViewType = 1010;
//            cardBean.data = CardBean1010;
//
//            list.add(cardBean);
//        }

        showLoading(VIEW_LOADING);
        GetTeacherDetailModelRequest request = new GetTeacherDetailModelRequest();
        request.setRequestUserId(UserInfoDo.getInstance().getUid());
        request.setTeacherUserId(itemId);
        JsonElement requestBody = RequestUtil.getInstance().toJsonElement(request);
        detailDo.loadTeacherDetailInfo(requestBody, new SuperService.CallBack<AbstractResponse<TeacherDetailModel>>() {
            @Override
            public void OnSucess(ResponseParameter<AbstractResponse<TeacherDetailModel>> response) {
                if (response.object.getSuccess().equals("1")) {
                    showLoading(VIEW_CONTENT);
                    final TeacherDetailModel detailModel = response.object.getData();
                    headData = detailModel.getTeacherHeadBean();
                    headView.fillViewWithData(headData);
                    setTitle(headData.name);
                    ArrayList<CardBean> list = detailModel.getCardList();
                    CardProcess.parseCardList(list, new CardProcess.CallBack<List<CardBean>>() {
                        @Override
                        public void parseCardSuccess(List<CardBean> list) {
                            listViewController.setData(list, true);
                        }
                    }, TeacherActivity.this);
                } else {
                    showLoading(VIEW_LOADFAILURE);
                    showToast(response.object.getError().getMsg());
                }
            }

            @Override
            public <D> void OnError(ResponseParameter<D> response) {
                if (response.error instanceof NetworkError){
                    showLoading(VIEW_WIFIFAILUER);
                }else if (response.error instanceof ServerError){
                    showLoading(VIEW_SERVERERROR);
                }else {
                    showLoading(VIEW_LOADFAILURE);
                }
            }

            @Override
            public void OnProcess(ResponseParameter<AbstractResponse<TeacherDetailModel>> response, int curValue, int totalValue) {

            }
        });

    }
}
