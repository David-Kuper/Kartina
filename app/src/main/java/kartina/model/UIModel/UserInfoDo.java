package kartina.model.UIModel;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.kartina.constant.UserType;
import com.kartina.model.User;
import com.kartina.service.model.AbstractRequest;
import com.kartina.service.model.AbstractResponse;
import com.kartina.service.model.user.GetUserInfoRequest;
import com.kartina.service.model.user.GetUserInfoResponse;
import com.kartina.service.model.user.LoginRequest;
import com.kartina.service.model.user.RegisterRequest;
import com.kartina.service.model.user.RegisterResponse;
import com.kartina.service.model.user.StudentLoginResponse;
import com.kartina.service.model.user.UserInfo;

import kartina.card.DataManager.Api;
import kartina.card.DataManager.RequestUtil;
import kartina.card.DataManager.SuperService;
import kartina.model.model.GetMyCommentResponse;
import kartina.model.model.GetMyFollowResponse;
import kartina.model.model.GetMyPostResponse;
import kartina.model.model.GetMyPraiseResponse;
import kartina.model.model.GetMyTopicResponse;
import kartina.model.model.MyComment;
import kartina.model.model.MyFollow;
import kartina.model.model.MyPraise;
import kartina.model.model.MyPublish;
import kartina.util.SharedpreferncesUtil;

/**
 * Created by David on 2016/11/18.
 */

public class UserInfoDo {
    private static Context appContext;
    private static UserInfo user;
    private static Gson gson;
    private static UserInfoDo userInfoDo;

    public static UserInfoDo getInstance() {
        if (userInfoDo == null)
            userInfoDo = new UserInfoDo();
        return userInfoDo;
    }

    public void init(Context context) {
        appContext = context;
        gson = new Gson();
        userInfoDo = new UserInfoDo();
    }

    public String getUid() {
        if (user == null) {
            user = SharedpreferncesUtil.getUserInfoFromDisk(appContext);
            if (user != null) {
                return user.getUserId();
            }
            return "";
        } else {
            return user.getUserId();
        }
    }

    public String getUserNick() {
        if (user != null) {
            return user.getNickName();
        } else if (SharedpreferncesUtil.getUserInfoFromDisk(appContext) != null) {
            return SharedpreferncesUtil.getUserInfoFromDisk(appContext).getNickName();
        } else {
            return null;
        }
    }

    public UserInfo getUserInfo() {
        return user;
    }

    /**
     * user can not be null
     *
     * @param user
     */
    public void setUserInfo(@NonNull UserInfo user) {
        this.user = user;
    }

    public void clearUserInfo() {
        this.user = null;
    }

    public <T extends AbstractResponse, D> void login(String uname, String pwd, int userType, Class<D> clazz, SuperService.CallBack<T> callBack) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName(uname);
        loginRequest.setPassword(pwd);
        loginRequest.setUserType(userType);
        RequestUtil.getInstance().postMethod(Api.api_login, gson.toJson(loginRequest), callBack, clazz);
    }

    public <T extends AbstractResponse> void register(RegisterRequest registerRequest, SuperService.CallBack<T> callBack) {
        RequestUtil.getInstance().postMethod(Api.api_register, gson.toJson(registerRequest), callBack, (Class<T>) RegisterResponse.class);
    }

    public <T extends AbstractResponse, D> void completeUInfo(AbstractRequest updateRequest, Class<D> clazz, SuperService.CallBack<T> callBack) {
        RequestUtil.getInstance().postMethod(Api.api_update_userinfo, gson.toJson(updateRequest), callBack, clazz);
    }

    public <T extends AbstractResponse> void loadUserInfo(GetUserInfoRequest getUserInfoRequest, SuperService.CallBack<T> callBack) {
        RequestUtil.getInstance().postMethod(Api.api_getuserinfo, gson.toJson(getUserInfoRequest), callBack, (Class<T>) GetUserInfoResponse.class);
    }

    public <T extends AbstractResponse> void getMyFollow(JsonElement requestBody, SuperService.CallBack<T> callBack) {
        RequestUtil.getInstance().postMethod(Api.api_get_myfollow, gson.toJson(requestBody), callBack, GetMyFollowResponse.class);
    }

    public <T extends AbstractResponse> void getMyLike(JsonElement requestBody, SuperService.CallBack<T> callBack) {
        RequestUtil.getInstance().postMethod(Api.api_get_mylike, gson.toJson(requestBody), callBack, GetMyPraiseResponse.class);
    }

    public <T extends AbstractResponse> void getMyComment(JsonElement requestBody, SuperService.CallBack<T> callBack) {
        RequestUtil.getInstance().postMethod(Api.api_get_mycomment, gson.toJson(requestBody), callBack, GetMyCommentResponse.class);
    }

    /**
     * 获取我发布的帖子
     *
     * @param requestBody
     * @param callBack
     * @param <T>
     */
    public <T extends AbstractResponse> void getMyNote(JsonElement requestBody, SuperService.CallBack<T> callBack) {
        RequestUtil.getInstance().postMethod(Api.api_get_mynote, gson.toJson(requestBody), callBack, GetMyPostResponse.class);
    }

    /**
     * 获取我发布的话题
     *
     * @param requestBody
     * @param callBack
     * @param <T>
     */
    public <T extends AbstractResponse> void getMyTalk(JsonElement requestBody, SuperService.CallBack<T> callBack) {
        RequestUtil.getInstance().postMethod(Api.api_get_mytopic, gson.toJson(requestBody), callBack, GetMyTopicResponse.class);
    }
}
