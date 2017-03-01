/*
 * 文件名：GetMyPraiseRequest.java
 * 版权：Copyright 2007-2016 shiqinghuang  Tech. Co. Ltd. All Rights Reserved. 
 * 描述： GetMyPraiseRequest.java
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
public class GetMyPraiseRequest extends BasePageSelectRequest {

    /**
     * 添加字段注释.
     */
    private int praiseType;

    /**
     * 设置praiseType.
     * 
     * @return 返回praiseType
     */
    public int getPraiseType() {
        return praiseType;
    }

    /**
     * 获取praiseType.
     * 
     * @param praiseType
     *            要设置的praiseType
     */
    public void setPraiseType(int praiseType) {
        this.praiseType = praiseType;
    }

}
