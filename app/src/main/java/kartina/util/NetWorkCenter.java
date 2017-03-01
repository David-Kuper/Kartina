/**
 * 
 */
package kartina.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import kartina.api.RetryNetwork;


/**
 * @author David
 * Created by David on 2016/12/18.
 */
public class NetWorkCenter implements RetryNetwork {
	public NetWorkReceiver netWorkReceiver ;
	public Context context;
	
	public NetWorkCenter(Context context){
		this.context = context;
	}
	public NetWorkReceiver getNetWorkReceiver(){
		if(netWorkReceiver == null){
			netWorkReceiver = new NetWorkReceiver();
		}
		return netWorkReceiver;
	}
	/**
	 * 判断当前网络是否是wifi网络
	 * 
	 * @param context
	 * @return boolean
	 */
	public static boolean isWifi(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null
				&& activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}

	/**
	 * 判断当前网络是否是3G网络
	 * 
	 * @param context
	 * @return boolean
	 */
	public static boolean is3G(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null
				&& activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否有网络链接
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}	
	
	@Override
	public void retry() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void netError() {
		// TODO Auto-generated method stub
		
	}

}
