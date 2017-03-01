package kartina.card.DataManager;

import android.app.Activity;
import android.os.Looper;
import android.util.Log;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kartina.card.ViewType.CardViewType;
import kartina.card.bean.CardBean;

/**
 * Created by David on 2016/11/20.
 */

public class CardProcess {
    public static ExecutorService threadPool = Executors.newFixedThreadPool(3);

    public static <T> void parseCardList(final List<CardBean> cardBeanList, final CallBack<T> callBack, final Activity activity) {
        threadPool.submit(new Runnable() {
            @Override
            public void run() {
                Log.e("CardProcess ", "cardBeanList size = " + cardBeanList.size());
                for (CardBean cardBean : cardBeanList) {
                    Log.e("CardProcess ", "type = " + cardBean.cardViewType + "       cardBean " + cardBean.data.getClass());
                    Class clazz = CardViewType.getCardViewByType(cardBean.cardViewType).beanClazz;
                    Log.e("CardProcess ", clazz.getName());
                    if (cardBean.data instanceof LinkedTreeMap) {
                        cardBean.data = RequestUtil.getInstance().fromJsonMap((LinkedTreeMap) cardBean.data, clazz);
                        Log.e("CardProcess Map", cardBean.data.getClass().getName());
                    } else {
                        Log.e("CardProcess aready ", cardBean.data.getClass().getName());
                    }
                }
                if (Thread.currentThread() == Looper.getMainLooper().getThread()){
                    Log.e("CardProcess","direct call");
                    callBack.parseCardSuccess((T) cardBeanList);
                }else {
                    Log.e("CardProcess","post runOnUiThread");
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callBack.parseCardSuccess((T) cardBeanList);
                        }
                    });
                }
            }
        });
    }

    public interface CallBack<T> {
        void parseCardSuccess(T list);
    }
}
