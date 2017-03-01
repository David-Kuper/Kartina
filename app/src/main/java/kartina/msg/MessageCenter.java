package kartina.msg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by David on 2016/11/18.
 */

public class MessageCenter {

    private static HashMap<String,List<Observer>> observerMap = new HashMap<>();

    public static Observer unRegisterMsgObserver(String msg,Observer observer){
        if (observer == null)
            return null;
        if (observerMap.containsKey(msg)){
            List<Observer> observerList = observerMap.get(msg);
            if (observerList != null && observerList.contains(observer)){
                observerList.remove(observer);
            }
        }
        return observer;
    }

    public static Observer registerMsgObserver(String msg,Observer observer){
        if (observer == null)
            return null;
         if (observerMap.containsKey(msg)){
             List<Observer> observerList = observerMap.get(msg);
             if (observerList == null){
                 observerList = new ArrayList<>();
             }
             observerList.add(observer);
         }else {
             List<Observer> observerList = new ArrayList<>();
             observerList.add(observer);
             observerMap.put(msg,observerList);
         }
        return observer;
    }

    public static<D> void postMsgNotify(String msg,Notification<D> data){
         List<Observer> observerList = observerMap.get(msg);
        if (observerList != null && observerList.size() > 0){
            Observer observer;
            for(int i = 0; i < observerList.size(); i++){
                observer = observerList.get(i);
                observer.onMessageCall(msg,data);
            }
        }
    }

    public interface Observer{
        void onMessageCall(String msg,Notification data);
    }

    public static class Notification<T>{
        public T data;
    }
}
