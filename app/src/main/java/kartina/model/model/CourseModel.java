/*
 * 文件名：CourseModel.java
 * 版权：Copyright 2007-2016 shiqinghuang  Tech. Co. Ltd. All Rights Reserved. 
 * 描述： CourseModel.java
 * 修改人：shiqinghuang
 * 修改时间：2016年11月20日
 * 修改内容：新增
 */
package kartina.model.model;

import java.util.ArrayList;

import kartina.card.bean.CardBean;
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
public class CourseModel {

    /**
     * 添加字段注释.
     */
    ArrayList<Tag> tagList;

    /**
     * 添加字段注释.
     */
    ArrayList<CardBean> cardList;

    /**
     * 添加字段注释.
     */
    Tag<Boolean> hasNext;

    /**
     * 获取hasNext.
     * 
     * @param hasNext
     *            要设置的hasNext
     */
    public void setHasNext(Tag<Boolean> hasNext) {
        this.hasNext = hasNext;
    }

    /**
     * 设置hasNext.
     * 
     * @return 返回hasNext
     */
    public Tag<Boolean> getHasNext() {
        return hasNext;
    }

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

    /**
     * 设置cardList.
     * 
     * @return 返回cardList
     */
    public ArrayList<CardBean> getCardList() {
        return cardList;
    }

    /**
     * 获取cardList.
     * 
     * @param cardList
     *            要设置的cardList
     */
    public void setCardList(ArrayList<CardBean> cardList) {
        this.cardList = cardList;
    }

}
