package kartina.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.ServerError;
import com.google.gson.JsonElement;
import com.kartina.service.model.AbstractResponse;

import java.util.List;

import kartina.R;
import kartina.activity.PublishActivity;
import kartina.activity.TalkActivity;
import kartina.card.CardAdapter;
import kartina.card.CardListView;
import kartina.card.DataManager.CardProcess;
import kartina.card.DataManager.RequestUtil;
import kartina.card.DataManager.ResponseParameter;
import kartina.card.DataManager.SuperService;
import kartina.card.ListViewController;
import kartina.card.ViewType.CardViewType;
import kartina.card.bean.CardBean;
import kartina.card.bean.Tag;
import kartina.model.UIModel.DiscoverDo;
import kartina.model.UIModel.UserInfoDo;
import kartina.model.model.GetDiscoverTopicModelRequest;
import kartina.model.model.TopicModel;
import kartina.msg.MessageCenter;
import kartina.msg.MessageConstant;
import kartina.util.Constants;

/**
 * Created by David on 2016/11/4.
 */

public class TalkFragment extends SuperFragment {
    private ListViewController listViewController;
    FloatingActionButton mFab;
    private View view;
    private String itemId;
    private DiscoverDo discoverDo;
    private MessageCenter.Observer publishObserver;

    public TalkFragment() {
        discoverDo = new DiscoverDo();
    }

    @Nullable
    @Override
    protected View getContentView() {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_talk, null, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CardListView cardListView = (CardListView) view.findViewById(R.id.listview);
        mFab = (FloatingActionButton) view.findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "唤起发布页面", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(getContext(), PublishActivity.class);
                intent.putExtra("title", "发布话题");
                intent.putExtra("publishType", Constants.PUBLISH_TALK);
                startActivity(intent);
            }
        });
        listViewController = new ListViewController(getActivity(), false);
        listViewController.setView(cardListView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        refreshTop();
    }

    private void init() {
        publishObserver = new MessageCenter.Observer() {
            @Override
            public void onMessageCall(String msg, MessageCenter.Notification data) {
                 if (MessageConstant.OBSERVER_PUBLISH.equals(msg)){
                     refreshTopicList();
                 }
            }
        };
        MessageCenter.registerMsgObserver(MessageConstant.OBSERVER_PUBLISH,publishObserver);

        listViewController.setOnItemClickListener(new CardAdapter.CardAdapterListener() {
            @Override
            public void OnViewPosition(View view, int position, String itemId, CardViewType cardViewType) {
                Intent intent = new Intent(getActivity(), TalkActivity.class);
                intent.putExtra("itemId",itemId);
                startActivity(intent);
            }
        });
        listViewController.setOnCheckMoreListener(new CardAdapter.CardAdapterMoreListener() {
            @Override
            public void OnChekMoreClicked(boolean hasNext) {
                if (hasNext) {
                    Toast.makeText(getContext(), "Click Check More!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "没有更多了", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void refreshTopicList() {
        refreshTop();
    }

    private void refreshTop() {
        loadData();
    }

    protected void loadData() {

//        List<CardBean> cardList = new ArrayList<>();
//        CardBean<CardBean1005> cardBean;
//        for (int i = 0; i < 8; i++) {
//            ItemHeader itemHeader = new ItemHeader();
//            ItemBottom itemBottom = new ItemBottom();
//            NoteBody noteBody = new NoteBody();
//            CardBean1005 cardBean1005 = new CardBean1005();
//            cardBean = new CardBean<>();
//            cardBean.cardViewType = 1005;
//
//            itemBottom.comment_num = 321;
//            itemBottom.focus_num = 42;
//            itemBottom.praise_num = 212;
//            itemHeader.rid = R.mipmap.ic_launcher;
//            itemHeader.avarta = "jpg";
//            itemHeader.from_who = "哈哈";
//            itemHeader.time = "23:23";
//            itemHeader.origin = "帖子";
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
        showLoading(VIEW_LOADING);
        final GetDiscoverTopicModelRequest request = new GetDiscoverTopicModelRequest();
        request.setRequestUserId(UserInfoDo.getInstance().getUid());
        JsonElement requestBody = RequestUtil.getInstance().toJsonElement(request);
        discoverDo.loadTopicList(requestBody, new SuperService.CallBack<AbstractResponse<TopicModel>>() {
            @Override
            public void OnSucess(final ResponseParameter<AbstractResponse<TopicModel>> response) {
                if (response.object.getSuccess().equals("1")) {
                    showLoading(VIEW_CONTENT);
                    TopicModel topicModel = response.object.getData();
                    discoverDo.setTopicModel(topicModel);
                    List<CardBean> cardList = topicModel.getCardList();
                    List<Tag> tagList = topicModel.getTagList();

                    CardProcess.parseCardList(cardList, new CardProcess.CallBack<List<CardBean>>() {
                        @Override
                        public void parseCardSuccess(List<CardBean> list) {
                            listViewController.setData(list, true);
                        }
                    }, getActivity());

                } else {
                    showLoading(VIEW_LOADFAILURE);
                    showToast(response.object.getError().getMsg());
                }
            }

            @Override
            public <D> void OnError(ResponseParameter<D> response) {
                if (response.error instanceof ServerError) {
                    showLoading(VIEW_SERVERERROR);
                } else if (response.error instanceof NetworkError) {
                    showLoading(VIEW_WIFIFAILUER);
                } else {
                    showLoading(VIEW_LOADFAILURE);
                }
            }

            @Override
            public void OnProcess(ResponseParameter<AbstractResponse<TopicModel>> response, int curValue, int totalValue) {

            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MessageCenter.unRegisterMsgObserver(MessageConstant.OBSERVER_PUBLISH,publishObserver);
    }

}
