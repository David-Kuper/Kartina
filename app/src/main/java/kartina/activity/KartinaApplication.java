package kartina.activity;

import android.app.Application;

import kartina.card.DataManager.RequestUtil;
import kartina.model.UIModel.UserInfoDo;
import kartina.msg.MessageCenter;
import kartina.util.AppUtils;

/**
 * Created by David on 2016/11/18.
 */

public class KartinaApplication extends Application {
    public static MessageCenter messageCenter ;

    @Override
    public void onCreate() {
        super.onCreate();
        RequestUtil.getInstance().init(getBaseContext());
        AppUtils.initUtils(getApplicationContext());
        UserInfoDo.getInstance().init(getApplicationContext());
    }
}
