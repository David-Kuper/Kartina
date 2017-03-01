package kartina.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.android.volley.NetworkError;
import com.android.volley.ServerError;
import com.google.gson.JsonElement;
import com.kartina.service.model.BasePageSelectRequest;

import java.util.List;

import kartina.R;
import kartina.card.CardAdapter;
import kartina.card.CardListView;
import kartina.card.DataManager.CardProcess;
import kartina.card.DataManager.RequestUtil;
import kartina.card.DataManager.ResponseParameter;
import kartina.card.DataManager.SuperService;
import kartina.card.ListViewController;
import kartina.card.bean.CardBean;
import kartina.model.UIModel.UserInfoDo;
import kartina.model.model.GetMyPostRequest;
import kartina.model.model.GetMyPostResponse;
import kartina.model.model.GetMyTopicRequest;
import kartina.model.model.GetMyTopicResponse;
import kartina.model.model.MyPost;
import kartina.model.model.MyTopic;

/**
 * Created by David on 2016/11/18.
 */

public class IPublishNoteFragment extends SuperFragment {
    private static IPublishNoteFragment instance;
    private ListViewController listViewController;
    private UserInfoDo userInfoDo;
    private int page = 0;
    private GetMyPostRequest request;

    public static IPublishNoteFragment getInstance() {
        if (instance == null) {
            instance = new IPublishNoteFragment();
        }
        return instance;
    }

    @Override
    protected View getContentView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_ipublish_note, null, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void loadData() {
        if (request == null)
            request = new GetMyPostRequest();
        page = 0;
        pageLoad(page);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CardListView cardListView = (CardListView) view.findViewById(R.id.listview);
        listViewController = new ListViewController(getActivity(), false);
        listViewController.setView(cardListView);
        listViewController.setOnCheckMoreListener(new CardAdapter.CardAdapterMoreListener() {
            @Override
            public void OnChekMoreClicked(boolean hasNext) {
                loadMore(page);
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        refreshTop();
    }

    private void init() {
        page = 0;
        userInfoDo = new UserInfoDo();
    }

    private void refreshTop() {
        loadData();
    }

    protected void loadMore(int page){
        pageLoad(page);
    }
    private void addPage(){
        page++;
    }
    private void pageLoad(final int page) {
        if (request == null){
            request = new GetMyPostRequest();
        }
        request.setRequestUserId(UserInfoDo.getInstance().getUid());
        request.setRowPage(10);
        request.setPageNum(page);
        JsonElement requestBody = RequestUtil.getInstance().toJsonElement(request);
        userInfoDo.getMyNote(requestBody, new SuperService.CallBack<GetMyPostResponse>() {
            @Override
            public void OnSucess(ResponseParameter<GetMyPostResponse> response) {
                if (response.object.getSuccess().equals("1")) {
                    addPage();
                    final MyPost myTopic = response.object.getData();
                    List<CardBean> cardBeanList = myTopic.getCardList();
                    if (cardBeanList != null && cardBeanList.size() > 0) {
                        CardProcess.parseCardList(cardBeanList, new CardProcess.CallBack<List<CardBean>>() {
                            @Override
                            public void parseCardSuccess(List<CardBean> list) {
                                if (page == 0){
                                    listViewController.setData(list, myTopic.getHasNext().getObject());
                                }else {
                                    listViewController.addData(list, myTopic.getHasNext().getObject());
                                }
                            }
                        }, getActivity());
                    } else {
                        if (page == 0){
                            showLoading(VIEW_NODATA);
                        }else {
                            showToast("没有更多啦！");
                        }
                    }
                } else {
                    showLoading(VIEW_LOADFAILURE);
                    showToast(response.object.getError().getMsg());
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
            public void OnProcess(ResponseParameter<GetMyPostResponse> response, int curValue, int totalValue) {

            }
        });
    }


}
