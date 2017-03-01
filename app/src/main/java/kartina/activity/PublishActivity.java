package kartina.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.gson.JsonElement;
import com.kartina.service.model.AbstractResponse;

import org.json.JSONObject;


import kartina.R;
import kartina.card.DataManager.RequestUtil;
import kartina.card.DataManager.ResponseParameter;
import kartina.card.DataManager.SuperService;
import kartina.card.bean.Tag;
import kartina.model.UIModel.UserActionDo;
import kartina.model.UIModel.UserInfoDo;
import kartina.model.model.PublishRequest;
import kartina.msg.MessageCenter;
import kartina.msg.MessageConstant;
import kartina.util.AppUtils;


public class PublishActivity extends SuperActivity {
    private LinearLayout tagsLayout;
    private TextInputEditText contentTitle;
    private TextInputEditText content;
    private Button publishBtn;
    private Spinner tagsSpinner;
    private Tag<String> tag;
    private int publishType;
    private String itemId;
    private UserActionDo userActionDo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_publish);
        // 获取传过来的数据
        Intent data = getIntent();
        String actionBarTitle = data.getStringExtra("title");
        publishType = data.getIntExtra("publishType", 0);
        itemId = data.getStringExtra("itemId");
        if (actionBarTitle == null) {
            setTitle("发布");
        } else {
            setTitle(actionBarTitle);
        }
        initViews();
    }

    @Override
    protected void loadData() {

    }

    protected void initViews() {
        userActionDo = new UserActionDo();
        tagsLayout = (LinearLayout) findViewById(R.id.tags_layout);
        contentTitle = (TextInputEditText) findViewById(R.id.title);
        content = (TextInputEditText) findViewById(R.id.content);
        publishBtn = (Button) findViewById(R.id.publish_btn);
        tagsSpinner = (Spinner) findViewById(R.id.tagsSpinner);
        setListener();
    }

    protected void setListener() {
        tagsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    tag = new Tag<String>(getResources().getStringArray(R.array.topicTags)[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        publishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppUtils.isLogin(PublishActivity.this)) {
                    showProgressDialog("发布", "正在发布。。。");
                    PublishRequest publishRequest = new PublishRequest();
                    publishRequest.setRequestUserId(UserInfoDo.getInstance().getUid());
                    publishRequest.setUserName(UserInfoDo.getInstance().getUserNick());
                    publishRequest.setPublishType(publishType);
                    publishRequest.setTitle(contentTitle.getText().toString());
                    publishRequest.setContent(content.getText().toString());
                    publishRequest.setScopeId(itemId);
                    JsonElement requestBody = RequestUtil.getInstance().toJsonElement(publishRequest);
                    userActionDo.publish(requestBody, new SuperService.CallBack<AbstractResponse>() {
                        @Override
                        public void OnSucess(ResponseParameter<AbstractResponse> response) {
                            clearProgressDialog();
                            if (response.object.getSuccess().equals("1")) {
                                MessageCenter.Notification notification = new MessageCenter.Notification<Integer>();
                                notification.data = publishType;
                                MessageCenter.postMsgNotify(MessageConstant.OBSERVER_PUBLISH, notification);
                                mHandler_UI.sendEmptyMessageDelayed(MSG_RELEASE_SUC,1500);
                            } else {
                                Message msg = new Message();
                                msg.what = MSG_RELEASE_FAL;
                                msg.obj = response.object.getError().getMsg();
                                mHandler_UI.sendMessageDelayed(msg,1500);
                            }
                        }

                        @Override
                        public <D> void OnError(ResponseParameter<D> response) {
                            clearProgressDialog();
                        }

                        @Override
                        public void OnProcess(ResponseParameter<AbstractResponse> response, int curValue, int totalValue) {

                        }
                    });
                }
            }
        });
    }

    /**
     * 检测输入是否完整
     *
     * @return
     */
    private boolean Check() {
        return true;
    }

    private static final int MSG_RELEASE_SUC = 0;
    private static final int MSG_RELEASE_FAL = 1;
    private static final int MSG_NET_ERROR = 2;

    private Handler mHandler_UI = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case MSG_RELEASE_SUC:
                    showToast("发布成功！");
//				finishActivity(Result_Release_OK);
                    finish();
                    break;
                case MSG_RELEASE_FAL:
                    showToast("发布失败！\n" + msg.obj.toString());
                    //finishActivity(Result_Release_FAIL);
                    finish();
                    break;
                case MSG_NET_ERROR:
                    showToast("网络错误！");
                    break;
                default:
                    showToast("未知错误");
                    JSONObject object = (JSONObject) msg.obj;
                    AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
                    builder.setTitle("故障！");
                    builder.setMessage(msg.obj.toString());
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    break;
            }
        }

    };

    private void init() {

    }
}
