/*
 * 文件名：GetPostDetailModelRequest.java
 * 版权：Copyright 2007-2016 shiqinghuang  Tech. Co. Ltd. All Rights Reserved. 
 * 描述： GetPostDetailModelRequest.java
 * 修改人：shiqinghuang
 * 修改时间：2016年11月21日
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
public class GetPostDetailModelRequest extends AbstractRequest {

    /**
     * 添加字段注释.
     */
    private String postId;

    /**
     * 设置postId.
     * 
     * @return 返回postId
     */
    public String getPostId() {
        return postId;
    }

    /**
     * 获取postId.
     * 
     * @param postId
     *            要设置的postId
     */
    public void setPostId(String postId) {
        this.postId = postId;
    }

}
