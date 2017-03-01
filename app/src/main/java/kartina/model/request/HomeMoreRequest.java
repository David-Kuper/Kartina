package kartina.model.request;

import com.kartina.service.model.AbstractRequest;

/**
 * Created by David on 2016/11/23.
 */

public class HomeMoreRequest extends AbstractRequest {
    private int moreType;

    public void setMoreType(int moreType) {
        this.moreType = moreType;
    }

    public int getMoreType() {
        return moreType;
    }
}
