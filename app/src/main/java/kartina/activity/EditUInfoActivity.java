package kartina.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.kartina.constant.UserType;
import com.kartina.service.model.AbstractRequest;
import com.kartina.service.model.user.StudentInfo;
import com.kartina.service.model.user.TeacherInfo;
import com.kartina.service.model.user.UpdateStudentInfoRequest;
import com.kartina.service.model.user.UpdateStudentInfoResponse;
import com.kartina.service.model.user.UserInfo;

import kartina.R;
import kartina.card.DataManager.RequestUtil;
import kartina.card.DataManager.ResponseParameter;
import kartina.card.DataManager.SuperService;
import kartina.model.UIModel.UserInfoDo;
import kartina.util.AppUtils;
import kartina.util.SharedpreferncesUtil;
import kartina.view.UserInfoItem;

public class EditUInfoActivity extends SuperActivity {
    private ImageView avatarView;
    private UserInfoItem nameView;
    private Spinner genderView;
    private UserInfoItem academyView;
    private UserInfoItem gradeOrResearchFieldView;
    private UserInfoItem majorOrProtaionView;
    private UserInfoItem schoolView;
    private TextView signView;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_edit_uinfo);
        setTitle("资料编辑");
        initView();
        loadData();
    }

    @Override
    protected void loadData() {
        loadUserInfo();
    }

    private void initView() {
        avatarView = (ImageView) findViewById(R.id.avatar);
        nameView = (UserInfoItem) findViewById(R.id.name);
        genderView = (Spinner) findViewById(R.id.gender);
        academyView = (UserInfoItem) findViewById(R.id.academy);
        gradeOrResearchFieldView = (UserInfoItem) findViewById(R.id.grade_or_protation);
        majorOrProtaionView = (UserInfoItem) findViewById(R.id.major_or_researchfield);
        schoolView = (UserInfoItem) findViewById(R.id.school);
        signView = (TextView) findViewById(R.id.sign);
        saveBtn = (Button) findViewById(R.id.save_btn);

        genderView.setGravity(Gravity.RIGHT);
        genderView.setSelection(0);

        genderView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用保存接口
                AbstractRequest request = null;
                UserInfo userInfo = UserInfoDo.getInstance().getUserInfo();
                if (userInfo != null && userInfo.getType() ==  UserType.Student.getValue()){
                    request = new UpdateStudentInfoRequest();
                    request.setRequestUserId(UserInfoDo.getInstance().getUserInfo().getUserId());
                    ((UpdateStudentInfoRequest)request).setAcademy(academyView.getRightContent());
                    ((UpdateStudentInfoRequest)request).setGrade(gradeOrResearchFieldView.getRightContent());
                    ((UpdateStudentInfoRequest)request).setMajor(majorOrProtaionView.getRightContent());
                    ((UpdateStudentInfoRequest)request).setSchool(schoolView.getRightContent());
                    ((UpdateStudentInfoRequest)request).setNickName(nameView.getRightContent());
                    ((UpdateStudentInfoRequest)request).setProfile(signView.getText().toString());
                    ((UpdateStudentInfoRequest)request).setSex(genderView.getSelectedItem().toString());
                }else {//用户为老师

                }
                UserInfoDo.getInstance().completeUInfo(request,UpdateStudentInfoResponse.class, new SuperService.CallBack<UpdateStudentInfoResponse>() {
                    @Override
                    public void OnSucess(ResponseParameter<UpdateStudentInfoResponse> response) {
                        if (response.object.getSuccess().equals("1")) {
                            UserInfoDo.getInstance().setUserInfo(response.object.getData());
                            showText("保存成功");
                            finish();
                        } else {
                            showText(response.object.getError().getMsg());
                            Log.e(this.getClass().getName() + "Error", RequestUtil.getInstance().toJsonString(response.object.getError()));
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
        });

    }

    private void loadUserInfo() {
        if (UserInfoDo.getInstance().getUserInfo() != null) {
            fillEditInfo(UserInfoDo.getInstance().getUserInfo());
        } else if (SharedpreferncesUtil.getUserInfoFromDisk(getApplicationContext()) != null) {
            fillEditInfo(SharedpreferncesUtil.getUserInfoFromDisk(getApplicationContext()));
        } else {
            AppUtils.toLogin(getBaseContext());
        }
    }

    private void fillEditInfo(UserInfo userInfo) {
        if (userInfo.getType() == UserType.Student.getValue()) {
            StudentInfo student = (StudentInfo) userInfo;
            //           avatarView.setImageResource();
            gradeOrResearchFieldView.setLeftText("年级");
            gradeOrResearchFieldView.setRightText(student.getGrade());
            academyView.setRightText(student.getAcademy());
            majorOrProtaionView.setLeftText("专业");
            majorOrProtaionView.setRightText(student.getMajor());
        } else if (userInfo.getType() == UserType.Teacher.getValue()) {
            TeacherInfo teacher = (TeacherInfo) userInfo;
            academyView.setRightText(teacher.getAcademy());
            gradeOrResearchFieldView.setLeftText("方向");
            gradeOrResearchFieldView.setRightText(teacher.getResearchField());
            majorOrProtaionView.setLeftText("职称");
            majorOrProtaionView.setRightText(teacher.getPositionalTitles());
        }
        nameView.setRightText(userInfo.getNickName());
        signView.setText(userInfo.getProfile());
        if (userInfo.getSex() != null && userInfo.getSex().equals("女")) {
            genderView.setSelection(1);
        } else {
            genderView.setSelection(0);
        }
    }
}
