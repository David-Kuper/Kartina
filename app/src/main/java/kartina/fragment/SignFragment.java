package kartina.fragment;

/**
 * Created by David on 2016/11/20.
 */

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.kartina.constant.UserType;
import com.kartina.service.model.user.RegisterRequest;
import com.kartina.service.model.user.RegisterResponse;

import kartina.R;
import kartina.card.DataManager.ResponseParameter;
import kartina.card.DataManager.SuperService;
import kartina.model.UIModel.UserInfoDo;
import kartina.util.CharCheckUtil;
import kartina.util.SharedpreferncesUtil;

/**注册的第一个步骤的碎片**/
public class SignFragment extends SuperFragment {
    private Context mContext;
    private Button btn_sigin; //注册按钮
    boolean isPassCode = true; //验证码是否正确通过检测
    boolean isGetCode = false; //获取不到验证码

    private SignHolder signHolder;
    private UserType userType;
    static final int MSG_CODE_PASS = 0;
    static final int MSG_CODE_GET = 1;
    static final int MSG_CODE_CALLBACK = 2;
    static final int MSG_CODE_PASSFAIL = 3;
    static final int MSG_PHONE_EXIST = 4;
    class SignHolder{
        EditText edit_phone;
        EditText edit_testCode;
        EditText edit_pwd;
        EditText edit_rePwd;
        Button btn_getTestCode;
        Spinner userTypeView;
    }
    private View view;
    private RegisterStateListener registerStateListener;
    public interface RegisterStateListener {
        void OnRegisterSuccess();
        void OnSignFailure(String msg);
    }

    public SignFragment(Context context){
        this.mContext = context;
    }


    private Handler mHandler_fmUI = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case MSG_CODE_CALLBACK:
                    break;
                case MSG_CODE_GET:
                    break;
                case MSG_CODE_PASS:
                    if((SignCheck())){//注册格式正确
                        final RegisterRequest registerRequest = new RegisterRequest();
                        registerRequest.setUserName(signHolder.edit_phone.getText().toString());
                        registerRequest.setPassword(signHolder.edit_pwd.getText().toString());
                        registerRequest.setUserType(userType.getValue());
                        UserInfoDo.getInstance().register(registerRequest, new SuperService.CallBack<RegisterResponse>() {
                            @Override
                            public void OnSucess(ResponseParameter<RegisterResponse> response) {
                                if (response.object.getSuccess().equals("1")){
                                    Log.e(this.getClass().getName(),"Register Sucess");
                                    UserInfoDo.getInstance().setUserInfo(response.object.getData());
                                    SharedpreferncesUtil.saveUserInfoToDisk(getContext(),response.object.getData());
                                    if (registerStateListener != null){
                                        registerStateListener.OnRegisterSuccess();
                                    }
                                }else {
                                    showToast(response.object.getError().getMsg());
                                    Log.e(this.getClass().getName(),"Register Failure.  MSG :"+response.object.getError().getMsg());
                                }
                            }

                            @Override
                            public <D> void OnError(ResponseParameter<D> response) {
                                Log.e(this.getClass().getName(),"OnError!" + response.error.toString());
                            }

                            @Override
                            public void OnProcess(ResponseParameter<RegisterResponse> response, int curValue, int totalValue) {

                            }
                        });
                    }
                    break;
                case MSG_CODE_PASSFAIL:
                    showToast("注册失败！验证码未通过");
                    break;
                case MSG_PHONE_EXIST:
                    showToast("手机号已存在");
                    break;
                default:
                    break;
            }
        }

    };


    @Override
    protected View getContentView() {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fm_sign_phone, null,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_sigin = (Button) view.findViewById(R.id.btn_sign);
        if(signHolder == null)
            signHolder = new SignHolder();
        signHolder.edit_phone = (EditText) view.findViewById(R.id.edit_phone_signfm);
        signHolder.edit_pwd = (EditText) view.findViewById(R.id.edit_pwd_signfm);
        signHolder.edit_rePwd =(EditText) view.findViewById(R.id.edit_rePwd_signfm);
        signHolder.edit_testCode = (EditText) view.findViewById(R.id.edit_testCode_signfm);
        signHolder.btn_getTestCode = (Button) view.findViewById(R.id.btn_getTestCode_signfm);
        signHolder.userTypeView = (Spinner) view.findViewById(R.id.user_type);
        setListener();
    }

    @Override
    protected void loadData() {

    }

    /**
     * 注册参数校验
     *
     * @param
     * @param
     * @return false || true
     */
    public boolean SignCheck() {
        if(signHolder.edit_phone.getText().toString().equals("")
                ||signHolder.edit_testCode.getText().toString().equals("")
                ||signHolder.edit_pwd.getText().equals("")
                ||signHolder.edit_rePwd.getText().equals("")){
            showToast("补充完整的信息才能创建哦！");
            return false;
        }else {
            if (!CharCheckUtil.isPhoneNum(signHolder.edit_phone.getText().toString())) {
                showToast("手机号格式不对！");
                return false;
            }
            if (!CharCheckUtil.isAllDigit(signHolder.edit_testCode.getText().toString())) {
                showToast("验证码是纯数字，请重新输入!");
                return false;
            }
            if(!signHolder.edit_pwd.getText().toString().
                    equals(signHolder.edit_rePwd.getText().toString())){
                showToast("两次输入密码不一致，请重新输入！");
                return false;
            }
            if(signHolder.edit_pwd.getText().toString().trim().equals("")
                    ||signHolder.edit_rePwd.getText().toString().trim().equals("")){
                showToast("密码不能为空！");
                return false;
            }
            if(signHolder.edit_testCode.getText().toString().trim().equals("")){
                showToast("验证码不能为空");
                return false;
            }
        }
        return true;
    }

    public void setRegisterStateListener(RegisterStateListener registerStateListener) {
        this.registerStateListener = registerStateListener;
    }
    private int getPassCode(){
        int code = (int) (Math.random() * 100000);
        isGetCode = true;
        return code;
    }
    protected void setListener() {

        btn_sigin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//				    Toast.makeText(mContext, "点击注册", 1500).show();
                //提交短信验证码
                //在短信验证码回调函数里面回调发送注册请求。
                try {
                    if (!isPassCode) {
                        mHandler_fmUI.sendEmptyMessage(MSG_CODE_PASSFAIL);
                    }else {
                        mHandler_fmUI.sendEmptyMessage(MSG_CODE_PASS);
                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        signHolder.userTypeView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    userType = UserType.Student;
                }else {
                    userType = UserType.Teacher;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        signHolder.btn_getTestCode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                /**发送验证码请求**/

                if(signHolder.edit_phone.getText().toString().trim().equals("")){
                    showToast("手机号不能为空！");
                }
                else{
                    time = 60;
                    mHandler_Timer.sendEmptyMessage(MSG_TIMER_WARN);
                    int passCode = getPassCode();
                    signHolder.edit_testCode.setText(String.valueOf(passCode));
                }
            }
        });
    }
    public static final int MSG_TIMER_WARN = 100;
    public int time = 60;
    Handler mHandler_Timer = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case MSG_TIMER_WARN:
                    if (time >0) {
                        time--;
                        if(signHolder.btn_getTestCode.isEnabled())
                            signHolder.btn_getTestCode.setEnabled(false);
                        signHolder.btn_getTestCode.setText("("+time+")s");
                        mHandler_Timer.sendEmptyMessageDelayed(MSG_TIMER_WARN, 1000);
                    }else {
                        signHolder.btn_getTestCode.setEnabled(true);
                        signHolder.btn_getTestCode.setText("获取验证码");
                    }
                    break;

                default:
                    break;
            }
        }

    };


}
