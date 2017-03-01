package kartina.card.DataManager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.LinkedTreeMap;
import com.kartina.service.model.AbstractResponse;

import java.lang.reflect.Type;
import java.util.Map;

import kartina.toolbox.GsonRequest;
import kartina.util.URLPath;

/**
 * Created by David on 2016/9/16.
 */

public class RequestUtil {
    private static final String TAG = "RequestUtil";
    RequestQueue volleyQueue;
    static RequestUtil instance = new RequestUtil();
    private Context context;
    private Gson gson;
    private JsonParser jsonParser;
    AbstractResponse abstractResp = new AbstractResponse<>();

    public void init(Context context) {
        this.context = context;
        gson = new Gson();
        jsonParser = new JsonParser();
        volleyQueue = Volley.newRequestQueue(context);
        volleyQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener() {
            @Override
            public void onRequestFinished(Request request) {
                Log.e("RequestUtil", " onRequestFinished!");
            }
        });
    }

    public static RequestUtil getInstance() {
        if (instance == null) {
            synchronized (instance) {
                if (instance == null) {
                    instance = new RequestUtil();
                }
            }
        }
        return instance;
    }

    public JsonElement toJsonElement(Object object){
        if (object == null)
            return null;

        return jsonParser.parse(gson.toJson(object));
    }

    public String toJsonString(Object object) {
        if (gson == null)
            return "";
        return gson.toJson(object);
    }

    public JsonElement toJsonObject(String key,String value){
        JsonPrimitive jsonPri = new JsonPrimitive(value);
        JsonObject jsonObject = new JsonObject();
        jsonObject.add(key,jsonPri);
        return jsonObject;
    }


    public <T> T fromJson(String jsonStr, Class<T> typeOfstr){
        if (gson == null)
            return  null;
        return gson.fromJson(jsonStr,typeOfstr);
    }

    public <T> T fromJsonMap(LinkedTreeMap map, Class<T> typeOfstr){
        if (gson == null)
            return  null;
        Log.e(TAG,"jsonSrc = " + gson.toJson(map));
        return gson.fromJson(gson.toJson(map),typeOfstr);
    }


    public synchronized <T> void getMethod(@NonNull Api api, String URLparams, @NonNull final SuperService.CallBack callBack, Class<T> clazz) {
        Log.e(TAG, "URL = " + URLPath.urlRelative + api.apiName + "\nmethod = " + Request.Method.GET);
        volleyQueue.add(new GsonRequest<T>(Request.Method.GET, URLPath.urlRelative + api.apiName + URLparams, clazz, null, new Response.Listener<T>() {
            @Override
            public void onResponse(T response) {
                Log.e("RequestUtil", " onResponse!");
                if (response != null) {
                    if (response instanceof Map) {

                    } else {
                        callBack.OnSucess(new ResponseParameter<T>(response));
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RequestUtil", " onErrorResponse!");
                if (error instanceof NetworkError) {
                    Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
                    callBack.OnError(new ResponseParameter<VolleyError>(error));
                } else if (error instanceof ClientError) {
                    Toast.makeText(context, "客户端异常", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(context, "服务端异常", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(context, "代理机构异常", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(context, "解析异常", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(context, "无网络链接，请检查您的网络", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(context, "请求超时", Toast.LENGTH_SHORT).show();
                }
            }
        }).setRetryPolicy(new DefaultRetryPolicy(2500, 2,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));

    }

    public synchronized <T extends AbstractResponse> void postMethod(@NonNull Api api, final String requestBody, @NonNull final SuperService.CallBack<T> callBack, Class<T> clazz) {
        Log.e(TAG, "URL = " + URLPath.urlRelative + api.apiName + "\nmethod = " + Request.Method.POST + "\n jsonBody = " + requestBody);
        volleyQueue.add(new GsonRequest<T>(Request.Method.POST, URLPath.urlRelative + api.apiName, clazz, requestBody, new Response.Listener<T>() {
            @Override
            public void onResponse(T response) {
                Log.e("RequestUtil", " onResponse!" + response.getClass().getName());
                Log.e("RequestUtil", "jsonString = " + gson.toJson(response));
                if (response != null)
                    callBack.OnSucess(new ResponseParameter<T>(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RequestUtil", " onErrorResponse!" + error.toString());
                callBack.OnError(new ResponseParameter<VolleyError>(error));
                if (error instanceof NetworkError) {
                    Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ClientError) {
                    Toast.makeText(context, "客户端异常", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(context, "服务端异常", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(context, "代理机构异常", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(context, "解析异常", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(context, "无网络链接，请检查您的网络", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(context, "请求超时", Toast.LENGTH_SHORT).show();
                }
            }
        }).setRetryPolicy(new DefaultRetryPolicy(2500, 2,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));
    }

    public synchronized <T extends AbstractResponse> void postMethod(@NonNull Api api, final String requestBody, @NonNull final SuperService.CallBack<T> callBack, Type type) {
        Log.e(TAG, "URL = " + URLPath.urlRelative + api.apiName + "\nmethod = " + Request.Method.POST + "\n jsonBody = " + requestBody);
        volleyQueue.add(new GsonRequest<T>(Request.Method.POST, URLPath.urlRelative + api.apiName, type, requestBody, new Response.Listener<T>() {
            @Override
            public void onResponse(T response) {
                Log.e("RequestUtil", " onResponse!" + response.getClass().getName());
                Log.e("RequestUtil", "jsonString = " + gson.toJson(response));
                if (response != null)
                    callBack.OnSucess(new ResponseParameter<T>(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RequestUtil", " onErrorResponse!" + error.toString());
                callBack.OnError(new ResponseParameter<VolleyError>(error));
                if (error instanceof NetworkError) {
                    Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ClientError) {
                    Toast.makeText(context, "客户端异常", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(context, "服务端异常", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(context, "代理机构异常", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(context, "解析异常", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(context, "无网络链接，请检查您的网络", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(context, "请求超时", Toast.LENGTH_SHORT).show();
                }
            }
        }).setRetryPolicy(new DefaultRetryPolicy(2500, 2,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));
    }

    private synchronized <T> RequestUtil excute() {
        volleyQueue.start();
        return instance;
    }
}
