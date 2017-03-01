/*
 * 文件名：CourseDetailModel.java
 * 版权：Copyright 2007-2016 shiqinghuang  Tech. Co. Ltd. All Rights Reserved. 
 * 描述： CourseDetailModel.java
 * 修改人：shiqinghuang
 * 修改时间：2016年11月20日
 * 修改内容：新增
 */
package kartina.model.model;

import java.util.ArrayList;

import kartina.card.bean.CardBean;
import kartina.card.bean.ClassDetailHeadBean;

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
public class CourseDetailModel {

    /**
     * 添加字段注释.
     */
    private ClassDetailHeadBean classDetailHeadBean;

    /**
     * 添加字段注释.
     */
    private ArrayList<CardBean> cardList;

    /**
     * 设置classDetailHeadBean.
     * 
     * @return 返回classDetailHeadBean
     */
    public ClassDetailHeadBean getClassDetailHeadBean() {
        return classDetailHeadBean;
    }

    /**
     * 获取classDetailHeadBean.
     * 
     * @param classDetailHeadBean
     *            要设置的classDetailHeadBean
     */
    public void setClassDetailHeadBean(ClassDetailHeadBean classDetailHeadBean) {
        this.classDetailHeadBean = classDetailHeadBean;
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
