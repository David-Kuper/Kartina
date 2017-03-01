/*
 * 文件名：getHomeMoreRequest.java
 * 版权：Copyright 2007-2016 shiqinghuang  Tech. Co. Ltd. All Rights Reserved. 
 * 描述： getHomeMoreRequest.java
 * 修改人：shiqinghuang
 * 修改时间：2016年11月23日
 * 修改内容：新增
 */
package kartina.model.model;

import com.kartina.service.model.AbstractRequest;

/**
 * TODO 添加类的一句话简单描述.
 * <p>
 * TODO 详细描述
 * <p>
 * TODO 示例代码
 * 
 * <pre>
 * </pre>
 * 
 * @author shiqinghuang
 */
public class GetHomeMoreRequest extends AbstractRequest {

    /**
     * 更多类型 1.课程 2.话题 3.老师.
     */
    private int moreType;

    /**
     * 设置moreType.
     * 
     * @return 返回moreType
     */
    public int getMoreType() {
        return moreType;
    }

    /**
     * 获取moreType.
     * 
     * @param moreType
     *            要设置的moreType
     */
    public void setMoreType(int moreType) {
        this.moreType = moreType;
    }

}
