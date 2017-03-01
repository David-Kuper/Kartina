/*
 * 文件名：PublishRequest.java
 * 版权：Copyright 2007-2016 shiqinghuang  Tech. Co. Ltd. All Rights Reserved. 
 * 描述： PublishRequest.java
 * 修改人：shiqinghuang
 * 修改时间：2016年11月22日
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
public class PublishRequest extends AbstractRequest {

    /**
     * 添加字段注释.
     */
    int publishType;

    /**
     * 帖子对应的课程、话题ID.
     */
    String scopeId;

    /**
     * 标签id
     */
    String tagId;

    /**
     * 发帖人名称.
     * 
     */
    String userName;

    /**
     * 添加字段注释.
     */
    String title;

    /**
     * 添加字段注释.
     */
    String content;

    /**
     * 设置publishType.
     * 
     * @return 返回publishType
     */
    public int getPublishType() {
        return publishType;
    }

    /**
     * 获取publishType.
     * 
     * @param publishType
     *            要设置的publishType
     */
    public void setPublishType(int publishType) {
        this.publishType = publishType;
    }

    /**
     * 设置scopeId.
     * 
     * @return 返回scopeId
     */
    public String getScopeId() {
        return scopeId;
    }

    /**
     * 获取scopeId.
     * 
     * @param scopeId
     *            要设置的scopeId
     */
    public void setScopeId(String scopeId) {
        this.scopeId = scopeId;
    }

    /**
     * 设置userName.
     * 
     * @return 返回userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 获取userName.
     * 
     * @param userName
     *            要设置的userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 设置title.
     * 
     * @return 返回title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 获取title.
     * 
     * @param title
     *            要设置的title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 设置content.
     * 
     * @return 返回content
     */
    public String getContent() {
        return content;
    }

    /**
     * 获取content.
     * 
     * @param content
     *            要设置的content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取tagId.
     * 
     * @param tagId
     *            要设置的tagId
     */
    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    /**
     * 设置tagId.
     * 
     * @return 返回tagId
     */
    public String getTagId() {
        return tagId;
    }

}
