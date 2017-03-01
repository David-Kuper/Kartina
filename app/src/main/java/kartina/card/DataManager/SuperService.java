package kartina.card.DataManager;

import com.kartina.service.model.AbstractResponse;

/**
 * Created by David on 2016/9/16.
 */

public interface SuperService {

    void setAPI(Api api);

    void setRequestParameter(RequestParameter parameter);

    <D> void setResponseParameter(ResponseParameter<D> parameter);

    void request(CallBack callBack);

    public interface CallBack<T extends AbstractResponse> {
        void OnSucess(ResponseParameter<T> response);

        <D> void OnError(ResponseParameter<D> response);

        void OnProcess(ResponseParameter<T> response, int curValue, int totalValue);
    }
}
