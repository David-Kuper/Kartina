/*
 * 文件名：CourseClassifyModel.java
 * 版权：Copyright 2007-2016 shiqinghuang  Tech. Co. Ltd. All Rights Reserved. 
 * 描述： CourseClassifyModel.java
 * 修改人：shiqinghuang
 * 修改时间：2016年11月20日
 * 修改内容：新增
 */
package kartina.model.model;

import java.util.ArrayList;

import kartina.card.bean.CardBean;

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
public class CourseClassifyModel {

    /**
     * 添加字段注释.
     */
    private ArrayList<CardBean> cardList;

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
