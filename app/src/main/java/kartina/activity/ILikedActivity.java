package kartina.activity;

import android.os.Bundle;

import com.android.volley.NetworkError;
import com.android.volley.ServerError;
import com.google.gson.JsonElement;
import com.kartina.constant.PraiseType;
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
import kartina.model.model.GetMyCommentResponse;
import kartina.model.model.GetMyPraiseRequest;
import kartina.model.model.GetMyPraiseResponse;
import kartina.model.model.MyComment;
import kartina.model.model.MyPraise;

public class ILikedActivity extends SuperActivity {
    private ListViewController listViewController;
    private int page = 0;
    private UserInfoDo userInfoDo;
    private GetMyPraiseRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_ifocus);
        setTitle("我赞过的");
        init();
       loadData();
    }

    @Override
    protected void loadData() {
        if (request == null)
            request = new GetMyPraiseRequest();
        page = 0;
        pageLoad(page);
    }

    private void init(){
        page = 0;
        userInfoDo = new UserInfoDo();

        CardListView cardListView = (CardListView) findViewById(R.id.listview);
        listViewController = new ListViewController(this,false);
        listViewController.setView(cardListView);

        listViewController.setOnCheckMoreListener(new CardAdapter.CardAdapterMoreListener() {
            @Override
            public void OnChekMoreClicked(boolean hasNext) {
                loadMore(page);
            }
        });

    }

    protected void loadMore(int page){
        pageLoad(page);
    }
    private void addPage(){
        page++;
    }

    private void pageLoad(final int page) {
        if (request == null){
            request = new GetMyPraiseRequest();
        }
        request.setPraiseType(PraiseType.User.getValue());
        request.setRowPage(10);
        request.setPageNum(page);
        request.setRequestUserId(UserInfoDo.getInstance().getUid());
        JsonElement requestBody = RequestUtil.getInstance().toJsonElement(request);
        userInfoDo.getMyLike(requestBody, new SuperService.CallBack<GetMyPraiseResponse>() {
            @Override
            public void OnSucess(ResponseParameter<GetMyPraiseResponse> response) {
                if (response.object.getSuccess().equals("1")) {
                    addPage();
                    final MyPraise myTopic = response.object.getData();
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
                        }, ILikedActivity.this);
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
            public void OnProcess(ResponseParameter<GetMyPraiseResponse> response, int curValue, int totalValue) {

            }
        });
    }
}
