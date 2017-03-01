package kartina.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Base64;


import com.kartina.model.User;
import com.kartina.service.model.user.UserInfo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import kartina.card.bean.Tag;


/**
 * 缓存工具类
 * 
 * @author zhou.ni 2015年5月13日
 */
public class SharedpreferncesUtil {
	
public static final String PREFERNCE_FILE_NAME = "obj"; // 缓存文件名
	
	public static final String USER_GUIDE_FILE_NAME = "guide";   //引导界面文件名
	
	//-----key-----
	public static final String KEY_USER_INFO = "user";
	public static final String USER_FILE_NAME = "user";
	public static final String SEARCH_LIST_FILENAME = "searchList";
	//----collection----
	public static final String KEY_COLLECTION_INFO = "collection";  //收藏文件名
	
	/**
	 * 保存用户信息
	 * @param context
	 * @param user
	 */
//	public static void saveUserInfo(Context context, UserInfo user){
//		saveObj(context, user, KEY_USER_INFO);
//	}
    public static UserInfo getUserInfoFromDisk(Context context) {
		ObjectInputStream inputStream = null;
		UserInfo userInfo = null;
		try {
			inputStream = new ObjectInputStream(context.openFileInput(USER_FILE_NAME));
			userInfo = (UserInfo) inputStream.readObject();
			return userInfo;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return userInfo;
	}
	public static void saveUserInfoToDisk(Context context,UserInfo userInfo){
		ObjectOutputStream outputStream = null;
		try {
			outputStream = new ObjectOutputStream(context.openFileOutput(USER_FILE_NAME, Context.MODE_PRIVATE));
			outputStream.writeObject(userInfo);
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
            if (outputStream != null){
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static ArrayList<Tag> getSearchListFromDisk(Context context) {
		ObjectInputStream inputStream = null;
		ArrayList<Tag> searchList = null;
		try {
			inputStream = new ObjectInputStream(context.openFileInput(SEARCH_LIST_FILENAME));
			searchList = (ArrayList<Tag>) inputStream.readObject();
			return searchList;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return searchList;
	}

	public static void saveSearchedListToDisk(Context context,ArrayList<Tag> searchList){
		ObjectOutputStream outputStream = null;
		try {
			outputStream = new ObjectOutputStream(context.openFileOutput(SEARCH_LIST_FILENAME, Context.MODE_PRIVATE));
			outputStream.writeObject(searchList);
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (outputStream != null){
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 取出用户信息
	 * @param context
	 * @return
	 */
//	public static UserInfo getUserInfo(Context context){
//		return (UserInfo) readObj(context, KEY_USER_INFO);
//	}

	/**
	 * @param context
	 * @return
	 */
	public static Object readObj(Context context, String key) {
		Object obj = null;
		SharedPreferences prefe = context.getSharedPreferences(PREFERNCE_FILE_NAME, 0);
		String replysBase64 = prefe.getString(key, "");
		if (TextUtils.isEmpty(replysBase64)) {
			return obj;
		}
		// 读取字节
		byte[] base64 = Base64.decode(replysBase64.getBytes(),Base64.DEFAULT);
		// 封装到字节读取流
		ByteArrayInputStream bais = new ByteArrayInputStream(base64);
		try {
			// 封装到对象读取流
			ObjectInputStream ois = new ObjectInputStream(bais);
			try {
				// 读取对象
				obj = ois.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return obj;
	}

	/**
	 * 存储一个对象
	 * 
	 * @param context
	 * @param
	 * @param key
	 */
	public static <T> void saveObj(Context context, T obj, String key) {
		T _obj = obj;

		SharedPreferences prefe = context.getSharedPreferences(PREFERNCE_FILE_NAME, 0);
		// 创建字节输出流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			// 创建对象输出流,封装字节流
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			// 将对象写入字节流
			oos.writeObject(_obj);
			// 将字节流编码成base64的字符串
			String list_base64 = new String(Base64.encode(baos.toByteArray(),Base64.DEFAULT));
			Editor editor = prefe.edit();
			editor.putString(key, list_base64);
			editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存收藏信息
	 * @param context
	 * @param
	 */
	public static void saveCollectionInfo(Context context, List<Object> list){
		saveObj(context, list, KEY_COLLECTION_INFO);
	}
	
	/**
	 * 取出收藏信息
	 * @param context
	 * @return
	 */
	public static List<Object> getCollectionInfo(Context context){
		return (List<Object>) readObj(context, KEY_COLLECTION_INFO);
	}
	
	/**
	 * 清除某个key对应的缓存
	 * 
	 * @param key
	 * @param context
	 */
	public static void clearByKey(String key, Context context) {
		SharedPreferences prefe = context.getSharedPreferences(PREFERNCE_FILE_NAME, 0);
		Editor editor = prefe.edit();
		editor.putString(key, "");
		editor.commit();
	}

	public static void clearDiskByKey(String key,Context context){
		ObjectOutputStream outputStream = null;
		try {
			outputStream = new ObjectOutputStream(context.openFileOutput(key,Context.MODE_PRIVATE));
			outputStream.write(" ".getBytes());
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (outputStream != null){
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 设置进入过引导界面
	 * 
	 * @param context
	 */
	public static void setGuided(Context context) {
		SharedPreferences prefe = context.getSharedPreferences(USER_GUIDE_FILE_NAME, 0);
		Editor editor = prefe.edit();
		editor.putBoolean("isguide", true);
		editor.commit();
	}

	/**
	 * 判断是否进入引导界面
	 * 
	 * @param context
	 * @return
	 */
	public static boolean getGuided(Context context) {
		SharedPreferences prefe = context.getSharedPreferences(USER_GUIDE_FILE_NAME, 0);
		boolean b = prefe.getBoolean("isguide", false);
		return b;
	}
	
	public static boolean getReadMode(Context context, String key, boolean defValue) {
		SharedPreferences prefe = context.getSharedPreferences(PREFERNCE_FILE_NAME, Context.MODE_PRIVATE);
		return prefe.getBoolean(key, defValue);
	}
	
	public static void putReadMode(Context context, String key, boolean state) {
		SharedPreferences prefe = context.getSharedPreferences(PREFERNCE_FILE_NAME, Context.MODE_PRIVATE);
		Editor editor = prefe.edit();
		editor.putBoolean(key, state);
		editor.commit();
	}
	
	public static int getFontSize(Context context, String key, int defValue) {
		SharedPreferences prefe = context.getSharedPreferences(PREFERNCE_FILE_NAME, Context.MODE_PRIVATE);
		return prefe.getInt(key, defValue);
	}
	
	public static void putFontSize(Context context, String key, int state) {
		SharedPreferences prefe = context.getSharedPreferences(PREFERNCE_FILE_NAME, Context.MODE_PRIVATE);
		Editor editor = prefe.edit();
		editor.putInt(key, state);
		editor.commit();
	}


}
