package kartina.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import kartina.R;
import kartina.model.UIModel.UserInfoDo;
import kartina.msg.MessageCenter;
import kartina.msg.MessageConstant;
import kartina.util.SharedpreferncesUtil;


public class SettingsActivity extends SuperActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_settings);
        initViews();
    }

    @Override
    protected void loadData() {

    }

    /**控件信息**/
        private RelativeLayout mPersonInfo;
        private RelativeLayout mSafeCenter;
        private RelativeLayout mUsualFuction;
        private RelativeLayout mAboutUs;
        private RelativeLayout mVersionInfo;
        private Button mExit;



        protected void initViews() {
            // TODO Auto-generated method stub
            mPersonInfo = (RelativeLayout) findViewById(R.id.layout_personInfo_Setting);
            mSafeCenter = (RelativeLayout) findViewById(R.id.layout_safeCenter_Setting);
            mUsualFuction = (RelativeLayout) findViewById(R.id.layout_usualFuc_Setting);
            mAboutUs     = (RelativeLayout) findViewById(R.id.layout_about_Setting);
            mVersionInfo = (RelativeLayout) findViewById(R.id.layout_version_Setting);
            mExit       =  (Button) findViewById(R.id.btn_exit_Setting);

            setListener();
        }

        protected void setListener() {
            // TODO Auto-generated method stub
            mPersonInfo.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(),EditUInfoActivity.class);
                    intent.putExtra("type", 1);
                    startActivity(intent);
                }
            });
            mSafeCenter.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                    builder.setTitle("温馨提示");
                    builder.setMessage("开发人员：麻伶毅   \n联系方式：15608082301  \n纯手工，纯手打，欢迎骚扰");
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
            mUsualFuction.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                    builder.setTitle("温馨提示");
                    builder.setMessage("开发人员：麻伶毅   \n联系方式：15608082301  \n纯手工，纯手打，欢迎骚扰");
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
            mAboutUs.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                    builder.setTitle("温馨提示");
                    builder.setMessage("开发人员：麻伶毅   \n联系方式：15608082301  \n纯手工，纯手打，欢迎骚扰");
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
            mVersionInfo.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                    builder.setTitle("温馨提示");
                    builder.setMessage("版本：1.0   \n开发人员：麻伶毅   \n联系方式：15608082301  \n纯手工，纯手打，欢迎骚扰");
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
            mExit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    showToast("退出当前账户");
                    /**
                     * 清除内存缓存以及SD卡缓存
                     */
                    SharedpreferncesUtil.clearDiskByKey(SharedpreferncesUtil.USER_FILE_NAME,getBaseContext());
                    UserInfoDo.getInstance().clearUserInfo();
                    MessageCenter.postMsgNotify(MessageConstant.OBSERVER_LOGOUT,new MessageCenter.Notification<Object>());
                    finish();
                }
            });
        }

}
