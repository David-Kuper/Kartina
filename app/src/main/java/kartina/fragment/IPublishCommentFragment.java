package kartina.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.android.volley.NetworkError;
import com.android.volley.ServerError;
import com.google.gson.JsonElement;
import com.kartina.constant.CommentType;
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
import kartina.model.model.GetMyCommentRequest;
import kartina.model.model.GetMyCommentResponse;
import kartina.model.model.GetMyPostResponse;
import kartina.model.model.MyComment;
import kartina.model.model.MyPost;

/**
 * Created by David on 2016/11/20.
 */

public class IPublishCommentFragment extends SuperFragment {
    private static IPublishCommentFragment instance;
    private ListViewController listViewController;
    private GetMyCommentRequest request;
    private int page = 0;
    private UserInfoDo userInfoDo;

    public static IPublishCommentFragment getInstance() {
        if (instance == null) {
            instance = new IPublishCommentFragment();
        }
        return instance;
    }

    @Override
    protected View getContentView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_ipublish_comment, null, false);
        return view;
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
    protected void loadData() {
        if (request == null)
            request = new GetMyCommentRequest();
        page = 0;
        pageLoad(page);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        loadData();
    }

    private void init() {
        page = 0;
        userInfoDo = new UserInfoDo();
    }

    protected void loadMore(int page){
        pageLoad(page);
    }
    private void addPage(){
        page++;
    }
    private void pageLoad(final int page) {
        if (request == null){
            request = new GetMyCommentRequest();
        }
        request.setCommentType(CommentType.User.getValue());
        request.setRowPage(10);
        request.setPageNum(page);
        request.setRequestUserId(UserInfoDo.getInstance().getUid());
        JsonElement requestBody = RequestUtil.getInstance().toJsonElement(request);
        userInfoDo.getMyComment(requestBody, new SuperService.CallBack<GetMyCommentResponse>() {
            @Override
            public void OnSucess(ResponseParameter<GetMyCommentResponse> response) {
                if (response.object.getSuccess().equals("1")) {
                    addPage();
                    final MyComment myTopic = response.object.getData();
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
            public void OnProcess(ResponseParameter<GetMyCommentResponse> response, int curValue, int totalValue) {

            }
        });
    }
}
