/*
 * 文件名：GetTopicDetailModelRequest.java
 * 版权：Copyright 2007-2016 shiqinghuang  Tech. Co. Ltd. All Rights Reserved. 
 * 描述： GetTopicDetailModelRequest.java
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
public class GetTopicDetailModelRequest extends AbstractRequest {

    /**
     * 添加字段注释.
     */
    private String topicId;

    /**
     * 设置topicId.
     * 
     * @return 返回topicId
     */
    public String getTopicId() {
        return topicId;
    }

    /**
     * 获取topicId.
     * 
     * @param topicId
     *            要设置的topicId
     */
    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

}
