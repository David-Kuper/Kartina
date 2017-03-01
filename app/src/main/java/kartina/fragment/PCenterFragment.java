package kartina.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kartina.constant.UserType;
import com.kartina.service.model.user.StudentInfo;
import com.kartina.service.model.user.TeacherInfo;
import com.kartina.service.model.user.UserInfo;

import kartina.R;
import kartina.activity.EditUInfoActivity;
import kartina.activity.ICommentedActivity;
import kartina.activity.IFocusActivity;
import kartina.activity.IPublishActivity;
import kartina.activity.ILikedActivity;
import kartina.activity.LoginActivity;
import kartina.activity.SettingsActivity;
import kartina.model.UIModel.UserInfoDo;
import kartina.msg.MessageCenter;
import kartina.msg.MessageConstant;
import kartina.util.AppUtils;
import kartina.util.SharedpreferncesUtil;
import kartina.view.CircleImageView;
import kartina.view.UserInfoItem;


/**
 * Created by David on 2016/10/31.
 */

public class PCenterFragment extends SuperFragment {
    private RelativeLayout loginLayout;
    private RelativeLayout notLoginLayout;
    private LinearLayout iJoinLayout;
    private LinearLayout iLikeLayout;
    private LinearLayout iCommentLayout;
    private LinearLayout iFocusLayout;
    private LinearLayout settingLayout;
    private ImageView editView;
    private CircleImageView avatarView;
    private UserInfoItem nameView;
    private UserInfoItem genderView;
    private UserInfoItem academyView;
    private UserInfoItem gradeOrResearchFiledView;
    private UserInfoItem majorOrProtaionView;
    private TextView signView;
    private View view;
    private MessageCenter.Observer logOutObserver;
    private MessageCenter.Observer userInfoObserver;

    boolean isLogin = false;

    @Override
    protected View getContentView() {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_pcenter, null, false);
        loginLayout = (RelativeLayout) view.findViewById(R.id.login);
        notLoginLayout = (RelativeLayout) view.findViewById(R.id.not_login);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iJoinLayout = (LinearLayout) view.findViewById(R.id.i_join);
        iCommentLayout = (LinearLayout) view.findViewById(R.id.i_comment);
        iFocusLayout = (LinearLayout) view.findViewById(R.id.i_focus);
        iLikeLayout = (LinearLayout) view.findViewById(R.id.i_liked);
        settingLayout = (LinearLayout) view.findViewById(R.id.setting);
        editView = (ImageView) view.findViewById(R.id.edit_userInfo);
        avatarView = (CircleImageView) view.findViewById(R.id.avatar);
        nameView = (UserInfoItem) view.findViewById(R.id.name);
        genderView = (UserInfoItem) view.findViewById(R.id.gender);
        academyView = (UserInfoItem) view.findViewById(R.id.academy);
        gradeOrResearchFiledView = (UserInfoItem) view.findViewById(R.id.grade_or_protation);
        majorOrProtaionView = (UserInfoItem) view.findViewById(R.id.major_or_researchfield);
        signView = (TextView) view.findViewById(R.id.sign);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }


    private void init() {
        if (AppUtils.isLogin(getActivity())) {
            showLoginedHead();
        } else {
            showNotLoginHead();
        }

        notLoginLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        userInfoObserver = MessageCenter.registerMsgObserver(MessageConstant.OBSERVER_USERINFOCHANGED, new MessageCenter.Observer() {
            @Override
            public void onMessageCall(String msg, MessageCenter.Notification data) {
                if (MessageConstant.OBSERVER_USERINFOCHANGED.equals(msg)) {
                    setUserInfo(UserInfoDo.getInstance().getUserInfo());
                }
            }
        });
        logOutObserver = MessageCenter.registerMsgObserver(MessageConstant.OBSERVER_LOGOUT, new MessageCenter.Observer() {
            @Override
            public void onMessageCall(String msg, MessageCenter.Notification data) {
                if (msg.equals(MessageConstant.OBSERVER_LOGOUT)) {
                    showNotLoginHead();
                }
            }
        });

        editView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入个人编辑页面
                if (AppUtils.isLogin(getActivity())) {
                    Intent intent = new Intent(getContext(), EditUInfoActivity.class);
                    startActivity(intent);
                }
            }
        });
        iJoinLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入我参与的页面
                if (AppUtils.isLogin(getActivity())) {
                    Intent intent = new Intent(getContext(), IPublishActivity.class);
                    startActivity(intent);
                } else {

                }
            }
        });
        iFocusLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入我关注的页面
                if (AppUtils.isLogin(getActivity())) {
                    Intent intent = new Intent(getContext(), IFocusActivity.class);
                    startActivity(intent);
                } else {

                }
            }
        });
        iLikeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入我赞过的页面
                if (AppUtils.isLogin(getActivity())) {
                    Intent intent = new Intent(getContext(), ILikedActivity.class);
                    startActivity(intent);
                } else {

                }
            }
        });
        iCommentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入我评论的页面
                if (AppUtils.isLogin(getActivity())) {
                    Intent intent = new Intent(getContext(), ICommentedActivity.class);
                    startActivity(intent);
                } else {

                }
            }
        });

        settingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入设置页面
                Intent intent = new Intent(getContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showNotLoginHead() {
        loginLayout.setVisibility(View.GONE);
        notLoginLayout.setVisibility(View.VISIBLE);
    }

    private void showLoginedHead() {
        UserInfo userInfo = UserInfoDo.getInstance().getUserInfo();
        if (userInfo == null) {
            userInfo = SharedpreferncesUtil.getUserInfoFromDisk(getContext());
            if (userInfo != null) {
                UserInfoDo.getInstance().setUserInfo(userInfo);
                setUserInfo(userInfo);
            } else {
                AppUtils.toLogin(getContext());
            }
        } else {
            setUserInfo(userInfo);
        }
        loginLayout.setVisibility(View.VISIBLE);
        notLoginLayout.setVisibility(View.GONE);
    }

    private void setUserInfo(UserInfo userInfo) {
        if (userInfo != null) {
            if (UserType.Student == UserType.getUserTypeByValue(userInfo.getType())){
                StudentInfo student = (StudentInfo) userInfo;
                nameView.setRightText(userInfo.getNickName());
                genderView.setRightText(student.getSex());
                academyView.setRightText(student.getAcademy());
                gradeOrResearchFiledView.setLeftText("年级：");
                gradeOrResearchFiledView.setRightText(student.getGrade());
                majorOrProtaionView.setRightText("专业：");
                majorOrProtaionView.setRightText(student.getMajor());
                signView.setText(userInfo.getProfile());
//            avatarView.setImageResource();
            }else {
                TeacherInfo teacher = (TeacherInfo) userInfo;
                nameView.setRightText(teacher.getNickName());
                genderView.setRightText(teacher.getSex());
                academyView.setRightText(teacher.getAcademy());
                gradeOrResearchFiledView.setLeftText("方向：");
                gradeOrResearchFiledView.setRightText(teacher.getResearchField());
                majorOrProtaionView.setRightText("职称：");
                majorOrProtaionView.setRightText(teacher.getPositionalTitles());
                signView.setText(teacher.getProfile());
//            avatarView.setImageResource();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (UserInfoDo.getInstance().getUserInfo() == null && SharedpreferncesUtil.getUserInfoFromDisk(getContext()) == null) {
            Log.e("PCenterFragment","not Login");
            showNotLoginHead();
        } else {
            Log.e("PCenterFragment","you are Logined");
            showLoginedHead();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MessageCenter.unRegisterMsgObserver(MessageConstant.OBSERVER_USERINFOCHANGED, userInfoObserver);
        MessageCenter.unRegisterMsgObserver(MessageConstant.OBSERVER_LOGOUT, logOutObserver);
    }

}
