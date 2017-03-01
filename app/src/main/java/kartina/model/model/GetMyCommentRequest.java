/*
 * 文件名：GetMyCommentRequest.java
 * 版权：Copyright 2007-2016 shiqinghuang  Tech. Co. Ltd. All Rights Reserved. 
 * 描述： GetMyCommentRequest.java
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
public class GetMyCommentRequest extends BasePageSelectRequest {

    private int commentType;

    /**
     * 设置commentType.
     * 
     * @return 返回commentType
     */
    public int getCommentType() {
        return commentType;
    }

    /**
     * 获取commentType.
     * 
     * @param commentType
     *            要设置的commentType
     */
    public void setCommentType(int commentType) {
        this.commentType = commentType;
    }

}
