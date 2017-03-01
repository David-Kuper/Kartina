package kartina.card.DataManager;

import com.android.volley.VolleyError;

/**
 * Created by David on 2016/9/16.
 */

public class ResponseParameter<T> {
    public T object;
    public VolleyError error;
    public ResponseParameter(T object){
        this.object = object;
    }
}
