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
public class GetTeacherDetailModelRequest extends AbstractRequest {

    /**
     * 添加字段注释.
     */
    private String teacherUserId;

    /**
     * 设置teacherUserId.
     * 
     * @return 返回teacherUserId
     */
    public String getTeacherUserId() {
        return teacherUserId;
    }

    /**
     * 获取teacherUserId.
     * 
     * @param teacherUserId
     *            要设置的teacherUserId
     */
    public void setTeacherUserId(String teacherUserId) {
        this.teacherUserId = teacherUserId;
    }

}
