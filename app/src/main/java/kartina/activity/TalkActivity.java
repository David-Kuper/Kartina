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
import kartina.card.bean.TalkHeadBean;
import kartina.card.view.TalkHeadView;
import kartina.model.UIModel.DetailDo;
import kartina.model.UIModel.UserInfoDo;
import kartina.model.model.GetTopicDetailModelRequest;
import kartina.model.model.TopicDetailModel;
import kartina.msg.MessageCenter;
import kartina.msg.MessageConstant;
import kartina.util.Constants;

public class TalkActivity extends SuperActivity {
    private TalkHeadView headView;
    private TalkHeadBean headData;
    private ListViewController listViewController;
    private FloatingActionButton mFab;
    private String itemId;
    private DetailDo detailDo;
    private MessageCenter.Observer publishObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_talk);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        itemId = intent.getStringExtra("itemId");
        if (title != null) {
            setTitle(title);
        } else {
            setTitle("话题");
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
        detailDo = new DetailDo();

        listViewController = new ListViewController(this, false);
        CardListView cardListView = (CardListView) findViewById(R.id.listview);
        headView = new TalkHeadView(this);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "唤起发布页面", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(getBaseContext(), PublishActivity.class);
                intent.putExtra("title","发布话题帖子");
                intent.putExtra("publishType", Constants.PUBLISH_NOTE_FROM_TOPIC);
                intent.putExtra("itemId",itemId);
                startActivity(intent);
            }
        });

        listViewController.setView(cardListView);
        listViewController.addHeaderView(headView);
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

    private void refreshTop() {
        loadData();
    }

    @Override
    protected void loadData() {
        showLoading(VIEW_LOADING);
        GetTopicDetailModelRequest request = new GetTopicDetailModelRequest();
        request.setTopicId(itemId);
        request.setRequestUserId(UserInfoDo.getInstance().getUid());
        JsonElement requestBody = RequestUtil.getInstance().toJsonElement(request);
        detailDo.loadTopicDetailInfo(requestBody, new SuperService.CallBack<AbstractResponse<TopicDetailModel>>() {
            @Override
            public void OnSucess(ResponseParameter<AbstractResponse<TopicDetailModel>> response) {
                  if (response.object.getSuccess().equals("1")){
                      showLoading(VIEW_CONTENT);
                      TopicDetailModel topicModel = response.object.getData();
                      headData = topicModel.getTalkHeadBean();
                      ArrayList<CardBean> cardList = topicModel.getCardList();

                      headView.fillViewWithData(headData);
                      headView.setNoteCounts(cardList.size());
                      CardProcess.parseCardList(cardList, new CardProcess.CallBack<List<CardBean>>() {
                          @Override
                          public void parseCardSuccess(List<CardBean> list) {
                              listViewController.setData(list,true);
                          }
                      },TalkActivity.this);
                  }else {
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
            public void OnProcess(ResponseParameter<AbstractResponse<TopicDetailModel>> response, int curValue, int totalValue) {

            }
        });
    }
    protected void refreshNoteList(){
        refreshTop();
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
