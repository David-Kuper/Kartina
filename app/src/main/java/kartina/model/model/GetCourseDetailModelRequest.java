/*
 * 文件名：GetCourseDetailModelRequest.java
 * 版权：Copyright 2007-2016 shiqinghuang  Tech. Co. Ltd. All Rights Reserved. 
 * 描述： GetCourseDetailModelRequest.java
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
public class GetCourseDetailModelRequest extends AbstractRequest {

    /**
     * 添加字段注释.
     */
    private String courseId;

    /**
     * 设置courseId.
     * 
     * @return 返回courseId
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * 获取courseId.
     * 
     * @param courseId
     *            要设置的courseId
     */
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

}
