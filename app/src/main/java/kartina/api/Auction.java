package kartina.api;

import kartina.view.CommonStateView;

/**
 * Created by David on 2016/10/31.
 */

public interface Auction {
    /**
     * 重新加载
     */
     void onReLoad(CommonStateView view);

    /**
     * 错误页面
     * @param code
     */
     void onError(CommonStateView view,int code);

    /**
     * 成功加载
     */
     void onSuccess(CommonStateView view);

}
