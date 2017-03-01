package kartina.fragment;

/**
 * Created by David on 2016/11/20.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import com.kartina.constant.UserType;
import com.kartina.service.model.user.RegisterRequest;
import com.kartina.service.model.user.UpdateStudentInfoRequest;
import com.kartina.service.model.user.UpdateStudentInfoResponse;
import com.kartina.service.model.user.UserInfo;

import kartina.R;
import kartina.card.DataManager.ResponseParameter;
import kartina.card.DataManager.SuperService;
import kartina.model.UIModel.UserInfoDo;
import kartina.util.SharedpreferncesUtil;

/**注册的第二个步骤的碎片**/
public class CompleteFragment extends SuperFragment {
    private Context mContext;
    private View view;
    private Button btn_finished; //完成按钮
    private StateChangeListerner stateChangeListerner;
    private CompleteHolder completeHolder;
    private UserType userType;
    public static final int MSG_COMPLETE_SUC = 10;
    public static final int MSG_COMPLETE_FAI = 11;
    public static final int MSG_NER_ERR = 12;
    class CompleteHolder{
        TextView txt_name;
        TextView txt_school;
        Spinner genderView;
        TextView txt_grade;
        EditText academyView;
    }
    public CompleteFragment(Context context){
        mContext = context;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(completeHolder == null)
            completeHolder = new CompleteHolder();

        btn_finished = (Button) view.findViewById(R.id.btn_finished_signfm);
        completeHolder.txt_grade = (EditText) view.findViewById(R.id.academy);
        completeHolder.txt_name = (EditText)  view.findViewById(R.id.edit_name_complete);
        completeHolder.txt_school = (EditText) view.findViewById(R.id.edit_school_complete);
        completeHolder.genderView = (Spinner) view.findViewById(R.id.edit_sex_complete);
        completeHolder.academyView = (EditText) view.findViewById(R.id.academy);
        setListener();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    protected View getContentView() {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fm_sign_complete_info, null,false);
        return view;
    }

    @Override
    protected void loadData() {

    }

    private void typeChooseWindow() {
        final String[] typesStr = {"四川大学","中山大学","北京大学","湖南大学","西南民族大学"};
        int item_choosed = 0;
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
        alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            int itemChoose;
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertBuilder.setSingleChoiceItems(typesStr, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                completeHolder.txt_school.setText(typesStr[which]);
            }
        });
        AlertDialog typeDialog = alertBuilder.create();
        typeDialog.setCanceledOnTouchOutside(true);
        typeDialog.setCancelable(true);
        typeDialog.setTitle("选择学校");
        typeDialog.show();

        WindowManager.LayoutParams params = typeDialog.getWindow()
                .getAttributes();
        params.width = 800;      //对话框宽度
        params.height = 1200;    //对话框高度
        //mCityDialog.getWindow().setLayout(200, 500);  //同上面一样的效果
        params.alpha = (float)0.7;   //设置透明度
        typeDialog.getWindow().setAttributes(params);

    }
    public void setListener(){

        completeHolder.txt_school.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                typeChooseWindow();
            }
        });

        btn_finished.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(isCompleteAll()){
                    if (UserInfoDo.getInstance().getUserInfo().getType() == UserType.Student.getValue()){
                        UpdateStudentInfoRequest updateUserInfoRequest = new UpdateStudentInfoRequest();
                        updateUserInfoRequest.setRequestUserId(UserInfoDo.getInstance().getUserInfo().getUserId());
                        updateUserInfoRequest.setAcademy(completeHolder.academyView.getText().toString());
                        updateUserInfoRequest.setSex(((String)completeHolder.genderView.getSelectedItem()).toString());
                        updateUserInfoRequest.setSchool(completeHolder.txt_school.getText().toString());
                        updateUserInfoRequest.setNickName(completeHolder.txt_name.getText().toString());
                        UserInfoDo.getInstance().completeUInfo(updateUserInfoRequest,UpdateStudentInfoResponse.class,new SuperService.CallBack<UpdateStudentInfoResponse>() {
                            @Override
                            public void OnSucess(ResponseParameter<UpdateStudentInfoResponse> response) {
                                if (response.object.getSuccess().equals("1")){
                                    UserInfoDo.getInstance().setUserInfo(response.object.getData());
                                    SharedpreferncesUtil.saveUserInfoToDisk(mContext, UserInfoDo.getInstance().getUserInfo());
                                    if (stateChangeListerner != null){
                                        stateChangeListerner.stateChanged(MSG_COMPLETE_SUC);
                                    }
                                }else {
                                    showToast(response.object.getError().getMsg());
                                    if (stateChangeListerner != null){
                                        stateChangeListerner.stateChanged(MSG_COMPLETE_FAI);
                                    }
                                }
                            }

                            @Override
                            public <D> void OnError(ResponseParameter<D> response) {

                            }

                            @Override
                            public void OnProcess(ResponseParameter<UpdateStudentInfoResponse> response, int curValue, int totalValue) {

                            }
                        });
                    }
                }else {
                    Dialog dialog = new Dialog(mContext);
                    dialog.setTitle("请完善基本信息！");
                    dialog.show();
                }
            }
        });
    }

    protected boolean isCompleteAll(){
        if (completeHolder.txt_grade.getText().toString().equals("")
                || completeHolder.txt_name.getText().toString().equals("")
                || completeHolder.txt_school.getText().toString().equals("")){
            return false;
        }
        return true;

    }

    public void setStateChangeListerner(StateChangeListerner stateChangeListerner) {
        this.stateChangeListerner = stateChangeListerner;
    }
    public interface StateChangeListerner{
        void stateChanged(int state);
    }
}
