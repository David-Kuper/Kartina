package kartina.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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
import kartina.card.bean.CardBean;
import kartina.model.NoteBody;
import kartina.model.UIModel.DetailDo;
import kartina.model.UIModel.UserActionDo;
import kartina.model.UIModel.UserInfoDo;
import kartina.model.model.GetPostDetailModelRequest;
import kartina.model.model.PostDetailModel;
import kartina.msg.MessageCenter;
import kartina.util.AppUtils;
import kartina.view.ChatView;
import kartina.view.NoteBodyView;

public class NoteActivity extends SuperActivity {
    private ImageView avatarView;
    private TextView nickView;
    private TextView timeView;
    private ImageView actionView;
    private TextView replyNumView;
    private NoteBodyView noteBodyView;
    private ChatView chatView;
    private ListViewController listViewController;
    boolean isFocus = false;
    private String itemId;
    private DetailDo detailDo;
    private UserActionDo userActionDo;
    private MessageCenter.Observer commentObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_note);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        itemId = intent.getStringExtra("itemId");
        if (title != null) {
            setTitle(title);
        } else {
            setTitle("帖子");
        }
        init();

        refreshTop();
    }

    private void init() {
        detailDo = new DetailDo();
        userActionDo = new UserActionDo();

        listViewController = new ListViewController(this, false);
        CardListView cardListView = (CardListView) findViewById(R.id.listview);
        avatarView = (ImageView) findViewById(R.id.avatar);
        timeView = (TextView) findViewById(R.id.time);
        nickView = (TextView) findViewById(R.id.nick);
        actionView = (ImageView) findViewById(R.id.action);
        replyNumView = (TextView) findViewById(R.id.reply_num);
        chatView = (ChatView) findViewById(R.id.chat_view);
        noteBodyView = (NoteBodyView) findViewById(R.id.note_body);

        listViewController.setView(cardListView);
        listViewController.setOnCheckMoreListener(new CardAdapter.CardAdapterMoreListener() {
            @Override
            public void OnChekMoreClicked(boolean hasNext) {
                Toast.makeText(getBaseContext(), "Click Check More!", Toast.LENGTH_SHORT).show();
            }
        });

        chatView.setChatViewListener(new ChatView.ChatViewListener() {
            @Override
            public void OnSendMsg(String content) {
                if (AppUtils.isLogin(NoteActivity.this)) {
                    AddCommentRequest request = new AddCommentRequest();
                    request.setRequestUserId(UserInfoDo.getInstance().getUid());
                    request.setContent(content);
                    request.setCommentType(CommentType.Post.getValue());
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

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFocus == false) {
                    actionView.setImageResource(R.mipmap.focus_normal);
                    isFocus = true;
                } else {
                    actionView.setImageResource(R.mipmap.focus_clicked);
                    isFocus = false;
                }
            }
        });
    }

    private void refreshTop() {
        loadData();
    }

    private void refreshCommentList() {
         refreshTop();
    }

    @Override
    protected void loadData() {
        showLoading(VIEW_LOADING);
        final GetPostDetailModelRequest request = new GetPostDetailModelRequest();
        request.setPostId(itemId);
        request.setRequestUserId(UserInfoDo.getInstance().getUid());
        JsonElement requestBody = RequestUtil.getInstance().toJsonElement(request);
        detailDo.loadNoteDetailInfo(requestBody, new SuperService.CallBack<AbstractResponse<PostDetailModel>>() {
            @Override
            public void OnSucess(ResponseParameter<AbstractResponse<PostDetailModel>> response) {
                if (response.object.getSuccess().equals("1")) {
                    showLoading(VIEW_CONTENT);
                    PostDetailModel postDetailModel = response.object.getData();
                    detailDo.setPostDetailModel(postDetailModel);
//                    avatarView.setImageResource();
                    nickView.setText(postDetailModel.getUserName());
                    timeView.setText(postDetailModel.getTime());
                    isFocus = postDetailModel.isFocused;
                    if (isFocus == false) {
                        actionView.setImageResource(R.mipmap.focus_normal);
                        isFocus = true;
                    } else {
                        actionView.setImageResource(R.mipmap.focus_clicked);
                        isFocus = false;
                    }

                    NoteBody noteBody = new NoteBody();
                    noteBody.title = postDetailModel.getTitle();
                    noteBody.describe = postDetailModel.getDescribe();
                    noteBodyView.setData(noteBody);

                    ArrayList<CardBean> list = postDetailModel.getCardList();
                    replyNumView.setText("( "+list.size()+" )");
                    CardProcess.parseCardList(list, new CardProcess.CallBack<List<CardBean>>() {
                        @Override
                        public void parseCardSuccess(List<CardBean> list) {
                            listViewController.setData(list, true);
                        }
                    }, NoteActivity.this);
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
            public void OnProcess(ResponseParameter<AbstractResponse<PostDetailModel>> response, int curValue, int totalValue) {

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
    }
}
