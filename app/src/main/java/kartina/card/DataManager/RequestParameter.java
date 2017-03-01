package kartina.card.DataManager;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by David on 2016/9/16.
 */

public class RequestParameter implements Serializable {
    int token;
    int key;
    Object extraData;

    public void setPageInfo(PageInfo pageInfo){
         extraData = pageInfo;
    }
}
