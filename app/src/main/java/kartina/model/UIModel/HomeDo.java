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
import kartina.model.model.HomeModel;
import kartina.model.model.HomeMoreModel;

/**
 * Created by David on 2016/11/20.
 */

public class HomeDo {
    private HomeModel homeModel;
    private Gson gson;

    public void setHomeModel(HomeModel homeModel ){
        this.homeModel = homeModel;
    }

    public HomeModel getHomeModel() {
        return homeModel;
    }

    public HomeDo(){
        gson = new Gson();
    }
    public <T extends AbstractResponse> void getHomeData(JsonElement requestBody, SuperService.CallBack<T> callBack){
        RequestUtil.getInstance().postMethod(Api.api_get_home,gson.toJson(requestBody),callBack, new TypeToken<AbstractResponse<HomeModel>>() {
        }.getType());
    }
    public <T extends AbstractResponse> void getMore(JsonElement requestBody, SuperService.CallBack<T> callBack){
        RequestUtil.getInstance().postMethod(Api.api_get_homemore,gson.toJson(requestBody),callBack, new TypeToken<AbstractResponse<HomeMoreModel>>() {
        }.getType());
    }
}
