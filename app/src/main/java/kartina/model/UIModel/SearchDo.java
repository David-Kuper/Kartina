package kartina.model.UIModel;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.kartina.service.app.model.GetHotTagModelResponse;
import com.kartina.service.model.AbstractResponse;

import kartina.card.DataManager.Api;
import kartina.card.DataManager.RequestUtil;
import kartina.card.DataManager.SuperService;
import kartina.model.model.HotTagModel;
import kartina.model.model.SelectResultModel;

/**
 * Created by David on 2016/11/21.
 */

public class SearchDo {
    Gson gson;
    public SearchDo(){
        gson = new Gson();
    }

    public <T extends AbstractResponse> void searchKeyWord(JsonElement requestBody, SuperService.CallBack<T> callBack){
        RequestUtil.getInstance().postMethod(Api.api_searchby_keyword,gson.toJson(requestBody),callBack,new TypeToken<AbstractResponse<SelectResultModel>>(){}.getType());
    }

    public <T extends AbstractResponse> void getHotTag(JsonElement requestBody, SuperService.CallBack<T> callBack){
        RequestUtil.getInstance().postMethod(Api.api_get_hottag,gson.toJson(requestBody),callBack, GetHotTagModelResponse.class);
    }
}
