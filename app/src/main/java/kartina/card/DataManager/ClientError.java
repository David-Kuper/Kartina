package kartina.card.DataManager;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;

/**
 * Created by David on 2016/11/19.
 */
public class ClientError extends VolleyError{
    public ClientError(NetworkResponse networkResponse) {
        super(networkResponse);
    }

    public ClientError() {
    }
}
