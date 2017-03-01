/*
 * 文件名：GetMyFollowRequest.java
 * 版权：Copyright 2007-2016 shiqinghuang  Tech. Co. Ltd. All Rights Reserved. 
 * 描述： GetMyFollowRequest.java
 * 修改人：shiqinghuang
 * 修改时间：2016年11月22日
 * 修改内容：新增
 */
package kartina.model.model;

import com.kartina.service.model.BasePageSelectRequest;

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
public class GetMyFollowRequest extends BasePageSelectRequest {

    /**
     * 添加字段注释.
     */
    private int followType;

    /**
     * 设置followType.
     * 
     * @return 返回followType
     */
    public int getFollowType() {
        return followType;
    }

    /**
     * 获取followType.
     * 
     * @param followType
     *            要设置的followType
     */
    public void setFollowType(int followType) {
        this.followType = followType;
    }

}
