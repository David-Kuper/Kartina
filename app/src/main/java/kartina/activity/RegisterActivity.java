package kartina.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import kartina.R;
import kartina.fragment.CompleteFragment;
import kartina.fragment.SignFragment;

public class RegisterActivity extends SuperActivity {

    String signStatus ;
    String errorMsg;
    String testCode;
    private String uid;
    private boolean isReqFinished = false;
    //用于测试时作为返回给源活动的Action标记

    private  SignFragment signFm;
    private CompleteFragment completeFm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initViews();
        if(signFm == null){
            signFm = new SignFragment(getBaseContext());
        }
        changeFragment(signFm,getSupportFragmentManager());
    }

    @Override
    protected void loadData() {

    }

    protected void initViews(){
        signFm = new SignFragment(getBaseContext());
        completeFm = new CompleteFragment(getBaseContext());

        signFm.setRegisterStateListener(new SignFragment.RegisterStateListener() {
            @Override
            public void OnRegisterSuccess() {
                changeFragment(completeFm,getSupportFragmentManager());
            }

            @Override
            public void OnSignFailure(String msg) {

            }
        });
        completeFm.setStateChangeListerner(new CompleteFragment.StateChangeListerner() {
            @Override
            public void stateChanged(int state) {
                switch (state) {
                    case CompleteFragment.MSG_COMPLETE_SUC:
                        Intent intent = new Intent(getBaseContext(),BasicActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case CompleteFragment.MSG_COMPLETE_FAI:
                        showToast("完善信息失败。");
                        break;
                    case CompleteFragment.MSG_NER_ERR:
                        showToast("网络故障");
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private static void changeFragment(Fragment targetFragment,FragmentManager fManager ){
        FragmentTransaction fTransaction = fManager.beginTransaction();

        fTransaction.replace(R.id.sign_container, targetFragment, "fragment");
        fTransaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fTransaction.commit();
    }

    private boolean statusParse(String status){
        if(signStatus.equals("0")){
            return true;
        }
        else if (signStatus.equals("0211")) {
            //注册失败
            return false;
        }
        else if(signStatus.equals("0111")){
            //获取验证码失败
            return false;
        }
        else {
            //其他失败
            return false;
        }
    }
}


