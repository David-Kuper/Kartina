/*
 * 文件名：HotTagModel.java
 * 版权：Copyright 2007-2016 shiqinghuang  Tech. Co. Ltd. All Rights Reserved. 
 * 描述： HotTagModel.java
 * 修改人：shiqinghuang
 * 修改时间：2016年11月20日
 * 修改内容：新增
 */
package kartina.model.model;

import java.util.ArrayList;

import kartina.card.bean.Tag;

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
public class HotTagModel {

    /**
     * 添加字段注释.
     */
    private ArrayList<Tag> tagList;

    /**
     * 设置tagList.
     * 
     * @return 返回tagList
     */
    public ArrayList<Tag> getTagList() {
        return tagList;
    }

    /**
     * 获取tagList.
     * 
     * @param tagList
     *            要设置的tagList
     */
    public void setTagList(ArrayList<Tag> tagList) {
        this.tagList = tagList;
    }

}
