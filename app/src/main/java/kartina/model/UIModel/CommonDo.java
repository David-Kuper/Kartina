package kartina.model.UIModel;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.kartina.service.model.AbstractResponse;

import java.util.ArrayList;

import kartina.card.DataManager.Api;
import kartina.card.DataManager.RequestUtil;
import kartina.card.DataManager.SuperService;
import kartina.card.bean.CardBean;
import kartina.model.model.CourseDetailModel;
import kartina.model.model.TeacherDetailModel;

/**
 * Created by David on 2016/11/21.
 */

public class CommonDo {
    Gson gson;
    public CommonDo(){
        gson = new Gson();
    }

    /**
     * 更多评论
     * @param requestBody
     * @param callBack
     * @param <T>
     */
    public <T extends AbstractResponse> void getMoreComment(JsonElement requestBody, SuperService.CallBack<T> callBack){
        RequestUtil.getInstance().postMethod(Api.api_get_mortcomment,gson.toJson(requestBody),callBack,new TypeToken<AbstractResponse<ArrayList<CardBean>>>(){}.getType());
    }
    /**
     * 更多帖子
     * @param requestBody
     * @param callBack
     * @param <T>
     */
    public <T extends AbstractResponse> void getMoreNote(JsonElement requestBody, SuperService.CallBack<T> callBack){
        RequestUtil.getInstance().postMethod(Api.api_get_mornote,gson.toJson(requestBody),callBack,new TypeToken<AbstractResponse<ArrayList<CardBean>>>(){}.getType());
    }
    /**
     * 更多话题
     * @param requestBody
     * @param callBack
     * @param <T>
     */
    public <T extends AbstractResponse> void getMoreTalk(JsonElement requestBody, SuperService.CallBack<T> callBack){
        RequestUtil.getInstance().postMethod(Api.api_get_mortalk,gson.toJson(requestBody),callBack,new TypeToken<AbstractResponse<ArrayList<CardBean>>>(){}.getType());
    }
}
