package kartina.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;

import java.io.File;
import java.io.FileFilter;
import java.util.List;
import java.util.regex.Pattern;

import kartina.activity.LoginActivity;
import kartina.model.UIModel.UserInfoDo;

/**
 * Created by David on 2016/11/18.
 */

public class AppUtils {
    private static final String PICASSO_CACHE = "picasso-cache";
    static Context mApplicationContext;
    public static void initUtils(Context applicationContext){
        mApplicationContext = applicationContext;
    }
    public static boolean isLogin(Activity activity){
        if (UserInfoDo.getInstance().getUserInfo() == null && SharedpreferncesUtil.getUserInfoFromDisk(mApplicationContext) == null){
            toLogin(activity);
            return  false;
        }else{
            if (UserInfoDo.getInstance().getUserInfo() == null){
                UserInfoDo.getInstance().setUserInfo(SharedpreferncesUtil.getUserInfoFromDisk(mApplicationContext));
            }
            return true;
        }
    }



    public static void toLogin(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    /** 是否开启debug模式 */
    public static boolean debug = true;

    /**
     * 判断app是否在后台运行
     *
     * @param context
     * @return
     */
    public static boolean isBackgroundRunning(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public static File createDefaultCacheDir(Context context) {
        File cache = new File(context.getApplicationContext().getCacheDir(),PICASSO_CACHE);
        if (!cache.exists()) {
            cache.mkdirs();
        }
        return cache;
    }
    /**
     * 设置是否开启debug模式
     * */
    public static void setDebug(boolean debug){
        AppUtils.debug = debug;
    }

    /**
     * 判断网络是否已经连接
     * @param context 上下文
     * @return true 已经连接网络， false 网络连接失效
     * */
    public static boolean isNetworkAvailable(Context context){
        try{
            ConnectivityManager cm = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if(cm != null){
                NetworkInfo ni = cm.getActiveNetworkInfo();
                if(ni!=null && ni.isConnected()){
                    if(ni.getState() == NetworkInfo.State.CONNECTED){
                        return true;
                    }
                }
            }
        } catch (Exception e){
            return false;
        }
        return false;
    }

    /**
     * 判断当前网络是否是WIFI
     * */
    public static boolean isWIFI(Context context){
        try{
            ConnectivityManager cm = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if(cm != null){
                NetworkInfo ni = cm.getActiveNetworkInfo();
                if(ni!=null && ni.getType() == ConnectivityManager.TYPE_WIFI){
                    return true;
                }
            }
        } catch (Exception e){
            return false;
        }
        return false;
    }

    /**
     * 判断当前网络是否是GPRS
     * */
    public static boolean is3G(Context context){
        try{
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if(cm != null){
                NetworkInfo ni = cm.getActiveNetworkInfo();
                if(ni!=null && ni.getType() == ConnectivityManager.TYPE_MOBILE){
                    return true;
                }
            }
        } catch (Exception e){

        }
        return false;
    }

    /**
     * 获取手机分辨率--W
     * */
    public static int getPhoneHW(Context context){
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int disW = dm.widthPixels;
        return disW;
    }


    /**
     * 获取手机分辨率--H
     * */
    public static int getPhoneWH(Context context){
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int disH = dm.heightPixels;
        return disH;
    }

    /**
     * 获取CPU数量
     * */
    public static int cpuNums(){
        int nums = 1;
        try{
            File file = new File("/sys/devices/system/cpu/");
            File[] files = file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File arg0) {
                    if(Pattern.matches("cpu[0-9]", arg0.getName())){
                        return true;
                    }
                    return false;
                }
            });
            nums = files.length;
        } catch (Exception e){
            e.printStackTrace();
        }
        return nums;
    }

    /**
     * 获取当前sdk版本
     * @return
     */
    public static int getAndroidSDKVersion() {
        int version = 0;
        try {
            version = Integer.valueOf(android.os.Build.VERSION.SDK_INT);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return version;
    }

}
