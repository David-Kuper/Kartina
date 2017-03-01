package kartina.model.UIModel;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.reflect.TypeToken;
import com.kartina.service.model.AbstractResponse;

import kartina.card.DataManager.Api;
import kartina.card.DataManager.RequestUtil;
import kartina.card.DataManager.SuperService;
import kartina.model.model.SelectResultModel;

/**
 * Created by David on 2016/11/21.
 */

public class UserActionDo {
    Gson gson;

    public UserActionDo(){
        gson = new Gson();
    }
    public <T extends AbstractResponse> void follow(JsonElement requestBody, SuperService.CallBack<T> callBack){
        RequestUtil.getInstance().postMethod(Api.api_follow,gson.toJson(requestBody),callBack,new TypeToken<AbstractResponse<Object>>(){}.getType());
    }

    public <T extends AbstractResponse> void like(JsonElement requestBody, SuperService.CallBack<T> callBack){
        RequestUtil.getInstance().postMethod(Api.api_like,gson.toJson(requestBody),callBack,new TypeToken<AbstractResponse<Object>>(){}.getType());
    }
    /**
     * 发布评论
     * @param requestBody
     * @param callBack
     * @param <T>
     */
    public <T extends AbstractResponse> void publishComment(JsonElement requestBody, SuperService.CallBack<T> callBack){
        RequestUtil.getInstance().postMethod(Api.api_comment,gson.toJson(requestBody),callBack,new TypeToken<AbstractResponse<Object>>(){}.getType());
    }
    /**
     * 发布帖子
     * @param requestBody
     * @param callBack
     * @param <T>
     */
    public <T extends AbstractResponse> void publishNote(JsonElement requestBody, SuperService.CallBack<T> callBack){
        RequestUtil.getInstance().postMethod(Api.api_publish_note,gson.toJson(requestBody),callBack,new TypeToken<AbstractResponse<Object>>(){}.getType());
    }

    public <T extends AbstractResponse> void publish(JsonElement requestBody, SuperService.CallBack<T> callBack){
        RequestUtil.getInstance().postMethod(Api.api_publish_note,gson.toJson(requestBody),callBack,new TypeToken<AbstractResponse<Object>>(){}.getType());
    }

    /**
     * 发布话题
     * @param requestBody
     * @param callBack
     * @param <T>
     */
    public <T extends AbstractResponse> void publishTalk(JsonElement requestBody, SuperService.CallBack<T> callBack){
        RequestUtil.getInstance().postMethod(Api.api_publish_talk,gson.toJson(requestBody),callBack,new TypeToken<AbstractResponse<Object>>(){}.getType());
    }


}
