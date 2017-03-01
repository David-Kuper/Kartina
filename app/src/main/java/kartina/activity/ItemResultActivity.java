package kartina.activity;
/**
 * 更多列表
 */
//TODO 更多结果列表
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
import kartina.card.OnItemActionClickListener;
import kartina.card.ViewType.CardViewType;
import kartina.card.bean.CardBean;
import kartina.card.bean.CardBean1000;
import kartina.card.bean.CardBean1001;
import kartina.card.bean.CardBean1005;
import kartina.card.view.CardView1008;
import kartina.model.ItemBottom;
import kartina.model.ItemHeader;
import kartina.model.NoteBody;
import kartina.model.UIModel.HomeDo;
import kartina.model.UIModel.UserInfoDo;
import kartina.model.model.HomeMoreModel;
import kartina.model.request.HomeMoreRequest;

public class ItemResultActivity extends SuperActivity {
    private ListViewController listViewController;
    private int groupType;
    private HomeDo homeDo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_item_result);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        groupType = intent.getIntExtra("moreType",0);
        if (title != null) {
            setTitle(title);
        } else {
            setTitle("更多");
        }
        init();
        refreshTop();
    }

    private void init() {
        homeDo = new HomeDo();
        listViewController = new ListViewController(this,false);
        CardListView cardListView = (CardListView) findViewById(R.id.listview);
        listViewController.setView(cardListView);

        listViewController.setOnItemClickListener(new CardAdapter.CardAdapterListener() {
            @Override
            public void OnViewPosition(View view, int position, String itemId, CardViewType cardViewType) {
                Intent intent = null;
                if (groupType == CardView1008.MORE_CLASS){
                    intent = new Intent(ItemResultActivity.this,ClassActivity.class);
                }else if (groupType == CardView1008.MORE_TALK){
                    intent = new Intent(ItemResultActivity.this,TalkActivity.class);
                }else if (groupType == CardView1008.MORE_TEACHER){
                    intent = new Intent(ItemResultActivity.this,TeacherActivity.class);
                }
                intent.putExtra("itemId",itemId);
                startActivity(intent);
            }
        });
        listViewController.setOnCheckMoreListener(new CardAdapter.CardAdapterMoreListener() {
            @Override
            public void OnChekMoreClicked(boolean hasNext) {
                  loadMore();
            }
        });
    }

    private void refreshTop() {
         loadData();
    }

    private void loadMore(){
    }
    @Override
    protected void loadData() {
//        List<CardBean> cardList = new ArrayList<>();
//        CardBean cardBean;
//         if (groupType == CardView1008.MORE_CLASS){
//             CardBean1000 cardBean1000;
//             for (int i = 0; i < 3; i++) {
//                 cardBean1000 = new CardBean1000();
//                 cardBean = new CardBean<>();
//                 cardBean1000.academy = "计算机学院";
//                 cardBean1000.name = "数据结构"+i;
//                 cardBean1000.describe = "他是是临时决定六块腹肌网络科技方法阿萨大富翁发问阿多阿道夫";
//                 cardBean1000.focusNum = 123;
//                 cardBean1000.noteNum = 4213;
//                 cardBean.cardViewType = 1000;
//                 cardBean.data = cardBean1000;
//                 cardList.add(cardBean);
//             }
//         }else if(groupType == CardView1008.MORE_TALK){
//             CardBean1005 cardBean1005;
//             for (int i = 0; i < 3; i++) {
//                 ItemHeader itemHeader = new ItemHeader();
//                 ItemBottom itemBottom = new ItemBottom();
//                 NoteBody noteBody = new NoteBody();
//                 cardBean1005 = new CardBean1005();
//                 cardBean = new CardBean<>();
//                 cardBean.cardViewType = 1005;
//
//                 itemBottom.comment_num = 321;
//                 itemBottom.focus_num = 42;
//                 itemBottom.praise_num = 212;
//                 itemHeader.rid = R.mipmap.ic_launcher;
//                 itemHeader.avarta = "http.jpg";
//                 itemHeader.from_who = "王二";
//                 itemHeader.time = "23:23";
//                 itemHeader.origin = "话题圈";
//                 noteBody.describe = "内容啊阿斯蒂芬了可使肌肤算法发送方认为" + i;
//                 noteBody.title = "标题啊搜房网分阿斯蒂芬无非是发生反倒是访问阿斯蒂芬";
//
//                 cardBean1005.mItemHeader = itemHeader;
//                 cardBean1005.mItemBottom = itemBottom;
//                 cardBean1005.noteBody = noteBody;
//
//                 cardBean.data = cardBean1005;
//                 cardList.add(cardBean);
//             }
//         }else if(groupType == CardView1008.MORE_TEACHER){
//             CardBean1001 cardBean1001;
//             for (int i = 0; i < 3; i++) {
//                 cardBean1001 = new CardBean1001();
//                 cardBean = new CardBean<>();
//                 cardBean1001.academy = "计算机学院";
//                 cardBean1001.avatar = null;
//                 cardBean1001.commentNum = 213;
//                 cardBean1001.focusNum = 312;
//                 cardBean1001.isActioned = false;
//                 cardBean1001.name = "王二思";
//                 cardBean1001.noteNum = 123;
//                 cardBean1001.praiseNum = 332;
//                 cardBean1001.prosational = "教授";
//                 cardBean1001.school = "四川大学";
//                 cardBean1001.searchFiled = "图形图像研究";
//                 cardBean.cardViewType = 1001;
//                 cardBean.data = cardBean1001;
//                 cardList.add(cardBean);
//             }
//         }
        showLoading(VIEW_LOADING);
        HomeMoreRequest request = new HomeMoreRequest();
        request.setRequestUserId(UserInfoDo.getInstance().getUid());
        request.setMoreType(groupType);
        JsonElement requestBody = RequestUtil.getInstance().toJsonElement(request);

        homeDo.getMore(requestBody, new SuperService.CallBack<AbstractResponse<HomeMoreModel>>() {
            @Override
            public void OnSucess(ResponseParameter<AbstractResponse<HomeMoreModel>> response) {
                if (response.object.getSuccess().equals("1")){
                    List<CardBean> cardBeanList = response.object.getData().getCardList();
                    if (cardBeanList != null && cardBeanList.size() > 0){
                        showLoading(VIEW_CONTENT);
                        CardProcess.parseCardList(cardBeanList, new CardProcess.CallBack<List<CardBean>>() {
                            @Override
                            public void parseCardSuccess(List<CardBean> list) {
                                listViewController.setData(list,false);
                            }
                        },ItemResultActivity.this);
                    }else {
                        showLoading(VIEW_NODATA);
                    }
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
            public void OnProcess(ResponseParameter<AbstractResponse<HomeMoreModel>> response, int curValue, int totalValue) {

            }
        });
    }
}
